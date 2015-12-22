package pl.kamcio96.packetapi;

import net.minecraft.server.v1_7_R4.*;
import net.minecraft.util.io.netty.channel.Channel;
import net.minecraft.util.io.netty.channel.ChannelException;
import net.minecraft.util.io.netty.channel.ChannelInitializer;
import net.minecraft.util.io.netty.channel.ChannelOption;
import net.minecraft.util.io.netty.handler.timeout.ReadTimeoutHandler;

public class PacketAPIServerConnector extends ChannelInitializer {

    private PacketAPIServerConnection conn;

    public PacketAPIServerConnector(PacketAPIServerConnection conn) {
        this.conn = conn;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
        try {
            channel.config().setOption(ChannelOption.IP_TOS, 24);
        } catch (ChannelException channelexception) {
        }

        try {
            channel.config().setOption(ChannelOption.TCP_NODELAY, false);
        } catch (ChannelException channelexception1) {
        }

        channel.pipeline().addLast("timeout", new ReadTimeoutHandler(30))
                .addLast("legacy_query", new LegacyPingHandler(this.conn))
                .addLast("splitter", new PacketSplitter())
                .addLast("decoder", new PacketDecoder(NetworkManager.h))
                .addLast("prepender", new PacketPrepender())
                .addLast("encoder", new PacketEncoder(NetworkManager.h));
        NetworkManager networkmanager = new NetworkManager(false);
        conn.getNetworkManagerList().add(networkmanager);
        channel.pipeline().addLast("packetAPI_handler", new PacketAPIHandler(networkmanager));
        channel.pipeline().addLast("packet_handler", networkmanager);
        networkmanager.a(new HandshakeListener(MinecraftServer.getServer(), networkmanager));
    }
}
