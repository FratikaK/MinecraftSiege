package com.github.fratikak.siegecraft.listener

import com.github.fratikak.siegecraft.SiegeManager
import com.github.fratikak.siegecraft.event.SiegeStartEvent
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

/**
 * ゲームに関係するEventについて処理する
 */
class SiegeControlListener : Listener {

    @EventHandler
    fun onSiegeStart(e: SiegeStartEvent) {

        //準備Task中であればreturn
        if (SiegeManager.isPreparation){
            return
        }

        SiegeManager.startMatch()

        //各チームをそれぞれのスポーンポイントに移動させる
        for (player in SiegeManager.blueTeam) {
            //TODO テレポート処理
            player.sendMessage(ChatColor.GOLD.toString() + "--------------------------")
            player.sendMessage(ChatColor.GOLD.toString() + "[勝利条件]：コアの破壊")
            player.sendMessage(ChatColor.GOLD.toString() + "--------------------------")
        }
        for (player in SiegeManager.redTeam) {
            //TODO テレポート処理
            player.sendMessage(ChatColor.GOLD.toString() + "--------------------------")
            player.sendMessage(ChatColor.GOLD.toString() + "[勝利条件]：残り時間が0になる")
            player.sendMessage(ChatColor.GOLD.toString() + "--------------------------")

        }

        //TODO それぞれのプレイヤーに武器を配布する

        //GameCountDownTaskの開始

    }
}