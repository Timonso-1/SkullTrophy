package de.timonso.skullTrophy

import org.bukkit.plugin.java.JavaPlugin
import java.time.format.DateTimeFormatter

class TrophyConfig(private val plugin: JavaPlugin) {

    var prefix = ""
    var dropChance = 0.0
    var itemName = ""
    var itemLore = emptyList<String>()
    var dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss 'Uhr am' dd.MM.yyyy")

    fun load() {
        plugin.saveDefaultConfig()
        plugin.reloadConfig()
        val c = plugin.config
        prefix = c.getString("prefix") ?: "<dark_gray>[<red>SkullTrophy<dark_gray>] <gray>"
        dropChance = c.getDouble("drop-chance", 0.25).coerceIn(0.0, 1.0)
        itemName = c.getString("item-name") ?: "<red>Kopf von <victim>"
        itemLore = c.getStringList("item-lore")
        dateFormatter = runCatching {
            DateTimeFormatter.ofPattern(c.getString("date-format") ?: "HH:mm:ss 'Uhr am' dd.MM.yyyy")
        }.getOrDefault(DateTimeFormatter.ofPattern("HH:mm:ss 'Uhr am' dd.MM.yyyy"))
    }
}