package com.github.fratikak.siegecraft.task

import com.github.fratikak.siegecraft.SiegeManager
import com.github.fratikak.siegecraft.event.SiegeStartEvent
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.scheduler.BukkitRunnable

/**
 * 準備フェーズのカウントダウンを行う
 */
class PreparationTask: BukkitRunnable() {
    private var preparationTime = 20

    override fun run() {

        if (!SiegeManager.isPreparation){
            Bukkit.getLogger().info("[PreparationTask]isPreparationがfalseだった為、キャンセルされました")
            cancel()
        }

        //0秒になったらゲーム開始のEventを呼び出す
        if (preparationTime <= 0){
            Bukkit.getPluginManager().callEvent(SiegeStartEvent())
            SiegeManager.isPreparation = false
            cancel()
        }

        if (preparationTime <= 20){
            for (player in Bukkit.getOnlinePlayers()){
                player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_HAT, 1f, 1f)
            }
        }

        if (preparationTime <= 5) {
            for (player in Bukkit.getOnlinePlayers()) {
                player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 24f)
            }
        }

        preparationTime -= 1
    }


}