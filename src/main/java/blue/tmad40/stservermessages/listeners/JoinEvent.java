
package main.java.blue.tmad40.stservermessages.listeners;

import main.java.blue.tmad40.stservermessages.Main;
import main.java.blue.tmad40.stservermessages.files.Config;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerListHeaderFooter;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.lang.reflect.Field;


public class JoinEvent implements Listener {

	/*
    // Method to set the tab list header/footer
    public static void HeaderFooter(Player arg0, String arg1, String arg2) {
        if (arg1 == null) {
            arg1 = "";
        }
        if (arg2 == null) {
            arg2 = "";
        }
        PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter(IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + arg1 + "\"}"));
        try {
            Field b = packet.getClass().getDeclaredField("b");
            b.setAccessible(true);
            b.set(packet, IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + arg2 + "\"}"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        ((CraftPlayer) arg0).getHandle().playerConnection.sendPacket(packet);
    }
    */

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {

		// Set the join message
		String join = Main.getInstance().getMessagesString("messages.join");
		event.setJoinMessage(join.replaceAll("%player%", event.getPlayer().getName()));

		/*
		// Set the tab list header and footer
		String header = Main.getInstance().getConfigString("tab-list.header");
		String footer = Main.getInstance().getConfigString("tab-list.footer");

		HeaderFooter(event.getPlayer(), header, footer);
		*/
		// Set the default values for tips/ads if they aren't already there
		if (!Config.getInstance().storage.getBoolean("preferences." + event.getPlayer().getName() + ".set")) {

			if (Main.getInstance().getConfig().getBoolean("announcements.tips.enabled")) {
				Config.getInstance().storage.set("preferences." + event.getPlayer().getName() + ".tips", true);
				Config.getInstance().storage.set("storage.preferences." + event.getPlayer().getName() + ".set", true);
			}

			if (Main.getInstance().getConfig().getBoolean("announcements.ads.enabled")) {
				Config.getInstance().storage.set("preferences." + event.getPlayer().getName() + ".ads", true);
				Config.getInstance().storage.set("preferences." + event.getPlayer().getName() + ".set", true);
			}

			Config.getInstance().saveFiles("storage");

		}
	}

}
