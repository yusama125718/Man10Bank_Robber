package yusama125718.man10_bank_robber.data_class;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import yusama125718.man10_bank_robber.Man10BankRobber;
import yusama125718.man10_bank_robber.data_class.states.EntryState;
import yusama125718.man10_bank_robber.data_class.states.ReadyState;
import yusama125718.man10_bank_robber.enums.RobberGameStateType;
import yusama125718.man10_bank_robber.enums.NexusMode;

import java.util.HashMap;
import java.util.UUID;

public class RobberGame {

    String gameName;
    public FileConfiguration config;
    Man10BankRobber plugin;
    public HashMap<String, RobberTeam> teams = new HashMap<>();
    public HashMap<UUID, RobberPlayer> preRegisteredPlayers = new HashMap<>();

    //ゲームステート
    public RobberGameStateType gameStateType = RobberGameStateType.NO_GAME;
    public RobberGameStateData gameState;

    // === コンフィグ ====
    //ロケーション
    Location readyLocation;
    //ベット金額
    public int minimumBet = 10000;
    public int maximumBet = 0;
    //プレイヤー人数
    public int minimumPlayersPerTeam = 3;
    public int maximumPlayersPerTeam = 5;
    //ネクサス金額モード
    NexusMode nexusMode = NexusMode.FIXED;
    int nexusModeValue = 1000;

    //時間
    public int timeENTRY = 60;
    public int timeREADY = 60;
    public int timeIN_GAME = 300;
    public RobberGame(Man10BankRobber plugin, String gameName, FileConfiguration config){
        this.gameName = gameName;
        this.config = config;
        this.plugin = plugin;

        loadConfig();
    }

    private void loadConfig(){
        readyLocation = this.config.getLocation("locations.ready");

        minimumBet = this.config.getInt("bet.minimumBet", minimumBet);
        maximumBet = this.config.getInt("bet.maximumBet", maximumBet);

        minimumPlayersPerTeam = this.config.getInt("players.minimumPlayersPerTeam", minimumPlayersPerTeam);
        maximumPlayersPerTeam = this.config.getInt("players.maximumPlayersPerTeam", maximumPlayersPerTeam);

        String nexusModeString = this.config.getString("nexus.mode");
        if(!NexusMode.isEnumString(nexusModeString)) nexusModeString = "FIXED";
        nexusMode = NexusMode.valueOf(nexusModeString);
        nexusModeValue = this.config.getInt("nexus.value", nexusModeValue);

        // 時間データをロードする

        timeENTRY = this.config.getInt("time.ENTRY", 60);
        timeREADY = this.config.getInt("time.READY", 60);
        timeIN_GAME = this.config.getInt("time.IN_GAME", 300);


        // チームデータをロードする
        ConfigurationSection teamsData = this.config.getConfigurationSection("teams");
        if(teamsData != null){
            for(String teamName: teamsData.getKeys(false)){
                teams.put(teamName, new RobberTeam(this, teamName, teamsData.getConfigurationSection(teamName)));
            }
        }
    }

    public RobberTeam getTeam(String teamName){
        return teams.get(teamName);
    }

    public boolean registerPlayer(Player p, int betPrice){
        RobberPlayer player = new RobberPlayer(p);
        player.betPrice = betPrice;

        if(!player.takeMoney(player.betPrice)){
            return false;
        }

        if(preRegisteredPlayers.containsKey(p.getUniqueId())) return false;
        preRegisteredPlayers.put(p.getUniqueId(), player);
        return true;
    }

    public boolean unRegisterPlayer(UUID uuid){
        if(!preRegisteredPlayers.containsKey(uuid)) return false;
        RobberPlayer player = preRegisteredPlayers.get(uuid);
        preRegisteredPlayers.remove(uuid);
        player.giveMoney(player.betPrice);
        return true;
    }

    public void teleportPlayersToReadyArea(){

    }


    public boolean canPlayGame(){
        for(String team: teams.keySet()){
            if(!teams.get(team).canPlayGame()) return false;
        }
        if(maximumPlayersPerTeam < minimumPlayersPerTeam || maximumPlayersPerTeam <= 0 || minimumPlayersPerTeam < 0){
            Man10BankRobber.logWarn("最大最小プレイヤー人数が不正です");
            return false;
        }
        if((maximumBet != 0 && maximumBet < minimumBet) || maximumBet < 0 || minimumBet < 0){
            Man10BankRobber.logWarn("最大最小ベット金額が不正です");
            return false;
        }
        if(nexusMode == NexusMode.PERCENTAGE && (nexusModeValue > 100 || nexusModeValue <= 0)){
            Man10BankRobber.logWarn("ネクサスモードの値が不正です");
            return false;
        }
        if(nexusMode == NexusMode.FIXED && nexusModeValue <= 0){
            Man10BankRobber.logWarn("ネクサスモードの値が不正です");
            return false;
        }
        if(readyLocation == null){
            Man10BankRobber.logWarn("準備位置が不正です");
            return false;
        }

        //時間チェック
        if(timeENTRY < 0 || timeREADY < 0 || timeIN_GAME < 0){
            Man10BankRobber.logWarn("時間の数値が不正です");
            return false;
        }

        return true;
    }

    public void setGameState(RobberGameStateType state){
        if(state == gameStateType) return;

        Bukkit.getScheduler().runTask(plugin, ()-> {
            //stop current state
            if(gameState != null){
                gameState.beforeEnd();
            }

            //start next state
            gameStateType = state;
            RobberGameStateData data = getStateData(gameStateType);
            if(data == null) return;
            gameState = data;
            gameState.beforeStart();
        });
    }

    public RobberGameStateData getStateData(RobberGameStateType state){
        switch (state){
            case ENTRY -> {
                return new EntryState();
            }
            case READY -> {
                return new ReadyState();
            }
        }
        return null;
    }




}
