package com.github.fratikak.siegecraft

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Statistic
import org.bukkit.entity.Player

object SiegeManager {

    //試合中かの判定
    var isMatching: Boolean = false

    //準備タスク中か
    var isPreparation: Boolean = false

    //終了タスク中か
    var isFinish : Boolean = false

    //コアの耐久度
    var coreHealth: Int = 0

    //制限時間
    const val timeLeft = 240

    //現在使用しているステージID
    var stageID = 0

    //攻撃チームのスコア
    var blueScore: Int = 0

    //防衛チームのスコア
    var redScore: Int = 0

    //攻撃チームのプレイヤーリスト
    var blueTeam = mutableListOf<Player>()

    //防衛チームのプレイヤーリスト
    var redTeam = mutableListOf<Player>()

    //ゲームに参加している全てのプレイヤー
    var gamePlayers = mutableListOf<Player>()

    /**
     * ゲームの開始の処理を行う
     */
    fun startMatch() {

        //試合中ならばreturn
        if (isMatching) {
            Bukkit.getLogger().info("[startMatch]isMatchingがtrueの為、開始出来ませんでした。")
            Bukkit.broadcastMessage("[Siege]" + ChatColor.RED + "ゲーム中の為、試合を開始出来ませんでした。")
            return
        }

        //参加プレイヤーがいなかったらreturn
        if (gamePlayers.isEmpty()) {
            Bukkit.getLogger().info("[startMatch]参加プレイヤーが存在しない為、開始出来ませんでした。")
            Bukkit.broadcastMessage("[Siege]" + ChatColor.RED + "参加プレイヤーが存在しない為、開始出来ませんでした。")
            return
        }

        //チーム分けの処理を行う。攻撃チームを優先してPlayerを格納する
        for (player in gamePlayers) {
            if (blueTeam.size > redTeam.size) {
                redTeam.add(player)
                continue
            }
            blueTeam.add(player)
        }

        //コアヘルスは攻撃チームの人数に比例して増加する
        coreHealth = blueTeam.size * 10

        //TODO マップの抽選の処理を記述しておく

        //スコア初期化
        blueScore = 0
        redScore = 0

        isMatching = true

        //TODO TEAMの登録

    }


    /**
     * チームの初期化を行う
     */
    fun initializeTeams() {
        blueTeam.clear()
        redTeam.clear()

        //キルデス数を別のスコアに格納しておく
        for (player in gamePlayers) {
            val getKills = player.getStatistic(Statistic.PLAYER_KILLS)
            val getDeaths = player.getStatistic(Statistic.DEATHS)
            player.setStatistic(Statistic.ITEM_ENCHANTED, getKills)
            player.setStatistic(Statistic.ANIMALS_BRED, getDeaths)

            //スコアのリセット
            player.setStatistic(Statistic.PLAYER_KILLS, 0)
            player.setStatistic(Statistic.CAKE_SLICES_EATEN, 0)
        }
    }
}
