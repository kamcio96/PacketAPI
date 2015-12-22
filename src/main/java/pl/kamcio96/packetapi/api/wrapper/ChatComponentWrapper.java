package pl.kamcio96.packetapi.api.wrapper;

import net.minecraft.server.v1_7_R4.ChatComponentText;

public class ChatComponentWrapper {

    public static Object makeChatComponent(String text) {
        return new ChatComponentText(text);
    }
}
