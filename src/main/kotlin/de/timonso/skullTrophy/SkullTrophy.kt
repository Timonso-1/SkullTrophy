package de.timonso.skullTrophy

import de.timonso.skullTrophy.command.SkullTrophyCommand
import de.timonso.skullTrophy.listener.PlayerDeathListener
import org.bukkit.plugin.java.JavaPlugin

class SkullTrophy : JavaPlugin() {
    lateinit var trophyConfig: TrophyConfig

    override fun onEnable() {
        trophyConfig = TrophyConfig(this).also { it.load() }
        server.pluginManager.registerEvents(PlayerDeathListener(trophyConfig), this)
        SkullTrophyCommand(trophyConfig).register(this)
    }
}