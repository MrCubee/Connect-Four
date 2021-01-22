package fr.mrcubee.connectfour.bukkit.listeners;

import fr.mrcubee.connectfour.bukkit.ConnectFour;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

/**
 * @author MrCubee
 */
public class InventoryClose implements Listener {

    private final ConnectFour connectFour;

    public InventoryClose(ConnectFour connectFour) {
        this.connectFour = connectFour;
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event == null)
            return;
        this.connectFour.getGameManager().closeGame((Player) event.getPlayer());
    }
}
