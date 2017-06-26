
package main.java.blue.tmad40.stservermessages.commands;

import main.java.blue.tmad40.stservermessages.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class SayCommand implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("say")) { // Start say command
			if (sender instanceof Player) { // Check if the sender is a player

				Player player = (Player) sender;

				if (player.hasPermission("stServerMessages.say")) { // Check if player has permission to use this command
					if (args.length > 0) { // Check if the argument length is greater than 0

						// Get all the arguments, make a string with them
						StringBuilder stringBuilder = new StringBuilder();

						for (int i = 0; i < args.length; i++) {
							stringBuilder.append(args[i]).append(" ");
						}

						String fullMessage = stringBuilder.toString().trim();

						// Broadcast the message
						String sayFormat = Main.getInstance().getMessagesString("messages.say");

						Bukkit.broadcastMessage(sayFormat.replaceAll("%message%", fullMessage));

						return true;

					} else { // If there are no arguments

						// Send an error message
						String prefix = Main.getInstance().getMessagesString("prefix.server");
						String noArgs = Main.getInstance().getMessagesString("messages.no-args").replaceAll("%args%", "<message>");

						sender.sendMessage(prefix + noArgs);

						return true;

					}
				} else {// Broadcast the message

					// Send an error message
					String prefix = Main.getInstance().getMessagesString("prefix.server");
					String noPerms = Main.getInstance().getMessagesString("messages.no-perms");

					sender.sendMessage(prefix + noPerms);

					return true;

				}
			} else { // If the sender isn't a player, IE console
				if (args.length > 0) { // Check if the argument length is greater than 0

					// Get all the arguments, make a string with them
					StringBuilder stringBuilder = new StringBuilder();

					for (int i = 0; i < args.length; i++) {
						stringBuilder.append(args[i]).append(" ");
					}

					String fullMessage = stringBuilder.toString().trim();

					// Broadcast the message
					String sayFormat = Main.getInstance().getMessagesString("messages.say");

					Bukkit.broadcastMessage(sayFormat.replaceAll("%message%", fullMessage));

					return true;

				} else { // If there are no arguments

					// Send an error message
					String noArgs = Main.getInstance().getMessagesString("messages.no-args").replaceAll("%args%", "<message>");

					Main.getInstance().getLogger().info(noArgs);

					return true;

				}
			}
		}

		return false;

	}
}
