package me.bigteddy98.packetapi.collections;

public enum Particle {
	HUGE_EXPLOSION("hugeexplosion"), LARGE_EXPLODE("largeexplode"), FIREWORKS_SPARK("fireworksSpark"), BUBBLE("bubble"), SUSPEND("suspend"), DEPTH_SUSPEND("depthSuspend"), TOWN_AURA("townaura"), CRIT("crit"), MAGIC_CRIT("magicCrit"), SMOKE("smoke"), MOB_SPELL("mobSpell"), MOB_SPELL_AMBIENT("mobSpellAmbient"), SPELL("spell"), INSTANT_SPELL("instantSpell"), WITCH_MAGIC("witchMagic"), NOTE("note"), PORTAL("portal"), ENCHANTMENT_TABLE("enchantmenttable"), EXPLODE("explode"), FLAME("flame"), LAVA("lava"), FOOTSTEP("footstep"), SPLASH("splash"), WAKE("wake"), LARGE_SMOKE("largesmoke"), CLOUD("cloud"), RED_DUST("reddust"), SNOWBALL_POOF("snowballpoof"), DRIP_WATER("dripWater"), DRIP_LAVA("dripLava"), SNOW_SHOVEL("snowshovel"), SLIME("slime"), HEART("heart"), ANGRY_VILLAGER("angryVillager"), HAPPY_VILLAGER("happyVillager");

	private String name;

	private Particle(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
