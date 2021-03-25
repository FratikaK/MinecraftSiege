package com.github.fratikak.siegecraft.event

import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class SiegeFinishEvent : Event() {
    override fun getHandlers(): HandlerList {
        return HANDLERS
    }

    companion object {
        private val HANDLERS = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList {
            return HANDLERS
        }
    }
}