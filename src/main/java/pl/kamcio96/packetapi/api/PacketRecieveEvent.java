package pl.kamcio96.packetapi.api;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import java.net.InetAddress;

public class PacketRecieveEvent extends PacketEvent {

    private final static HandlerList handlers = new HandlerList();

    public static HandlerList getHandlerList() {
        return handlers;
    }

	/*===========================================================*/

    public PacketRecieveEvent(PacketWrapper packet, Player player, InetAddress address) {
        super(packet, player, address);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
