package fr.mrcubee.connectfour.bukkit.game;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * @author MrCubee
 */
public class CFSettings {

    private static void setItemMeta(ItemStack itemStack, String name) {
        ItemMeta itemMeta;

        if (itemStack == null || name == null)
            return;
        itemMeta = itemStack.getItemMeta();
        if (itemMeta == null)
            return;
        itemMeta.setDisplayName(name);
        itemMeta.setLore(null);
    }

    private static ItemStack createWoolItem(String name, DyeColor dyeColor) {
        ItemStack itemStack;

        if (name == null || dyeColor == null)
            return null;
        itemStack = new ItemStack(Material.WOOL, 1, dyeColor.getWoolData());
        setItemMeta(itemStack, name);
        return itemStack;
    }

    private static ItemStack createGlassItem(String name, DyeColor dyeColor) {
        ItemStack itemStack;

        if (name == null || dyeColor == null)
            return null;
        itemStack = new ItemStack(Material.STAINED_GLASS, 1, dyeColor.getData());
        setItemMeta(itemStack, name);
        return itemStack;
    }

    public static final ItemStack PLAYER_WOOL = createWoolItem(ChatColor.YELLOW + "You", DyeColor.YELLOW);
    public static final ItemStack BOT_WOOL = createWoolItem(ChatColor.RED + "BOT", DyeColor.RED);
    public static final ItemStack WALL_GLASS = createGlassItem("", DyeColor.GRAY);
}
