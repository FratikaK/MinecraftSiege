package com.github.fratikak.siegecraft.listener

import com.github.fratikak.siegecraft.SiegeManager
import com.github.fratikak.siegecraft.event.SiegeJoinPlayerEvent
import com.github.fratikak.siegecraft.task.PreparationTask
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.plugin.Plugin

class LobbyItemListener(private val plugin: Plugin) : Listener {

    private val manager = SiegeManager

    /**
     * プレイヤーがダイヤモンドを持ってインタラクトした場合、
     * PreparationTaskを開始します
     */
    @EventHandler
    fun onDiamondInteract(e: PlayerInteractEvent) {

        //左クリックでインタラクト、所持アイテムがダイアモンドでなければreturn
        if (e.action == Action.LEFT_CLICK_AIR || e.action == Action.LEFT_CLICK_BLOCK) {
            return
        }
        if (e.player.itemInHand.type != Material.DIAMOND) {
            return
        }

        if (manager.gamePlayers.contains(e.player)){
            e.player.sendMessage("[Siege]" + ChatColor.RED + "すでにゲームに参加しています")
            return
        }

        //試合中、準備フェーズ中でなければ、PreparationTaskを開始します
        if (!manager.isMatching && !manager.isPreparation) {
            //eventに関わったプレイヤーをgamePlayersに格納
            manager.gamePlayers.add(e.player)

            //PreparationTask
            manager.isPreparation = true
            PreparationTask().runTaskTimer(plugin, 0, 20)
            Bukkit.broadcastMessage("[Siege]" + ChatColor.AQUA + "20秒後にゲームを開始します")
            return
        }

        //PreparationTask中、または試合中に行われた場合、途中参加の処理をします
        Bukkit.getPluginManager().callEvent(SiegeJoinPlayerEvent(e.player))
    }
}