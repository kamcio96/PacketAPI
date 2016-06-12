package pl.kamcio96.packetapi;

import net.minecraft.server.v1_10_R1.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.spigotmc.SpigotConfig;

import java.lang.reflect.Field;
import java.util.logging.Level;

public class PacketAPI extends JavaPlugin {

    @Override
    public void onEnable() {

        try {
            Class.forName("org.spigotmc.SpigotConfig");
        } catch (ClassNotFoundException e) {
            getServer().getConsoleSender().sendMessage(ChatColor.RED + "SpigotConfig not found!");
            getServer().getConsoleSender().sendMessage(ChatColor.RED + "PacketAPI requires Spigot!");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        if (!SpigotConfig.lateBind) {
            getServer().getConsoleSender().sendMessage(ChatColor.RED + "SpigotConfig found but late-bind is disabled!");
            getServer().getConsoleSender().sendMessage(ChatColor.RED + "PacketAPI requires late-bind option!");
            getServer().getConsoleSender().sendMessage(ChatColor.RED + "Please change it in spigot.yml!");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        try {
            MinecraftServer mcserver = MinecraftServer.getServer();
            Field f = MinecraftServer.class.getDeclaredField("p");

            f.setAccessible(true);
            Object o = f.get(mcserver);
            if (o != null) {
                throw new IllegalStateException("Server reload isn't supported by PacketAPI. Please restart server!!!");
            }

            f.set(mcserver, new PacketAPIServerConnection(mcserver));
            getLogger().log(Level.INFO, "All works fine ;)");
        } catch (Exception e) {
            getServer().getConsoleSender().sendMessage("[PacketAPI] " + ChatColor.RED + "Fatal error: ");
            e.printStackTrace();
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        // test:
        /*Bukkit.getPluginManager().registerEvents(new Listener() {

            @EventHandler
            public void onPacketRecieve(PacketRecieveEvent event) {
                System.out.println(event.getPacket().getName());
            }

            @EventHandler
            public void onPacketSend(PacketSendEvent event) {
                System.out.println(event.getPacket().getName());
            }

        }, this);*/

		/*try { // TODO
            Metrics metrics = new Metrics(this);
			metrics.start();
		} catch (IOException e) {
			// Failed to submit the stats :-(
		}*/
    }
}
