package fr.mrcubee.connectfour.bukkit.listeners;

import fr.mrcubee.connectfour.bukkit.ConnectFour;
import org.bukkit.plugin.PluginManager;

/**
 * @author MrCubee
 */
public class RegisterListeners {

    public static void register(ConnectFour connectFour) {
        PluginManager pluginManager;

        if (connectFour == null)
            return;
        pluginManager = connectFour.getServer().getPluginManager();
        pluginManager.registerEvents(new InventoryClick(connectFour), connectFour);
        pluginManager.registerEvents(new InventoryClose(connectFour), connectFour);
    }
}
