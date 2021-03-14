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

    /**
     * 試合中にダイアモンドブロックを破壊した時に
     * CoreDamageEventをcallします
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    fun onDiamondBlockDamage(e: BlockBreakEvent) {

        if (e.block.type == Material.DIAMOND_BLOCK){
            e.isCancelled = true
            Bukkit.getPluginManager().callEvent(CoreDamageEvent())
        }
    }

    /**
     * opを所持していないプレイヤーのブロック破壊はキャンセルします
     */
    @EventHandler(priority = EventPriority.LOWEST)
    fun onBlockBreak(e:BlockBreakEvent){
        if(!e.player.isOp){
            e.isCancelled = true
        }
    }
}