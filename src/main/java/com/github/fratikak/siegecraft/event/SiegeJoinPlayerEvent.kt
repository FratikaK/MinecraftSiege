package com.github.fratikak.siegecraft.event

import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class SiegeJoinPlayerEvent(joinPlayer: Player): Event() {

    private val HandlerList : HandlerList = HandlerList()

    override fun getHandlers(): HandlerList {
        return HandlerList
    }
    //Getter
    val player : Player = joinPlayer

}