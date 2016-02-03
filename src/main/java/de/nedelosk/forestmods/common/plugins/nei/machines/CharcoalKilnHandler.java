package de.nedelosk.forestmods.common.plugins.nei.machines;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.opengl.GL11;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import de.nedelosk.forestmods.api.crafting.ForestDayCrafting;
import de.nedelosk.forestmods.api.crafting.WoodType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class CharcoalKilnHandler extends TemplateRecipeHandler {

	@Override
	public String getRecipeName() {
		return StatCollector.translateToLocal("forestday.nei.kiln.charcoal");
	}

	@Override
	public String getGuiTexture() {
		return "forestday:textures/gui/nei/charcoal_kiln.png";
	}

	@Override
	public String getOverlayIdentifier() {
		return "ForestDayKilnCharcoal";
	}

	@Override
	public void loadTransferRects() {
		transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(69, 23, 22, 15), "ForestDayKilnCharcoal", new Object[0]));
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		if (result == null) {
			return;
		}
		Iterator<WoodType> types = ForestDayCrafting.woodManager.getWoodTypes().values().iterator();
		while (types.hasNext()) {
			WoodType type = types.next();
			for ( ItemStack charcoal : type.getCharcoalDropps() ) {
				if (result.getItem() == charcoal.getItem() && result.getItemDamage() == charcoal.getItemDamage()) {
					KilnCharcoalCachedRecipe res = new KilnCharcoalCachedRecipe(type.getCharcoalDropps(), type.getWood());
					arecipes.add(res);
				}
			}
		}
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if (outputId.equals("ForestDayKilnCharcoal") && getClass() == CharcoalKilnHandler.class) {
			Iterator<WoodType> types = ForestDayCrafting.woodManager.getWoodTypes().values().iterator();
			while (types.hasNext()) {
				WoodType type = types.next();
				KilnCharcoalCachedRecipe res = new KilnCharcoalCachedRecipe(type.getCharcoalDropps(), type.getWood());
				arecipes.add(res);
			}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		boolean foundType = false;
		Iterator<WoodType> types = ForestDayCrafting.woodManager.getWoodTypes().values().iterator();
		while (types.hasNext()) {
			WoodType type = types.next();
			KilnCharcoalCachedRecipe res = new KilnCharcoalCachedRecipe(type.getCharcoalDropps(), type.getWood());
			if (res.contains(res.input, ingredient)) {
				res.setIngredientPermutation(res.input, ingredient);
				arecipes.add(res);
				foundType = true;
			}
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
					for ( int i = 1; i < 4; i++ ) {
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
