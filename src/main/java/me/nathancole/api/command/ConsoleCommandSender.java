package me.nathancole.api.command;

public class ConsoleCommandSender implements CommandSender {
    @Override
    public void sendMessage(String message) {
        System.out.println(message);
    }
}
