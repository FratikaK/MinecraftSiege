package com.github.fratikak.siegecraft.task

import com.github.fratikak.siegecraft.SiegeManager
import com.github.fratikak.siegecraft.event.SiegeFinishEvent
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.scheduler.BukkitRunnable

class GameFinishTask : BukkitRunnable() {
    private var timeLeft = 20
    override fun run() {
        if (SiegeManager.isMatching) {
            Bukkit.getLogger().info("[GameFinishTask]isMatchingがtrueの為実行を終了しました")
            cancel()
            return
        }
        if (SiegeManager.isPreparation) {
            cancel()
            return
        }
        if (!SiegeManager.isFinish) {
            Bukkit.getLogger().info("[GameFinishTask]isFinishがfalseの為実行を終了しました")
            cancel()
            return
        }
        if (timeLeft <= 0) {
            cancel()
            Bukkit.getPluginManager().callEvent(SiegeFinishEvent())
            return
        }

        for (player in Bukkit.getOnlinePlayers()) {
            player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_HAT, 1f, 1f)
        }

        if (timeLeft <= 10) {
            Bukkit.broadcastMessage("[Siege]ゲーム終了まで${timeLeft}秒")
        }
        timeLeft--
    }
}