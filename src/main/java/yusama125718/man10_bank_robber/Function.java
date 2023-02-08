package yusama125718.man10_bank_robber;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static yusama125718.man10_bank_robber.Man10_Bank_Robber.*;

public class Function {
    public static ItemStack GetItem(Material mate, Integer amount, String name, Integer cmd){
        ItemStack item = new ItemStack(mate,amount);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text(name));
        meta.setCustomModelData(cmd);
        item.setItemMeta(meta);
        return item;
    }

    public static void Broadcast(String message){
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!dissableplayers.contains(player.getUniqueId())) {
                player.sendMessage(message);
            }
        }
    }
}
