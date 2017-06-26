
package main.java.blue.tmad40.stservermessages.commands;

import main.java.blue.tmad40.stservermessages.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class MeCommand implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("me")) { // Start me command
			if (sender instanceof Player) { // Check if the sender is a player

				Player player = (Player) sender;

				if (player.hasPermission("stServerMessages.me")) { // Check if player has permission to use this command
					if (args.length > 0) { // Check if the argument length is greater than 0

						// Get all the arguments, make a string with them
						StringBuilder stringBuilder = new StringBuilder();

						for (int i = 0; i < args.length; i++) {
							stringBuilder.append(args[i]).append(" ");
						}

						String fullMessage = stringBuilder.toString().trim();

						// Broadcast the message
						// TODO: Send this message as a player, not a broadcast
						String meFormat = Main.getInstance().getMessagesString("messages.me");

						Bukkit.broadcastMessage(meFormat.replaceAll("%player%", player.getName()).replaceAll("%message%", fullMessage));

						return true;

					} else { // If there are no arguments

						// Send an error message
						String prefix = Main.getInstance().getMessagesString("prefix.server");
						String noArgs = Main.getInstance().getMessagesString("messages.no-args".replaceAll("%args%", "<message>"));

						sender.sendMessage(prefix + noArgs);

						return true;

					}
				} else { // If the player doesn't have permission to run the command

					// Send an error message
					String prefix = Main.getInstance().getMessagesString("prefix.server");
					String noPerms = Main.getInstance().getMessagesString("messages.no-perms");

					sender.sendMessage(prefix + noPerms);

					return true;

				}
			} else { // If the sender isn't a player, IE console

				// Send an error message
				String noConsole = Main.getInstance().getMessagesString("messages.no-console");

				Main.getInstance().getLogger().info(noConsole);

				return true;

			}
		}

		return false;

	}
}
