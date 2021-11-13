import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Level

class Main : JavaPlugin() {
    override fun onEnable() {
        logger.log(Level.INFO,"Now Enabling... [v${description.version}]")
        server.pluginManager.registerEvents(Events(),this)
    }
}