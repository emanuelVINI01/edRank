package com.emanuelvini.edrankup.placeholder;

import com.emanuelvini.edmina.Main;
import com.emanuelvini.edrankup.models.Rank;
import com.emanuelvini.edrankup.util.ConfigurationManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RankPlaceholder extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "edrankup";
    }

    @Override
    public @NotNull String getAuthor() {
        return "emanuelVINI";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.1";
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        Rank rank = ConfigurationManager.getRank(player);
        if (params.equalsIgnoreCase("rank")) {
            return rank.getName();
        }
        if (params.equalsIgnoreCase("tag")) {
            return rank.getTag();
        }
        if (params.equalsIgnoreCase("exp")) {
            return ConfigurationManager.format(Math.round(rank.getExp()));
        }
        if (params.equalsIgnoreCase("bar")) {
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
            return bar.toString();
        }
        if (params.equalsIgnoreCase("clan_with_name")) {
            ClanPlayer clanPlayer = SimpleClans.getInstance().getClanManager().getClanPlayer(player);
            if (clanPlayer != null) {
                return String.format("§7[%s§7]", clanPlayer.getClan().getColorTag().replace("&","§"));
            } else {
                return "§cNenhum";
            }
        }
        if (params.equalsIgnoreCase("clan")) {
            ClanPlayer clanPlayer = SimpleClans.getInstance().getClanManager().getClanPlayer(player);
            if (clanPlayer != null) {
                return String.format("§7[%s§7] ", clanPlayer.getClan().getColorTag().replace("&","§"));
            } else {
                return "";
            }
        }
        if (params.equalsIgnoreCase("clan_tab")) {
            ClanPlayer clanPlayer = SimpleClans.getInstance().getClanManager().getClanPlayer(player);
            if (clanPlayer != null) {
                return String.format(" §7[%s§7] ", clanPlayer.getClan().getColorTag().replace("&","§"));
            } else {
                return "";
            }
        }
        return null;
    }
}
