package com.github.fratikak.siegecraft.task

import com.github.fratikak.siegecraft.SiegeManager
import org.bukkit.scheduler.BukkitRunnable

class GameCountDownTask : BukkitRunnable() {

    private var timeLeft = SiegeManager.timeLeft

    override fun run() {

        //TODO updateScoreboard(toTimeLeft)

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