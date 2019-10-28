package me.nathancole.api.commands;

import me.nathancole.api.command.Command;
import me.nathancole.api.command.CommandExecutor;
import me.nathancole.api.command.CommandSender;

public class StopCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String[] args) {
        sender.sendMessage("STOP!!!!!!!!!!!!!!");
        return false;
    }
}
