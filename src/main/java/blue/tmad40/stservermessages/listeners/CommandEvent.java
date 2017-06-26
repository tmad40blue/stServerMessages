
package main.java.blue.tmad40.stservermessages.listeners;

import main.java.blue.tmad40.stservermessages.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.List;


public class CommandEvent implements Listener {

	@EventHandler
	public void PlayerCommand(PlayerCommandPreprocessEvent event) {

		// Get a list of disallowed commands
		final List<String> commands = Main.getInstance().getConfig().getStringList("disallowed-commands");

		// Split the command input, get the first argument
		String command = event.getMessage().split(" ")[0].split("/")[1];

		// Loop through the first arguments, look for a match between the input and disallowed commands
		for (String s : commands) {
			if (command.trim().toLowerCase().equals(s.trim().toLowerCase())) {
				if (!(event.getPlayer().hasPermission("stServerMessages.commands"))) { // If the player doesn't have permission to use the disallowed command

					// Cancel the command
					event.setCancelled(true);

					// Send an error message
					String prefix = Main.getInstance().getMessagesString("prefix.server");
					String noPerms = Main.getInstance().getMessagesString("messages.no-perms");

					event.getPlayer().sendMessage(prefix + noPerms);

					// Stop the loop
					break;

				}
			}
		}

	}
}
