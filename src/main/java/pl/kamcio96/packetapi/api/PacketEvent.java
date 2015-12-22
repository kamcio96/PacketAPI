package pl.kamcio96.packetapi.api;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

abstract class PacketEvent extends Event implements Cancellable {

    private PacketWrapper packet;
    private Connection connection;
    private boolean cancelled = false;

    public PacketEvent(PacketWrapper packet, Connection connection) {
        super(true);
        this.packet = packet;
        this.connection = connection;
    }

    public PacketWrapper getPacket() {
        return packet;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    public Connection getConnection() {
        return connection;
    }

}
