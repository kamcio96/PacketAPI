package me.bigteddy98.packetapi;

import net.minecraft.server.v1_7_R4.ChatComponentText;

public class PacketDataWrapper {

	public static Object ServerPingPlayerSample(int players, int maxPlayers) {
		return new net.minecraft.server.v1_7_R4.ServerPingPlayerSample(players, maxPlayers);
	}

	public static Object ServerPingServerData(String name, int protocol) {
		return new net.minecraft.server.v1_7_R4.ServerPingServerData(name, protocol);
	}

	public static Object IChatBaseComponent(String text) {
		return new ChatComponentText(text);
	}
}
