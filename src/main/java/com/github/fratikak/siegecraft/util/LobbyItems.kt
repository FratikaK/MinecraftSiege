package com.github.fratikak.siegecraft.util

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

object LobbyItems {

    /**
     * インタラクトイベントで表示させるGUI
     *
     * @param player 表示させたいプレイヤー
     */
    fun lobbyGUI(player: Player) {
        val inventory = Bukkit.createInventory(null, 54)

        player.openInventory(inventory)
    }

    /**
     * 基本となるロビーアイテムを付与します
     *
     * @param player 付与したいプレイヤー
     */
    fun setLobbyItem(player: Player) {
        val inventory = player.inventory
        val diamond = getMetaItem(
            Material.DIAMOND,
            ChatColor.AQUA.toString() + "ゲームに参加する",
            ChatColor.YELLOW.toString() + "ゲームに参加するには右クリックしてください"
        )
        val end = getMetaItem(
            Material.END_CRYSTAL,
            ChatColor.GOLD.toString() + "メニュー",
            ChatColor.YELLOW.toString() + "右クリックするとメニューを開きます"
        )

        inventory.clear()
        inventory.setItem(0, diamond)
        inventory.setItem(1, end)
    }

    /**
     * 名前と説明文を加えたItemStackを返す
     * 色をつけたい場合は引数にChatColorをつけてください
     *
     * @param material アイテムの種類
     * @param itemName つけたいアイテムの名前
     * @param itemLore つけたいアイテムの説明文
     * @return 名前と説明文が付与されているアイテム
     */
    fun getMetaItem(material: Material, itemName: String, itemLore: String): ItemStack {
        val item = ItemStack(material)
        val meta = item.itemMeta
        meta?.setDisplayName(itemName)
        val list = mutableListOf<String>()
        list.add(itemLore)
        meta?.lore = list
        meta?.setCustomModelData(1)

        item.itemMeta = meta
        return item
    }
}