package me.bigteddy98.packetapi.collections;

public enum Slot {
	HELD(0), BOOTS(1), LEGGINGS(2), CHESTPLATE(3), HELMET(4);

	private int id;

	private Slot(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
