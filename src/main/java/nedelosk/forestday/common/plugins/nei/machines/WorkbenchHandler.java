package nedelosk.forestday.common.plugins.nei.machines;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import nedelosk.forestday.common.crafting.WorkbenchRecipe;
import nedelosk.forestday.common.crafting.WorkbenchRecipeManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.OreDictionary;

import org.lwjgl.opengl.GL11;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class WorkbenchHandler extends TemplateRecipeHandler {

	@Override
	public String getRecipeName() {
		return StatCollector.translateToLocal("forestday.nei.workbench");
	}

	@Override
	public String getGuiTexture() {
		return "forestday:textures/gui/nei/workbench.png";
	}

	@Override
	public String getOverlayIdentifier() {
		return "ForestDayWorkbench";
	}

	@Override
	public void loadTransferRects() {
		transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(44, 37, 76, 17),
				"ForestDayWorkbench", new Object[0]));
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		if (result == null) {
			return;
		}

		List<WorkbenchRecipe> recipes = WorkbenchRecipeManager.getRecipes();
		for (WorkbenchRecipe recipe : recipes) {
			if (recipe.getOutput().getItem() == result.getItem()
					&& recipe.getOutput().getItemDamage() == result.getItemDamage()) {
				List<ItemStack> input;
				List<ItemStack> inputTool;
				List<ItemStack> inputPattern = null;
				boolean notPattern = false;

				if (recipe.getInput() != null) {
					input = new ArrayList<ItemStack>();
					input.add(recipe.getInput());
				} else {
					input = OreDictionary.getOres(recipe.getsInput().getOreDict());
					for (ItemStack stack : input)
						stack.stackSize = recipe.getsInput().stackSize;
				}

				if (recipe.getInputPattern() != null) {
					inputPattern = new ArrayList<ItemStack>();
					inputPattern.add(recipe.getInputPattern());
				} else if (recipe.getsInputPattern() != null) {
					inputPattern = OreDictionary.getOres(recipe.getsInputPattern().getOreDict());
				}

				if (recipe.getInputPattern() == null && recipe.getsInputPattern() == null) {
					notPattern = true;
				}

				if (recipe.getInputTool() != null) {
					inputTool = new ArrayList<ItemStack>();
					inputTool.add(recipe.getInputTool());
				} else {
					inputTool = OreDictionary.getOres(recipe.getsInputTool().getOreDict());
				}
				if (inputTool != null && input != null && inputPattern != null) {
					WorkbenchCachedRecipe res = new WorkbenchCachedRecipe(input, inputTool, inputPattern,
							recipe.getOutput());

					arecipes.add(res);
				} else if (inputTool != null && input != null && notPattern) {
					WorkbenchCachedRecipe res = new WorkbenchCachedRecipe(input, inputTool, recipe.getOutput());
					arecipes.add(res);
				}
			}
		}

	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if (outputId.equals("ForestDayWorkbench") && getClass() == WorkbenchHandler.class) {
			List<ItemStack> input;
			List<ItemStack> inputTool;
			List<ItemStack> inputPattern = null;
			boolean notPattern = false;

			List<WorkbenchRecipe> recipes = WorkbenchRecipeManager.getRecipes();
			for (WorkbenchRecipe recipe : recipes) {
				if (recipe.getInput() != null) {
					input = new ArrayList<ItemStack>();
					input.add(recipe.getInput());
				} else {
					input = OreDictionary.getOres(recipe.getsInput().getOreDict());
					for (ItemStack stack : input)
						stack.stackSize = recipe.getsInput().stackSize;
				}

				if (recipe.getInputPattern() != null) {
					inputPattern = new ArrayList<ItemStack>();
					inputPattern.add(recipe.getInputPattern());
				} else if (recipe.getsInputPattern() != null) {
					inputPattern = OreDictionary.getOres(recipe.getsInputPattern().getOreDict());
				}

				if (recipe.getInputPattern() == null && recipe.getsInputPattern() == null) {
					notPattern = true;
				}

				if (recipe.getInputTool() != null) {
					inputTool = new ArrayList<ItemStack>();
					inputTool.add(recipe.getInputTool());
				} else {
					inputTool = OreDictionary.getOres(recipe.getsInputTool().getOreDict());
				}
				if (inputTool != null && input != null && inputPattern != null) {
					WorkbenchCachedRecipe res = new WorkbenchCachedRecipe(input, inputTool, inputPattern,
							recipe.getOutput());
					arecipes.add(res);
				} else if (inputTool != null && input != null && notPattern) {
					WorkbenchCachedRecipe res = new WorkbenchCachedRecipe(input, inputTool, recipe.getOutput());
					arecipes.add(res);
				}
			}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		List<ItemStack> input;
		List<ItemStack> inputTool;
		List<ItemStack> inputPattern = null;
		boolean notPattern = false;

		List<WorkbenchRecipe> recipes = WorkbenchRecipeManager.getRecipes();
		for (WorkbenchRecipe recipe : recipes) {
			if (recipe.getInput() != null) {
				input = new ArrayList<ItemStack>();
				input.add(recipe.getInput());
			} else {
				input = OreDictionary.getOres(recipe.getsInput().getOreDict());
				for (ItemStack stack : input)
					stack.stackSize = recipe.getsInput().stackSize;
			}

			if (recipe.getInputPattern() != null) {
				inputPattern = new ArrayList<ItemStack>();
				inputPattern.add(recipe.getInputPattern());
			} else if (recipe.getsInputPattern() != null) {
				inputPattern = OreDictionary.getOres(recipe.getsInputPattern().getOreDict());
			}

			if (recipe.getInputPattern() == null && recipe.getsInputPattern() == null) {
				notPattern = true;
			}

			if (recipe.getInputTool() != null) {
				inputTool = new ArrayList<ItemStack>();
				inputTool.add(recipe.getInputTool());
			} else {
				inputTool = OreDictionary.getOres(recipe.getsInputTool().getOreDict());
			}
			if (inputTool != null && input != null && inputPattern != null) {
				WorkbenchCachedRecipe res = new WorkbenchCachedRecipe(input, inputTool, inputPattern,
						recipe.getOutput());
				if (res.contains(res.input, ingredient)) {
					res.setIngredientPermutation(res.input, ingredient);
					arecipes.add(res);
				}
			} else if (inputTool != null && input != null && notPattern) {
				WorkbenchCachedRecipe res = new WorkbenchCachedRecipe(input, inputTool, recipe.getOutput());
				if (res.contains(res.input, ingredient)) {
					res.setIngredientPermutation(res.input, ingredient);
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
		;
	}

	public class WorkbenchCachedRecipe extends TemplateRecipeHandler.CachedRecipe {

		private ArrayList<PositionedStack> input;
		private PositionedStack output;

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

		public WorkbenchCachedRecipe(List<ItemStack> input, List<ItemStack> inputTool, List<ItemStack> inputPattern,
				ItemStack output) {
			this.input = new ArrayList<PositionedStack>();
			if (input != null) {
				this.input.add(new PositionedStack(input, 21, 37));
				if (inputTool != null) {
					this.input.add(new PositionedStack(inputTool, 75, 14));
				}
				if (inputPattern != null) {
					this.input.add(new PositionedStack(inputPattern, 21, 14));
				}
				if (output != null) {
					this.output = new PositionedStack(output, 129, 37);
				}

			}

		}

		public WorkbenchCachedRecipe(List<ItemStack> input, List<ItemStack> inputTool, ItemStack output) {
			this.input = new ArrayList<PositionedStack>();
			if (input != null) {
				this.input.add(new PositionedStack(input, 21, 37));
				if (inputTool != null) {
					this.input.add(new PositionedStack(inputTool, 75, 14));
				}
				if (output != null) {
					this.output = new PositionedStack(output, 129, 37);
				}

			}

		}

	}

}
