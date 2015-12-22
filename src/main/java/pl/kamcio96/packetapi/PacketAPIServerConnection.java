package pl.kamcio96.packetapi;

import net.minecraft.server.v1_7_R4.MinecraftServer;
import net.minecraft.server.v1_7_R4.NetworkManager;
import net.minecraft.server.v1_7_R4.ServerConnection;
import net.minecraft.util.io.netty.bootstrap.ServerBootstrap;
import net.minecraft.util.io.netty.channel.ChannelFuture;
import net.minecraft.util.io.netty.channel.nio.NioEventLoopGroup;
import net.minecraft.util.io.netty.channel.socket.nio.NioServerSocketChannel;

import java.lang.reflect.Field;
import java.net.InetAddress;
import java.util.List;

public class PacketAPIServerConnection extends ServerConnection {

    private List superEList;
    private List superFList;
    private NioEventLoopGroup superCEventGroup;

    public PacketAPIServerConnection(MinecraftServer minecraftserver) {
        super(minecraftserver);
        try {

            Field e = ServerConnection.class.getDeclaredField("e");
            e.setAccessible(true);
            Field f = ServerConnection.class.getDeclaredField("f");
            f.setAccessible(true);

            superEList = (List) e.get(this);
            superFList = (List) f.get(this);

            Field c = ServerConnection.class.getDeclaredField("c");
            c.setAccessible(true);
            superCEventGroup = (NioEventLoopGroup) c.get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void a(InetAddress inetaddress, int port) {
        try {
            System.out.println("[PacketAPI] Bind custom ServerConnection on " + (inetaddress != null ? inetaddress.getHostAddress() : "") + ":" + port);
            ServerBootstrap sb = new ServerBootstrap();
            sb.channel(NioServerSocketChannel.class);
            sb.childHandler(new PacketAPIServerConnector(this));
            sb.group(superCEventGroup);
            sb.localAddress(inetaddress, port);
            ChannelFuture f = sb.bind().syncUninterruptibly();

            synchronized (superEList) {
                superEList.add(f);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<NetworkManager> getNetworkManagerList() {
        return (List<NetworkManager>) superFList;
    }

}
