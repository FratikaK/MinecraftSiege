package com.github.fratikak.siegecraft

import com.github.fratikak.siegecraft.listener.*
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class SiegeCraft : JavaPlugin() {
    override fun onEnable() {
        // Plugin startup logic
        Bukkit.getPluginManager().registerEvents(BlockControlListener(), this)
        Bukkit.getPluginManager().registerEvents(CoreControlListener(this), this)
        Bukkit.getPluginManager().registerEvents(JoinQuitListener(), this)
        Bukkit.getPluginManager().registerEvents(LobbyItemListener(this), this)
        Bukkit.getPluginManager().registerEvents(PlayerControlListener(this), this)
        Bukkit.getPluginManager().registerEvents(SiegeControlListener(this), this)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}