package nedelosk.forestday.common.plugins.nei.structures;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import nedelosk.forestday.structure.alloysmelter.crafting.AlloySmelterRecipe;
import nedelosk.forestday.structure.alloysmelter.crafting.AlloySmelterRecipeManager;
import nedelosk.forestday.structure.alloysmelter.gui.GuiAlloySmelterController;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class AlloySmelterRecipeHandler extends TemplateRecipeHandler {

  @Override
  public String getRecipeName() {
    return StatCollector.translateToLocal("forestday.nei.smelter.alloy");
  }

  @Override
  public String getGuiTexture() {
    return "forestday:textures/gui/nei/smelter_alloy.png";
  }

  @Override
  public String getOverlayIdentifier() {
    return "ForestDayAlloySmelter";
  }
  
  @Override
	public Class<? extends GuiContainer> getGuiClass() {
		return GuiAlloySmelterController.class;
	}

  @Override
  public void loadTransferRects() {
    transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(8, 5, 18, 18), "ForestDayAlloySmelter", new Object[0]));
  }

  @Override
  public void loadCraftingRecipes(ItemStack result) {
    if(result == null) {
      return;
    }

    List<AlloySmelterRecipe> recipes = AlloySmelterRecipeManager.getInstance().getRecipe();
    for (AlloySmelterRecipe recipe : recipes) {
      ItemStack output = recipe.getOutput1();
      if(result.getItem() == output.getItem() && result.getItemDamage() == output.getItemDamage()) {
        AlloySmelterRecipeCachedRecipe res = new AlloySmelterRecipeCachedRecipe(recipe.getMinHeat(), recipe.getMaxHeat(), recipe.getInput1(), recipe.getInput2(), output);
        arecipes.add(res);
      }
    }

  }

  @Override
  public void loadCraftingRecipes(String outputId, Object... results) {
    if(outputId.equals("ForestDayAlloySmelter") && getClass() == AlloySmelterRecipeHandler.class) {
      List<AlloySmelterRecipe> recipes = AlloySmelterRecipeManager.getInstance().getRecipe();
      for (AlloySmelterRecipe recipe : recipes) {
        ItemStack output = recipe.getOutput1();
        AlloySmelterRecipeCachedRecipe res = new AlloySmelterRecipeCachedRecipe(recipe.getMinHeat(), recipe.getMaxHeat(), recipe.getInput1(), recipe.getInput2(), output);
        arecipes.add(res);
      }
    } else {
      super.loadCraftingRecipes(outputId, results);
    }
  }

  @Override
  public void loadUsageRecipes(ItemStack ingredient) {
    List<AlloySmelterRecipe> recipes = AlloySmelterRecipeManager.getInstance().getRecipe();
    for (AlloySmelterRecipe recipe : recipes) {
   	 ItemStack output = recipe.getOutput1();
    	AlloySmelterRecipeCachedRecipe res = new AlloySmelterRecipeCachedRecipe(recipe.getMinHeat(), recipe.getMaxHeat(), recipe.getInput1(), recipe.getInput2(), output);
      if(res.contains(res.input, ingredient)) {
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
    GuiDraw.drawTexturedModalRect(8, 5, 166, 0, 20, 20);
    GuiDraw.drawTexturedModalRect(9, 5, 187, 0, 18, 18);
  }

  @Override
  public void drawExtras(int recipeIndex) {
	    AlloySmelterRecipeCachedRecipe recipe = (AlloySmelterRecipeCachedRecipe) arecipes.get(recipeIndex);
	    int maxHeat = recipe.getMaxHeat();
	    int minHeat = recipe.getMinHeat();
	    GuiDraw.drawString("Min " + (StatCollector.translateToLocal("forestday.tooltip.heat")) + minHeat, 90, 45, 4210752, false);
	    GuiDraw.drawString("Max " + (StatCollector.translateToLocal("forestday.tooltip.heat")) + maxHeat, 90, 12, 4210752, false);
  }

  public class AlloySmelterRecipeCachedRecipe extends TemplateRecipeHandler.CachedRecipe {

    private ArrayList<PositionedStack> input;
    private PositionedStack output;
    private int minHeat;
    private int maxHeat;
    
    public int getMinHeat() {
		return minHeat;
	}
    
    public int getMaxHeat() {
		return maxHeat;
	}

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

    public AlloySmelterRecipeCachedRecipe(int minHeat, int maxHeat, ItemStack input0, ItemStack input1, ItemStack output) {
      this.input = new ArrayList<PositionedStack>();
      if(input0 != null && input1 != null) {
        this.input.add(new PositionedStack(input0, 50, 8));
        this.input.add(new PositionedStack(input1, 50, 40));
      if(output != null) {
        this.output = new PositionedStack(output, 118, 24);
      }
      this.minHeat = minHeat;
      this.maxHeat = maxHeat;
    }

    }
    
  }

}
