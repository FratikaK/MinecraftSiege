package com.github.fratikak.siegecraft.listener

import com.github.fratikak.siegecraft.SiegeManager
import com.github.fratikak.siegecraft.event.SiegeQuitPlayerEvent
import com.github.fratikak.siegecraft.util.LobbyItems
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class JoinQuitListener : Listener {

    /**
     * プレイヤーがJoinした時にステータスの初期化、
     * ロビーアイテムを付与します
     */
    @EventHandler
    fun onPlayerJoin(e: PlayerJoinEvent) {
        val player = e.player
        player.health = 20.0
        player.foodLevel = 20
        player.teleport(player.world.spawnLocation)
        e.joinMessage = ChatColor.AQUA.toString() + player.displayName + "がログインしました"

        LobbyItems.setLobbyItem(player)
    }

    /**
     * プレイヤーがログアウトした時にSiegePlayerQuitEventをcallします
     */
    @EventHandler
    fun onPlayerQuit(e: PlayerQuitEvent) {
        val player = e.player
        e.quitMessage = "${ChatColor.AQUA}${player.displayName}がログアウトしました"

        //ゲームプレイヤーならEvent call
        if (SiegeManager.gamePlayers.contains(player)){
            Bukkit.getPluginManager().callEvent(SiegeQuitPlayerEvent(player))
        }
    }
}