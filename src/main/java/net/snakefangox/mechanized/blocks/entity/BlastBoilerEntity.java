package net.snakefangox.mechanized.blocks.entity;

import alexiil.mc.lib.attributes.fluid.amount.FluidAmount;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.snakefangox.mechanized.MRegister;
import net.snakefangox.mechanized.gui.SteamBoilerContainer;
import net.snakefangox.mechanized.steam.Steam;

public class BlastBoilerEntity extends AbstractSteamBoilerEntity {

	public static final int STEAM_PER_OP = 4;
	public static final FluidAmount FLUID_PER_OP = FluidAmount.of(STEAM_PER_OP * 2, Steam.UNIT);
	public static final int FUEL_PER_OP = (STEAM_PER_OP * 2) - 1;
	
	public BlastBoilerEntity() {
		super(MRegister.BLAST_BOILER_ENTITY);
	}

	@Override
	protected FluidAmount fluidPerOp() {
		return FLUID_PER_OP;
	}

	@Override
	protected int steamPerOp() {
		return STEAM_PER_OP;
	}

	@Override
	protected void extractTick() {
		fuel = Math.max(0, fuel - FUEL_PER_OP);
	}

	@Override
	@Environment(EnvType.CLIENT)
	public Text getDisplayName() {
		return world.getBlockState(pos).getBlock().getName();
	}

	@Override
	public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
		return new SteamBoilerContainer(syncId, inv, ScreenHandlerContext.create(world,pos));
	}
	@Override
	public void writeScreenOpeningData(ServerPlayerEntity serverPlayerEntity, PacketByteBuf packetByteBuf) {
		packetByteBuf.writeBlockPos(pos);
	}
}
