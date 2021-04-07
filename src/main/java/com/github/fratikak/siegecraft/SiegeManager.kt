package com.github.fratikak.siegecraft

import com.github.fratikak.siegecraft.util.TeamSystem
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.Statistic
import org.bukkit.entity.Player

object SiegeManager {

    //試合中かの判定
    var isMatching: Boolean = false

    //準備タスク中か
    var isPreparation: Boolean = false

    //終了タスク中か
    var isFinish: Boolean = false

    //コアの耐久度
    var coreHealth: Int = 0

    //準備Task時間
    var preparationTime = 20

    //制限時間
    var timeLeft = 240

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

    //それぞれのチームリスポーン地点
    var blueLocation: Location? = null
    var redLocation: Location? = null

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
                TeamSystem().setPlayerTeams(player)
                continue
            }
            blueTeam.add(player)
            TeamSystem().setPlayerTeams(player)
        }

        //コアヘルスは攻撃チームの人数に比例して増加する
        coreHealth = blueTeam.size * 10

        //スコア初期化
        blueScore = 0
        redScore = 0

        isMatching = true
    }


    /**
     * チームの初期化を行う
     */
    fun initializeTeams() {
        //Teamからプレイヤーを削除
        for (player in Bukkit.getOnlinePlayers()) {
            TeamSystem().removePlayerTeams(player)
        }

        //リストを空にする
        blueTeam.clear()
        redTeam.clear()

        //キルデス数を別のスコアに格納しておく
        for (player in gamePlayers) {
            scoreRegistration(player)
        }
    }

    /**
     * キルデス数を別の統計に格納します
     * @param player 処理したいプレイヤー
     */
    fun scoreRegistration(player: Player){
        val getKills = player.getStatistic(Statistic.PLAYER_KILLS)
        val getDeaths = player.getStatistic(Statistic.DEATHS)
        player.setStatistic(Statistic.ITEM_ENCHANTED, getKills)
        player.setStatistic(Statistic.ANIMALS_BRED, getDeaths)

        //スコアのリセット
        player.setStatistic(Statistic.PLAYER_KILLS, 0)
        player.setStatistic(Statistic.DEATHS, 0)
    }

    /**
     * 数値の初期化処理をします
     */
    fun initParam() {
        isMatching = false
        isPreparation = false
        isFinish = false
        coreHealth = 0
        preparationTime = 20
        timeLeft = 240
        stageID = 0
        blueScore = 0
        redScore = 0
    }
}
