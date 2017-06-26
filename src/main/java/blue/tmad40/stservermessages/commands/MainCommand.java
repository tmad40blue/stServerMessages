
package main.java.blue.tmad40.stservermessages.commands;

import main.java.blue.tmad40.stservermessages.Main;
import main.java.blue.tmad40.stservermessages.files.Config;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class MainCommand implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("stsm")) { // Start stsm command
			if (sender instanceof Player) { // Check if the sender is a player

				Player player = (Player) sender;

				if (args.length > 0) { // Check if the argument length is greater than 0

					if (args[0].equalsIgnoreCase("reload")) { // Reload Command
						if (player.hasPermission("stServerMessages.reload")) { // Check if player has permission to use this command

							// Reload the config, send a message
							Main.getInstance().reloadConfig();
							Config.getInstance().loadFiles();

							String prefix = Main.getInstance().getMessagesString("prefix.server");

							sender.sendMessage(prefix + "§aConfig reloaded");

							return true;

						} else {

							// Send an error message
							String prefix = Main.getInstance().getMessagesString("prefix.server");
							String noPerms = Main.getInstance().getMessagesString("messages.no-perms");

							sender.sendMessage(prefix + noPerms);

							return true;

						}

					} else if (args[0].equalsIgnoreCase("ads")) { // Ads Preference Command
						if (Main.getInstance().getConfig().getBoolean("announcements.ads.enabled")) { // Check if ads are enabled
							if (player.hasPermission("stServerMessages.preferences.ads")) { // Check if player has permission to use this command
								if (args.length > 1) { // Check if the argument length is greater than 1

									if (args[1].equalsIgnoreCase("true")) {

										// Set the players preference to true, save the config
										Main.getInstance().getConfig().set("storage.preferences." + player.getName() + ".ads", "true");
										Config.getInstance().saveFiles("storage");

										// Send a message
										String prefix = Main.getInstance().getMessagesString("prefix.server");
										String adsSetTrue = Main.getInstance().getMessagesString("messages.ads-set-true");

										sender.sendMessage(prefix + adsSetTrue);

										return true;

									} else if (args[1].equalsIgnoreCase("false")) {

										// Set the players preference to false, save the config
										Main.getInstance().getConfig().set("storage.preferences." + player.getName() + ".ads", "false");
										Config.getInstance().saveFiles("storage");

										// Send a message
										String prefix = Main.getInstance().getMessagesString("prefix.server");
										String adsSetFalse = Main.getInstance().getMessagesString("messages.ads-set-false");

										sender.sendMessage(prefix + adsSetFalse);

										return true;

									} else { // If the argument doesn't match any of the options

										// Send an error message
										String prefix = Main.getInstance().getMessagesString("prefix.server");
										String invalidArg = Main.getInstance().getMessagesString("messages.invalid-arg").replaceAll("%arg%", args[1]).replaceAll("%args%",
												"<true|false>");

										sender.sendMessage(prefix + invalidArg);

										return true;

									}
								} else { // If there are no arguments

									// Send an error message
									String prefix = Main.getInstance().getMessagesString("prefix.server");
									String noArgs = Main.getInstance().getMessagesString("messages.no-args").replaceAll("%args%", "<true|false>");

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
						}
					} else if (args[0].equalsIgnoreCase("tips")) { // Tips Preference Command
						if (Main.getInstance().getConfig().getBoolean("announcements.tips.enabled")) { // Check if tips are enabled
							if (player.hasPermission("stServerMessages.preferences.tips")) { // Check if player has permission to use this command
								if (args.length > 1) { // Check if the argument length is greater than 1

									if (args[1].equalsIgnoreCase("true")) {

										// Set the players preference to true, save the config
										Main.getInstance().getConfig().set("storage.preferences." + player.getName() + ".tips", "true");
										Config.getInstance().saveFiles("storage");

										// Send a message
										String prefix = Main.getInstance().getMessagesString("prefix.server");
										String tipsSetTrue = Main.getInstance().getMessagesString("messages.tips-set-true");

										sender.sendMessage(prefix + tipsSetTrue);

										return true;

									} else if (args[1].equalsIgnoreCase("false")) {

										// Set the players preference to true, save the config
										Main.getInstance().getConfig().set("storage.preferences." + player.getName() + ".tips", "false");
										Config.getInstance().saveFiles("storage");

										// Send a message
										String prefix = Main.getInstance().getMessagesString("prefix.server");
										String tipsSetFalse = Main.getInstance().getMessagesString("messages.tips-set-false");

										sender.sendMessage(prefix + tipsSetFalse);

										return true;

									} else { // If the argument doesn't match any of the options

										// Send an error message
										String prefix = Main.getInstance().getMessagesString("prefix.server");
										String invalidArg = Main.getInstance().getMessagesString("messages.invalid-arg").replaceAll("%arg%", args[1]).replaceAll("%args%",
												"<true|false>");

										sender.sendMessage(prefix + invalidArg);

										return true;

									}
								} else { // If there are no arguments

									// Send an error message
									String prefix = Main.getInstance().getMessagesString("prefix.server");
									String noArgs = Main.getInstance().getMessagesString("messages.no-args").replaceAll("%args%", "<true|false>");

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
						}
					} else { // If the argument doesn't match any of the options

						// Send an error message
						String prefix = Main.getInstance().getMessagesString("prefix.server");
						String invalidArg = Main.getInstance().getMessagesString("messages.invalid-arg").replaceAll("%arg%", args[0]).replaceAll("%args%", "<tips|ads>");

						sender.sendMessage(prefix + invalidArg);

						return true;

					}
				} else { // If there are no arguments

					// Send an error message
					String prefix = Main.getInstance().getMessagesString("prefix.server");
					String noArgs = Main.getInstance().getMessagesString("messages.no-args").replaceAll("%args%", "<tips|ads>");

					sender.sendMessage(prefix + noArgs);

					return true;

				}
			} else { // If the sender isn't a player, IE console
				if (args.length > 0) { // Check if the argument length is greater than 0
					if (args[0].equalsIgnoreCase("reload")) { // Reload Command

						// Reload the config, send a message
						Main.getInstance().reloadConfig();
						Config.getInstance().loadFiles();

						Main.getInstance().getLogger().info("Config reloaded");

						return true;

					} else { // If the argument doesn't match any of the options

						// Send an error message
						String invalidArg = Main.getInstance().getMessagesString("messages.invalid-arg").replaceAll("%arg%", args[0]).replaceAll("%args%", "<reload>");

						Main.getInstance().getLogger().info(invalidArg);

						return true;

					}
				} else { // If there are no arguments

					// Send an error message
					String noArgs = Main.getInstance().getMessagesString("messages.no-args").replaceAll("%args%", "<reload>");

					Main.getInstance().getLogger().info(noArgs);

					return true;

				}
			}
		}

		return false;

	}
}
