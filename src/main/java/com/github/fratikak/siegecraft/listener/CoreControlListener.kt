package com.github.fratikak.siegecraft.listener

import com.github.fratikak.siegecraft.SiegeCraft
import com.github.fratikak.siegecraft.SiegeManager
import com.github.fratikak.siegecraft.event.CoreDamageEvent
import com.github.fratikak.siegecraft.task.GameFinishTask
import org.bukkit.*
import org.bukkit.entity.EntityType
import org.bukkit.entity.Firework
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.plugin.Plugin
import java.util.*

/**
 * ゲームのコアブロックに関するリスナクラス
 */
class CoreControlListener(private val plugin: Plugin) : Listener{

    @EventHandler
    fun onSiegeCoreDamage(e: CoreDamageEvent){
        //
        if(SiegeManager.coreHealth <= 0){
            return
        }

        //コアの耐久値を減らす
        SiegeManager.coreHealth--
        Bukkit.broadcastMessage("[Siege]" + ChatColor.GOLD + "コアの残り耐久値: " + SiegeManager.coreHealth)

        for (player in Bukkit.getOnlinePlayers()){
            player.playSound(player.location, Sound.BLOCK_ANVIL_PLACE,2f,0f)
        }

        if (SiegeManager.coreHealth <= 0){
            for (player in Bukkit.getOnlinePlayers()){
                player.playSound(player.location, Sound.ENTITY_LIGHTNING_BOLT_IMPACT,10f,0f)
                spawnFireworks(player.location,1)
            }
            SiegeManager.isMatching = false
            SiegeManager.isFinish = true
            //終了Taskを呼び出す
            GameFinishTask().runTaskTimer(plugin,0,20)
        }
    }

    /**
     * 花火を打ち上げる処理
     *
     * @param location どこに花火をだすか
     * @param amount   打ち上げる回数
     */
    private fun spawnFireworks(location: Location, amount: Int) {
        val fw = Objects.requireNonNull(location.world)?.spawnEntity(location, EntityType.FIREWORK) as Firework
        val fwm = fw.fireworkMeta
        fwm.power = 2
        fwm.addEffect(FireworkEffect.builder().withColor(Color.LIME).flicker(true).build())
        fw.fireworkMeta = fwm
        fw.detonate()
        for (i in 0 until amount) {
            val fw2 = location.world?.spawnEntity(location, EntityType.FIREWORK) as Firework
            fw2.fireworkMeta = fwm
        }
    }
}