package fr.mrcubee.connectfour.bukkit.listeners;

import fr.mrcubee.connectfour.bukkit.ConnectFour;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * @author MrCubee
 */
public class InventoryClick implements Listener {

    private final ConnectFour connectFour;

    public InventoryClick(ConnectFour connectFour) {
        this.connectFour = connectFour;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event == null || event.isCancelled())
            return;
        event.setCancelled(this.connectFour.getGameManager().clickInventory(event.getInventory(),
                (Player) event.getWhoClicked(), event.getSlot()));
    }
}
