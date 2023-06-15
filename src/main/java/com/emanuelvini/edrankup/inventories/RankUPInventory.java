package com.emanuelvini.edrankup.inventories;

import com.emanuelvini.edmina.Main;
import com.emanuelvini.edmina.util.ItemStackBuilder;
import com.emanuelvini.edmina.util.PlayerManager;
import com.emanuelvini.edrankup.models.Rank;
import com.emanuelvini.edrankup.util.ActionBar;
import com.emanuelvini.edrankup.util.ConfigurationManager;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class RankUPInventory implements InventoryProvider {
    public static final SmartInventory INVENTORY = SmartInventory.builder()
            .provider(new RankUPInventory())
            .size(4, 9)
            .title("§eRankUP")
            .build();
    @Override
    public void init(Player player, InventoryContents inventoryContents) {
        Rank rank = ConfigurationManager.getRank(player);
        long squares = 16;
        long greenSquares = Math.round((Math.min(Main.economy.getBalance(player)/rank.getCoins(), 1)*100)/6);
        StringBuilder bar = new StringBuilder("&a");
        for (int i = 0; i < greenSquares; i++) {
            bar.append("▌");
        }
        bar.append("&7");
        for (int i = 0; i < squares-greenSquares; i++) {
            bar.append("▌");
        }

        Rank nextRank = ConfigurationManager.ranks.get(ConfigurationManager.ranks.indexOf(rank) + 1);
        inventoryContents.set(1, 4, ClickableItem.empty(new ItemStackBuilder(Material.SIGN).withName("§eEvolução").withLore(
                " §eInformações da Evolução: ",
                "§r",
                String.format("  §8➟ §fPróximo Rank: %s", nextRank.getTag()),
                "§r",
                String.format("  §8➟ §fProgresso: %s", bar),
                "§r",
                " §eRequisitos da Evolução: ",
                String.format("  §8➟ §fDinheiro: §2$§a%s", ConfigurationManager.format(Math.round(rank.getCoins()))),
                String.format("  §8➟ §fEXP: §b♣%s", ConfigurationManager.format(Math.round(rank.getExp())))
        ).buildStack()));

        inventoryContents.set(1,1, ClickableItem.of(new ItemStackBuilder(Material.WOOL).withData(14).withName("§cCancelar").buildStack(), e -> {
            player.closeInventory();
        }));
        inventoryContents.set(1,7, ClickableItem.of(new ItemStackBuilder(Material.WOOL).withData(13).withName("§aConfirmar").buildStack(), e -> {
            if (PlayerManager.getXP(player.getName()) >= rank.getExp() && Main.economy.getBalance(player) >= rank.getCoins()) {
                if (ConfigurationManager.ranks.indexOf(rank) == ConfigurationManager.ranks.size()-1) {
                    player.sendMessage("§cVocê já está no último rank!");
                } else {
                    PlayerManager.withdrawXP(player.getName(), rank.getExp());
                    Main.economy.withdrawPlayer(player, rank.getCoins());
                    for (String command : rank.getCommands()) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("$player", player.getName()));
                    }
                    for (Player pd : Bukkit.getOnlinePlayers()) {
                        ActionBar.sendActionBar(pd, String.format("§e%s§a evoluiu para o rank %s§a!", player.getName(), nextRank.getTag()));
                    }
                    player.sendMessage("§a§lGG! §aVocê evoluiu de rank com sucesso.");
                    player.closeInventory();
                }
            } else {
                player.sendMessage("§cVocê não tem os recursos necessários para evoluir de rank! Confira em /ranks");
            }
        }));
    }

    @Override
    public void update(Player player, InventoryContents inventoryContents) {
        Rank rank = ConfigurationManager.getRank(player);
        long squares = 16;
        long greenSquares = Math.round((Math.min(Main.economy.getBalance(player)/rank.getCoins(), 1)*100)/6);
        StringBuilder bar = new StringBuilder("&a");
        for (int i = 0; i < greenSquares; i++) {
            bar.append("▌");
        }
        bar.append("&7");
        for (int i = 0; i < squares-greenSquares; i++) {
            bar.append("▌");
        }

        Rank nextRank = ConfigurationManager.ranks.get(ConfigurationManager.ranks.indexOf(rank) + 1);
        inventoryContents.set(1, 4, ClickableItem.empty(new ItemStackBuilder(Material.SIGN).withName("§eEvolução").withLore(
                " §eInformações da Evolução: ",
                "§r",
                String.format("  §8➟ §fPróximo Rank: %s", nextRank.getTag()),
                "§r",
                String.format("  §8➟ §fProgresso: %s", bar),
                "§r",
                " §eRequisitos da Evolução: ",
                String.format("  §8➟ §fDinheiro: §2$§a%s", ConfigurationManager.format(Math.round(rank.getCoins()))),
                String.format("  §8➟ §fEXP: §b♣%s", ConfigurationManager.format(Math.round(rank.getExp())))
        ).buildStack()));
    }
}
