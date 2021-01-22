package fr.mrcubee.connectfour.bukkit;

import fr.mrcubee.connectfour.bukkit.game.CFGameManager;
import fr.mrcubee.connectfour.bukkit.listeners.RegisterListeners;
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
        this.gameManager = new CFGameManager();
        RegisterListeners.register(this);
    }

    @Override
    public void onDisable() {

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player))
            return false;
        this.gameManager.openGame((Player) sender);
        return true;
    }

    public CFGameManager getGameManager() {
        return this.gameManager;
    }
}
