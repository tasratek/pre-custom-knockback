package io.github.tasratech.customknockback;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomKnockback extends JavaPlugin {
	private double groundHorizMultiplier;

	private double groundVertMultiplier;

	private double airHorizMultiplier;

	private double airVertMultiplier;

	private boolean kbEnabled;

	private double sprintMultiplierHoriz;

	private double sprintMultiplierVert;

	private double sprintYawFactor;

	private double fallHorizMultiplie;

	private double fallVertMultiplier;

	private double toggleFallMaxHeight;

	private double toggleFallMinHeight;


	@Override
	public void onEnable() {
		getCommand("knockback").setExecutor(new KnockbackCommand(this));
		Bukkit.getServer().getPluginManager().registerEvents(new EventListener(this), this);
		saveDefaultConfig();
		getConfig().options().copyDefaults(true);
		loadConfig();
		Bukkit.getLogger().info("CustomKnockback has been enabled.");
	}

	@Override
	public void onDisable() {
		Bukkit.getLogger().info("CustomKnockback has been disabled.");
	}

	private void loadConfig() {
		kbEnabled = getConfig().getBoolean("enabled");
		groundHorizMultiplier = getConfig().getDouble("ground_horizontal_multiplier");
		groundVertMultiplier = getConfig().getDouble("ground_vertical_multiplier");
		airHorizMultiplier = getConfig().getDouble("air_horizontal_multiplier");
		airVertMultiplier = getConfig().getDouble("air_vertical_multiplier");
		sprintMultiplierHoriz = getConfig().getDouble("sprint_multiplier_horizontal");
		sprintMultiplierVert = getConfig().getDouble("sprint_multiplier_vertical");
		sprintYawFactor = getConfig().getDouble("sprint_yaw_factor");
		fallHorizMultiplie = getConfig().getDouble("fall_multiplier_horizontal");
		fallVertMultiplier = getConfig().getDouble("fall_multiplier_vertical");
		toggleFallMaxHeight = getConfig().getDouble("fall_toggle_max_height");
		toggleFallMinHeight = getConfig().getDouble("fall_toggle_min_height");
	}

	double getGroundHorizMultiplier() {
		return groundHorizMultiplier;
	}

	void setGroundHorizMultiplier(double groundHorizMultiplier) {
		this.groundHorizMultiplier = groundHorizMultiplier;
	}

	void setKbEnabled(boolean enabled) {
		kbEnabled = enabled;
	}

	public boolean isKbEnabled() {
		return kbEnabled;
	}

	double getGroundVertMultiplier() {
		return groundVertMultiplier;
	}

	void setGroundVertMultiplier(double groundVertMultiplier) {
		this.groundVertMultiplier = groundVertMultiplier;
	}

	double getSprintMultiplierHoriz() {
		return sprintMultiplierHoriz;
	}

	void setSprintMultiplierHoriz(double sprintMultiplierHoriz) {
		this.sprintMultiplierHoriz = sprintMultiplierHoriz;
	}

	double getSprintMultiplierVert() {
		return sprintMultiplierVert;
	}

	void setSprintMultiplierVert(double sprintMultiplierVert) {
		this.sprintMultiplierVert = sprintMultiplierVert;
	}

	public double getAirHorizMultiplier() {
		return airHorizMultiplier;
	}

	public void setAirHorizMultiplier(double airHorizMultiplier) {
		this.airHorizMultiplier = airHorizMultiplier;
	}

	public double getAirVertMultiplier() {
		return airVertMultiplier;
	}

	public void setAirVertMultiplier(double airVertMultiplier) {
		this.airVertMultiplier = airVertMultiplier;
	}

	public double getSprintYawFactor() {
		return sprintYawFactor;
	}

	public void setSprintYawFactor(double sprintYawFactor) {
		this.sprintYawFactor = sprintYawFactor;
	}

	public double getFallHorizMultiplie() {
		return fallHorizMultiplie;
	}

	public void setFallHorizMultiplier(double fallHorizMultiplie) {
		this.fallHorizMultiplie = fallHorizMultiplie;
	}

	public double getFallVertMultiplier() {
		return fallVertMultiplier;
	}

	public void setFallVertMultiplier(double fallVertMultiplier) {
		this.fallVertMultiplier = fallVertMultiplier;
	}

	public double getToggleFallMaxHeight() {
		return toggleFallMaxHeight;
	}

	public void setToggleFallMaxHeight(double toggleFallMaxHeight) {
		this.toggleFallMaxHeight = toggleFallMaxHeight;
	}

	public double getToggleFallMinHeight() {
		return toggleFallMinHeight;
	}

	public void setToggleFallMinHeight(double toggleFallMinHeight) {
		this.toggleFallMinHeight = toggleFallMinHeight;
	}
}
