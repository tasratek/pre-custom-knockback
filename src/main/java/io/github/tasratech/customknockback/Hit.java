package io.github.tasratech.customknockback;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class Hit {
	private Vector direction;

	private boolean sprintKb;

	private int kbEnchantLevel;

	private Player attacker;

	private boolean toggleFallKnockback;

	public Hit(Vector direction, boolean sprintKb, int kbEnchantLevel, Player attacker, boolean toggleFallKnockback) {
		this.direction = direction;
		this.sprintKb = sprintKb;
		this.kbEnchantLevel = kbEnchantLevel;
		this.attacker = attacker;
		this.toggleFallKnockback = toggleFallKnockback;
	}

	public Vector getDirection() {
		return direction;
	}

	public void setDirection(Vector direction) {
		this.direction = direction;
	}

	public boolean isSprintKb() {
		return sprintKb;
	}

	public void setSprintKb(boolean sprintKb) {
		this.sprintKb = sprintKb;
	}

	public int getKbEnchantLevel() {
		return kbEnchantLevel;
	}

	public void setKbEnchantLevel(int kbEnchantLevel) {
		this.kbEnchantLevel = kbEnchantLevel;
	}

	public Player getAttacker() {
		return attacker;
	}

	public boolean isToggleFallKnockback() {
		return toggleFallKnockback;
	}

	public void setToggleFallKnockback(boolean toggleFallKnockback) {
		this.toggleFallKnockback = toggleFallKnockback;
	}
}
