package pl.kamcio96.packetapi;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import net.minecraft.server.v1_9_R1.LazyInitVar;
import net.minecraft.server.v1_9_R1.MinecraftServer;
import net.minecraft.server.v1_9_R1.NetworkManager;
import net.minecraft.server.v1_9_R1.ServerConnection;
import org.apache.logging.log4j.LogManager;

import java.lang.reflect.Field;
import java.net.InetAddress;
import java.util.List;

public class PacketAPIServerConnection extends ServerConnection {

    private List superHList;
    private List superGList;

    public PacketAPIServerConnection(MinecraftServer minecraftserver) {
        super(minecraftserver);

        try {
            Field e = ServerConnection.class.getDeclaredField("h");
            e.setAccessible(true);
            Field f = ServerConnection.class.getDeclaredField("g");
            f.setAccessible(true);

            superHList = (List) e.get(this);
            superGList = (List) f.get(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void a(InetAddress inetaddress, int port) {

        Class<? extends ServerSocketChannel> socketClass;
        LazyInitVar<? extends EventLoopGroup> lazyinitvar;
        if(Epoll.isAvailable() && MinecraftServer.getServer().ae()) {
            socketClass = EpollServerSocketChannel.class;
            lazyinitvar = ServerConnection.b;
            LogManager.getLogger().info("Using epoll channel type");
        } else {
            socketClass = NioServerSocketChannel.class;
            lazyinitvar = ServerConnection.a;
            LogManager.getLogger().info("Using default channel type");
        }

        try {
            System.out.println("[PacketAPI] Bind custom ServerConnection on " + (inetaddress != null ? inetaddress.getHostAddress() : "") + ":" + port);
            ServerBootstrap sb = new ServerBootstrap();
            sb.channel(socketClass);
            sb.childHandler(new PacketAPIServerConnector(this));
            sb.group(lazyinitvar.c());
            sb.localAddress(inetaddress, port);
            ChannelFuture f = sb.bind().syncUninterruptibly();

            synchronized (superGList) {
                superGList.add(f);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<NetworkManager> getNetworkManagerList() {
        return (List<NetworkManager>) superHList;
    }

}
