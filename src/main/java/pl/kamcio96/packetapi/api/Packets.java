package pl.kamcio96.packetapi.api;

import net.minecraft.server.v1_9_R1.*;
import org.bukkit.Chunk;
import org.bukkit.*;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.craftbukkit.v1_9_R1.CraftChunk;
import org.bukkit.craftbukkit.v1_9_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_9_R1.entity.*;
import org.bukkit.craftbukkit.v1_9_R1.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import pl.kamcio96.packetapi.api.collections.*;
import pl.kamcio96.packetapi.api.collections.Slot;
import pl.kamcio96.packetapi.api.wrapper.BlockWrapper;
import pl.kamcio96.packetapi.api.wrapper.DifficultyWrapper;

import java.util.*;

public class Packets {

    public static PacketWrapper PacketPlayOutAbilities(boolean isInvulnerable, boolean isFlying, boolean canFly, boolean canInstantlyBuild, float flySpeed, float walkSpeed) {
        PlayerAbilities abilities = new PlayerAbilities();
        abilities.isInvulnerable = isInvulnerable;
        abilities.isFlying = isFlying;
        abilities.canFly = canFly;
        abilities.canInstantlyBuild = canInstantlyBuild;
        abilities.flySpeed = flySpeed;
        abilities.walkSpeed = walkSpeed;

        return new PacketWrapper(new PacketPlayOutAbilities(abilities));
    }

    public static PacketWrapper PacketPlayOutAnimation(Player p, Animation animation) {
        return new PacketWrapper(new PacketPlayOutAnimation(((CraftPlayer) p).getHandle(), animation.getId()));
    }

    public static PacketWrapper PacketPlayOutAttachEntity(Entity entity1, Entity entity2) {
        return new PacketWrapper(new PacketPlayOutAttachEntity(((CraftEntity) entity2).getHandle(), ((CraftEntity) entity1).getHandle()));
    }

    public static PacketWrapper PacketPlayOutAttachEntity(Entity entity1, Entity entity2, int data) {
        return new PacketWrapper(new PacketPlayOutAttachEntity(((CraftEntity) entity2).getHandle(), ((CraftEntity) entity1).getHandle()));
    }

    public static PacketWrapper PacketPlayOutBed(Player p, int bedX, int bedY, int bedZ) {
        return new PacketWrapper(new PacketPlayOutBed(((CraftPlayer) p).getHandle(), new BlockPosition(bedX, bedY, bedZ)));
    }

    public static PacketWrapper PacketOutBlockAction(int xLoc, int yLoc, int zLoc, int data1, int data2, Material material) {
        return new PacketWrapper(new PacketPlayOutBlockAction(new BlockPosition(xLoc, yLoc, zLoc), (Block) BlockWrapper.toMinecraftBlock(material), data2, data1));
    }

    public static PacketWrapper PacketPlayOutBlockBreakAnimation(int breakId, int xLoc, int yLoc, int zLoc, byte destroy_stage) {
        return new PacketWrapper(new PacketPlayOutBlockBreakAnimation(breakId, new BlockPosition(xLoc, yLoc, zLoc), destroy_stage));
    }

    public static PacketWrapper PacketPlayOutBlockChange(int locX, int locY, int locZ, World world) {
        return new PacketWrapper(new PacketPlayOutBlockChange(((CraftWorld) world).getHandle(), new BlockPosition(locX, locY, locZ)));
    }

    public static PacketWrapper PacketPlayOutChat(String text) {
        return new PacketWrapper(new PacketPlayOutChat(new ChatComponentText(text)));
    }

    public static PacketWrapper PacketPlayOutCloseWindow(int window_id) {
        return new PacketWrapper(new PacketPlayOutCloseWindow(window_id));
    }

    public static PacketWrapper PacketPlayOutCollect(Entity collected, Entity collector) {
        return new PacketWrapper(new PacketPlayOutCollect(collected.getEntityId(), collector.getEntityId()));
    }

    public static PacketWrapper PacketPlayOutEntity(int entity_id) {
        return new PacketWrapper(new PacketPlayOutEntity(entity_id));
    }

    public static PacketWrapper PacketPlayOutEntityDestroy(int[] entity_ids) {
        return new PacketWrapper(new PacketPlayOutEntityDestroy(entity_ids));
    }

    public static PacketWrapper PacketPlayOutEntityEffect(LivingEntity e, int effect_id, int duration, int amplification, boolean ambient) {
        return PacketPlayOutEntityEffect(e, effect_id, duration, amplification, ambient, true);
    }

