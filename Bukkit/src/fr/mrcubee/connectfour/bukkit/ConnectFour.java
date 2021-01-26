package fr.mrcubee.connectfour.bukkit;

import fr.mrcubee.annotation.spigot.config.ConfigAnnotation;
import fr.mrcubee.connectfour.bukkit.game.CFGameManager;
import fr.mrcubee.connectfour.bukkit.game.CFSettings;
import fr.mrcubee.connectfour.bukkit.listeners.RegisterListeners;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author MrCubee
 */
public class ConnectFour extends JavaPlugin {

    private CFGameManager gameManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        ConfigAnnotation.loadClass(getConfig(), CFSettings.class);
        this.gameManager = new CFGameManager();
        RegisterListeners.register(this);
    }

    @Override
    public void onDisable() {

    }

    private Player getPlayer(String playerName) {
        Player player;

        if (playerName == null)
            return null;
        player = Bukkit.getPlayer(playerName);
        if (!player.getName().equalsIgnoreCase(playerName))
            return null;
        return player;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player target;

        if (args.length > 1 && args[0].equalsIgnoreCase("play")) {
            target = getPlayer(args[1]);
            if (target == null || !target.isOnline()) {
                sender.sendMessage(ChatColor.RED + "The player does not exist or is offline.");
                return true;
            }
            this.gameManager.openGame(target);
            sender.sendMessage(ChatColor.GREEN + "The game starts for " + target.getName());
            return true;
        }
        if (!(sender instanceof Player))
            return false;
        if (args.length > 0) {
            target = getPlayer(args[0]);
            if (target == null || !target.isOnline()) {
                sender.sendMessage(ChatColor.RED + "The player does not exist or is offline.");
                return true;
            }
            this.gameManager.spectate((Player) sender, target);
            return true;
        }
        this.gameManager.openGame((Player) sender);
        return true;
    }

    public CFGameManager getGameManager() {
        return this.gameManager;
    }
}
