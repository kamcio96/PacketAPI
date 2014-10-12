package me.bigteddy98.packetapi.api.wrapper;

import net.minecraft.server.v1_7_R4.Block;

import org.bukkit.Material;

public class BlockWrapper {

	public static Material makeMaterialFromBlock(Object block) {
		//not sure if this works. //TO-DO test it
		return Material.valueOf(((Block) block).getName().toUpperCase());
	}

	@SuppressWarnings("deprecation")
	public static Object makeBlock(Material mat) {
		return Block.getById(mat.getId());
	}
}
