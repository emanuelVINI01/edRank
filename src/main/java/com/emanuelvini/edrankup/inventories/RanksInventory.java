package com.emanuelvini.edrankup.inventories;

import com.emanuelvini.edrankup.models.Rank;
import com.emanuelvini.edrankup.util.ConfigurationManager;
import com.emanuelvini.edrankup.util.ItemStackBuilder;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.content.Pagination;
import fr.minuskube.inv.content.SlotIterator;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RanksInventory implements InventoryProvider {
    public static final SmartInventory INVENTORY = SmartInventory.builder()
            .provider(new RanksInventory())
            .size(4, 9)
            .title("§eRanks")
            .build();
    @Override
    public void init(Player player, InventoryContents contents) {
        Pagination pagination = contents.pagination();

        ArrayList<ClickableItem> rankItems = new ArrayList<>();

        for (Rank r : ConfigurationManager.ranks) {
            List<String> lore = new ArrayList<>(Arrays.asList(String.format("&fDinheiro: &2&l$&a%s", ConfigurationManager.format(Math.round(r.getCoins()))),
                    String.format("&fEXP: &e✤ %s", ConfigurationManager.format(Math.round(r.getExp()))),
                    "§r"));
            lore.addAll(r.getLore());
            rankItems.add(ClickableItem.empty(
               new ItemStackBuilder().withName(r.getName()).withLore(lore).toSkullBuilder().withTexture(r.getTexture()).buildSkull()
            ));
            /*rankItems.add(ClickableItem.empty(
                    new ItemStackBuilder().asMaterial(Material.getMaterial(r.getBlockId())).withName(r.getName()).
                            withLore(lore).buildStack()
            ));*/
        }

        pagination.setItems(rankItems.toArray(new ClickableItem[0]));
        pagination.setItemsPerPage(7);

        pagination.addToIterator(contents.newIterator(SlotIterator.Type.HORIZONTAL, 1, 1));

        contents.set(2, 3, ClickableItem.of(new ItemStackBuilder(Material.ARROW).withName("§cAnterior").buildStack(),
                e -> INVENTORY.open(player, pagination.previous().getPage())));
        contents.set(2, 5, ClickableItem.of(new ItemStackBuilder(Material.ARROW).withName("§aPróxima").buildStack(),
                e -> INVENTORY.open(player, pagination.next().getPage())));
    }

    @Override
    public void update(Player player, InventoryContents inventoryContents) {

    }
}
