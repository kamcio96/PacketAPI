package pl.kamcio96.packetapi.api;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

import java.net.InetAddress;

abstract class PacketEvent extends Event implements Cancellable {

    private PacketWrapper packet;
    private Player player;
    private InetAddress address;
    private boolean cancelled = false;

    public PacketEvent(PacketWrapper packet, Player player, InetAddress address) {
        super(true);
        this.packet = packet;
        this.player = player;
        this.address = address;
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

    public boolean hasPlayer() {
        return player != null;
    }

    public Player getPlayer() {
        return player;
    }

    public InetAddress getAddress() {
        return address;
    }

}
