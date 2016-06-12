package pl.kamcio96.packetapi.api.wrapper;

import org.bukkit.craftbukkit.v1_10_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class ItemStackConverter {

    public static Object toMinecraftItemStack(ItemStack stack) {
        return CraftItemStack.asNMSCopy(stack);
    }

    public static ItemStack toBukkitItemStack(Object nms_stack) {
        return CraftItemStack.asBukkitCopy((net.minecraft.server.v1_10_R1.ItemStack) nms_stack);
    }
}
