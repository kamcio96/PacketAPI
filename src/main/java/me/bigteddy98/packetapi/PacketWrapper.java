package me.bigteddy98.packetapi;

import java.lang.reflect.Field;

import net.minecraft.server.v1_7_R4.Packet;

import org.bukkit.World;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PacketWrapper {

	protected Object packet;

	public PacketWrapper(Object packet) {
		this.packet = packet;
	}

	public void setValue(String fieldName, Object value) {
		try {
			Field f = this.packet.getClass().getDeclaredField(fieldName);
			f.setAccessible(true);
			f.set(this.packet, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Object getValue(String fieldName) {
		try {
			Field f = this.packet.getClass().getDeclaredField(fieldName);
			f.setAccessible(true);
			return f.get(this.packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void send(Player p) {
		((CraftPlayer) p).getHandle().playerConnection.sendPacket((Packet) this.packet);
	}

	public void broadcast(World world) {
		for (Player p : world.getPlayers()) {
			this.send(p);
		}
	}

	public Object getNMSPacket() {
		return this.packet;
	}

	public String getName() {
		return this.packet.getClass().getSimpleName();
	}
}
