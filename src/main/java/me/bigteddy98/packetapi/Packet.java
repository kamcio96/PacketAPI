package me.bigteddy98.packetapi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.bigteddy98.packetapi.api.Reflection;
import me.bigteddy98.packetapi.collections.Animation;
import me.bigteddy98.packetapi.collections.Particle;
import me.bigteddy98.packetapi.collections.Slot;
import me.bigteddy98.packetapi.collections.StateChange;
import me.bigteddy98.packetapi.collections.Status;
import me.bigteddy98.packetapi.collections.WorldDataConverter;
import net.minecraft.server.v1_7_R4.Block;
import net.minecraft.server.v1_7_R4.ChatComponentText;
import net.minecraft.server.v1_7_R4.EntityHuman;
import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.server.v1_7_R4.Item;
import net.minecraft.server.v1_7_R4.MobEffect;
import net.minecraft.server.v1_7_R4.PacketPlayOutAbilities;
import net.minecraft.server.v1_7_R4.PacketPlayOutAttachEntity;
import net.minecraft.server.v1_7_R4.PacketPlayOutBed;
import net.minecraft.server.v1_7_R4.PacketPlayOutBlockAction;
import net.minecraft.server.v1_7_R4.PacketPlayOutBlockBreakAnimation;
import net.minecraft.server.v1_7_R4.PacketPlayOutBlockChange;
import net.minecraft.server.v1_7_R4.PacketPlayOutChat;
import net.minecraft.server.v1_7_R4.PacketPlayOutCloseWindow;
import net.minecraft.server.v1_7_R4.PacketPlayOutCollect;
import net.minecraft.server.v1_7_R4.PacketPlayOutEntity;
import net.minecraft.server.v1_7_R4.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_7_R4.PacketPlayOutEntityEffect;
import net.minecraft.server.v1_7_R4.PacketPlayOutEntityEquipment;
import net.minecraft.server.v1_7_R4.PacketPlayOutEntityHeadRotation;
import net.minecraft.server.v1_7_R4.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_7_R4.PacketPlayOutEntityStatus;
import net.minecraft.server.v1_7_R4.PacketPlayOutEntityTeleport;
import net.minecraft.server.v1_7_R4.PacketPlayOutEntityVelocity;
import net.minecraft.server.v1_7_R4.PacketPlayOutExperience;
import net.minecraft.server.v1_7_R4.PacketPlayOutExplosion;
import net.minecraft.server.v1_7_R4.PacketPlayOutGameStateChange;
import net.minecraft.server.v1_7_R4.PacketPlayOutHeldItemSlot;
import net.minecraft.server.v1_7_R4.PacketPlayOutKeepAlive;
import net.minecraft.server.v1_7_R4.PacketPlayOutKickDisconnect;
import net.minecraft.server.v1_7_R4.PacketPlayOutMap;
import net.minecraft.server.v1_7_R4.PacketPlayOutMapChunk;
import net.minecraft.server.v1_7_R4.PacketPlayOutMapChunkBulk;
import net.minecraft.server.v1_7_R4.PacketPlayOutMultiBlockChange;
import net.minecraft.server.v1_7_R4.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_7_R4.PacketPlayOutNamedSoundEffect;
import net.minecraft.server.v1_7_R4.PacketPlayOutOpenSignEditor;
import net.minecraft.server.v1_7_R4.PacketPlayOutOpenWindow;
import net.minecraft.server.v1_7_R4.PacketPlayOutPosition;
import net.minecraft.server.v1_7_R4.PacketPlayOutRelEntityMove;
import net.minecraft.server.v1_7_R4.PacketPlayOutRelEntityMoveLook;
import net.minecraft.server.v1_7_R4.PacketPlayOutRespawn;
import net.minecraft.server.v1_7_R4.PacketPlayOutSetSlot;
import net.minecraft.server.v1_7_R4.PacketPlayOutSpawnEntity;
import net.minecraft.server.v1_7_R4.PacketPlayOutSpawnEntityExperienceOrb;
import net.minecraft.server.v1_7_R4.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_7_R4.PacketPlayOutSpawnEntityPainting;
import net.minecraft.server.v1_7_R4.PacketPlayOutSpawnEntityWeather;
import net.minecraft.server.v1_7_R4.PacketPlayOutSpawnPosition;
import net.minecraft.server.v1_7_R4.PacketPlayOutStatistic;
import net.minecraft.server.v1_7_R4.PacketPlayOutTabComplete;
import net.minecraft.server.v1_7_R4.PacketPlayOutTransaction;
import net.minecraft.server.v1_7_R4.PacketPlayOutUpdateHealth;
import net.minecraft.server.v1_7_R4.PacketPlayOutUpdateSign;
import net.minecraft.server.v1_7_R4.PacketPlayOutUpdateTime;
import net.minecraft.server.v1_7_R4.PacketPlayOutWindowItems;
import net.minecraft.server.v1_7_R4.PacketPlayOutWorldParticles;
import net.minecraft.server.v1_7_R4.PlayerAbilities;
import net.minecraft.server.v1_7_R4.PlayerInteractManager;
import net.minecraft.util.com.mojang.authlib.GameProfile;

