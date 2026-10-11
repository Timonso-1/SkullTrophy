package de.timonso.skullTrophy.command

import de.timonso.skullTrophy.TrophyConfig
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin

class SkullTrophyCommand(private val trophyConfig: TrophyConfig) {

    private val mm = MiniMessage.miniMessage()

    fun register(plugin: JavaPlugin) {
        plugin.getCommand("skulltrophy")?.setExecutor { sender, _, _, args ->
            if (args.firstOrNull().equals("reload", true)) {
                trophyConfig.load()
                send(sender, "Config neu geladen.")
            } else {
                send(sender, "Nutzung: /skulltrophy reload")
            }
            true
        }
    }

    private fun send(sender: CommandSender, message: String) {
        sender.sendMessage(mm.deserialize(trophyConfig.prefix + message))
    }
}