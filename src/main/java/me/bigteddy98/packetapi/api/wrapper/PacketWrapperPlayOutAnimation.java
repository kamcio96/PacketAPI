package me.bigteddy98.packetapi.api.wrapper;

import me.bigteddy98.packetapi.PacketWrapper;
import me.bigteddy98.packetapi.collections.Animation;

public class PacketWrapperPlayOutAnimation extends PacketWrapper {

	public PacketWrapperPlayOutAnimation(Object packet) {
		super(packet);
	}

	public int getEntityId() {
		return (Integer) getValue("a");
	}

	public void setEntityId(int entityId) {
		setValue("a", entityId);
	}

	public Animation getAnimation() {
		int animationId = (Integer) getValue("b");
		for (Animation ani : Animation.values()) {
			if (ani.getId() == animationId) {
				return ani;
			}
		}
		return null;
	}

	public void setAnimation(Animation animation) {
		setValue("b", animation.getId());
	}

}
