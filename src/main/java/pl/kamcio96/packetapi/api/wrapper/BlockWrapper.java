package pl.kamcio96.packetapi.api.wrapper;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R4.util.CraftMagicNumbers;

public class BlockWrapper {

    public static Material toBukkitMaterial(Object block) {
        //not sure if this works. //TO-DO test it
        return CraftMagicNumbers.getMaterial((net.minecraft.server.v1_7_R4.Block) block);
    }

    @SuppressWarnings("deprecation")
    public static Object toMinecraftBlock(Material mat) {
        return CraftMagicNumbers.getBlock(mat);
    }
}
