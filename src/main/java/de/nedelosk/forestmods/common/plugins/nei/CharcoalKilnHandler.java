package de.nedelosk.forestmods.common.plugins.nei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.lwjgl.opengl.GL11;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import de.nedelosk.forestmods.api.ForestModsApi;
import de.nedelosk.forestmods.common.utils.CharcoalKilnUtil;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class CharcoalKilnHandler extends TemplateRecipeHandler {

	@Override
	public String getRecipeName() {
		return StatCollector.translateToLocal("nei.kiln.charcoal.drops");
	}

	@Override
	public String getGuiTexture() {
		return "forestmods:textures/gui/nei/charcoal_kiln.png";
	}

	@Override
	public String getOverlayIdentifier() {
		return "ForestDayKilnCharcoal";
	}

	@Override
	public void loadTransferRects() {
		transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(69, 23, 22, 15), "ForestModsKilnCharcoal", new Object[0]));
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		if (result == null) {
			return;
		}
		for(Entry<ItemStack, List<ItemStack>> entry : ForestModsApi.getCharcoalKilnDrops().entrySet()) {
			List<ItemStack> drops = entry.getValue();
			drops.add(new ItemStack(Items.coal, 8, 1));
			for(ItemStack drop : drops) {
				if (NEIServerUtils.areStacksSameTypeCrafting(drop, result)) {
					KilnCharcoalCachedRecipe res = new KilnCharcoalCachedRecipe(drops, entry.getKey());
					arecipes.add(res);
					break;
				}
			}
		}
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if (outputId.equals("ForestModsKilnCharcoal") && getClass() == CharcoalKilnHandler.class) {
			for(Entry<ItemStack, List<ItemStack>> entry : ForestModsApi.getCharcoalKilnDrops().entrySet()) {
				List<ItemStack> drops = entry.getValue();
				drops.add(new ItemStack(Items.coal, 8, 1));
				KilnCharcoalCachedRecipe res = new KilnCharcoalCachedRecipe(drops, entry.getKey());
				arecipes.add(res);
			}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		boolean foundType = false;
		if (!CharcoalKilnUtil.isWood(ingredient)) {
			return;
		}
		List<ItemStack> drops = ForestModsApi.getCharcoalDrops(ingredient);
		drops.add(new ItemStack(Items.coal, 8, 1));
		KilnCharcoalCachedRecipe res = new KilnCharcoalCachedRecipe(drops, ingredient);
		if (res.contains(res.input, ingredient)) {
			res.setIngredientPermutation(res.input, ingredient);
			arecipes.add(res);
			foundType = true;
		}
	}

	@Override
	public void drawBackground(int recipeIndex) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GuiDraw.changeTexture(getGuiTexture());
		GuiDraw.drawTexturedModalRect(0, 0, 0, 0, 166, 65);
	}

	public class KilnCharcoalCachedRecipe extends TemplateRecipeHandler.CachedRecipe {

		private ArrayList<PositionedStack> input;
		private PositionedStack output;
		private ArrayList<PositionedStack> outputs;

		@Override
		public List<PositionedStack> getIngredients() {
			return getCycledIngredients(cycleticks / 20, input);
		}

		@Override
		public PositionedStack getResult() {
			return output;
		}

		public ArrayList<PositionedStack> getInput() {
			return input;
		}

		@Override
		public List<PositionedStack> getOtherStacks() {
			return getCycledIngredients(cycleticks / 20, outputs);
		}

		public KilnCharcoalCachedRecipe(List<ItemStack> outputs, ItemStack wood) {
			this.input = new ArrayList<PositionedStack>();
			this.outputs = new ArrayList<PositionedStack>();
			if (wood != null) {
				this.input.add(new PositionedStack(wood, 22, 23));
			}
			if (outputs != null) {
				this.output = new PositionedStack(outputs.get(0), 113, 14);
				if (outputs.size() > 1) {
					for(int i = 1; i < 4; i++) {
						int x = 0;
						int y = 0;
						switch (i) {
							case 1:
								x = 131;
								y = 14;
								break;
							case 2:
								x = 113;
								y = 32;
								break;
							case 3:
								x = 131;
								y = 32;
								break;
						}
						if (outputs.size() > i && outputs.get(i) != null) {
							this.outputs.add(new PositionedStack(outputs.get(i), x, y));
						}
					}
				}
			}
		}
	}
}
