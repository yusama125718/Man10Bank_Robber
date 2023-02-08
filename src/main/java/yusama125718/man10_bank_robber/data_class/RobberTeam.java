package yusama125718.man10_bank_robber.data_class;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import yusama125718.man10_bank_robber.Man10BankRobber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class RobberTeam {

    public ConfigurationSection config;
    private RobberGame game;

    public HashMap<UUID, RobberPlayer> players = new HashMap<>();
    public String teamName;

    // params
    public String alias;
    public String prefix;

    //ロケーション
    Location spawnPoint;

    // nexus
    List<Location> nexusBlocks = new ArrayList<>();
    public RobberTeam(RobberGame game, String teamName, ConfigurationSection config){
        this.config = config;
        this.teamName = teamName;
        this.game = game;

        loadConfig();
    }

    private void loadConfig(){
        alias = this.config.getString("alias", teamName);
        prefix = this.config.getString("prefix", null);

        spawnPoint = this.config.getLocation("spawnPoint");
        nexusBlocks = (List<Location>) this.config.getList("nexusBlocks");
    }

    public boolean canPlayGame(){
        if(spawnPoint == null){
            Man10BankRobber.logWarn(teamName + "のスポーンがセットされていません");
            return false;
        }
        if(nexusBlocks == null || nexusBlocks.size() == 0){
            Man10BankRobber.logWarn(teamName + "のネクサスブロックがセットされていません");
            return false;
        }
        return true;
    }

    public void teleportAllPlayersToSpawn(){
        for(RobberPlayer player: players.values()){
            player.getPlayer().teleport(spawnPoint);
        }
    }


}
