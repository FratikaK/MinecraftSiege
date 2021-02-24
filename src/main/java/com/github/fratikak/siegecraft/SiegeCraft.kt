package com.github.fratikak.siegecraft

import com.github.fratikak.siegecraft.listener.JoinQuitListener
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class SiegeCraft : JavaPlugin() {
    override fun onEnable() {
        // Plugin startup logic
        Bukkit.getPluginManager().registerEvents(JoinQuitListener(), this)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}