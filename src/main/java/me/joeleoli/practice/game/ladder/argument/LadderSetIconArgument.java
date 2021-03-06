package me.joeleoli.practice.game.ladder.argument;

import me.joeleoli.practice.command.CommandException;
import me.joeleoli.practice.command.PluginCommandArgument;
import me.joeleoli.practice.game.ladder.Ladder;
import me.joeleoli.practice.manager.ManagerHandler;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LadderSetIconArgument extends PluginCommandArgument {

    private List<String> aliases = Collections.emptyList();

    public List<String> getAliases() {
        return this.aliases;
    }

    public boolean requiresPlayer() {
        return true;
    }

    public boolean requiresPermission() {
        return true;
    }

    public String getPermission() {
        return "practice.admin";
    }

    public void onCommand(CommandSender sender, String[] args) throws CommandException {
        if (args.length < 2) {
            throw new CommandException(Collections.singletonList("You provided too few arguments."));
        }

        if (!ManagerHandler.getLadderManager().getLadders().containsKey(args[1])) {
            throw new CommandException(Collections.singletonList("That ladder does not exist."));
        }

        Player player = (Player) sender;

        if (player.getItemInHand() == null) {
            throw new CommandException(Collections.singletonList("You must have an item in your hand."));
        }

        Ladder ladder = ManagerHandler.getLadderManager().getLadders().get(args[1]);

        ladder.setDisplayIcon(player.getItemInHand());
        ladder.save(player);
    }

    public List<String> onTabComplete(CommandSender sender, String[] args) {
        ArrayList<String> returnList = new ArrayList<>();

        if (args.length == 2) {
            for (String name : ManagerHandler.getLadderManager().getLadders().keySet()) {
                if (name.toLowerCase().startsWith(args[1].toLowerCase())) {
                    returnList.add(name);
                }
            }
        }
        else {
            for (String name : ManagerHandler.getLadderManager().getLadders().keySet()) {
                returnList.add(name);
            }
        }

        return returnList;
    }

}