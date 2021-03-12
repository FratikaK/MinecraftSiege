package com.github.fratikak.siegecraft.listener

import com.github.fratikak.siegecraft.SiegeManager
import com.github.fratikak.siegecraft.event.CoreDamageEvent
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

class BlockControlListener : Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onDiamondBlockDamage(e: BlockBreakEvent) {

        if (e.block.type == Material.DIAMOND_BLOCK){
            e.isCancelled = true
            Bukkit.getPluginManager().callEvent(CoreDamageEvent())
        }
    }
}