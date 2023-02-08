package yusama125718.man10_bank_robber.data_class;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import yusama125718.man10_bank_robber.Man10BankRobber;
import yusama125718.man10_bank_robber.data_class.states.EntryState;
import yusama125718.man10_bank_robber.enums.RobberGameStateType;
import yusama125718.man10_bank_robber.enums.NexusMode;

import java.util.HashMap;
import java.util.UUID;

public class RobberGame {

    String gameName;
    public FileConfiguration config;
    Man10BankRobber plugin;
    HashMap<String, RobberTeamData> teams = new HashMap<>();
    public HashMap<UUID, RobberPlayer> preRegisteredPlayers = new HashMap<>();

    //ゲームステート
    RobberGameStateType gameStateType = RobberGameStateType.NO_GAME;
    RobberGameStateData gameState;

    // === コンフィグ ====
    //ロケーション
    Location readyLocation;
    //ベット金額
    int minimumBet = 10000;
    int maximumBet = 0;
    //プレイヤー人数
    int minimumPlayersPerTeam = 3;
    int maximumPlayersPerTeam = 5;
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
                teams.put(teamName, new RobberTeamData(this, teamName, teamsData.getConfigurationSection(teamName)));
            }
        }
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
        return;
    }

    public RobberGameStateData getStateData(RobberGameStateType state){
        switch (state){
            case ENTRY -> {
                return new EntryState();
            }
        }
        return null;
    }




}
