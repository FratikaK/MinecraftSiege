package com.github.fratikak.siegecraft.task

import com.github.fratikak.siegecraft.SiegeManager
import com.github.fratikak.siegecraft.event.SiegeStartEvent
import com.github.fratikak.siegecraft.util.ScoreBoardSystem
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.scheduler.BukkitRunnable

/**
 * 準備フェーズのカウントダウンを行う
 */
class PreparationTask: BukkitRunnable() {
    var preparationTime = 20

    override fun run() {

        if (!SiegeManager.isPreparation){
            Bukkit.getLogger().info("[PreparationTask]isPreparationがfalseだった為、キャンセルされました")
            cancel()
        }

        if (SiegeManager.gamePlayers.isEmpty()){
            Bukkit.getLogger().info("[PreparationTask]gamePlayersが空である為、キャンセルされました")
            SiegeManager.isPreparation = false
            cancel()
        }

        //0秒になったらゲーム開始のEventを呼び出す
        if (preparationTime <= 0){
            SiegeManager.isPreparation = false
            Bukkit.getPluginManager().callEvent(SiegeStartEvent())
            cancel()
        }

        if (preparationTime <= 20){
            for (player in Bukkit.getOnlinePlayers()){
                player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_HAT, 1f, 1f)
                ScoreBoardSystem(player).updateScoreBoard()
            }
        }

        if (preparationTime <= 5) {
            Bukkit.broadcastMessage("[Siege]ゲーム開始まで$preparationTime 秒")
            for (player in Bukkit.getOnlinePlayers()) {
                player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 24f)
            }
        }

        preparationTime--
    }


}