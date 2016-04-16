package pl.kamcio96.packetapi.api.wrapper;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_9_R1.util.CraftMagicNumbers;

public class BlockWrapper {

    public static Material toBukkitMaterial(Object block) {
        return CraftMagicNumbers.getMaterial((net.minecraft.server.v1_9_R1.Block) block);
    }

    public static Object toMinecraftBlock(Material mat) {
        return CraftMagicNumbers.getBlock(mat);
    }
}
