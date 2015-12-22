package pl.kamcio96.packetapi.api;

import org.bukkit.event.HandlerList;

public class PacketSendEvent extends PacketEvent {

    private final static HandlerList handlers = new HandlerList();

    public static HandlerList getHandlerList() {
        return handlers;
    }

	/*===========================================================*/

    public PacketSendEvent(PacketWrapper packet, Connection connection) {
        super(packet, connection);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
