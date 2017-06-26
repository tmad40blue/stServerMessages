package main.java.blue.tmad40.stservermessages;

import main.java.blue.tmad40.stservermessages.commands.MainCommand;
import main.java.blue.tmad40.stservermessages.commands.MeCommand;
import main.java.blue.tmad40.stservermessages.commands.SayCommand;
import main.java.blue.tmad40.stservermessages.files.Config;
import main.java.blue.tmad40.stservermessages.listeners.*;
import main.java.blue.tmad40.stservermessages.listeners.PingEvent;
import main.java.blue.tmad40.stservermessages.metrics.MetricsLite;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.io.IOException;
import java.util.List;
import java.util.Random;


public class Main extends JavaPlugin {

	// Provide instance of Main class
	private static Main instance;

	public Main() {
		instance = this;
	}

	public static Main getInstance() {
		return instance;
	}

	@Override
	public void onEnable() {

		this.saveDefaultConfig();
		Config.getInstance().loadFiles();

		// Register events
		getServer().getPluginManager().registerEvents(new PingEvent(), this);
		getServer().getPluginManager().registerEvents(new JoinEvent(), this);
		getServer().getPluginManager().registerEvents(new QuitEvent(), this);
		getServer().getPluginManager().registerEvents(new CommandEvent(), this);
		getServer().getPluginManager().registerEvents(new DeathEvent(), this);

		// Register commands
		this.getCommand("me").setExecutor(new MeCommand());
		this.getCommand("say").setExecutor(new SayCommand());
		this.getCommand("stsm").setExecutor(new MainCommand());

		// Setup variables for announcers
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		Random random = new Random();
		String messagesTipsPrefix = getConfigString("announcements.tips.prefix");
		String messagesAdsPrefix = getConfigString("announcements.ads.prefix");
		List<String> tipsList = getConfig().getStringList("announcements.tips.messages");
		List<String> adsList = getConfig().getStringList("announcements.ads.messages");

		// Tip announcer
		if (getConfig().getBoolean("announcements.tips.enabled")) {
			scheduler.scheduleSyncRepeatingTask(this, new Runnable() {

				@Override
				public void run() {
					String randomTip = tipsList.get(random.nextInt(tipsList.size()));
					String proccessedTip = ChatColor.translateAlternateColorCodes('&', randomTip).replaceAll("%newline%", "\n");

					// Send the tip to the console
					Bukkit.getConsoleSender().sendMessage(messagesTipsPrefix + proccessedTip);

					// Loop through online players, send the tip
					for (Player player : getServer().getOnlinePlayers()) {
						if (Config.getInstance().storage.getBoolean("preferences." + player.getName() + ".tips")) {
							player.sendMessage(messagesTipsPrefix + proccessedTip);
						}
					}
				}
			}, getConfig().getInt("announcements.tips.delay") * 20 * 60, getConfig().getInt("announcements.tips.interval") * 20 * 60);
		}

		// Ad announcer
		if (getConfig().getBoolean("announcements.ads.enabled")) {
			scheduler.scheduleSyncRepeatingTask(this, new Runnable() {

				@Override
				public void run() {
					String randomAd = adsList.get(random.nextInt(adsList.size()));
					String processedAd = ChatColor.translateAlternateColorCodes('&', randomAd).replaceAll("%newline%", "\n");

					// Send the ad to the console
					Bukkit.getConsoleSender().sendMessage(messagesAdsPrefix + processedAd);

					// Loop through online players, send the ad
					for (Player player : getServer().getOnlinePlayers()) {
						if (Config.getInstance().storage.getBoolean("preferences." + player.getName() + ".ads")) {
							player.sendMessage(messagesAdsPrefix + processedAd);
						}
					}
				}
			}, getConfig().getInt("announcements.ads.delay") * 20 * 60, getConfig().getInt("announcements.ads.interval") * 20 * 60);
		}

		// Death counter expire
		scheduler.scheduleSyncRepeatingTask(this, new Runnable() {

			@Override
			public void run() {

				// Loop through online players
				for (Player player : getServer().getOnlinePlayers()) {
					String name = player.getName();
					int counter = Config.getInstance().storage.getInt("death-counter." + name);
					if (counter > 0) {
						--counter;
						Config.getInstance().storage.set("death-counter." + name, counter);
					} else if (counter == 0) {
						Config.getInstance().storage.set("death-counter." + name, null);
					}
				}

				Config.getInstance().saveFiles("storage");
			}
		}, getConfig().getInt("deaths.counter-expire-interval") * 20 * 60, getConfig().getInt("deaths.counter-expire-interval") * 20 * 60);

		// Setup Metrics
		try {
			MetricsLite metrics = new MetricsLite(this);
			metrics.start();
		} catch (IOException e) {

		}
	}

	// Method for getting strings from the config with color codes
	public String getConfigString(String input) {
		return ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString(input));
	}

	public String getMessagesString(String input) {
		return ChatColor.translateAlternateColorCodes('&', Config.getInstance().messages.getString(input));
	}
}
