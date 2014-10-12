package me.bigteddy98.packetapi.collections;

public enum Animation {
	SWING_ARM(0), DAMAGE(1), LEAVE_BED(2), EAT_FOOD(3), CRITICAL_EFFECT(4), MAGICAL_CRIT_EFFECT(5), CROUCH(104), UNCROUCH(105);

	private int id;

	private Animation(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

}
