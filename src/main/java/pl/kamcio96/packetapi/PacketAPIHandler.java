package pl.kamcio96.packetapi;

import net.minecraft.server.v1_7_R4.NetworkManager;
import net.minecraft.server.v1_7_R4.PlayerConnection;
import net.minecraft.util.io.netty.channel.ChannelDuplexHandler;
import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
import net.minecraft.util.io.netty.channel.ChannelPromise;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.kamcio96.packetapi.api.PacketRecieveEvent;
import pl.kamcio96.packetapi.api.PacketSendEvent;
import pl.kamcio96.packetapi.api.PacketWrapper;

import java.net.InetSocketAddress;

public class PacketAPIHandler extends ChannelDuplexHandler {

    private NetworkManager manager;

    public PacketAPIHandler(NetworkManager manager) {
        this.manager = manager;
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        Player player = null;
        if (manager.getPacketListener() instanceof PlayerConnection) {
            player = ((PlayerConnection) manager.getPacketListener()).getPlayer();
        }

        PacketSendEvent event = new PacketSendEvent(new PacketWrapper(msg), player, ((InetSocketAddress) manager.getSocketAddress()).getAddress());
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return;
        }
        super.write(ctx, msg, promise);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Player player = null;
        if (manager.getPacketListener() instanceof PlayerConnection) {
            player = ((PlayerConnection) manager.getPacketListener()).getPlayer();
        }

        PacketRecieveEvent event = new PacketRecieveEvent(new PacketWrapper(msg), player, ((InetSocketAddress) manager.getSocketAddress()).getAddress());
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return;
        }
        super.channelRead(ctx, msg);
    }
}
