package com.github.fratikak.siegecraft.util

import com.github.fratikak.siegecraft.SiegeManager
import com.github.fratikak.siegecraft.task.GameCountDownTask
import com.github.fratikak.siegecraft.task.PreparationTask
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Statistic
import org.bukkit.entity.Player
import org.bukkit.scoreboard.DisplaySlot
import org.jetbrains.annotations.NotNull

/**
 * スコアボード全般を司るクラス
 * チーム毎のポイント表示、コアヘルス、残り時間を表示します
 */
class ScoreBoardSystem(@NotNull val player: Player) {

    private val manager = SiegeManager

    /**
     * サイドバーに表示するテキストのリストを返します
     */
    private fun boardLine(): MutableList<String>? {

        //表示するテキストを格納するリスト
        val messageList = mutableListOf<String>()

        if (manager.isPreparation) {//準備フェーズの場合
            //参加人数
            val joinPlayers = manager.gamePlayers.size
            messageList.add("")
            messageList.add("参加プレイヤー数 $joinPlayers")
            messageList.add("")
            messageList.add(ChatColor.AQUA.toString() + "ClassLevel : ${ChatColor.GOLD}${player.level}")
            messageList.add("")

            messageList.add("ゲーム開始まで${PreparationTask().preparationTime}秒")
            messageList.add("")
            return messageList

        } else if (manager.isMatching) {//試合中の場合
            //チームのポイント
            val bluePoint = manager.blueScore
            val redPoint = manager.redScore
            messageList.add("")
            messageList.add(ChatColor.BLUE.toString() + "攻撃チーム : $bluePoint ")
            messageList.add(ChatColor.RED.toString() + "防衛チーム : $redPoint ")
            messageList.add("")
            messageList.add("kills : ${player.getStatistic(Statistic.PLAYER_KILLS)}")
            messageList.add("deaths : ${player.getStatistic(Statistic.DEATHS)}")
            messageList.add("所持金 : ${ChatColor.GOLD}${player.getStatistic(Statistic.BANNER_CLEANED)}")
            messageList.add("TimeLeft  ${GameCountDownTask().toTimeLeft()}")
            messageList.add("")
            return messageList
        }
        return null
    }

    private val scoreBoard = Bukkit.getScoreboardManager()?.newScoreboard

    fun updateScoreBoard(){

        //参加プレイヤーがいなければreturn
        if (Bukkit.getOnlinePlayers().isEmpty()){
            return
        }

        //Objectiveを取得
        var obj = scoreBoard?.getObjective("side")

        //Objectiveが存在しない場合は作成
        if (obj == null){
            obj = scoreBoard?.registerNewObjective("side","dummy")
        }

        //Slotを設定
        obj?.displaySlot = DisplaySlot.SIDEBAR
        obj?.displayName = ChatColor.GREEN.toString() + "SiegeCraft"

        //行を取得
        val lines = boardLine()
        //nullが帰ってきた場合は非表示にしてreturn
        if (lines == null){
            scoreBoard?.clearSlot(DisplaySlot.SIDEBAR)
            return
        }

        //リストを反転
        lines.reverse()

        //現在指定されているEntryをすべて解除
        clearEntries()

        var count = 0
        for (msg in lines){

            if (obj != null) {
                obj.getScore(msg).score = count
                count++
            }

            if (player.scoreboard != scoreBoard){
                if (scoreBoard != null) {
                    player.scoreboard = scoreBoard
                }
            }
        }
    }

    /**
     * 現在設定されているEntryをすべてリセットします
     */
    private fun clearEntries(){
        scoreBoard?.entries?.forEach(scoreBoard::resetScores)
    }

    /**
     * サイドバーを削除します
     */
    fun clearSideBar(){
        scoreBoard?.clearSlot(DisplaySlot.SIDEBAR)
    }
}