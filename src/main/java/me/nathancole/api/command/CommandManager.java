package me.nathancole.api.command;

import com.google.common.collect.Maps;

import java.util.Collection;
import java.util.concurrent.ConcurrentMap;

public class CommandManager {

    private ConcurrentMap<String, Command> cmds;

    public CommandManager() {
        cmds = Maps.newConcurrentMap();
    }

    public Command getCommand(String cmd) {
        for (String s : cmds.keySet()) {
            if (cmd.equalsIgnoreCase(s)) {
                return cmds.get(s);
            }
        }

        Command command = new Command(cmd);
        cmds.put(cmd, command);
        return command;
    }

    public boolean isAlias(String label) {
        for (Command c : cmds.values()) {
            if (c.getAliases().contains(label.toLowerCase())) return true;
        }
        return false;
    }

    public Command getCommandFromAlias(String label) {
        for (Command c : cmds.values()) {
            if (c.getAliases().contains(label.toLowerCase())) return c;
        }
        return null;
    }

    public boolean commandRegistered(String label) {
        for (Command c : cmds.values()) {
            if (c.getAliases().contains(label.toLowerCase())) return true;
        }
        return cmds.keySet().contains(label);
    }

    public Collection<Command> getCommands() {
        return cmds.values();
    }

    public boolean callCommand(CommandSender sender, Command command, String[] args) {
        command.getExecutor().onCommand(sender, command, args);
        return false;
    }
}
