package yusama125718.man10_bank_robber.data_class;

import com.shojabon.mcutils.Utils.BaseUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import yusama125718.man10_bank_robber.Man10BankRobber;

import java.util.HashMap;
import java.util.List;

public class RobberShop {

    RobberPlayer player;
    HashMap<String, Integer> boughtItemCount = new HashMap<>();

    public RobberShop(RobberPlayer player){
        this.player = player;
    }

    public void buyItem(String itemId, int price){
        if(!canBuyItem(itemId)){
            player.getPlayer().sendMessage(Man10BankRobber.getMessage("buy.limited")
                    .replace("{money}", BaseUtils.priceString(player.buyCredits))
                    .replace("{name}", getAlias(itemId)));
            return;
        }
        if(player.buyCredits < price){
            player.getPlayer().sendMessage(Man10BankRobber.getMessage("buy.noMoney")
                    .replace("{money}", BaseUtils.priceString(player.buyCredits))
                    .replace("{name}", getAlias(itemId)));
            return;
        }
        if(!boughtItemCount.containsKey(itemId)){
            boughtItemCount.put(itemId, 0);
        }
        boughtItemCount.put(itemId, boughtItemCount.get(itemId)+1);
        player.buyCredits -= price;
        player.getPlayer().sendMessage(Man10BankRobber.getMessage("buy.success")
                .replace("{money}", BaseUtils.priceString(player.buyCredits))
                .replace("{name}", getAlias(itemId))
        );
    }

    public List<String> getGroupOfItemId(String itemId){
        return Man10BankRobber.shops.getStringList("items." + itemId + ".groups");
    }

    public String getAlias(String itemId){
        return Man10BankRobber.shops.getString("items." + itemId + ".alias", "");
    }

    public int getItemBuyLimit(String itemId){
        return Man10BankRobber.shops.getInt("items." + itemId + ".limit", 0);
    }

    public int getGroupBuyLimit(String groupId){
        return Man10BankRobber.shops.getInt("groups." + groupId + ".limit", 0);
    }

    public boolean canBuyItem(String itemId){
        int currentItemCount = 0;
        if(boughtItemCount.containsKey(itemId)){
            currentItemCount = boughtItemCount.get(itemId);
        }
        if(getItemBuyLimit(itemId) <= currentItemCount) return false;
        for(String group: getGroupOfItemId(itemId)){
            if(getGroupBuyLimit(group) <= currentItemCount) return false;
        }
        return true;
    }



}
