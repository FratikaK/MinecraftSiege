package com.github.fratikak.siegecraft.listener

import com.github.fratikak.siegecraft.SiegeManager
import com.github.fratikak.siegecraft.task.RecoveryStaminaTask
import com.github.fratikak.siegecraft.task.SprintControlTask
import com.github.fratikak.siegecraft.util.GameStage
import org.bukkit.ChatColor
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
        if (manager.isMatching) {
            e.isCancelled = true
        }
        //voidダメージならば初期スポーンへ
        if (e.cause == EntityDamageEvent.DamageCause.VOID) {
            if (!manager.gamePlayers.contains(player)) {
                e.entity.teleport(e.entity.world.spawnLocation)
            }
        }
    }

    @EventHandler
    fun onPlayerDeath(e: PlayerDeathEvent) {
        //TODO 死亡メッセージの編集
    }

    @EventHandler
    fun onPlayerRespawn(e: PlayerRespawnEvent) {
        val player = e.player
        //TODO 観戦Task実行
        if (manager.isMatching) {
            if (manager.blueTeam.contains(player)) {
                e.respawnLocation = manager.blueLocation!!
            }else if (manager.redTeam.contains(player)){
                e.respawnLocation = manager.redLocation!!
            }
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