package com.emanuelvini.edrankup.util;

import com.emanuelvini.edrankup.models.Rank;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public class ConfigurationManager {

    public static List<Rank> ranks = new ArrayList<>();

    public static void loadConfig(FileConfiguration configuration) {
        for (String rankKey : configuration.getConfigurationSection("ranks").getKeys(false)) {
            ConfigurationSection rankSection = configuration.getConfigurationSection(String.format("ranks.%s", rankKey));
            Rank rank = new Rank(rankSection.getDouble("coins"), rankSection.getDouble("exp"), rankSection.getDouble("time"),
                    rankSection.getString("permission"), parseColored(rankSection.getString("name")), parseColored(rankSection.getString("tag")),
                    rankSection.getString("texture"),
                    rankSection.getStringList("commands"), parseColored(rankSection.getStringList("lore")));
            ranks.add(rank);
            Bukkit.getConsoleSender().sendMessage(String.format("§b[edRankUP] §eRank §6%s§e carregado com sucesso!", rank.getName()));
        }
    }

    public static Rank getRank(Player p) {
        Rank rank = null;
        for (Rank r : ranks) {
            if (p.hasPermission(r.getPermission())) {
                rank = r;
            }
        }
        return rank;
    }

    public static String parseColored(String coloredString) {
        return coloredString.replace("&","§");
    }
    public static List<String> parseColored(List<String> coloredStrings) {
        return coloredStrings.stream().map(ConfigurationManager::parseColored).collect(Collectors.toList());
    }
    private static final NavigableMap<Long, String> suffixes = new TreeMap<>();
    static {
        suffixes.put(1_000L, "k");
        suffixes.put(1_000_000L, "M");
        suffixes.put(1_000_000_000L, "B");
        suffixes.put(1_000_000_000_000L, "T");
        suffixes.put(1_000_000_000_000_000L, "Q");
        suffixes.put(1_000_000_000_000_000_000L, "QQ");
    }

    public static String format(long value) {
        //Long.MIN_VALUE == -Long.MIN_VALUE so we need an adjustment here
        if (value == Long.MIN_VALUE) return format(Long.MIN_VALUE + 1);
        if (value < 0) return "-" + format(-value);
        if (value < 1000) return Long.toString(value); //deal with easy case

        Map.Entry<Long, String> e = suffixes.floorEntry(value);
        Long divideBy = e.getKey();
        String suffix = e.getValue();

        long truncated = value / (divideBy / 10); //the number part of the output times 10
        boolean hasDecimal = truncated < 100 && (truncated / 10d) != (truncated / 10);
        return hasDecimal ? (truncated / 10d) + suffix : (truncated / 10) + suffix;
    }
}
