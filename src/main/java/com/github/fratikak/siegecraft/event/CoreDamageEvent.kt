package com.github.fratikak.siegecraft.event

import org.bukkit.event.Event
import org.bukkit.event.HandlerList

/**
 * コアにダメージが入った場合に呼び出されるEvent
 */
class CoreDamageEvent : Event() {
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