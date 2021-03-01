package com.github.fratikak.siegecraft.task

import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

/**
 * スプリント中にFoodLevelを減らすTask
 */
class SprintControlTask(private val player:Player) : BukkitRunnable(){

    override fun run() {
        player.foodLevel--

        if (!player.isSprinting){
            cancel()
        }
    }
}