    public static PacketWrapper PacketPlayOutEntityEffect(LivingEntity e, int effect_id, int duration, int amplification, boolean ambient, boolean showparticle) {
        return new PacketWrapper(new PacketPlayOutEntityEffect(((CraftEntity) e).getHandle().getId(), new MobEffect(MobEffectList.fromId(effect_id), duration, amplification, ambient, showparticle)));
    }

    private static EnumItemSlot convert(Slot slot) {
        for(EnumItemSlot nmsslot : EnumItemSlot.values()) {
            if(nmsslot.b() == slot.getId()) {
                return nmsslot;
            }
        }

        throw new RuntimeException("Report to author!");
    }

    public static PacketWrapper PacketPlayOutEntityEquipment(int entity_id, Slot type, ItemStack stack) {
        return new PacketWrapper(new PacketPlayOutEntityEquipment(entity_id,  convert(type), CraftItemStack.asNMSCopy(stack)));
    }

    public static PacketWrapper PacketPlayOutEntityHeadRotation(Entity entity, byte yaw) {
        return new PacketWrapper(new PacketPlayOutEntityHeadRotation(((CraftEntity) entity).getHandle(), yaw));
    }

    public static PacketWrapper PacketPlayOutEntityMetadata(Entity entity, boolean paramBoolean) {
        return new PacketWrapper(new PacketPlayOutEntityMetadata(entity.getEntityId(), ((CraftEntity) entity).getHandle().getDataWatcher(), paramBoolean));
    }

    public static PacketWrapper PacketPlayOutEntityStatus(Entity entity, Status type) {
        return new PacketWrapper(new PacketPlayOutEntityStatus(((CraftEntity) entity).getHandle(), type.getId()));
    }

    public static PacketWrapper PacketPlayOutEntityTeleport(int entity_id, Location loc, boolean onGround) {
        PacketWrapper wrapper = new PacketWrapper(new PacketPlayOutEntityTeleport());
        wrapper.setValue(entity_id, "a");
        wrapper.setValue(loc.getX(), "b");
        wrapper.setValue(loc.getY(), "c");
        wrapper.setValue(loc.getZ(), "d");
        wrapper.setValue((byte)((int)(loc.getYaw() * 256.0F / 360.0F)), "e");
        wrapper.setValue((byte)((int)(loc.getPitch() * 256.0F / 360.0F)), "f");
        wrapper.setValue(onGround, "g");
        return wrapper;
    }

    public static PacketWrapper PacketPlayOutEntityVelocity(int entity_id, Entity e) {
        return new PacketWrapper(new PacketPlayOutEntityVelocity(entity_id, ((CraftEntity) e).getHandle().motX, ((CraftEntity) e).getHandle().motY, ((CraftEntity) e).getHandle().motZ));
    }

    public static PacketWrapper PacketPlayOutExperience(float bar_0_1, int level, int totalExp) {
        return new PacketWrapper(new PacketPlayOutExperience(bar_0_1, level, totalExp));
    }

    public static PacketWrapper PacketPlayOutExplosion(Location loc, float radius) {
        return new PacketWrapper(new PacketPlayOutExplosion(loc.getX(), loc.getY(), loc.getZ(), radius, new ArrayList<BlockPosition>(), null));
    }

    public static PacketWrapper PacketPlayOutGameStateChange(StateChange reason, int value) {
        return new PacketWrapper(new PacketPlayOutGameStateChange(reason.getId(), value));
    }

    public static PacketWrapper PacketPlayOutHeldItemSlot(int slot) {
        return new PacketWrapper(new PacketPlayOutHeldItemSlot(slot));
    }

    public static PacketWrapper PacketPlayOutKeepAlive(int alive_id) {
        return new PacketWrapper(new PacketPlayOutKeepAlive(alive_id));
    }

    public static PacketWrapper PacketPlayOutKickDisconnect(String text) {
        return new PacketWrapper(new PacketPlayOutKickDisconnect(new ChatComponentText(text)));
    }

    //public static PacketWrapper PacketPlayOutMap(int damage_value, byte[] data, byte scale) {
    //    return new PacketWrapper(new PacketPlayOutMap(damage_value, data, scale));
    //}

    public static PacketWrapper PacketPlayOutMapChunk(Chunk chunk, boolean flag, int paramInt) {
        return new PacketWrapper(new PacketPlayOutMapChunk(((CraftChunk) chunk).getHandle(), flag, paramInt));
    }

