package pl.kamcio96.packetapi.api.collections;

public enum Slot {
    HELD(0), MAIN_HAND(0), OFF_HAND(1), BOOTS(2), LEGGINGS(3), CHESTPLATE(4), HELMET(5);

    private int id;

    private Slot(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
