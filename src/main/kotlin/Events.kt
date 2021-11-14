import org.bukkit.Material
import org.bukkit.entity.Item
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockDropItemEvent
import org.bukkit.event.entity.ItemSpawnEvent
import org.bukkit.event.inventory.CraftItemEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.event.inventory.SmithItemEvent
import org.bukkit.inventory.AnvilInventory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.Damageable
import java.util.*

class Events : Listener {
    @EventHandler
    fun mine(e: ItemSpawnEvent) {
        val materialList = Material.values().toMutableList()
        materialList.filter { !it.isAir }
        val itemSel = materialList[Random().nextInt(materialList.size)]
        e.entity.itemStack = ItemStack(itemSel,Random().nextInt(itemSel.maxStackSize))
    }

    @EventHandler
    fun pick(e: CraftItemEvent) {
        val result = e.inventory.result
        if (result?.type?.maxDurability?.toInt() == 0) return
        if (result?.itemMeta is Damageable) {
            if ((e.isShiftClick || e.isRightClick)) e.isCancelled = true
            e.inventory.result = getResult(result)
        }
    }

    @EventHandler
    fun smith(e: SmithItemEvent) {
        val result = e.inventory.result
        if (result?.type?.maxDurability?.toInt() == 0) return
        if (result?.itemMeta is Damageable) {
            if ((e.isShiftClick || e.isRightClick)) e.isCancelled = true
            e.inventory.result = getResult(result)
        }
    }

    @EventHandler
    fun anvil(e: InventoryClickEvent) {
        if (e.slotType == InventoryType.SlotType.RESULT) {
            if (e.inventory.type == InventoryType.ANVIL) {
                val anvil = e.inventory as AnvilInventory
                val result = anvil.result
                if (result?.itemMeta is Damageable) {
                    if ((e.isShiftClick || e.isRightClick) && result.type.maxDurability.toInt() != 0) e.isCancelled = true
                    anvil.result = getResult(result)
                }
            }
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