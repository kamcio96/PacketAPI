package pl.kamcio96.packetapi;

import net.minecraft.server.v1_8_R3.ChatComponentText;

public class PacketDataWrapper {

    public static Object ServerPingPlayerSample(int players, int maxPlayers) {
        return new net.minecraft.server.v1_8_R3.ServerPing.ServerPingPlayerSample(players, maxPlayers);
    }

    public static Object ServerPingServerData(String name, int protocol) {
        return new net.minecraft.server.v1_8_R3.ServerPing.ServerData(name, protocol);
    }

    public static Object IChatBaseComponent(String text) {
        return new ChatComponentText(text);
    }
}
