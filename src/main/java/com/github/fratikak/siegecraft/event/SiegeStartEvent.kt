package com.github.fratikak.siegecraft.event

import org.bukkit.event.Event
import org.bukkit.event.HandlerList

/**
 * ゲーム開始時に呼び出されるイベント
 */
class SiegeStartEvent :Event(){

    private val HandlerList : HandlerList = HandlerList()

    override fun getHandlers(): HandlerList {
        return HandlerList
    }
}