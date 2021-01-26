package fr.mrcubee.connectfour.bukkit.game;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author MrCubee
 */
public class CFGameManager {

    private Map<Player, CFGame> games;

    public CFGameManager() {
        this.games = new WeakHashMap<Player, CFGame>();
    }

    public boolean openGame(Player player) {
        CFGame cfGame;

        if (player == null || this.games.get(player) != null)
            return false;
        cfGame = CFGame.create(player);
        if (cfGame == null)
            return false;
        this.games.put(player, cfGame);
        player.openInventory(cfGame.getInventory());
        return true;
    }


    public boolean closeGame(Player player) {
        if (player == null || this.games.remove(player) == null)
            return false;
        player.closeInventory();
        return true;
    }

    private boolean checkGame(CFGame game) {
        Player player;

        if (game == null)
            return false;
        player = game.getPlayer();
        if (player == null)
            return false;
        if (game.isFull()) {
            player.sendMessage(ChatColor.GRAY + "No winners.");
            return true;
        }
        if (game.isPlayerWin()) {
            player.sendMessage(ChatColor.GOLD + "You win !");
            return true;
        }
        if (game.isBotWin()) {
            player.sendMessage(ChatColor.RED + "You lose !");
            return true;
        }
        return false;
    }

    public boolean clickInventory(Inventory inventory, Player player, int index) {
        CFGame currentCFGame = null;
        int row;
        int column;

        if (inventory == null || player == null || index < 0 || index >= 54)
            return false;
        for (CFGame cfGame : this.games.values()) {
            if (cfGame.equals(inventory)) {
                currentCFGame = cfGame;
                break;
            }
        }
        if (currentCFGame == null)
            return false;
        if (!player.equals(currentCFGame.getPlayer()) || checkGame(currentCFGame))
            return true;
        row = index / 9;
        column = index % 9;
        if (row > 5 || column < 1 || column > 7)
            return true;
        if (!currentCFGame.play(column - 1))
            return true;
        if (!checkGame(currentCFGame)) {
            currentCFGame.getBotChoice();
            checkGame(currentCFGame);
        }
        return true;
    }
}
