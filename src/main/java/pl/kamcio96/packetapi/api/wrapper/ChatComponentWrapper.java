package pl.kamcio96.packetapi.api.wrapper;

import net.minecraft.server.v1_8_R3.ChatComponentText;

public class ChatComponentWrapper {

    public static Object toMinecraftChatComponent(String text) {
        return new ChatComponentText(text);
    }
}
