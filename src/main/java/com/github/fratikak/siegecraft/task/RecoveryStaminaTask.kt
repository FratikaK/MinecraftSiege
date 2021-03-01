package com.github.fratikak.siegecraft.task

import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

/**
 * スプリントをしていない時にスタミナを回復させます
 */
class RecoveryStaminaTask(private val player: Player) :BukkitRunnable(){

    private var interval = 10
    override fun run() {
        //スタミナが満タンになればキャンセル
        if (player.foodLevel == 20){
            cancel()
            return
        }
        //スプリントを始めたらキャンセル
        if (player.isSprinting){
            cancel()
            return
        }
        //待機時間が0以下になればスタミナを回復し始める
        if (interval <= 0){
            player.foodLevel++
            return
        }
        interval--
    }
}