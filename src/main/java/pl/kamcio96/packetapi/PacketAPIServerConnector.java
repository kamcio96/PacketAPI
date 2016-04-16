package pl.kamcio96.packetapi;

import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import net.minecraft.server.v1_9_R1.*;

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
                .addLast("decoder", new PacketDecoder(EnumProtocolDirection.SERVERBOUND))
                .addLast("prepender", new PacketPrepender())
                .addLast("encoder", new PacketEncoder(EnumProtocolDirection.CLIENTBOUND));
        NetworkManager networkmanager = new NetworkManager(EnumProtocolDirection.SERVERBOUND);
        conn.getNetworkManagerList().add(networkmanager);
        channel.pipeline().addLast("packetAPI_handler", new PacketAPIHandler(networkmanager));
        channel.pipeline().addLast("packet_handler", networkmanager);
        networkmanager.setPacketListener(new HandshakeListener(MinecraftServer.getServer(), networkmanager));
    }
}
