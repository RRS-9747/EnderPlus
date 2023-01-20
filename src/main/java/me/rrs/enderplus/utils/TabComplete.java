package me.rrs.enderplus.utils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TabComplete implements TabCompleter {

    private final List<String> results = new ArrayList<>();
    private final List<String> completions = new ArrayList<>();

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, Command cmd, @NotNull String label, String[] args) {
        if ("enderplus".equals(cmd.getName()) && 1 == args.length) {
            results.clear();
            results.add("help");
            results.add("reload");
        }
        return sortedResults(args[0]);
    }

    private List<String> sortedResults(final String arg) {
        completions.clear();
        StringUtil.copyPartialMatches(arg, results, completions);
        Collections.sort(completions);
        return completions;
    }
}