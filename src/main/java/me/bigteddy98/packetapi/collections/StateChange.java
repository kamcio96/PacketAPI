package me.bigteddy98.packetapi.collections;

public enum StateChange {
	INVALID_BED(0), END_RAIN(1), BEGIN_RAIN(2), CHANGE_GAMEMODE(3), SHOW_CREDITS(4), DEMO_MESSAGE(5), ARROW_PLAYER_HIT(6), FADE_VALUE(7), FADE_TIME(8);

	private int id;

	private StateChange(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

}
