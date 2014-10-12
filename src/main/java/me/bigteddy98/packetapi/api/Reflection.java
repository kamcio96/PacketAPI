package me.bigteddy98.packetapi.api;

import java.lang.reflect.Field;

import net.minecraft.server.v1_7_R4.ItemStack;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;

public class Reflection {

	private static Field handle;

	static {
		try {
			handle = CraftItemStack.class.getDeclaredField("handle");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("This version of PacketAPI is outdated, please report to the author.");
		}
	}

	public static ItemStack getHandle(CraftItemStack stack) {
		try {
			return (ItemStack) handle.get(stack);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// easy way to get NMS classes
	public static Class<?> getMCClass(String name) throws ClassNotFoundException {
		String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".";
		String className = "net.minecraft.server." + version + name;
		return Class.forName(className);
	}

	// easy way to get CraftBukkit classes
	public static Class<?> getCraftClass(String name) throws ClassNotFoundException {
		String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".";
		String className = "org.bukkit.craftbukkit." + version + name;
		return Class.forName(className);
	}

	public static Field getFieldByClass(Class<?> clazz, Class<?> type) {
		for (Field field : clazz.getDeclaredFields()) {
			if (field.getType().getName().equals(type.getName())) {
				field.setAccessible(true);
				return field;
			}
		}
		System.out.println("This version of PacketAPI is outdated, please report to the author.");
		return null;
	}

	public static boolean getBoolean(Field field, Object packet) {
		try {
			return field.getBoolean(packet);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("This version of PacketAPI is outdated, please report to the author.");
		}
		return false;
	}

	public static void setBoolean(Field field, Object packet, boolean booleann) {
		try {
			field.setBoolean(packet, booleann);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("This version of PacketAPI is outdated, please report to the author.");
		}
	}

	public static float getFloat(Field field, Object packet) {
		try {
			return field.getFloat(packet);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("This version of PacketAPI is outdated, please report to the author.");
		}
		return 0;
	}

	public static void setFloat(Field field, Object packet, float floatt) {
		try {
			field.setFloat(packet, floatt);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("This version of PacketAPI is outdated, please report to the author.");
		}
	}

	public static int getInt(Field field, Object packet) {
		try {
			return field.getInt(packet);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("This version of PacketAPI is outdated, please report to the author.");
		}
		return 0;
	}

	public static void setInt(Field field, Object packet, int intt) {
		try {
			field.setInt(packet, intt);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("This version of PacketAPI is outdated, please report to the author.");
		}
	}
}
