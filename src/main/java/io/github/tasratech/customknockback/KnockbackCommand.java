package io.github.tasratech.customknockback;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class KnockbackCommand implements CommandExecutor {
	private CustomKnockback plugin;

	KnockbackCommand(CustomKnockback plugin) {
		this.plugin = plugin;
	}

	private void mainMenu(CommandSender sender) {
		sender.sendMessage(ChatColor.AQUA + "Knockback editor");
		sender.sendMessage(ChatColor.GRAY + "----------------");
		sender.sendMessage(ChatColor.AQUA + "Set custom knockback:");
		sender.sendMessage(ChatColor.DARK_AQUA
				+ "/knockback set <ground horizontal multiplier> <ground vertical multiplier> <air horizontal multiplier> <air vertical multiplier> <sprint multiplier horizontal> <sprint multiplier vertical> <sprint yaw factor> <fall multiplier horizontal> <fall multiplier vertical> <fall max height> <fall min height>");
		sender.sendMessage(ChatColor.AQUA + "Toggle custom knockback:");
		sender.sendMessage(ChatColor.DARK_AQUA + "/knockback <on/off>");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("knockback")) {
			if (sender instanceof Player && !sender.hasPermission("knockback.admin")) {
				sender.sendMessage(ChatColor.RED + "You do not have permission to execute this command.");
				return true;
			}
			if (args.length == 0) {
				mainMenu(sender);
				return true;
			}
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("on")) {
					plugin.setKbEnabled(true);
					plugin.getConfig().set("enabled", Boolean.valueOf(true));
					plugin.saveConfig();
					sender.sendMessage(ChatColor.AQUA + "Custom knockback enabled!");
				} else if (args[0].equalsIgnoreCase("off")) {
					plugin.setKbEnabled(false);
					plugin.getConfig().set("enabled", Boolean.valueOf(false));
					plugin.saveConfig();
					sender.sendMessage(ChatColor.AQUA + "Custom knockback disabled!");
				} else if (args[0].equalsIgnoreCase("set")) {
					sender.sendMessage(ChatColor.RED
							+ "Usage: /knockback set <horizontal multiplier> <vertical multiplier> <sprint multiplier horizontal> <sprint multiplier vertical> <fall multiplier horizontal> <fall multiplier vertical> <fall max height> <fall min height>");
				} else if (args[0].equalsIgnoreCase("debugnormal")) {
					if (!(sender instanceof Player)) {
						sender.sendMessage("Only players can do this.");
						return true;
					}
					Player p = (Player) sender;
					p.setVelocity(new Vector(1.0D * plugin.getGroundHorizMultiplier(),
							1.0D * plugin.getGroundVertMultiplier(), 0.0D));
					p.sendMessage("You've been knocked back. (NORMAL)");
				} else if (args[0].equalsIgnoreCase("debugsprint")) {
					if (!(sender instanceof Player)) {
						sender.sendMessage("Only players can do this.");
						return true;
					}
					Player p = (Player) sender;
					p.setVelocity(
							new Vector(1.0D * plugin.getGroundHorizMultiplier() * plugin.getSprintMultiplierHoriz(),
									1.0D * plugin.getGroundVertMultiplier() * plugin.getSprintMultiplierVert(), 0.0D));
					p.sendMessage("You've been knocked back. (SPRINT)");
				} else {
					mainMenu(sender);
				}
			} else if (args.length == 12 && args[0].equalsIgnoreCase("set")) {
				try {
					plugin.setGroundHorizMultiplier(Double.parseDouble(args[1]));
					plugin.setGroundVertMultiplier(Double.parseDouble(args[2]));
					plugin.setAirHorizMultiplier(Double.parseDouble(args[3]));
					plugin.setAirVertMultiplier(Double.parseDouble(args[4]));
					plugin.setSprintMultiplierHoriz(Double.parseDouble(args[5]));
					plugin.setSprintMultiplierVert(Double.parseDouble(args[6]));
					plugin.setSprintYawFactor(Double.parseDouble(args[7]));
					plugin.setFallHorizMultiplier(Double.parseDouble(args[8]));
					plugin.setFallVertMultiplier(Double.parseDouble(args[9]));
					plugin.setToggleFallMaxHeight(Double.parseDouble(args[10]));
					plugin.setToggleFallMinHeight(Double.parseDouble(args[11]));

					plugin.getConfig().set("ground_horizontal_multiplier",
							Double.valueOf(plugin.getGroundHorizMultiplier()));
					plugin.getConfig().set("ground_vertical_multiplier",
							Double.valueOf(plugin.getGroundVertMultiplier()));
					plugin.getConfig().set("air_horizontal_multiplier", Double.valueOf(plugin.getAirHorizMultiplier()));
					plugin.getConfig().set("air_vertical_multiplier", Double.valueOf(plugin.getAirVertMultiplier()));
					plugin.getConfig().set("sprint_multiplier_horizontal",
							Double.valueOf(plugin.getSprintMultiplierHoriz()));
					plugin.getConfig().set("sprint_multiplier_vertical",
							Double.valueOf(plugin.getSprintMultiplierVert()));
					plugin.getConfig().set("sprint_yaw_factor", Double.valueOf(plugin.getSprintYawFactor()));
					plugin.getConfig().set("fall_multiplier_horizontal",
							Double.valueOf(plugin.getFallHorizMultiplie()));
					plugin.getConfig().set("fall_multiplier_vertical", Double.valueOf(plugin.getFallVertMultiplier()));
					plugin.getConfig().set("fall_toggle_max_height", Double.valueOf(plugin.getToggleFallMaxHeight()));
					plugin.getConfig().set("fall_toggle_min_height", Double.valueOf(plugin.getToggleFallMinHeight()));

					plugin.saveConfig();
					sender.sendMessage(ChatColor.AQUA + "Knockback set!");
				} catch (NumberFormatException exception) {
					sender.sendMessage(ChatColor.RED + "The multipliers must be numbers!");
				}
			} else {
				mainMenu(sender);
			}
		}
		return true;
	}
}
