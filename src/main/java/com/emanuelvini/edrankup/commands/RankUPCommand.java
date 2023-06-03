package com.emanuelvini.edrankup.commands;

import com.emanuelvini.edrankup.inventories.RankUPInventory;
import com.emanuelvini.edrankup.models.Rank;
import com.emanuelvini.edrankup.util.ActionBar;
import com.emanuelvini.edrankup.util.ConfigurationManager;
import com.emanuelvini.edmina.Main;
import com.emanuelvini.edmina.util.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RankUPCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;
            Rank actualRank = ConfigurationManager.getRank(p);

                if (ConfigurationManager.ranks.indexOf(actualRank) == ConfigurationManager.ranks.size()-1) {
                    p.sendMessage("§cVocê já está no último rank!");
                } else {
                    RankUPInventory.INVENTORY.open(p);
                }
        }
        return false;
    }
}
