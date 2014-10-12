package me.bigteddy98.packetapi.collections;

public enum Status {
	UNKNOWN_ID_0(0), ENTITY_HURT(1), UNKNOWN_ID_2(2), ENTITY_DEATH(3), IRON_GOLEM_ARMS(4), WOLF_TAMING(6), WOLF_TAMED(7), WOLF_SHAKING_WATER(8), ACCEPT_EATING(9), SHEEP_EAT_GRASS(10), IRON_HOLEM_ROSE(11), VILLAGER_HEARTS(12), VILLAGER_ANGRY(13), VILLAGER_HAPPY(14), WITCH_MAGIC(15), UNKNOWN_ID_16(16), FIREWORK_EXPLODING(17), UNKNOWN_ID_18(18);

	private byte id;

	private Status(int id) {
		this.id = (byte) id;
	}

	public byte getId() {
		return id;
	}
}
