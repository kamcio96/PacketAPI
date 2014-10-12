package me.bigteddy98.packetapi;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import me.bigteddy98.packetapi.api.PacketHandler;
import me.bigteddy98.packetapi.api.PacketListener;
import me.bigteddy98.packetapi.api.PacketRecieveEvent;
import me.bigteddy98.packetapi.api.PacketSendEvent;
import me.bigteddy98.packetapi.api.PacketType;
import me.bigteddy98.packetapi.api.Reflection;

public class ExamplePlugin implements PacketListener {

	public ExamplePlugin(PacketAPI plugin) {
		PacketAPI.getInstance().addListener(this);
	}

	@PacketHandler(listenType = PacketType.PacketPlayInArmAnimation)
	public void onRecieve(PacketRecieveEvent event) {
		System.out.println("in: " + event.getPacket().getName());
		event.setCancelled(true);
	}

	@PacketHandler(listenType = PacketType.PacketStatusOutServerInfo)
	public void onSend(PacketSendEvent event) throws Exception {
		Object sample = PacketDataWrapper.ServerPingPlayerSample(1002, 1021);
		Method setSample = Reflection.getMCClass("ServerPing").getMethod("setPlayerSample", sample.getClass());
		Field f = Reflection.getFieldByClass(event.getPacket().getNMSPacket().getClass(), Reflection.getMCClass("ServerPing"));
		Object currentPing = f.get(event.getPacket().getNMSPacket());
		setSample.invoke(currentPing, sample);
	}
}
