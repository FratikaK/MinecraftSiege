package com.github.fratikak.siegecraft.task

import com.github.fratikak.siegecraft.SiegeManager
import com.github.fratikak.siegecraft.util.ScoreBoardSystem
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable

/**
 * ゲームの残り時間カウント、スコアボード更新など
 * １秒毎に更新したい内容を反映するタスク
 */
class GameCountDownTask : BukkitRunnable() {

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

        SiegeManager.timeLeft--
    }
}