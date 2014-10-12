package me.bigteddy98.packetapi.api.wrapper;

import net.minecraft.server.v1_7_R4.EnumGamemode;

import org.bukkit.GameMode;

public class GamemodeWrapper {

	public static GameMode toBukkitGamemode(Object enumGamemode) {
		//not sure if this works. //TO-DO test it
		EnumGamemode gamemode = (EnumGamemode) enumGamemode;
		return GameMode.valueOf(gamemode.toString().toUpperCase());
	}

	public static Object toEnumGameMode(GameMode gamemode) {
		//not sure if this works. //TO-DO test it
		return EnumGamemode.valueOf(gamemode.toString().toUpperCase());
	}
}
