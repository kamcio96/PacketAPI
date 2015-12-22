package pl.kamcio96.packetapi.api;

import org.bukkit.entity.Player;

import java.net.InetAddress;

public interface Connection {

    InetAddress getAddress();

    boolean hasPlayer();

    Player getPlayer();

}