    public static PacketWrapper PacketPlayOutMultiBlockChange(Chunk chunk, int block_count, short[] data) {
        return new PacketWrapper(new PacketPlayOutMultiBlockChange(block_count, data, ((CraftChunk) chunk).getHandle()));
    }

    // TODO
    /* static PacketWrapper PacketPlayOutNamedEntitySpawn(HumanEntity e) {
        return new PacketWrapper(new PacketPlayOutNamedEntitySpawn(((CraftHumanEntity) e).getHandle()));
    }

    public static PacketWrapper PacketPlayOutNamedEntitySpawn(Player randomOnlinePlayer, GameProfile profile, int x, int y, int z, byte yaw, byte pitch, int itemInHand) {
        EntityHuman entity = new EntityPlayer(((CraftPlayer) randomOnlinePlayer).getHandle().server, ((CraftPlayer) randomOnlinePlayer).getHandle().r(), profile, new PlayerInteractManager(((CraftPlayer) randomOnlinePlayer).getHandle().world));
        entity.setLocation(x, y, z, yaw, pitch);
        try { // R3 , R2
            entity.setEquipment(0, new net.minecraft.server.v1_7_R4.ItemStack(Item.getById(itemInHand)));
        } catch (Exception e) {
            try {
                entity.setEquipment(0, new net.minecraft.server.v1_7_R4.ItemStack((Item) Item.class.getMethod("d", int.class).invoke(itemInHand)));
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

        return new PacketWrapper(new PacketPlayOutNamedEntitySpawn(entity));
    }*/

    public static PacketWrapper PacketPlayOutNamedSoundEffect(String name, int locX, int locY, int locZ, float volume, float pitch) {
        return new PacketWrapper(new PacketPlayOutNamedSoundEffect(SoundEffect.a.get(new MinecraftKey(name)), SoundCategory.NEUTRAL, locX, locY, locZ, volume, pitch));
    }

    public static PacketWrapper PacketPlayOutNamedSoundEffect(String name, String category, int locX, int locY, int locZ, float volume, float pitch) {
        return new PacketWrapper(new PacketPlayOutNamedSoundEffect(SoundEffect.a.get(new MinecraftKey(name)), SoundCategory.a(category), locX, locY, locZ, volume, pitch));
    }

    public static PacketWrapper PacketPlayOutOpenSignEditor(int locX, int locY, int locZ) {
        return new PacketWrapper(new PacketPlayOutOpenSignEditor(new BlockPosition(locX, locY, locZ)));
    }

    //public static PacketWrapper PacketPlayOutOpenWindow(int window_id, String inventory_title, int slot_size, boolean provided_title) {
    //    return new PacketWrapper(new PacketPlayOutOpenWindow(window_id, inventory_title, slot_size, provided_title));
    //}

    //public static PacketWrapper PacketPlayOutOpenWindow(int window_id, String inventory_title, int slot_size, boolean provided_title, int horse_entity_id) {
    //    return new PacketWrapper(new PacketPlayOutOpenWindow(window_id, inventory_title, new ChatComponentText(inventory_title), slot_size, provided_title, horse_entity_id));
    //}

    public static PacketWrapper PacketPlayOutPosition(int locX, int locY, int locZ, int yaw, int pitch, int teleportId) {
        return new PacketWrapper(new PacketPlayOutPosition(locX, locY, locZ, yaw, pitch, new HashSet<PacketPlayOutPosition.EnumPlayerTeleportFlags>(), teleportId));
    }

    public static PacketWrapper PacketPlayOutRelEntityMove(int entity_id, byte DX, byte DY, byte DZ, boolean isOnGround) {
        return new PacketWrapper(new PacketPlayOutEntity.PacketPlayOutRelEntityMove(entity_id, DX, DY, DZ, isOnGround));
    }

    public static PacketWrapper PacketPlayOutRelEntityMoveLook(int entity_id, byte DX, byte DY, byte DZ, byte yaw, byte pitch, boolean isOnGround) {
        return new PacketWrapper(new PacketPlayOutEntity.PacketPlayOutRelEntityMoveLook(entity_id, DX, DY, DZ, yaw, pitch, isOnGround));
    }

    public static PacketWrapper PacketPlayOutRespawn(Environment dimension, Difficulty diff, GameMode gamemode) {
        return new PacketWrapper(new PacketPlayOutRespawn(WorldDataConverter.getEnvironment(dimension), (EnumDifficulty) DifficultyWrapper.toMinecraftDiffuculty(diff), WorldDataConverter.getLevel(), WorldDataConverter.getGameMode(gamemode)));
    }

