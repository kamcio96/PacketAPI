package pl.kamcio96.packetapi.api.wrapper;

import net.minecraft.server.v1_10_R1.EnumGamemode;
import org.bukkit.GameMode;

public class GamemodeWrapper {

    public static GameMode toBukkitGamemode(Object enumGamemode) {
        EnumGamemode gamemode = (EnumGamemode) enumGamemode;
        return GameMode.valueOf(gamemode.toString().toUpperCase());
    }

    public static Object toMinecraftGameMode(GameMode gamemode) {
        return EnumGamemode.valueOf(gamemode.toString().toUpperCase());
    }
}
