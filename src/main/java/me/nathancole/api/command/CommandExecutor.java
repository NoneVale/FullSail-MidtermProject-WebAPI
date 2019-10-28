package me.nathancole.api.command;

public interface CommandExecutor {

    boolean onCommand(CommandSender sender, Command command, String[] args);
}