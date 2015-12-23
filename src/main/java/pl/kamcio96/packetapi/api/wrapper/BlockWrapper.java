package pl.kamcio96.packetapi.api.wrapper;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers;

public class BlockWrapper {

    public static Material toBukkitMaterial(Object block) {
        return CraftMagicNumbers.getMaterial((net.minecraft.server.v1_8_R3.Block) block);
    }

    public static Object toMinecraftBlock(Material mat) {
        return CraftMagicNumbers.getBlock(mat);
    }
}
