package com.github.fratikak.siegecraft.task

import com.github.fratikak.siegecraft.SiegeManager
import com.github.fratikak.siegecraft.util.ScoreBoardSystem
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable

class GameCountDownTask : BukkitRunnable() {

    private var timeLeft = SiegeManager.timeLeft

    override fun run() {

        if (!SiegeManager.isMatching){
            Bukkit.getLogger().info("[GameCountDownTask]isMatchingがtrueでない為キャンセルしました")
            cancel()
        }

        if (SiegeManager.isPreparation){
            Bukkit.getLogger().info("[gameCountDownTask]isPreparationがfalseである為キャンセルしました")
            cancel()
        }

        //スコアボード更新
        for (player in Bukkit.getOnlinePlayers()){
            ScoreBoardSystem(player).updateScoreBoard()
        }

        if (SiegeManager.coreHealth <= 0){
            cancel()
        }

        timeLeft--
    }

    /**
     * 試合残り時間を分:秒表示にする
     */
    fun toTimeLeft(): String {
        val min = (timeLeft % 3600) / 60
        val sec = timeLeft % 60
        return "$min:$sec"
    }
}