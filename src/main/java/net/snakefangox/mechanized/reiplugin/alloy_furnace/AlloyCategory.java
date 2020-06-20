package net.snakefangox.mechanized.reiplugin.alloy_furnace;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.EntryStack;
import me.shedaniel.rei.api.RecipeCategory;
import me.shedaniel.rei.api.RecipeDisplay;
import me.shedaniel.rei.api.widgets.Widgets;
import me.shedaniel.rei.gui.widget.EntryWidget;
import me.shedaniel.rei.gui.widget.RecipeArrowWidget;
import me.shedaniel.rei.gui.widget.RecipeBaseWidget;
import me.shedaniel.rei.gui.widget.Widget;
import me.shedaniel.rei.plugin.DefaultPlugin;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.snakefangox.mechanized.MRegister;
import net.snakefangox.mechanized.Mechanized;
import net.snakefangox.mechanized.blocks.entity.AlloyFurnaceEntity;
import net.snakefangox.mechanized.recipes.AlloyRecipe;
import net.snakefangox.mechanized.reiplugin.alloy_furnace.AlloyCategory.AlloyRecipeDisplay;

@Environment(EnvType.CLIENT)
public class AlloyCategory implements RecipeCategory<AlloyRecipeDisplay> {

	public static final Identifier ALLOY_CAT = new Identifier(Mechanized.MODID, "plugins/alloy_furnace");

	@Override
	public Identifier getIdentifier() {
		return ALLOY_CAT;
	}

	@Override
	public String getCategoryName() {
		return I18n.translate(Mechanized.MODID + ".reiplugin.alloy_furnace");
	}


	@Override
	public EntryStack getLogo() {
		return EntryStack.create(MRegister.ALLOY_FURNACE);
	}

	@Override
	public List<Widget> setupDisplay(AlloyRecipeDisplay recipeDisplay, Rectangle bounds) {
		Point startPoint = new Point(bounds.getCenterX() - 41, bounds.getCenterY() - 17);
		List<Widget> widgets = new LinkedList<>(Collections.singletonList(Widgets.createRecipeBase(bounds)));
		
		widgets.add(Widgets.createArrow(new Point(startPoint.x + 24, startPoint.y + 8))
				.animationDurationTicks(AlloyFurnaceEntity.SMELT_TIME * 50));
		widgets.add(Widgets.createSlot(new Point(startPoint.x - 16, startPoint.y + 1))
				.entries(recipeDisplay.getInputEntries().get(0)).markInput());
		widgets.add(Widgets.createSlot(new Point(startPoint.x + 2, startPoint.y + 1))
				.entries(recipeDisplay.getInputEntries().get(1)).markInput());
		widgets.add(Widgets.createSlot(new Point(startPoint.x + 55, startPoint.y + 9)).entries(recipeDisplay.getOutputEntries())
				.markOutput());
		return widgets;
	}

	public static class AlloyRecipeDisplay implements RecipeDisplay {

		final List<List<EntryStack>> input;
		final List<EntryStack> output;

		public AlloyRecipeDisplay(AlloyRecipe recipeIn) {
			input = new ArrayList<List<EntryStack>>();

			List<EntryStack> entries1 = new ArrayList<>();
			for (ItemStack stack : recipeIn.input1.getMatchingStacksClient()) {
				EntryStack estack = EntryStack.create(stack);
				estack.setAmount(recipeIn.amount1);
				entries1.add(estack);
			}
			input.add(entries1);

			List<EntryStack> entries2 = new ArrayList<>();
			for (ItemStack stack : recipeIn.input2.getMatchingStacksClient()) {
				EntryStack estack = EntryStack.create(stack);
				estack.setAmount(recipeIn.amount2);
				entries2.add(estack);
			}
			input.add(entries2);

			output = Collections.singletonList(EntryStack.create(recipeIn.getOutput()));
		}

		@Override
		public List<List<EntryStack>> getInputEntries() {
			return input;
		}

		@Override
		public List<EntryStack> getOutputEntries() {
			return output;
		}

		@Override
		public Identifier getRecipeCategory() {
			return ALLOY_CAT;
		}

	}
}
