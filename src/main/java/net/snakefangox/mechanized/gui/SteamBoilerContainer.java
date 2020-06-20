package net.snakefangox.mechanized.gui;

import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import io.github.cottonmc.cotton.gui.widget.WBar;
import io.github.cottonmc.cotton.gui.widget.WBar.Direction;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.text.Text;

import static net.snakefangox.mechanized.MRegister.STEAM_BOILER_SCREEN_HANDLER;

public class SteamBoilerContainer extends SyncedGuiDescription {

	public SteamBoilerContainer(int syncID, PlayerInventory playerInventory, ScreenHandlerContext context) {
		super(STEAM_BOILER_SCREEN_HANDLER, syncID, playerInventory, getBlockInventory(context),
				getBlockPropertyDelegate(context));
		WGridPanel root = new WGridPanel();
		setRootPanel(root);

		WItemSlot input = WItemSlot.of(blockInventory, 0);
		root.add(input, 4, 2);

		WBar fire = new WBar(Components.FURNACE_FLAME_BG, Components.FURNACE_FLAME, 0, 1, Direction.UP);
		root.add(fire, 4, 1);
		WBar water = new WBar(Components.TANK_BG, Components.WATER_TANK, 2, 3, Direction.UP);
		root.add(water, 0, 0, 1, 3);
		WBar steam = new WBar(Components.TANK_BG, Components.STEAM_TANK, 4, 5, Direction.UP);
		root.add(steam, 8, 0, 1, 3);

		root.add(createPlayerInventoryPanel(), 0, 4);

		root.validate(this);
	}

	@Override
	public void close(PlayerEntity player) {
		super.close(player);
	}
	
	public static class SteamBoilerScreen extends CottonInventoryScreen<SteamBoilerContainer> {
		public SteamBoilerScreen(SteamBoilerContainer container, PlayerInventory playerInventory, Text text) {
			super(container,playerInventory.player);
		}
	}
}
