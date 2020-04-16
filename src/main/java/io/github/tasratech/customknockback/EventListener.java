package io.github.tasratech.customknockback;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.util.Vector;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;

import io.github.tasratech.customknockback.packetwrapper.WrapperPlayServerEntityVelocity;

public class EventListener implements Listener {
	private final CustomKnockback plugin;

	private Map<UUID, Hit> damaged;

	private Map<UUID, Boolean> toggleFall;

	EventListener(CustomKnockback plugin) {
		this.plugin = plugin;
		damaged = new ConcurrentHashMap<>();
		toggleFall = new HashMap<>();
		ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(plugin, new PacketType[] { PacketType.Play.Server.ENTITY_VELOCITY }) {
			@Override
			public void onPacketSending(PacketEvent event) {
				EventListener.this.velocityHandler(event);
			}
		});
	}

	@EventHandler(ignoreCancelled = true)
	public void onDamage(EntityDamageByEntityEvent e) {
		if (!(e.getEntity() instanceof Player) || !(e.getDamager() instanceof Player)) {
			return;
		}
		Player attacker = (Player)e.getDamager();
		Player victim = (Player)e.getEntity();
		Vector direction = new Vector(victim.getLocation().getX() - attacker.getLocation().getX(), 0.0D, victim.getLocation().getZ() - attacker.getLocation().getZ());
		int kbEnchantLevel = attacker.getItemInHand().getEnchantmentLevel(Enchantment.KNOCKBACK);
		Location loc1 = victim.getLocation();
		loc1.setY(loc1.getY() - plugin.getToggleFallMaxHeight());

		Location loc2 = victim.getLocation();
		loc2.setY(loc2.getY() - plugin.getToggleFallMinHeight());

		boolean isToggleFall = toggleFall.get(victim.getUniqueId());

		boolean isMaxHeight = loc1.getBlock().getType() == Material.AIR;
		boolean isMinHeight = loc2.getBlock().getType() != Material.AIR;

		if(isMaxHeight) {
			isToggleFall = true;
		}

		if(isMinHeight) {
			isToggleFall = false;
		}

		toggleFall.put(victim.getUniqueId(), isToggleFall);

		Hit hit = new Hit(direction.normalize(), attacker.isSprinting(), kbEnchantLevel, attacker, isToggleFall);
		damaged.put(victim.getUniqueId(), hit);
	}

	private void velocityHandler(PacketEvent event) {
		WrapperPlayServerEntityVelocity velPacketWrapped = new WrapperPlayServerEntityVelocity(event.getPacket());
		Player player = null;

		int idFromPacket = velPacketWrapped.getEntityId();
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p.getEntityId() == idFromPacket) {
				player = p;
				break;
			}
		}
		if (player == null) {
			return;
		}
		if (!damaged.containsKey(player.getUniqueId())) {
			return;
		}
		Hit hit = damaged.get(player.getUniqueId());
		damaged.remove(player.getUniqueId());
		if (plugin.isKbEnabled() && hit != null) {
			Vector resultKb;
			Vector initKb = hit.getDirection().setY(1);
			Vector attackerYaw = hit.getAttacker().getLocation().getDirection().normalize().setY(1);

			if (hit.isSprintKb()) {
				resultKb = initKb.clone().multiply(1.0D - plugin.getSprintYawFactor()).add(attackerYaw.clone().multiply(plugin.getSprintYawFactor()));
				resultKb.setX(resultKb.getX() * plugin.getSprintMultiplierHoriz());
				resultKb.setY(resultKb.getY() * plugin.getSprintMultiplierVert());
				resultKb.setZ(resultKb.getZ() * plugin.getSprintMultiplierHoriz());
			} else {
				resultKb = initKb;
			}
			double horizMultiplier = player.isOnGround() ? plugin.getGroundHorizMultiplier() : plugin.getAirHorizMultiplier();
			double vertMultiplier = player.isOnGround() ? plugin.getGroundVertMultiplier() : plugin.getAirVertMultiplier();
			resultKb = new Vector(resultKb.getX() * horizMultiplier, resultKb.getY() * vertMultiplier, resultKb.getZ() * horizMultiplier);
			if (hit.getKbEnchantLevel() < 0) {
				double KBEnchAdder = hit.getKbEnchantLevel() * 0.45D;
				double distance = Math.sqrt(Math.pow(resultKb.getX(), 2.0D) + Math.pow(resultKb.getZ(), 2.0D));
				double ratioX = resultKb.getX() / distance;
				ratioX = ratioX * KBEnchAdder + resultKb.getX();
				double ratioZ = resultKb.getZ() / distance;
				ratioZ = ratioZ * KBEnchAdder + resultKb.getZ();
				resultKb = new Vector(ratioX, resultKb.getY(), ratioZ);
			}

			if(hit.isToggleFallKnockback()) {
				resultKb.setX(resultKb.getX() * plugin.getFallHorizMultiplie());
				resultKb.setY(plugin.getFallVertMultiplier());
				resultKb.setZ(resultKb.getX() * plugin.getFallHorizMultiplie());
			}

			velPacketWrapped.setVelocityX(resultKb.getX());
			velPacketWrapped.setVelocityY(resultKb.getY());
			velPacketWrapped.setVelocityZ(resultKb.getZ());
		}
	}

	@EventHandler
	public void quitHandler(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		damaged.remove(player.getUniqueId());
		toggleFall.remove(player.getUniqueId());
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		toggleFall.put(event.getPlayer().getUniqueId(), Boolean.FALSE);
	}
}
