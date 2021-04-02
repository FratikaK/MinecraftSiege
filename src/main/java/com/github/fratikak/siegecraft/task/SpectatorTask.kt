package com.github.fratikak.siegecraft.task

import com.github.fratikak.siegecraft.SiegeManager
import org.bukkit.GameMode
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

/**
 * 試合中にプレイヤーが死亡した場合、一定時間観戦者として復活まで待機させます
 * @param player 死亡したプレイヤー
 * @param attack プレイヤーが攻撃チームに所属しているか
 */
class SpectatorTask(private val player: Player, private val attack: Boolean) : BukkitRunnable() {

    private var timeLeft = 10

    override fun run() {
        //試合中でなければreturn
        if (!SiegeManager.isMatching) {
            cancel()
            return
        }

        if (timeLeft <= 5) {
            player.sendMessage("[Siege]復活まで${timeLeft}秒")
        }

        if (timeLeft <= 0) {
            cancel()
            if (attack) {
                SiegeManager.blueLocation?.let { player.teleport(it) }
            } else {
                SiegeManager.redLocation?.let { player.teleport(it) }
            }
            player.gameMode = GameMode.SURVIVAL
            //TODO プレイヤーが復帰する時のEventをCall
            return
        }
        timeLeft--
    }

}