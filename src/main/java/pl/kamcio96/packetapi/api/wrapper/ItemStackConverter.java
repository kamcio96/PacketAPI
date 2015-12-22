package pl.kamcio96.packetapi.api.wrapper;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import pl.kamcio96.packetapi.api.Reflection;

public class ItemStackConverter {
    public static Object toNMSItemStack(CraftItemStack stack) {
        return Reflection.getHandle(stack);
    }

    @SuppressWarnings("deprecation")
    public static ItemStack toBukkitItemStack(Object nms_stack) {
        //not sure if this works. //TO-DO test it
        net.minecraft.server.v1_7_R4.ItemStack stack = ((net.minecraft.server.v1_7_R4.ItemStack) nms_stack);
        return new ItemStack(Material.valueOf(stack.getName()).getId(), stack.count, (short) 100, (byte) stack.getData());
    }
}
