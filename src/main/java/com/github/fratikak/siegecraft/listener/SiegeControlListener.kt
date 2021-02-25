package com.github.fratikak.siegecraft.listener

import com.github.fratikak.siegecraft.SiegeManager
import com.github.fratikak.siegecraft.event.SiegeStartEvent
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

        //TODO それぞれのプレイヤーに武器を配布する
    }
}