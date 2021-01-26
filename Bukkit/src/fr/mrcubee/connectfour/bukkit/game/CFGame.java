package fr.mrcubee.connectfour.bukkit.game;

import fr.mrcubee.connectfour.CFBot;
import fr.mrcubee.connectfour.CFTable;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * @author MrCubee
 */
public class CFGame {

    private final Player player;
    private final CFTable cfTable;
    private final Inventory inventory;

    private CFGame(Player player, CFTable cfTable, Inventory inventory) {
        this.player = player;
        this.cfTable = cfTable;
        this.inventory = inventory;
    }

    public boolean play(int column) {
        int index;
        int y;
        int x;

        if (this.cfTable.isFull() || this.cfTable.isWin((byte) 1) || this.cfTable.isWin((byte) 2))
            return false;
        index = cfTable.setPlayer(column, (byte) 1);
        if (index < 0)
            return false;
        y = index / cfTable.getColumns();
        x = index % cfTable.getColumns();
        this.inventory.setItem((y * 9) + x + 1, CFSettings.PLAYER_WOOL);
        return true;
    }

    public void getBotChoice() {
        int index;
        int y;
        int x;

        if (this.cfTable.isFull() || this.cfTable.isWin((byte) 1) || this.cfTable.isWin((byte) 2))
            return;
        index = cfTable.setPlayer(CFBot.getBestColumn(cfTable, 2, (byte) 2, (byte) 1), (byte) 2);
        if (index < 0)
            return;
        y = index / cfTable.getColumns();
        x = index % cfTable.getColumns();
        this.inventory.setItem((y * 9) + x + 1, CFSettings.BOT_WOOL);
    }

    public boolean equals(Inventory inventory) {
        return this.inventory.equals(inventory);
    }

    protected static CFGame create(Player player) {
        CFTable cfTable;
        Inventory inventory;

        if (player == null)
            return null;
        cfTable = CFTable.create(6, 7);
        if (cfTable == null)
            return null;
        inventory = Bukkit.createInventory(null, 54, ChatColor.GOLD + "ConnectFour");
        if (inventory == null)
            return null;
        for (int i = 0; i < 6; i++) {
            inventory.setItem(i * 9, CFSettings.WALL_GLASS);
            inventory.setItem(i * 9 + 8, CFSettings.WALL_GLASS);
        }
        return new CFGame(player, cfTable, inventory);
    }

    public boolean isFull() {
        return this.cfTable.isFull();
    }

    public boolean isPlayerWin() {
        return this.cfTable.isWin((byte) 1);
    }

    public boolean isBotWin() {
        return this.cfTable.isWin((byte) 2);
    }

    public Player getPlayer() {
        return this.player;
    }

    protected Inventory getInventory() {
        return this.inventory;
    }
}
