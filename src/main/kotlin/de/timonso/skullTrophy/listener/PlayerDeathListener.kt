package de.timonso.skullTrophy.listener

import de.timonso.skullTrophy.TrophyConfig
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import java.time.LocalDateTime
import kotlin.random.Random

class PlayerDeathListener(private val config: TrophyConfig) : Listener {
    private val mm = MiniMessage.miniMessage()

    @EventHandler
    fun onDeath(event: PlayerDeathEvent) {
        val victim = event.player
        val killer = victim.killer ?: return
        if (killer == victim) return
        if (victim.hasPermission("skulltrophy.bypass")) return
        if (Random.nextDouble() >= config.dropChance) return

        val resolvers = arrayOf(
            Placeholder.unparsed("victim", victim.name),
            Placeholder.unparsed("killer", killer.name),
            Placeholder.unparsed("date", LocalDateTime.now().format(config.dateFormatter))
        )
        val noItalic = { c: Component -> c.decoration(TextDecoration.ITALIC, false) }

        val head = ItemStack(Material.PLAYER_HEAD)
        head.editMeta(SkullMeta::class.java) { meta ->
            meta.owningPlayer = victim
            meta.displayName(noItalic(mm.deserialize(config.itemName, *resolvers)))
            meta.lore(config.itemLore.map { noItalic(mm.deserialize(it, *resolvers)) })
        }
        event.drops.add(head)
    }
}