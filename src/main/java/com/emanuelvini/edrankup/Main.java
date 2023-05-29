package com.emanuelvini.edrankup;

import com.emanuelvini.edrankup.commands.RankUPCommand;
import com.emanuelvini.edrankup.commands.RanksCommand;
import com.emanuelvini.edrankup.placeholder.RankPlaceholder;
import com.emanuelvini.edrankup.util.ConfigurationManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private Economy economy;
    @Override
    public void onEnable() {
        super.onEnable();
        saveDefaultConfig();
        ConfigurationManager.loadConfig(getConfig());
        new RankPlaceholder().register();
        setupEconomy();
        getCommand("rankup").setExecutor(new RankUPCommand());
        getCommand("ranks").setExecutor(new RanksCommand());
        getServer().getConsoleSender().sendMessage("§b[edRankUP] §aPlugin iniciado com sucesso!");
    }
    private boolean setupEconomy() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return economy != null;
    }
}
