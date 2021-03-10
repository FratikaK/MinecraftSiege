package com.github.fratikak.siegecraft

import com.github.fratikak.siegecraft.listener.JoinQuitListener
import com.github.fratikak.siegecraft.listener.LobbyItemListener
import com.github.fratikak.siegecraft.listener.PlayerControlListener
import com.github.fratikak.siegecraft.listener.SiegeControlListener
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class SiegeCraft : JavaPlugin() {
    override fun onEnable() {
        // Plugin startup logic
        Bukkit.getPluginManager().registerEvents(JoinQuitListener(), this)
        Bukkit.getPluginManager().registerEvents(LobbyItemListener(this),this)
        Bukkit.getPluginManager().registerEvents(PlayerControlListener(this), this)
        Bukkit.getPluginManager().registerEvents(SiegeControlListener(this), this)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}