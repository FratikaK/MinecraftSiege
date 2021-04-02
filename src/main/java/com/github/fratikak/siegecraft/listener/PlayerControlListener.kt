package com.github.fratikak.siegecraft.listener

import com.github.fratikak.siegecraft.SiegeManager
import com.github.fratikak.siegecraft.task.RecoveryStaminaTask
import com.github.fratikak.siegecraft.task.SpectatorTask
import com.github.fratikak.siegecraft.task.SprintControlTask
import org.bukkit.ChatColor
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.FoodLevelChangeEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerRespawnEvent
import org.bukkit.event.player.PlayerToggleSprintEvent
import org.bukkit.plugin.Plugin

class PlayerControlListener(private val plugin: Plugin) : Listener {

    private val manager = SiegeManager

    /**
     * 試合中でない場合はダメージを受けません
     * voidダメージを受けた場合は初期スポーン地点へ戻ります
     */
    @EventHandler
    fun onPlayerDamage(e: EntityDamageEvent) {
        //プレイヤーじゃなければreturn
        if (e.entity.type != EntityType.PLAYER) {
            return
        }

        val player = e.entity as Player
        if (!manager.isMatching) {
            e.isCancelled = true
        }
        //voidダメージならば初期スポーンへ
        if (e.cause == EntityDamageEvent.DamageCause.VOID) {
            if (!manager.gamePlayers.contains(player)) {
                e.entity.teleport(e.entity.world.spawnLocation)
            }
        }
    }

    private var deathLocation :Location? = null
    @EventHandler
    fun onPlayerDeath(e: PlayerDeathEvent) {
        deathLocation = e.entity.location
    }

    /**
     * プレイヤーがリスポーンする場合の処理をします
     */
    @EventHandler
    fun onPlayerRespawn(e: PlayerRespawnEvent) {
        val player = e.player
        //試合中であれば復帰処理、試合中でなければロビーへ
        if (manager.isMatching) {
            e.respawnLocation = deathLocation!!
            player.sendTitle(
                "${ChatColor.RED}You are Dead!", "${ChatColor.WHITE}10秒後に復活します",
                20, 100, 20
            )
            player.gameMode = GameMode.SPECTATOR
            //どちらのチームに所属しているかで復帰場所を変える
            if (manager.blueTeam.contains(player)) {
                SpectatorTask(player, true).runTaskTimer(plugin, 0, 20)
            } else if (manager.redTeam.contains(player)) {
                SpectatorTask(player, false).runTaskTimer(plugin, 0, 20)
            }
        } else {
            e.respawnLocation = player.world.spawnLocation
        }
    }

    /**
     * プレイヤーが走ったり、歩いたりでスタミナが増減します
     */
    @EventHandler
    fun onStaminaControl(e: PlayerToggleSprintEvent) {
        if (e.isSprinting) {
            SprintControlTask(e.player).runTaskTimer(plugin, 0, 10)
        } else {
            RecoveryStaminaTask(e.player).runTaskTimer(plugin, 0, 5)
        }
    }

    /**
     * 通常の食料ゲージ減少はキャンセルします
     */
    @EventHandler
    fun onFoodLevel(e: FoodLevelChangeEvent) {
        e.isCancelled = true
    }
}