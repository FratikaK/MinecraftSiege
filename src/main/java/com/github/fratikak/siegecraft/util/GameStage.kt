package com.github.fratikak.siegecraft.util

import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.Player

/**
 * ステージ毎のLocationを格納する
 */
enum class GameStage {

    APARTMENT {
        override fun blueLocation(p: Player): Location {
            return Location(p.world, 24.0, 89.0, -327.0)
        }

        override fun redLocation(p: Player): Location {
            return Location(p.world, 27.0, 84.0, -336.0)
        }
    };

    abstract fun blueLocation(p: Player): Location
    abstract fun redLocation(p: Player): Location

}