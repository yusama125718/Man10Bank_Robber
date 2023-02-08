package yusama125718.man10_bank_robber;

import org.bukkit.entity.Item;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Data {
    public enum Status{
        nogame,
        entry,
        ready,
        fight,
        end
    }

    public enum Team{
        blue,
        yellow
    }

    public static class PlayerData{
        public Team team;       //チーム
        public Double bet;      //ベット額
        public Double money;    //Buyに使えるお金
        public Integer kill;    //kill数
        public Integer death;   //death数
        public Double goldheld; //盗んでいるお金
        public Double attack;    //持ち帰ったお金
        public PlayerInventory inv;       //ゲーム中のインベントリ
        public PlayerInventory saveinv;   //ゲーム前のインベントリ
        public PlayerData(Team t){
            team = t;
            kill = 0;
            death = 0;
            goldheld = (double) 0;
            attack = (double) 0;
        }
        public PlayerData(Double b){
            bet = b;
            money = b;
            kill = 0;
            death = 0;
            goldheld = (double) 0;
            attack = (double) 0;
        }
    }

    public static class BuyMenuData{
        public Inventory inv;   //メニューの中身
        public HashMap<ItemStack,String> items = new HashMap<>();       //アイテム <アイテム,呼び出すメニュー名>
        public BuyMenuData(Inventory Inv, HashMap<ItemStack,String> Map){
            inv = Inv;
            items = Map;
        }
    }

    public static class ShopData{
        public ItemStack icon;  //icon
        public Inventory inv;   //shopの見た目
        public HashMap<ItemStack, Double> values = new HashMap<>();  //アイテム <見た目,値段>
        public HashMap<ItemStack, ItemStack> items = new HashMap<>();  //アイテム <見た目,商品>
        public ShopData(Inventory Inv, HashMap<ItemStack, ItemStack> Items, HashMap<ItemStack, Double> Values, ItemStack Icon){
            icon = Icon;
            inv = Inv;
            items = Items;
            values = Values;
        }
    }

    public static class AddItemData{
        public String name;
        public HashMap<ItemStack, Double> values = new HashMap<>();  //アイテム <見た目,値段>
        public HashMap<ItemStack, ItemStack> items = new HashMap<>();  //アイテム <見た目,商品>
        public AddItemData(String Name, HashMap<ItemStack, Double> Values, HashMap<ItemStack, ItemStack> Items){
            name = Name;
            values = Values;
            items = Items;
        }
    }
}
