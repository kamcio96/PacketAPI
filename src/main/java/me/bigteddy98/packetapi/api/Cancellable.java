package me.bigteddy98.packetapi.api;

public class Cancellable {
	private boolean cancelled = false;

	public boolean isCancelled() {
		return cancelled;
	}

	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

}
