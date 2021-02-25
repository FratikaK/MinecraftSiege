package com.github.fratikak.siegecraft

import com.github.fratikak.siegecraft.listener.JoinQuitListener
import com.github.fratikak.siegecraft.listener.SiegeControlListener
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class SiegeCraft : JavaPlugin() {
    override fun onEnable() {
        // Plugin startup logic
        Bukkit.getPluginManager().registerEvents(JoinQuitListener(), this)
        Bukkit.getPluginManager().registerEvents(SiegeControlListener(), this)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}