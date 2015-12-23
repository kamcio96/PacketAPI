package pl.kamcio96.packetapi.api.wrapper;

import pl.kamcio96.packetapi.api.PacketWrapper;
import pl.kamcio96.packetapi.collections.Animation;

@Deprecated
@SuppressWarnings(value = {"TODO class"})
public class PacketWrapperPlayOutAnimation extends PacketWrapper {

    public PacketWrapperPlayOutAnimation(Object packet) {
        super(packet);
    }

    public int getEntityId() {
        return (Integer) getValue("a");
    }

    public void setEntityId(int entityId) {
        setValue(entityId, "a");
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
        setValue(animation.getId(), "b");
    }

}
