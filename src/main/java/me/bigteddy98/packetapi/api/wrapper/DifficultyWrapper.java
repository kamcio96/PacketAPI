package me.bigteddy98.packetapi.api.wrapper;

import net.minecraft.server.v1_7_R4.EnumDifficulty;

import org.bukkit.Difficulty;

public class DifficultyWrapper {
	public static Difficulty toBukkitDiffuculty(Object enumDiffi) {
		//not sure if this works. //TO-DO test it
		EnumDifficulty diffi = (EnumDifficulty) enumDiffi;
		return Difficulty.valueOf(diffi.toString().toUpperCase());
	}

	public static Object toEnumDiffuculty(Difficulty diffi) {
		//not sure if this works. //TO-DO test it
		return EnumDifficulty.valueOf(diffi.toString().toUpperCase());
	}
}
