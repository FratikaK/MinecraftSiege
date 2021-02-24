package com.github.fratikak.siegecraft.listener

import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class JoinQuitListener : Listener {
    @EventHandler
    fun onPlayerJoin(e: PlayerJoinEvent) {
        val player = e.player
        player.health = 20.0
        player.foodLevel = 20
        player.teleport(player.world.spawnLocation)
        e.joinMessage = ChatColor.AQUA.toString() + player.displayName + "がログインしました"
    }
}