    public static PacketWrapper PacketPlayOutSetSlot(int window_id, short slot, ItemStack stack) {
        return new PacketWrapper(new PacketPlayOutSetSlot(window_id, slot, CraftItemStack.asNMSCopy(stack)));
    }

    public static PacketWrapper PacketPlayOutSpawnEntity(Entity e, int type) {
        return new PacketWrapper(new PacketPlayOutSpawnEntity(((CraftEntity) e).getHandle(), type));
    }

    public static PacketWrapper PacketPlayOutSpawnEntityExperienceOrb(ExperienceOrb orb) {
        return new PacketWrapper(new PacketPlayOutSpawnEntityExperienceOrb(((CraftExperienceOrb) orb).getHandle()));
    }

    public static PacketWrapper PacketPlayOutSpawnEntityLiving(LivingEntity e) {
        return new PacketWrapper(new PacketPlayOutSpawnEntityLiving(((CraftLivingEntity) e).getHandle()));
    }

    public static PacketWrapper PacketPlayOutSpawnEntityPainting(Painting e) {
        return new PacketWrapper(new PacketPlayOutSpawnEntityPainting(((CraftPainting) e).getHandle()));
    }

    public static PacketWrapper PacketPlayOutSpawnEntityWeather(Entity e) {
        return new PacketWrapper(new PacketPlayOutSpawnEntityWeather(((CraftEntity) e).getHandle()));
    }

    public static PacketWrapper PacketPlayOutSpawnPosition(int xLoc, int yLoc, int zLoc) {
        return new PacketWrapper(new PacketPlayOutSpawnPosition(new BlockPosition(xLoc, yLoc, zLoc)));
    }

    //public static PacketWrapper PacketPlayOutStatistics(Map<String, Integer> list) {
    //    return new PacketWrapper(new PacketPlayOutStatistic(list));
    //}

    public static PacketWrapper PacketPlayOutTabComplete(String[] tabs) {
        return new PacketWrapper(new PacketPlayOutTabComplete(tabs));
    }

    public static PacketWrapper PacketPlayOutTransaction(int window_id, short action_id, boolean accepted) {
        return new PacketWrapper(new PacketPlayOutTransaction(window_id, action_id, accepted));
    }

    public static PacketWrapper PacketPlayOutUpdateHealth(float health_level, short food_level, float saturation) {
        return new PacketWrapper(new PacketPlayOutUpdateHealth(health_level, food_level, saturation));
    }

    public static PacketWrapper PacketPlayOutUpdateSign(World world, int xLoc, int yLoc, int zLoc, String[] lines) {
        return new PacketWrapper(new PacketPlayOutUpdateSign(((CraftWorld) world).getHandle(), new BlockPosition(xLoc, yLoc, zLoc), toComponents(lines)));
    }

    private static IChatBaseComponent[] toComponents(String[] lines) {
        IChatBaseComponent[] out = new IChatBaseComponent[lines.length];
        for(int i=0; i<lines.length; i++) {
            out[i] = new ChatComponentText(lines[i]);
        }
        return out;
    }

    public static PacketWrapper PacketPlayOutUpdateTime(long ticks_living, long day_time) {
        return new PacketWrapper(new PacketPlayOutUpdateTime(ticks_living, day_time, true));
    }

    public static PacketWrapper PacketPlayOutWindowItems(int window_id, List<ItemStack> stacks) {
        return new PacketWrapper(new PacketPlayOutWindowItems(window_id, convertItemStacks(stacks)));
    }

    private static List<net.minecraft.server.v1_9_R1.ItemStack> convertItemStacks(List<ItemStack> stacks) {
        List<net.minecraft.server.v1_9_R1.ItemStack> nms_stacks = new ArrayList<>();

        for (ItemStack stack : stacks) {
            try {
                nms_stacks.add(CraftItemStack.asNMSCopy(stack));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return nms_stacks;
    }

    //public static PacketWrapper PacketPlayOutWorldParticles(Particle type, float x, float y, float z, float offSetX, float offSetY, float offSetZ, float particleData, int amount) {
    //    return new PacketWrapper(new PacketPlayOutWorldParticles(EnumParticle.valueOf(type.getName().toUpperCase()), x, y, z, offSetX, offSetY, offSetZ, particleData, amount));
    //}

}
