import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.CraftItemEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.Damageable
import org.bukkit.inventory.meta.Repairable
import java.util.*

class Events : Listener {
    @EventHandler
    fun pick(e:CraftItemEvent) {
        val result = e.recipe.result
        if (result.itemMeta is Repairable) {
            if (e.isShiftClick || e.isRightClick) e.isCancelled = true
            e.inventory.result = getResult(result)
        }
    }

    private fun getResult(result: ItemStack) : ItemStack {
        return result.apply {
            val max = this.type.maxDurability
            itemMeta = itemMeta.apply {
                val dMeta = this as Damageable
                dMeta.damage = Random().nextInt(max.toInt())
            }
        }
    }
}