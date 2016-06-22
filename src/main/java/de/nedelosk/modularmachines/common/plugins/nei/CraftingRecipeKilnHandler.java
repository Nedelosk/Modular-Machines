package de.nedelosk.modularmachines.common.plugins.nei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import de.nedelosk.modularmachines.common.core.BlockManager;
import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CraftingRecipeKilnHandler extends TemplateRecipeHandler {

	public class CachedKilnRecipe extends CachedRecipe {

		public ArrayList<PositionedStack> ingredients;
		public PositionedStack result;

		public CachedKilnRecipe(Object[] items, ItemStack out) {
			result = new PositionedStack(out, 119, 24);
			ingredients = new ArrayList<PositionedStack>();
			setIngredients(items);
		}

		public void setIngredients(Object[] items) {
			for(int x = 0; x < 3; x++) {
				for(int y = 0; y < 3; y++) {
					if (items[y * 3 + x] == null) {
						continue;
					}
					PositionedStack stack = new PositionedStack(items[y * 3 + x], 25 + x * 18, 6 + y * 18, false);
					stack.setMaxSize(1);
					ingredients.add(stack);
				}
			}
		}

		@Override
		public List<PositionedStack> getIngredients() {
			return getCycledIngredients(cycleticks / 20, ingredients);
		}

		@Override
		public PositionedStack getResult() {
			return result;
		}

		public void computeVisuals() {
			for(PositionedStack p : ingredients) {
				p.generatePermutations();
			}
		}
	}

	@Override
	public void loadTransferRects() {
		transferRects.add(new RecipeTransferRect(new Rectangle(84, 23, 24, 18), "crafting"));
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass() {
		return GuiCrafting.class;
	}

	@Override
	public String getRecipeName() {
		return NEIClientUtils.translate("kiln.charcoal");
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if (outputId.equals("crafting") && getClass() == CraftingRecipeKilnHandler.class) {
			for(ItemStack stack : CharcoalKilnHelper.getWoods()) {
				CachedKilnRecipe recipe = new CachedKilnRecipe(
						new Object[] { stack, stack, stack, stack, new ItemStack(BlockManager.blockGravel), stack, stack, stack, stack },
						CharcoalKilnHelper.createKiln(stack));
				recipe.computeVisuals();
				arecipes.add(recipe);
			}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		Block block = Block.getBlockFromItem(result.getItem());
		if (block != null && block instanceof BlockCharcoalKiln) {
			ItemStack stack = CharcoalKilnHelper.getFromKiln(result);
			CachedKilnRecipe recipe = new CachedKilnRecipe(
					new Object[] { stack, stack, stack, stack, new ItemStack(BlockManager.blockGravel), stack, stack, stack, stack }, result);
			recipe.computeVisuals();
			arecipes.add(recipe);
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack stack) {
		if (!CharcoalKilnHelper.isWood(stack)) {
			if (stack.getItem() != null && stack.getItem() == Item.getItemFromBlock(BlockManager.blockGravel) && stack.getItemDamage() == 0) {
				for(ItemStack woodStack : CharcoalKilnHelper.getWoods()) {
					CachedKilnRecipe recipe = new CachedKilnRecipe(
							new Object[] { woodStack, woodStack, woodStack, woodStack, stack, woodStack, woodStack, woodStack, woodStack },
							CharcoalKilnHelper.createKiln(woodStack));
					recipe.computeVisuals();
					arecipes.add(recipe);
				}
			}
			return;
		}
		CachedKilnRecipe recipe = new CachedKilnRecipe(
				new Object[] { stack, stack, stack, stack, new ItemStack(BlockManager.blockGravel), stack, stack, stack, stack },
				CharcoalKilnHelper.createKiln(stack));
		recipe.computeVisuals();
		arecipes.add(recipe);
	}

	@Override
	public String getGuiTexture() {
		return "textures/gui/container/crafting_table.png";
	}

	@Override
	public String getOverlayIdentifier() {
		return "crafting";
	}

	@Override
	public boolean hasOverlay(GuiContainer gui, Container container, int recipe) {
		return super.hasOverlay(gui, container, recipe);
	}

	@Override
	public IRecipeOverlayRenderer getOverlayRenderer(GuiContainer gui, int recipe) {
		IRecipeOverlayRenderer renderer = super.getOverlayRenderer(gui, recipe);
		if (renderer != null) {
			return renderer;
		}
		return null;
	}

	@Override
	public IOverlayHandler getOverlayHandler(GuiContainer gui, int recipe) {
		IOverlayHandler handler = super.getOverlayHandler(gui, recipe);
		if (handler != null) {
			return handler;
		}
		return null;
	}
}
