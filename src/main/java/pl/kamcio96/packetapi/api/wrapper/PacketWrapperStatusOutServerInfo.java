package pl.kamcio96.packetapi.api.wrapper;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.v1_9_R1.ChatComponentText;
import net.minecraft.server.v1_9_R1.ServerPing;
import net.minecraft.server.v1_9_R1.ServerPing;
import pl.kamcio96.packetapi.api.PacketWrapper;

@Deprecated
@SuppressWarnings(value = {"TODO class"})
public class PacketWrapperStatusOutServerInfo extends PacketWrapper {

    private ServerPing ping;

    public PacketWrapperStatusOutServerInfo(Object packet) {
        super(packet);

        try {
            this.ping = (ServerPing) this.getValue("b");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setMOTD(String motd) {
        this.ping.setMOTD(new ChatComponentText(motd));
    }

    public GameProfile[] getOnlinePlayers() {
        return this.ping.b().c();
    }

    public void setOnlinePlayers(GameProfile[] onlinePlayers) {
        this.ping.b().a(onlinePlayers);
    }

    public String getFavicon() {
        return this.ping.d();
    }

    public void setFavicon(String favicon) {
        this.ping.setFavicon(favicon);
    }

    public String getVersionName() {
        return this.ping.getServerData().a();
    }

    public int getVersionProtocol() {
        return this.ping.getServerData().getProtocolVersion();
    }
}
