package me.bigteddy98.packetapi.api;

import me.bigteddy98.packetapi.PacketWrapper;

public class PacketSendEvent {

	private PacketWrapper packet;
	private Cancellable cancel;
	private String recieverName;

	public PacketSendEvent(PacketWrapper packet, Cancellable cancel, String recieverName) {
		this.packet = packet;
		this.cancel = cancel;
		this.recieverName = recieverName;
	}

	public PacketWrapper getPacket() {
		return packet;
	}
	
	public boolean isCancelled(){
		return this.cancel.isCancelled();
	}
	
	public void setCancelled(boolean cancel){
		this.cancel.setCancelled(cancel);
	}

	public String getRecieverName() {
		return recieverName;
	}

	@Deprecated
	public String getPacketName() {
		return this.packet.getNMSPacket().getClass().getSimpleName();
	}
}
