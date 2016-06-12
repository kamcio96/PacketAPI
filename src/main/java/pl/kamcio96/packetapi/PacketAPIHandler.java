package pl.kamcio96.packetapi;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import net.minecraft.server.v1_10_R1.LoginListener;
import net.minecraft.server.v1_10_R1.NetworkManager;
import net.minecraft.server.v1_10_R1.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.kamcio96.packetapi.api.Connection;
import pl.kamcio96.packetapi.api.PacketRecieveEvent;
import pl.kamcio96.packetapi.api.PacketSendEvent;
import pl.kamcio96.packetapi.api.PacketWrapper;

import java.net.InetAddress;
import java.net.InetSocketAddress;

public class PacketAPIHandler extends ChannelDuplexHandler implements Connection {

    private NetworkManager manager;

    public PacketAPIHandler(NetworkManager manager) {
        this.manager = manager;
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        PacketSendEvent event = new PacketSendEvent(new PacketWrapper(msg), this);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return;
        }
        super.write(ctx, msg, promise);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        PacketRecieveEvent event = new PacketRecieveEvent(new PacketWrapper(msg), this);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return;
        }
        super.channelRead(ctx, msg);
    }

    @Override
    public InetAddress getAddress() {
        return ((InetSocketAddress) manager.getSocketAddress()).getAddress();
    }

    @Override
    public boolean hasPlayer() {
        return getPlayer() != null;
    }

    @Override
    public Player getPlayer() {
        if (manager.i() instanceof PlayerConnection) {
            return ((PlayerConnection) manager.i()).getPlayer();
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PacketAPIHandler that = (PacketAPIHandler) o;

        return !(manager != null ? !manager.equals(that.manager) : that.manager != null);

    }

    @Override
    public int hashCode() {
        return manager != null ? manager.hashCode() : 0;
    }

}
