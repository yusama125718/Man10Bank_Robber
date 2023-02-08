package yusama125718.man10_bank_robber.commands.normal_commands;


import com.shojabon.mcutils.Utils.SCommandRouter.SCommandArgument;
import com.shojabon.mcutils.Utils.SCommandRouter.SCommandObject;
import com.shojabon.mcutils.Utils.SCommandRouter.SCommandRouter;
import yusama125718.man10_bank_robber.Man10BankRobber;
import yusama125718.man10_bank_robber.Man10_Bank_Robber;
import yusama125718.man10_bank_robber.commands.normal_commands.sub_commands.RegisterCommand;


public class NormalCommands extends SCommandRouter {

    Man10BankRobber plugin;

    public NormalCommands(Man10BankRobber plugin){
        this.plugin = plugin;
        registerCommands();
        registerEvents();
    }

    public void registerEvents(){
//        setNoPermissionEvent(e -> e.sender.sendMessage(Man10_Bank_Robber.prefix + "§c§lあなたは権限がありません"));
//        setOnNoCommandFoundEvent(e -> e.sender.sendMessage(Man10ShopV3.prefix + "§c§lコマンドが存在しません"));
    }

    public void registerCommands(){
        addCommand(
                new SCommandObject()
                        .addArgument(new SCommandArgument().addAllowedString("register"))

                        .addRequiredPermission("man10bankrobber.register")
                        .addExplanation("ゲームに登録").
                        setExecutor(new RegisterCommand(plugin))
        );
    }

}
