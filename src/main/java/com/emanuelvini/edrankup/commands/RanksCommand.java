package com.emanuelvini.edrankup.commands;

import com.emanuelvini.edrankup.inventories.RanksInventory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RanksCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            RanksInventory.INVENTORY.open((Player) commandSender);
        }
        return false;
    }
}
