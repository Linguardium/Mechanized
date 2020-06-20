package net.snakefangox.mechanized.blocks.entity;

import alexiil.mc.lib.attributes.fluid.amount.FluidAmount;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.FurnaceScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.snakefangox.mechanized.MRegister;
import net.snakefangox.mechanized.gui.SteamBoilerContainer;
import net.snakefangox.mechanized.steam.Steam;

public class BasicBoilerEntity extends AbstractSteamBoilerEntity {

	public static final FluidAmount FLUID_PER_OP = FluidAmount.of(1, Steam.UNIT);
	
	public BasicBoilerEntity() {
		super(MRegister.BASIC_BOILER_ENTITY);
	}

	@Override
	protected FluidAmount fluidPerOp() {
		return FLUID_PER_OP;
	}

	@Override
	protected int steamPerOp() {
		return 1;
	}

	@Override
	protected void extractTick() {
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
