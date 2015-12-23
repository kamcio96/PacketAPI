package pl.kamcio96.packetapi.api;

import org.bukkit.Bukkit;

import java.lang.reflect.Field;

public class Reflection {

    private static String version;

    static {
        version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    }

    public static String getPackageVersion() {
        return version;
    }

    // easy way to get NMS classes
    public static Class<?> getMCClass(String name) throws ClassNotFoundException {
        String className = "net.minecraft.server." + version + "." + name;
        return Class.forName(className);
    }

    // easy way to get CraftBukkit classes
    public static Class<?> getCraftClass(String name) throws ClassNotFoundException {
        String className = "org.bukkit.craftbukkit." + version + "." + name;
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
