package com.github.fratikak.siegecraft.listener

import com.github.fratikak.siegecraft.SiegeManager
import com.github.fratikak.siegecraft.event.SiegeStartEvent
import com.github.fratikak.siegecraft.util.GameStage
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

/**
 * ゲームに関係するEventについて処理する
 */
class SiegeControlListener : Listener {

    @EventHandler
    fun onSiegeStart(e: SiegeStartEvent) {

        //準備Task中であればreturn
        if (SiegeManager.isPreparation) {
            return
        }

        SiegeManager.startMatch()

        //使用ステージ選出(ステージIDをランダムに決める)
        val stageID = 0  //TODO ステージを増やす度に乱数の範囲を広げる
        SiegeManager.stageID = stageID

        //各チームをそれぞれのスポーンポイントに移動させる
        for (player in SiegeManager.blueTeam) {
            //ステージIDに対応したステージにテレポート
            teleportStageBlue(stageID, player)

            player.sendMessage(ChatColor.GOLD.toString() + "--------------------------")
            player.sendMessage(ChatColor.GOLD.toString() + "[勝利条件]：コアの破壊")
            player.sendMessage(ChatColor.GOLD.toString() + "--------------------------")
        }
        for (player in SiegeManager.redTeam) {
            //ステージIDに対応したステージにテレポート
            teleportStageRed(stageID, player)

            player.sendMessage(ChatColor.GOLD.toString() + "--------------------------")
            player.sendMessage(ChatColor.GOLD.toString() + "[勝利条件]：残り時間が0になる")
            player.sendMessage(ChatColor.GOLD.toString() + "--------------------------")

        }

        //TODO それぞれのプレイヤーに武器を配布する

        //GameCountDownTaskの開始

    }

    private fun teleportStageBlue(stageId: Int, p: Player) {
        when (stageId) {
            0 -> p.teleport(GameStage.APARTMENT.redLocation(p))
        }
    }

    private fun teleportStageRed(stageId: Int, p: Player) {
        when (stageId) {
            0 -> p.teleport(GameStage.APARTMENT.redLocation(p))
        }
    }
}