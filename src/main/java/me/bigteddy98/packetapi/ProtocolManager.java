package me.bigteddy98.packetapi;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import me.bigteddy98.packetapi.api.Cancellable;
import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.server.v1_7_R4.MinecraftServer;
import net.minecraft.server.v1_7_R4.NetworkManager;
import net.minecraft.server.v1_7_R4.Packet;
import net.minecraft.server.v1_7_R4.ServerConnection;
import net.minecraft.util.io.netty.channel.Channel;
import net.minecraft.util.io.netty.channel.ChannelDuplexHandler;
import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
import net.minecraft.util.io.netty.channel.ChannelPromise;

import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.ServerListPingEvent;

public class ProtocolManager implements Listener {

	private PacketAPI plugin;
	private Field mChannel;
	private Field getConsole;
	private Field getServerConnection;
	private Field getF;

	private HashMap<String, Channel> channels = new HashMap<String, Channel>();
	private List<Channel> injectedChannels = new ArrayList<Channel>();

	public ProtocolManager(PacketAPI plugin) {

		try {
			mChannel = NetworkManager.class.getDeclaredField("m");
			mChannel.setAccessible(true);
			getConsole = CraftServer.class.getDeclaredField("console");
			getConsole.setAccessible(true);
			getServerConnection = MinecraftServer.class.getDeclaredField("o");
			getServerConnection.setAccessible(true);
			getF = ServerConnection.class.getDeclaredField("f");
			getF.setAccessible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.plugin = plugin;
		this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);

		for (Player p : this.plugin.getServer().getOnlinePlayers()) {
			injectPlayer(p);
		}
	}

	@EventHandler
	private void onJoin(PlayerJoinEvent event) {
		injectPlayer(event.getPlayer());
	}

	@EventHandler
	public void onPing(ServerListPingEvent event) throws IllegalArgumentException, IllegalAccessException {

		MinecraftServer server = (MinecraftServer) getConsole.get(this.plugin.getServer());
		ServerConnection connection = (ServerConnection) this.getServerConnection.get(server);
		@SuppressWarnings("unchecked")
		List<NetworkManager> list = (List<NetworkManager>) getF.get(connection);

		for (NetworkManager manager : list) {
			injectConnection(manager);
		}
	}

	public void disable() {
		HandlerList.unregisterAll(this);
		for (Entry<String, Channel> channel : this.channels.entrySet()) {
			if (this.plugin.getServer().getPlayerExact(channel.getKey()) != null) {
				channel.getValue().pipeline().remove("PacketAPI");
			}
		}
	}

	private void injectPlayer(final Player p) {
		try {
			EntityPlayer ep = ((CraftPlayer) p).getHandle();
			Channel channel = (Channel) mChannel.get(ep.playerConnection.networkManager);
			channels.put(p.getName(), channel);

			channel.pipeline().addBefore("packet_handler", "PacketAPI", new ChannelDuplexHandler() {

				@Override
				public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
					Cancellable cancel = new Cancellable();
					plugin.packetSend(new PacketWrapper((Packet) msg), cancel, p.getName());
					if (cancel.isCancelled()) {
						return;
					}
					super.write(ctx, msg, promise);
				}

				@Override
				public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
					Cancellable cancel = new Cancellable();
					plugin.packetRecieve(new PacketWrapper((Packet) msg), cancel, p.getName());
					if (cancel.isCancelled()) {
						return;
					}
					super.channelRead(ctx, msg);
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void injectConnection(NetworkManager manager) throws IllegalArgumentException, IllegalAccessException {
		Channel channel = (Channel) mChannel.get(manager);

		if (this.injectedChannels.contains(channel)) {
			return;
		}
		this.injectedChannels.add(channel);

		channel.pipeline().addBefore("packet_handler", "PacketAPI Ping", new ChannelDuplexHandler() {

			@Override
			public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
				if (!msg.getClass().getSimpleName().startsWith("PacketStatus")) {
					super.write(ctx, msg, promise);
					return;
				}
				Cancellable cancel = new Cancellable();
				plugin.packetSend(new PacketWrapper((Packet) msg), cancel, null);
				if (cancel.isCancelled()) {
					return;
				}
				super.write(ctx, msg, promise);
			}

			@Override
			public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
				if (!msg.getClass().getSimpleName().startsWith("PacketStatus")) {
					super.channelRead(ctx, msg);
					return;
				}
				Cancellable cancel = new Cancellable();
				plugin.packetRecieve(new PacketWrapper((Packet) msg), cancel, null);
				if (cancel.isCancelled()) {
					return;
				}
				super.channelRead(ctx, msg);
			}
		});
	}
}
