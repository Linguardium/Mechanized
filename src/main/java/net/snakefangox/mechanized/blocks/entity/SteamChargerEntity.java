package net.snakefangox.mechanized.blocks.entity;

import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.Tickable;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Direction;
import net.snakefangox.mechanized.MRegister;
import net.snakefangox.mechanized.parts.StandardInventory;
import net.snakefangox.mechanized.steam.Steam;
import net.snakefangox.mechanized.steam.SteamItem;
import net.snakefangox.mechanized.steam.SteamUtil;
import net.snakefangox.mechanized.tools.InventoryTools;

public class SteamChargerEntity extends BlockEntity implements Steam, Tickable, StandardInventory, BlockEntityClientSerializable {

	DefaultedList<ItemStack> inv = DefaultedList.ofSize(1, ItemStack.EMPTY);
	
	public SteamChargerEntity() {
		super(MRegister.STEAM_CHARGER_ENTITY);
	}

	@Override
	public void tick() {
		if (world.isClient)
			return;
		if (world.getTime() % 5 == 0) {
			SteamUtil.directionalEqualizeSteam(world, this, pos, null, getCachedState().get(HorizontalFacingBlock.FACING));
		}
		if(world.getTime() % 80 == 0) {
			sync();
		}
	}

	@Override
	public int getSteamAmount(Direction dir) {
		if(inv.get(0).getItem() instanceof SteamItem) {
			return ((SteamItem)inv.get(0).getItem()).getSteamAmount(inv.get(0));
		}
		return 0;
	}

	@Override
	public int getMaxSteamAmount(Direction dir) {
		if(inv.get(0).getItem() instanceof SteamItem) {
			return ((SteamItem)inv.get(0).getItem()).getMaxSteamAmount(inv.get(0));
		}
		return 0;
	}

	@Override
	public void setSteamAmount(Direction dir, int amount) {
		if(inv.get(0).getItem() instanceof SteamItem) {
			((SteamItem)inv.get(0).getItem()).setSteamAmount(inv.get(0), amount);
		}
	}
	
	@Override
	public boolean canPipeConnect(Direction dir) {
		return getCachedState().get(HorizontalFacingBlock.FACING) == dir;
	}

	@Override
	public int getMaxCountPerStack() {
		return 1;
	}
	
	@Override
	public void fromTag(BlockState state, CompoundTag tag) {
		super.fromTag(state, tag);
		Inventories.fromTag(tag, inv);
	}

	@Override
	public CompoundTag toTag(CompoundTag tag) {
		Inventories.toTag(tag, inv);
		return super.toTag(tag);
	}
	
	@Override
	public BlockEntityUpdateS2CPacket toUpdatePacket() {
		return super.toUpdatePacket();
	}

	@Override
	public DefaultedList<ItemStack> getItems() {
		return inv;
	}
	
	@Override
	public void fromClientTag(CompoundTag tag) {
		Inventories.fromTag(tag, inv);
	}

	@Override
	public CompoundTag toClientTag(CompoundTag tag) {
		InventoryTools.toTagIncEmpty(tag, inv, true);
		return tag;
	}
}
