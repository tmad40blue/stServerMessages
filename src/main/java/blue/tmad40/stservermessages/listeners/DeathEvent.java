
package main.java.blue.tmad40.stservermessages.listeners;

import main.java.blue.tmad40.stservermessages.Main;
import main.java.blue.tmad40.stservermessages.files.Config;
import org.apache.commons.lang.WordUtils;
import org.bukkit.entity.*;
import org.bukkit.entity.Skeleton.SkeletonType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.List;
import java.util.Random;


public class DeathEvent implements Listener {

	// TODO Make this more generic, put into main class
	public String parseStringListRandom(String input) {

		List<String> list = Config.getInstance().messages.getStringList(input);

		final Random random = new Random();
		final String raw = list.get(random.nextInt(list.size()));
		return raw.replaceAll("&", "§").replaceAll("§§", "&");

	}

	/*
	 * TODO Fix the following damage events
	 * Wither, magic, falling_block, block_explosion, poison, thorns, entity_explosion
	 */

	/*
	 * TODO Fix the following mobs/entities
	 * Witches, skeletons, creepers, ghasts, blazes, tnt, endercrystals, wither spawning, falling anvils
	 */

	/*
	 * TODO Fix the following PvP parts
	 * Potions, arrows
	 */

	/*
	 * TODO Add some other cool messages to mimic vanilla
	 * For example, player fell while running from a zombie
	 */

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {

		// Set player variables
		Player player = event.getEntity();
		String name = player.getName();

		// Get the current death counter of the deceased
		int counter = Config.getInstance().storage.getInt("death-counter." + name);

		if (counter <= Main.getInstance().getConfig().getInt("deaths.counter-before-hidden")) { // If the counter is less than or equal to the max deaths before being hidden

			// Increment counter, save it to the config
			++counter;

			Config.getInstance().storage.set("death-counter." + name, counter);
			Config.getInstance().saveFiles("storage");

			// Find out what that cause of death was, and who the killer is
			DamageCause cause = player.getLastDamageCause().getCause();

			if (cause == DamageCause.ENTITY_ATTACK) { // If the cause of death was an entity
				if (player.getLastDamageCause() instanceof EntityDamageByEntityEvent) {

					// Not sure what this does..
					EntityDamageByEntityEvent damageevent = (EntityDamageByEntityEvent) player.getLastDamageCause();

					if (player.getKiller() instanceof Player && (player.getKiller() != null)) { // If the killer was a player

						// Get the name of the killer and the name of their weapon
						String attacker = player.getKiller().getName();
						String weapon = WordUtils.capitalizeFully(player.getKiller().getItemInHand().getType().toString().replaceAll("_", " "));

						// Send the death message
						event.setDeathMessage(
								parseStringListRandom("deaths.pvp.any").replaceAll("%victim%", name).replaceAll("%attacker%", attacker).replaceAll("%weapon%", weapon));

					} else {
						Entity attacker = damageevent.getDamager();

						if (attacker instanceof Zombie) { // If the killer was a zombie

							String mob;

							// Check if the zombie was a jockey, baby, or villager
							if (attacker.getVehicle() != null && attacker.getVehicle() instanceof Chicken) {
								mob = Main.getInstance().getMessagesString("deaths.names.chicken-jockey");
							} else {
								if (((Zombie) attacker).isBaby()) {
									if (((Zombie) attacker).isVillager()) {
										mob = Main.getInstance().getMessagesString("deaths.names.baby-zombie-villager");
									} else {
										mob = Main.getInstance().getMessagesString("deaths.names.baby-zombie");
									}
								} else {
									if (((Zombie) attacker).isVillager()) {
										mob = Main.getInstance().getMessagesString("deaths.names.zombie-villager");
									} else {
										mob = Main.getInstance().getMessagesString("deaths.names.zombie");
									}
								}
							}

							// Send the death message
							event.setDeathMessage(parseStringListRandom("deaths.mobs.zombie").replaceAll("%victim%", name).replaceAll("%attacker%", mob));

						} else if (attacker instanceof Enderman) { // If the killer was an enderman

							String mob = Main.getInstance().getMessagesString("deaths.names.enderman");

							// Send the death message
							event.setDeathMessage(parseStringListRandom("deaths.mobs.any").replaceAll("%victim%", name).replaceAll("%attacker%", mob));

						} else if (attacker instanceof CaveSpider) { // If the killer was a cave spider

							String mob = Main.getInstance().getMessagesString("deaths.names.cave-spider");

							// Send the death message
							event.setDeathMessage(parseStringListRandom("deaths.mobs.any").replaceAll("%victim%", name).replaceAll("%attacker%", mob));

						} else if (attacker instanceof Spider) { // If the killer was a spider

							String mob;

							// Check if the spider is a jockey
							if (attacker.getPassenger() != null && attacker.getPassenger() instanceof Skeleton) {
								mob = Main.getInstance().getMessagesString("deaths.names.spider-jockey");
							} else {
								mob = Main.getInstance().getMessagesString("deaths.names.spider");
							}

							// Send the death message
							event.setDeathMessage(parseStringListRandom("deaths.mobs.any").replaceAll("%victim%", name).replaceAll("%attacker%", mob));

						} else if (attacker instanceof PigZombie) { // If the killer was a pig zombie

							String mob;

							// Check if the pig zombie is a baby or jockey
							if (attacker.getVehicle() != null && attacker.getVehicle() instanceof Chicken) {
								mob = Main.getInstance().getMessagesString("deaths.names.chicken-jockey");
							} else {
								if (((PigZombie) attacker).isBaby()) {
									mob = Main.getInstance().getMessagesString("deaths.names.baby-pig-zombie");
								} else {
									mob = Main.getInstance().getMessagesString("deaths.names.pig-zombie");
								}

							}

							// Send the death message
							event.setDeathMessage(parseStringListRandom("deaths.mobs.any").replaceAll("%victim%", name).replaceAll("%attacker%", mob));

						} else if (attacker instanceof Blaze) { // If the killer was a blaze

							String mob = Main.getInstance().getMessagesString("deaths.names.blaze");

							// Send the death message
							event.setDeathMessage(parseStringListRandom("deaths.mobs.any").replaceAll("%victim%", name).replaceAll("%attacker%", mob));

						} else if (attacker instanceof Giant) { // If the killer was a giant

							String mob = Main.getInstance().getMessagesString("deaths.names.giant");

							// Send the death message
							event.setDeathMessage(parseStringListRandom("deaths.mobs.any").replaceAll("%victim%", name).replaceAll("%attacker%", mob));

						} else if (attacker instanceof Guardian) { // If the killer was a guardian

							String mob;

							// Check if it's an elder guardian
							if (((Guardian) attacker).isElder()) {
								mob = Main.getInstance().getMessagesString("deaths.names.elder-guardian");
							} else {
								mob = Main.getInstance().getMessagesString("deaths.names.guardian");
							}

							// Send the death message
							event.setDeathMessage(parseStringListRandom("deaths.mobs.any").replaceAll("%victim%", name).replaceAll("%attacker%", mob));

						} else if (attacker instanceof Endermite) { // If the killer was an endermite

							String mob = Main.getInstance().getMessagesString("deaths.names.endermite");

							// Send the death message
							event.setDeathMessage(parseStringListRandom("deaths.mobs.any").replaceAll("%victim%", name).replaceAll("%attacker%", mob));

						} else if (attacker instanceof Ghast) { // If the killer was a ghast

							String mob = Main.getInstance().getMessagesString("deaths.names.ghast");

							// Send the death message
							event.setDeathMessage(parseStringListRandom("deaths.mobs.any").replaceAll("%victim%", name).replaceAll("%attacker%", mob));

						} else if (attacker instanceof MagmaCube) { // If the killer was a magma cube

							String mob = Main.getInstance().getMessagesString("deaths.names.magma-cube");

							// Send the death message
							event.setDeathMessage(parseStringListRandom("deaths.mobs.any").replaceAll("%victim%", name).replaceAll("%attacker%", mob));

						} else if (attacker instanceof Silverfish) { // If the killer was a silverfish

							String mob = Main.getInstance().getMessagesString("deaths.names.silverfish");

							// Send the death message
							event.setDeathMessage(parseStringListRandom("deaths.mobs.any").replaceAll("%victim%", name).replaceAll("%attacker%", mob));

						} else if (attacker instanceof Skeleton) { // If the killer was a skeleton

							String mob;

							// Check if it's a wither skeleton
							// TODO I'm not sure if I need to check for a jockey type here
							if (((Skeleton) attacker).getSkeletonType() == SkeletonType.WITHER) {
								mob = Main.getInstance().getMessagesString("deaths.names.wither-skeleton");
							} else {
								mob = Main.getInstance().getMessagesString("deaths.names.skeleton");
							}

							event.setDeathMessage(parseStringListRandom("deaths.mobs.any").replaceAll("%victim%", name).replaceAll("%attacker%", mob));

						} else if (attacker instanceof Arrow) { // If the killer was an arrow

							// TODO I don't know if this even works
							if ((((Arrow) damageevent.getDamager()).getShooter() instanceof Skeleton)) {

								String mob = Main.getInstance().getMessagesString("deaths.names.skeleton");

								// Send the death message
								event.setDeathMessage(parseStringListRandom("deaths.mobs.any").replaceAll("%victim%", name).replaceAll("%attacker%", mob));
							}

						} else if (attacker instanceof Rabbit) { // If the killer was a rabbit

							// Check if it's a killer rabbit
							if (((Rabbit) attacker).getRabbitType() == Rabbit.Type.THE_KILLER_BUNNY) {

								String mob = Main.getInstance().getMessagesString("deaths.names.killer-bunny");

								// Send the death message
								event.setDeathMessage(parseStringListRandom("deaths.mobs.any").replaceAll("%victim%", name).replaceAll("%attacker%", mob));
							}

							// No else statement needed because people shouldn't be able to die from non-killer bunnies

						} else if (attacker instanceof Slime) { // If the killer was a slime

							String mob;

							// Check the slime size
							if (((Slime) attacker).getSize() == 1) {
								mob = Main.getInstance().getMessagesString("deaths.names.tiny-slime");
							} else if (((Slime) attacker).getSize() == 2) {
								mob = Main.getInstance().getMessagesString("deaths.names.small-slime");
							} else if (((Slime) attacker).getSize() == 4) {
								mob = Main.getInstance().getMessagesString("deaths.names.big-slime");
							} else if (((Slime) attacker).getSize() > 4) {
								mob = Main.getInstance().getMessagesString("deaths.names.huge-slime");
							} else {
								mob = Main.getInstance().getMessagesString("deaths.names.slime");
							}

							// Send the death message
							event.setDeathMessage(parseStringListRandom("deaths.mobs.any").replaceAll("%victim%", name).replaceAll("%attacker%", mob));

						} else if (attacker instanceof IronGolem) { // If the killer was an iron golem

							String mob = Main.getInstance().getMessagesString("deaths.names.iron-golem");

							// Send the death message
							event.setDeathMessage(parseStringListRandom("deaths.mobs.any").replaceAll("%victim%", name).replaceAll("%attacker%", mob));

						} else if (attacker instanceof Witch) { // If the killer was a witch

							String mob = Main.getInstance().getMessagesString("deaths.names.witch");

							// Send the death message
							event.setDeathMessage(parseStringListRandom("deaths.mobs.any").replaceAll("%victim%", name).replaceAll("%attacker%", mob));

						} else if (attacker instanceof Wolf) { // If the killer was a wolf

							String mob = Main.getInstance().getMessagesString("deaths.names.wolf");

							// Send the death message
							event.setDeathMessage(parseStringListRandom("deaths.mobs.any").replaceAll("%victim%", name).replaceAll("%attacker%", mob));

						} else if (attacker instanceof Creeper) { // If the killer was a creeper

							String mob = Main.getInstance().getMessagesString("deaths.names.creeper");

							// Send the death message
							event.setDeathMessage(parseStringListRandom("deaths.mobs.any").replaceAll("%victim%", name).replaceAll("%attacker%", mob));

						} else if (attacker instanceof Wither) { // If the killer was a wither

							String mob = Main.getInstance().getMessagesString("deaths.names.wither");

							// Send the death message
							event.setDeathMessage(parseStringListRandom("deaths.mobs.any").replaceAll("%victim%", name).replaceAll("%attacker%", mob));

						} else if (attacker instanceof EnderDragon) { // If the killer was an ender dragon

							String mob = Main.getInstance().getMessagesString("deaths.names.ender-dragon");

							// Send the death message
							event.setDeathMessage(parseStringListRandom("deaths.mobs.any").replaceAll("%victim%", name).replaceAll("%attacker%", mob));

						} else { // Catch deaths we couldn't figure out, only things we might've missed are ranged mobs or creepers

							// Send the death message
							event.setDeathMessage(parseStringListRandom("deaths.unknown").replaceAll("%victim%", name));

						}
					}
				}

			} else if (cause == DamageCause.CONTACT) { // If the cause of death was cactus poking

				event.setDeathMessage(parseStringListRandom("deaths.contact").replaceAll("%victim%", name));

			} else if (cause == DamageCause.DROWNING) { // If the cause of death was drowning

				event.setDeathMessage(parseStringListRandom("deaths.drowning").replaceAll("%victim%", name));

			} else if (cause == DamageCause.FALL) { // If the cause of death was falling

				event.setDeathMessage(parseStringListRandom("deaths.fall").replaceAll("%victim%", name));

			} else if (cause == DamageCause.FIRE) { // If the cause of death was standing in fire

				event.setDeathMessage(parseStringListRandom("deaths.fire").replaceAll("%victim%", name));

			} else if (cause == DamageCause.FIRE_TICK) { // If the cause of death was being on fire

				event.setDeathMessage(parseStringListRandom("deaths.fire").replaceAll("%victim%", name));

			} else if (cause == DamageCause.LAVA) { // If the cause of death was being in lava

				event.setDeathMessage(parseStringListRandom("deaths.lava").replaceAll("%victim%", name));

			} else if (cause == DamageCause.LIGHTNING) { // If the cause of death was being struck by lightning

				event.setDeathMessage(parseStringListRandom("deaths.lightning").replaceAll("%victim%", name));

			} else if (cause == DamageCause.STARVATION) { // If the cause of death was starving to death

				event.setDeathMessage(parseStringListRandom("deaths.starvation").replaceAll("%victim%", name));

			} else if (cause == DamageCause.SUFFOCATION) { // If the cause of death was being suffocated

				event.setDeathMessage(parseStringListRandom("deaths.suffocation").replaceAll("%victim%", name));

			} else if (cause == DamageCause.SUICIDE) { // If the cause of death was suicide

				event.setDeathMessage(parseStringListRandom("deaths.suicide").replaceAll("%victim%", name));

			} else if (cause == DamageCause.VOID) { // If the cause of death was falling into the void

				event.setDeathMessage(parseStringListRandom("deaths.void").replaceAll("%victim%", name));

			} else if (cause == DamageCause.WITHER) { // If the cause of death was the wither effect

				// TODO Make this work, uses unknown message for now
				event.setDeathMessage(parseStringListRandom("deaths.unknown").replaceAll("%victim%", name));

			} else if (cause == DamageCause.MAGIC) { // If the cause of death was a potion/spell

				// TODO Make this work, uses unknown message for now
				event.setDeathMessage(parseStringListRandom("deaths.unknown").replaceAll("%victim%", name));

			} else if (cause == DamageCause.FALLING_BLOCK) { // If the cause of death was a falling block

				// TODO Make this work, uses unknown message for now
				event.setDeathMessage(parseStringListRandom("deaths.unknown").replaceAll("%victim%", name));

			} else if (cause == DamageCause.BLOCK_EXPLOSION) { // If the cause of death was a block explosion

				// TODO Make this work
				event.setDeathMessage(parseStringListRandom("deaths.block-explosion").replaceAll("%victim%", name));

			} else if (cause == DamageCause.POISON) { // If the cause of death was poison

				// TODO Make this work
				event.setDeathMessage(parseStringListRandom("deaths.block-explosion").replaceAll("%victim%", name));

			} else if (cause == DamageCause.THORNS) { // If the cause of death was the thorns enchantment

				// TODO Make this work
				event.setDeathMessage(parseStringListRandom("deaths.block-explosion").replaceAll("%victim%", name));

			}

			else if (cause == DamageCause.ENTITY_EXPLOSION) { // If the cause of death was an entity explosion (Creepers?)

				// TODO Make this work
				event.setDeathMessage(parseStringListRandom("deaths.mobs.creeper").replaceAll("%victim%", name));

			} else { // Catch deaths we couldn't figure out

				event.setDeathMessage(parseStringListRandom("deaths.unknown").replaceAll("%victim%", name));

			}
		} else { // Don't set a death message if the player has too many deaths
			event.setDeathMessage("");
		}
	}
}
