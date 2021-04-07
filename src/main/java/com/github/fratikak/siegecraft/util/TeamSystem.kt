package com.github.fratikak.siegecraft.util

import com.github.fratikak.siegecraft.SiegeManager
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.scoreboard.Team

/**
 * Teamに関するクラス
 */
class TeamSystem {

    private val manager = SiegeManager
    private val scoreBoardManager = Bukkit.getScoreboardManager()
    private val scoreBoard = scoreBoardManager?.newScoreboard

    private lateinit var attackTeam: Team
    private lateinit var defenceTeam: Team

    private val ATTACK = "ATTACK"
    private val DEFENCE = "DEFENCE"

    /**
     * チームが作成されてなければ登録します
     */
    private fun createTeam() {
        attackTeam = scoreBoard?.registerNewTeam(ATTACK)!!
        attackTeam.prefix = "${ChatColor.BLUE}攻撃"
        attackTeam.displayName = "${ChatColor.BLUE}ATTACK TEAM"
        attackTeam.setAllowFriendlyFire(false)

        defenceTeam = scoreBoard.registerNewTeam(DEFENCE)
        defenceTeam.prefix = "${ChatColor.RED}防衛"
        defenceTeam.displayName = "${ChatColor.RED}DEFENCE TEAM"
        defenceTeam.setAllowFriendlyFire(false)
    }


    /**
     * プレイヤーを対応したチームに登録します
     * @param player 登録したいプレイヤー
     */
    fun setPlayerTeams(player: Player) {
        //ゲームプレイヤーではないならreturn
        if (!manager.gamePlayers.contains(player)) {
            return
        }
        //チームの作成
        createTeam()

        if (manager.blueTeam.contains(player)) {
            attackTeam.addPlayer(player)
        } else {
            defenceTeam.addPlayer(player)
        }
    }

    /**
     * プレイヤーをチームから削除します
     * @param player 削除したいプレイヤー
     */
    fun removePlayerTeams(player: Player){
        //ゲームプレイヤーではないならreturn
        if (!manager.gamePlayers.contains(player)){
            return
        }

        if (manager.blueTeam.contains(player)){
            attackTeam.removePlayer(player)
        }else{
            defenceTeam.removePlayer(player)
        }
    }
}
