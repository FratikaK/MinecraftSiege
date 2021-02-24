package com.github.fratikak.siegecraft

import org.bukkit.Bukkit
import org.bukkit.Statistic
import org.bukkit.entity.Player

object SiegeManager {

    //試合中かの判定
    var isMatching: Boolean = false
    //準備タスク中か
    var isPreparation: Boolean = false
    //スタートカウントダウン中か
    var isStarting :Boolean = false
    //コアの耐久度
    var coreHealth: Int = 0
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
     * チームの初期化を行う
     */
    fun initializeTeams() {
        blueTeam.clear()
        redTeam.clear()

        //キルデス数を別のスコアに格納しておく
        for (player in gamePlayers){
            val getKills = player.getStatistic(Statistic.PLAYER_KILLS)
            val getDeaths = player.getStatistic(Statistic.CAKE_SLICES_EATEN)
            player.setStatistic(Statistic.ITEM_ENCHANTED,getKills)
            player.setStatistic(Statistic.ANIMALS_BRED,getDeaths)

            //スコアのリセット
            player.setStatistic(Statistic.PLAYER_KILLS,0)
            player.setStatistic(Statistic.CAKE_SLICES_EATEN,0)
        }
    }
}