import org.bukkit.Chunk;
import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.craftbukkit.v1_7_R4.CraftChunk;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftExperienceOrb;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftHumanEntity;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPainting;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Painting;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Packet {

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
		return new PacketWrapper(new net.minecraft.server.v1_7_R4.PacketPlayOutAnimation(((CraftPlayer) p).getHandle(), animation.getId()));
	}

	public static PacketWrapper PacketPlayOutAttachEntity(Entity entity1, Entity entity2) {
		return new PacketWrapper(new PacketPlayOutAttachEntity(0, ((CraftEntity) entity2).getHandle(), ((CraftEntity) entity1).getHandle()));
	}

	public static PacketWrapper PacketPlayOutAttachEntity(Entity entity1, Entity entity2, int data) {
		return new PacketWrapper(new PacketPlayOutAttachEntity(data, ((CraftEntity) entity2).getHandle(), ((CraftEntity) entity1).getHandle()));
	}

	public static PacketWrapper PacketPlayOutBed(Player p, int bedX, int bedY, int bedZ) {
		return new PacketWrapper(new PacketPlayOutBed(((CraftPlayer) p).getHandle(), bedX, bedY, bedZ));
	}

	@SuppressWarnings("deprecation")
	public static PacketWrapper PacketOutBlockAction(int xLoc, int yLoc, int zLoc, int data1, int data2, Material material) {
		return new PacketWrapper(new PacketPlayOutBlockAction(xLoc, yLoc, zLoc, Block.getById(material.getId()), data2, data1));
	}

	public static PacketWrapper PacketPlayOutBlockBreakAnimation(int breakId, int xLoc, int yLoc, int zLoc, byte destroy_stage) {
		return new PacketWrapper(new PacketPlayOutBlockBreakAnimation(breakId, xLoc, yLoc, zLoc, destroy_stage));
	}

	public static PacketWrapper PacketPlayOutBlockChange(int locX, int locY, int locZ, World world) {
		return new PacketWrapper(new PacketPlayOutBlockChange(locX, locY, locZ, ((CraftWorld) world).getHandle()));
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

	public static PacketWrapper PacketPlayOutEntityEffect(LivingEntity e, int effect_id, int duration, int amplification, int ambient) {
		return new PacketWrapper(new PacketPlayOutEntityEffect(((CraftEntity) e).getHandle().getId(), new MobEffect(effect_id, amplification, ambient)));
	}

	public static PacketWrapper PacketPlayOutEntityEquipment(int entity_id, Slot type, ItemStack stack) {
		return new PacketWrapper(new PacketPlayOutEntityEquipment(entity_id, type.getId(), Reflection.getHandle((CraftItemStack) stack)));
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

	public static PacketWrapper PacketPlayOutEntityTeleport(int entity_id, Location loc, boolean bool) {
		return new PacketWrapper(new PacketPlayOutEntityTeleport(entity_id, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), ((byte) (int) (loc.getYaw() * 256.0F / 360.0F)), ((byte) (int) (loc.getPitch() * 256.0F / 360.0F)), bool));
	}

	public static PacketWrapper PacketPlayOutEntityVelocity(int entity_id, Entity e) {
		return new PacketWrapper(new PacketPlayOutEntityVelocity(entity_id, ((CraftEntity) e).getHandle().motX, ((CraftEntity) e).getHandle().motY, ((CraftEntity) e).getHandle().motZ));
	}

	public static PacketWrapper PacketPlayOutExperience(float bar_0_1, int level, int totalExp) {
		return new PacketWrapper(new PacketPlayOutExperience(bar_0_1, level, totalExp));
	}

	public static PacketWrapper PacketPlayOutExplosion(Location loc, float radius) {
		return new PacketWrapper(new PacketPlayOutExplosion(loc.getX(), loc.getY(), loc.getZ(), radius, new ArrayList<Object>(), null));
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

	public static PacketWrapper PacketPlayOutMap(int damage_value, byte[] data, byte scale) {
		return new PacketWrapper(new PacketPlayOutMap(damage_value, data, scale));
	}

	public static PacketWrapper PacketPlayOutMapChunk(Chunk chunk, boolean flag, int paramInt, int version) {
		return new PacketWrapper(new PacketPlayOutMapChunk(((CraftChunk) chunk).getHandle(), flag, paramInt, version));
	}

	public static PacketWrapper PacketPlayOutMapChunkBulk(List<Chunk> chunks, int version) {
		return new PacketWrapper(new PacketPlayOutMapChunkBulk(convert(chunks), version));
	}

	private static List<net.minecraft.server.v1_7_R4.Chunk> convert(List<Chunk> chunks) {
		List<net.minecraft.server.v1_7_R4.Chunk> list = new ArrayList<net.minecraft.server.v1_7_R4.Chunk>();
		for (Chunk chunk : chunks) {
			list.add(((CraftChunk) chunk).getHandle());
		}
		return list;
	}

	public static PacketWrapper PacketPlayOutMultiBlockChange(Chunk chunk, int block_count, short[] data) {
		return new PacketWrapper(new PacketPlayOutMultiBlockChange(block_count, data, ((CraftChunk) chunk).getHandle()));
	}

	public static PacketWrapper PacketPlayOutNamedEntitySpawn(HumanEntity e) {
		return new PacketWrapper(new PacketPlayOutNamedEntitySpawn(((CraftHumanEntity) e).getHandle()));
	}

	public static PacketWrapper PacketPlayOutNamedEntitySpawn(Player randomOnlinePlayer, GameProfile profile, int x, int y, int z, byte yaw, byte pitch, int itemInHand) {
		EntityHuman entity = new EntityPlayer(((CraftPlayer) randomOnlinePlayer).getHandle().server, ((CraftPlayer) randomOnlinePlayer).getHandle().r(), profile, new PlayerInteractManager(((CraftPlayer) randomOnlinePlayer).getHandle().world));
		entity.setLocation(x, y, z, yaw, pitch);
		entity.setEquipment(0, new net.minecraft.server.v1_7_R4.ItemStack(Item.getById(itemInHand)));
		return new PacketWrapper(new PacketPlayOutNamedEntitySpawn(entity));
	}

	public static PacketWrapper PacketPlayOutNamedSoundEffect(String name, int locX, int locY, int locZ, float volume, float pitch) {
		return new PacketWrapper(new PacketPlayOutNamedSoundEffect(name, locX, locY, locZ, volume, pitch));
	}

	public static PacketWrapper PacketPlayOutOpenSignEditor(int locX, int locY, int locZ) {
		return new PacketWrapper(new PacketPlayOutOpenSignEditor(locX, locY, locZ));
	}

	public static PacketWrapper PacketPlayOutOpenWindow(int window_id, int inventory_type, String inventory_title, int slot_size, boolean provided_title) {
		return new PacketWrapper(new PacketPlayOutOpenWindow(window_id, inventory_type, inventory_title, slot_size, provided_title));
	}

	public static PacketWrapper PacketPlayOutOpenWindow(int window_id, int inventory_type, String inventory_title, int slot_size, boolean provided_title, int horse_entity_id) {
		return new PacketWrapper(new PacketPlayOutOpenWindow(window_id, inventory_type, inventory_title, slot_size, provided_title, horse_entity_id));
	}

	public static PacketWrapper PacketPlayOutPosition(int locX, int locY, int locZ, int yaw, int pitch, boolean isOnGround) {
		return new PacketWrapper(new PacketPlayOutPosition(locX, locY, locZ, yaw, pitch, isOnGround));
	}

	public static PacketWrapper PacketPlayOutRelEntityMove(int entity_id, byte DX, byte DY, byte DZ, boolean isOnGround) {
		return new PacketWrapper(new PacketPlayOutRelEntityMove(entity_id, DX, DY, DZ, isOnGround));
	}

	public static PacketWrapper PacketPlayOutRelEntityMoveLook(int entity_id, byte DX, byte DY, byte DZ, byte yaw, byte pitch, boolean isOnGround) {
		return new PacketWrapper(new PacketPlayOutRelEntityMoveLook(entity_id, DX, DY, DZ, yaw, pitch, isOnGround));
	}

	public static PacketWrapper PacketPlayOutRespawn(Environment dimension, Difficulty diff, GameMode gamemode) {
		return new PacketWrapper(new PacketPlayOutRespawn(WorldDataConverter.getEnvironment(dimension), WorldDataConverter.getDifficulty(diff), WorldDataConverter.getLevel(), WorldDataConverter.getGameMode(gamemode)));
	}

	public static PacketWrapper PacketPlayOutSetSlot(int window_id, short slot, ItemStack stack) {
		return new PacketWrapper(new PacketPlayOutSetSlot(window_id, slot, Reflection.getHandle((CraftItemStack) stack)));
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
		return new PacketWrapper(new PacketPlayOutSpawnPosition(xLoc, yLoc, zLoc));
	}

	public static PacketWrapper PacketPlayOutStatistics(Map<String, Integer> list) {
		return new PacketWrapper(new PacketPlayOutStatistic(list));
	}

	public static PacketWrapper PacketPlayOutTabComplete(String[] tabs) {
		return new PacketWrapper(new PacketPlayOutTabComplete(tabs));
	}

	public static PacketWrapper PacketPlayOutTransaction(int window_id, short action_id, boolean accepted) {
		return new PacketWrapper(new PacketPlayOutTransaction(window_id, action_id, accepted));
	}

	public static PacketWrapper PacketPlayOutUpdateHealth(float health_level, short food_level, float saturation) {
		return new PacketWrapper(new PacketPlayOutUpdateHealth(health_level, food_level, saturation));
	}

	public static PacketWrapper PacketPlayOutUpdateSign(int xLoc, int yLoc, int zLoc, String[] lines) {
		return new PacketWrapper(new PacketPlayOutUpdateSign(xLoc, yLoc, zLoc, lines));
	}

	public static PacketWrapper PacketPlayOutUpdateTime(long ticks_living, long day_time) {
		return new PacketWrapper(new PacketPlayOutUpdateTime(ticks_living, day_time, true));
	}

	public static PacketWrapper PacketPlayOutWindowItems(int window_id, List<ItemStack> stacks) {
		return new PacketWrapper(new PacketPlayOutWindowItems(window_id, convertItemStacks(stacks)));
	}

	private static List<net.minecraft.server.v1_7_R4.ItemStack> convertItemStacks(List<ItemStack> stacks) {
		List<net.minecraft.server.v1_7_R4.ItemStack> nms_stacks = new ArrayList<net.minecraft.server.v1_7_R4.ItemStack>();

		for (ItemStack stack : stacks) {
			try {
				nms_stacks.add(Reflection.getHandle((CraftItemStack) stack));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return nms_stacks;
	}

	public static PacketWrapper PacketPlayOutWorldParticles(Particle type, float x, float y, float z, float offSetX, float offSetY, float offSetZ, float particleData, int amount) {
		return new PacketWrapper(new PacketPlayOutWorldParticles(type.getName(), x, y, z, offSetX, offSetY, offSetZ, particleData, amount));
	}
}
