package pl.kamcio96.packetapi.api.wrapper;

import net.minecraft.server.v1_10_R1.EnumDifficulty;
import org.bukkit.Difficulty;

public class DifficultyWrapper {

    public static Difficulty toBukkitDiffuculty(Object enumDiffi) {
        EnumDifficulty diffi = (EnumDifficulty) enumDiffi;
        return Difficulty.valueOf(diffi.toString().toUpperCase());
    }

    public static Object toMinecraftDiffuculty(Difficulty diffi) {
        return EnumDifficulty.valueOf(diffi.toString().toUpperCase());
    }
}
