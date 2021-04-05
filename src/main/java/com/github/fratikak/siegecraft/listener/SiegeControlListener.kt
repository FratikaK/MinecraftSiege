package com.github.fratikak.siegecraft.listener

import com.github.fratikak.siegecraft.SiegeManager
import com.github.fratikak.siegecraft.event.SiegeFinishEvent
import com.github.fratikak.siegecraft.event.SiegeJoinPlayerEvent
import com.github.fratikak.siegecraft.event.SiegeStartEvent
import com.github.fratikak.siegecraft.task.GameCountDownTask
import com.github.fratikak.siegecraft.task.PreparationTask
import com.github.fratikak.siegecraft.util.GameStage
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.Statistic
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin

/**
 * ゲームに関係するEventについて処理する
 */
class SiegeControlListener(private val plugin: Plugin) : Listener {

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

        //使用するステージが決まっているのでリスポーンLocationを登録する
        for (player in SiegeManager.gamePlayers) {
            when (stageID) {
                0 -> {
                    SiegeManager.blueLocation = GameStage.APARTMENT.blueLocation(player)
                    SiegeManager.redLocation = GameStage.APARTMENT.redLocation(player)
                }
            }
        }

        //各チームをそれぞれのスポーンポイントに移動させる
        for (player in SiegeManager.blueTeam) {
            //ステージIDに対応したステージにテレポート
            teleportStageBlue(stageID, player)

            player.sendMessage(ChatColor.GOLD.toString() + "--------------------------")
            player.sendMessage(ChatColor.GOLD.toString() + "[勝利条件]：コアの破壊")
            player.sendMessage(ChatColor.GOLD.toString() + "--------------------------")

            //武器の配布
            player.inventory.addItem(ItemStack(Material.GOLDEN_PICKAXE))
        }
        for (player in SiegeManager.redTeam) {
            //ステージIDに対応したステージにテレポート
            teleportStageRed(stageID, player)

            player.sendMessage(ChatColor.GOLD.toString() + "--------------------------")
            player.sendMessage(ChatColor.GOLD.toString() + "[勝利条件]：残り時間が0になる")
            player.sendMessage(ChatColor.GOLD.toString() + "--------------------------")

            //TODO 武器の配布
        }
        //GameCountDownTaskの開始
        GameCountDownTask(plugin).runTaskTimer(plugin, 0, 20)
    }

    /**
     * ゲームに参加させる処理をします
     */
    @EventHandler
    fun onPlayerJoinSiege(e: SiegeJoinPlayerEvent) {
        val player = e.player

        if (SiegeManager.gamePlayers.contains(player)) {
            player.sendMessage("[Siege]" + ChatColor.RED + "すでにゲームに参加しています")
            return
        }

        if (SiegeManager.isPreparation) {
            SiegeManager.gamePlayers.add(player)
            player.sendMessage("[Siege]" + ChatColor.GREEN + "ゲームに参加します")
            return
        }

        SiegeManager.gamePlayers.add(player)

        //試合中であれば途中参加
        if (SiegeManager.isMatching) {
            SiegeManager.gamePlayers.add(player)
            if (SiegeManager.blueTeam.size > SiegeManager.redTeam.size) {
                SiegeManager.redTeam.add(player)
                teleportStageRed(SiegeManager.stageID, player)
                return
            }
            SiegeManager.blueTeam.add(player)
            teleportStageBlue(SiegeManager.stageID, player)
        }
    }

    /**
     * ゲームが終了する時の処理
     */
    @EventHandler
    fun onSiegeFinish(e: SiegeFinishEvent) {
        if (!SiegeManager.isFinish) {
            return
        }
        //チームを初期化
        SiegeManager.initializeTeams()
        //全プレイヤーを初期スポーンへ移動
        for (player in Bukkit.getOnlinePlayers()) {
            player.teleport(player.world.spawnLocation)
            //所持金リセット
            player.setStatistic(Statistic.BANNER_CLEANED, 0)
        }

        SiegeManager.isFinish = false
        SiegeManager.initializeTeams()
        SiegeManager.initParam()

        //ゲームプレイヤーが2人以上いればPreparationTaskを開始
        if (SiegeManager.gamePlayers.size >= 2) {
            SiegeManager.isPreparation = true
            PreparationTask().runTaskTimer(plugin, 0, 20)
        } else {
            SiegeManager.gamePlayers.clear()
        }
    }

    private fun teleportStageBlue(stageId: Int, p: Player) {
        when (stageId) {
            0 -> p.teleport(GameStage.APARTMENT.blueLocation(p))
        }
    }

    private fun teleportStageRed(stageId: Int, p: Player) {
        when (stageId) {
            0 -> p.teleport(GameStage.APARTMENT.redLocation(p))
        }
    }
}