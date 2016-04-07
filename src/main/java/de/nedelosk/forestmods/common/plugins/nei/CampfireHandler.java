package de.nedelosk.forestmods.common.plugins.nei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.FurnaceRecipeHandler;
import codechicken.nei.recipe.TemplateRecipeHandler;
import de.nedelosk.forestmods.client.gui.GuiCampfire;
import de.nedelosk.forestmods.common.crafting.CampfireRecipeManager;
import de.nedelosk.forestmods.common.crafting.CampfireRecipeManager.CampfireRecipe;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class CampfireHandler extends FurnaceRecipeHandler {

	public CampfireHandler() {
		List<RecipeTransferRect> list = new ArrayList<>();
		list.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(85, 35, 22, 15), "ForestModsCampfire", new Object[0]));
		list.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(65, 35, 13, 13), "fuel", new Object[0]));
		RecipeTransferRectHandler.registerRectsToGuis(getRecipeTransferRectGuis(), list);
		transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(99, 23, 22, 15), "ForestModsCampfire", new Object[0]));
		transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(76, 23, 13, 13), "fuel", new Object[0]));
	}

	@Override
	public String getRecipeName() {
		return StatCollector.translateToLocal("nei.campfire");
	}

	@Override
	public String getGuiTexture() {
		return "forestmods:textures/gui/nei/campfire.png";
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass() {
		return GuiCampfire.class;
	}

	@Override
	public String getOverlayIdentifier() {
		return "ForestDayCampfire";
	}

	@Override
	public void loadTransferRects() {
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		if (result == null) {
			return;
		}
		List<CampfireRecipe> recipes = CampfireRecipeManager.getRecipes();
		for(CampfireRecipe recipe : recipes) {
			ItemStack output = recipe.getOutput();
			if (result.getItem() == output.getItem() && result.getItemDamage() == output.getItemDamage()) {
				CampfireCachedRecipe res = new CampfireCachedRecipe(recipe.getInput(), recipe.getInput2(), output);
				arecipes.add(res);
			}
		}
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if (outputId.equals("ForestModsCampfire") && getClass() == CampfireHandler.class) {
			List<CampfireRecipe> recipes = CampfireRecipeManager.getRecipes();
			for(CampfireRecipe recipe : recipes) {
				ItemStack output = recipe.getOutput();
				CampfireCachedRecipe res = new CampfireCachedRecipe(recipe.getInput(), recipe.getInput2(), output);
				arecipes.add(res);
			}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		List<CampfireRecipe> recipes = CampfireRecipeManager.getRecipes();
		for(CampfireRecipe recipe : recipes) {
			ItemStack output = recipe.getOutput();
			CampfireCachedRecipe res = new CampfireCachedRecipe(recipe.getInput(), recipe.getInput2(), output);
			if (res.contains(res.input, ingredient)) {
				res.setIngredientPermutation(res.input, ingredient);
				arecipes.add(res);
			}
		}
	}

	@Override
	public void drawBackground(int recipeIndex) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GuiDraw.changeTexture(getGuiTexture());
		GuiDraw.drawTexturedModalRect(0, 0, 0, 0, 166, 65);
	}

	private static ArrayList<PositionedStack> fuels;

	public class CampfireCachedRecipe extends TemplateRecipeHandler.CachedRecipe {

		private ArrayList<PositionedStack> input;
		private PositionedStack output;

		@Override
		public List<PositionedStack> getIngredients() {
			return getCycledIngredients(cycleticks / 20, input);
		}

		@Override
		public PositionedStack getOtherStack() {
			if (fuels == null) {
				fuels = cloneList(afuels);
			}
			return fuels.get((cycleticks / 48) % afuels.size());
		}

		public ArrayList<PositionedStack> cloneList(List<FuelPair> list) {
			ArrayList<PositionedStack> clone = new ArrayList(list.size());
			for(FuelPair item : list) {
				clone.add(new PositionedStack(item.stack.items, 75, 40));
			}
			return clone;
		}

		@Override
		public PositionedStack getResult() {
			return output;
		}

		public ArrayList<PositionedStack> getInput() {
			return input;
		}

		public CampfireCachedRecipe(ItemStack input, ItemStack input1, ItemStack output) {
			this.input = new ArrayList<PositionedStack>();
			if (input != null) {
				this.input.add(new PositionedStack(input, 22, 22));
			}
			if (input1 != null) {
				this.input.add(new PositionedStack(input1, 40, 22));
			}
			if (output != null) {
				this.output = new PositionedStack(output, 131, 22);
			}
		}
	}
}
