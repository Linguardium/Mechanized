package net.snakefangox.mechanized.gui;

import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.text.Text;

import static net.snakefangox.mechanized.MRegister.PLACER_CONTAINER_SCREEN_HANDLER;

public class PlacerContainer extends SyncedGuiDescription {

	public PlacerContainer(int syncID, PlayerInventory playerInventory, ScreenHandlerContext context) {
		super(PLACER_CONTAINER_SCREEN_HANDLER, syncID, playerInventory, getBlockInventory(context),
				getBlockPropertyDelegate(context));
		WGridPanel root = new WGridPanel();
		setRootPanel(root);

		WItemSlot slot = WItemSlot.outputOf(blockInventory, 0);
		root.add(slot, 4, 1);
		
		root.add(createPlayerInventoryPanel(), 0, 4);

		root.validate(this);
	}

	public static class PlacerScreen extends CottonInventoryScreen<PlacerContainer> {
		public PlacerScreen(PlacerContainer container, PlayerInventory playerInventory, Text text) {
			super(container,playerInventory.player, text);
		}
	}
}
