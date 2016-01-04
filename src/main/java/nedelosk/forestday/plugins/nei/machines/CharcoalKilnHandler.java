package nedelosk.forestday.plugins.nei.machines;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.OreDictionary;

import org.lwjgl.opengl.GL11;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import nedelosk.forestday.common.types.WoodType;
import nedelosk.forestday.common.types.WoodTypeManager;

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
		transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(69, 23, 22, 15),
				"ForestDayKilnCharcoal", new Object[0]));
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		if (result == null) {
			return;
		}

		Iterator<WoodType> types = WoodTypeManager.woodTypes.values().iterator();
		while (types.hasNext()) {
			WoodType type = types.next();
			for (ItemStack charcoal : type.charcoalDropps) {
				if (result.getItem() == charcoal.getItem() && result.getItemDamage() == charcoal.getItemDamage()) {
					KilnCharcoalCachedRecipe res = new KilnCharcoalCachedRecipe(type.charcoalDropps, type.wood);
					arecipes.add(res);
				}
			}
		}
		if (result.getItem() == Items.coal && result.getItemDamage() == 1) {
			Iterator<Item> items = Item.itemRegistry.iterator();
			List<ItemStack> stacks = new ArrayList<>();
			while (items.hasNext()) {
				Item item = items.next();
				item.getSubItems(item, null, stacks);
			}
			for (ItemStack stack : stacks) {
				for (int i : OreDictionary.getOreIDs(stack)) {
					if (OreDictionary.getOreName(i).contains("log")) {
						KilnCharcoalCachedRecipe res = new KilnCharcoalCachedRecipe(
								Arrays.asList(new ItemStack[] { new ItemStack(Items.coal, 1, 1) }), stack);
						arecipes.add(res);
					}
				}
			}
		}
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if (outputId.equals("ForestDayKilnCharcoal") && getClass() == CharcoalKilnHandler.class) {
			Iterator<WoodType> types = WoodTypeManager.woodTypes.values().iterator();
			while (types.hasNext()) {
				WoodType type = types.next();
				ItemStack wood = type.wood;
				KilnCharcoalCachedRecipe res = new KilnCharcoalCachedRecipe(type.charcoalDropps, wood);
				arecipes.add(res);
			}
			Iterator<Item> items = Item.itemRegistry.iterator();
			List<ItemStack> stacks = new ArrayList<>();
			while (items.hasNext()) {
				Item item = items.next();
				item.getSubItems(item, null, stacks);
			}
			for (ItemStack stack : stacks) {
				for (int i : OreDictionary.getOreIDs(stack)) {
					if (OreDictionary.getOreName(i).contains("log")) {
						KilnCharcoalCachedRecipe res = new KilnCharcoalCachedRecipe(
								Arrays.asList(new ItemStack[] { new ItemStack(Items.coal, 1, 1) }), stack);
						arecipes.add(res);
					}
				}
			}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		boolean foundType = false;
		Iterator<WoodType> types = WoodTypeManager.woodTypes.values().iterator();
		while (types.hasNext()) {
			WoodType type = types.next();
			ItemStack wood = type.wood;
			KilnCharcoalCachedRecipe res = new KilnCharcoalCachedRecipe(type.charcoalDropps, wood);
			if (res.contains(res.input, ingredient)) {
				res.setIngredientPermutation(res.input, ingredient);
				arecipes.add(res);
				foundType = true;
			}
		}
		if (!foundType) {
			for (int i : OreDictionary.getOreIDs(ingredient)) {
				if (OreDictionary.getOreName(i).contains("log")) {
					KilnCharcoalCachedRecipe res = new KilnCharcoalCachedRecipe(
							Arrays.asList(new ItemStack[] { new ItemStack(Items.coal, 1, 1) }), ingredient);
					arecipes.add(res);
				}
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
					for (int i = 1; i < 4; i++) {
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
