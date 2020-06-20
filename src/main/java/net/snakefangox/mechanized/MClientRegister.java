package net.snakefangox.mechanized;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.impl.screenhandler.ExtendedScreenHandlerType;
import net.minecraft.client.render.entity.FallingBlockEntityRenderer;
import net.minecraft.screen.ScreenHandlerContext;
import net.snakefangox.mechanized.blocks.AlloyFurnace;
import net.snakefangox.mechanized.blocks.entity.FanEntityRenderer;
import net.snakefangox.mechanized.blocks.entity.SteamChargerEntityRenderer;
import net.snakefangox.mechanized.gui.*;
import net.snakefangox.mechanized.gui.AlloyFurnaceContainer.AlloyFurnaceScreen;
import net.snakefangox.mechanized.gui.PlacerContainer.PlacerScreen;
import net.snakefangox.mechanized.gui.SteamGaugeContainer.SteamGaugeScreen;
import net.snakefangox.mechanized.gui.PressureValveContainer.PressureValveScreen;
import net.snakefangox.mechanized.gui.SteamBoilerContainer.SteamBoilerScreen;
import net.snakefangox.mechanized.gui.UpgradeTableContainer.UpgradeTableScreen;
import net.snakefangox.mechanized.networking.ToClientHandlers;

import static net.snakefangox.mechanized.MRegister.*;

@Environment(EnvType.CLIENT)
public class MClientRegister {

	public static void registerClient() {

		ToClientHandlers.initPacketHandlers();

		BlockEntityRendererRegistry.INSTANCE.register(MRegister.FAN_ENTITY, FanEntityRenderer::new);
		BlockEntityRendererRegistry.INSTANCE.register(MRegister.STEAM_CHARGER_ENTITY, SteamChargerEntityRenderer::new);
		ScreenRegistry.register(ALLOY_FURNACE_SCREEN_HANDLER, AlloyFurnaceScreen::new);

		ScreenRegistry.register(STEAM_BOILER_SCREEN_HANDLER, SteamBoilerScreen::new);
		ScreenRegistry.register(PRESSURE_VALVE_SCREEN_HANDLER,PressureValveScreen::new);
		ScreenRegistry.register(STEAM_GAUGE_SCREEN_HANDLER, SteamGaugeScreen::new);
		ScreenRegistry.register(UPGRADE_TABLE_SCREEN_HANDLER, UpgradeTableScreen::new);
		ScreenRegistry.register(PLACER_CONTAINER_SCREEN_HANDLER, PlacerScreen::new);
		
		EntityRendererRegistry.INSTANCE.register(MRegister.FLYING_BLOCK, (entityRenderDispatcher, context) -> new FallingBlockEntityRenderer(entityRenderDispatcher));
	}

}
