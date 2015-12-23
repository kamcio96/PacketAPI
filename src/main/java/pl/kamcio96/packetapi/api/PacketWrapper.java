package pl.kamcio96.packetapi.api;

import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

public class PacketWrapper {

    protected Object packet;

    public PacketWrapper(Object packet) {
        this.packet = packet;
    }

    public void setValue(Object value, String... names) {
        if (names.length == 0) throw new IllegalArgumentException();
        Exception e = null;
        for (String fieldName : names) {
            try {
                Field f = this.packet.getClass().getDeclaredField(fieldName);
                f.setAccessible(true);
                f.set(this.packet, value);
                return;
            } catch (Exception ex) {
                e = ex;
            }
        }
        throw new RuntimeException(e);
    }

    public Object getValue(String... names) {
        if (names.length == 0) throw new IllegalArgumentException();
        Exception e = null;
        for (String fieldName : names) {
            try {
                Field f = this.packet.getClass().getDeclaredField(fieldName);
                f.setAccessible(true);
                return f.get(this.packet);
            } catch (Exception ex) {
                e = ex;
            }
        }
        throw new RuntimeException(e);
    }

    public void send(Player p) {
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket((Packet) this.packet);
    }

    public Object getNMSPacket() {
        return this.packet;
    }

    public String getName() {
        return this.packet.getClass().getSimpleName();
    }
}
