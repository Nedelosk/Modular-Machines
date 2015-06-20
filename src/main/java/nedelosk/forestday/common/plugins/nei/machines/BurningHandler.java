package nedelosk.forestday.common.plugins.nei.machines;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import nedelosk.forestday.common.machines.base.heater.generator.HeatGeneratorRecipe;
import nedelosk.forestday.common.machines.base.heater.generator.HeatGeneratorRecipeManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class BurningHandler extends TemplateRecipeHandler {

  @Override
  public String getRecipeName() {
    return StatCollector.translateToLocal("forestday.nei.burning");
  }

  @Override
  public String getGuiTexture() {
    return "forestday:textures/gui/nei/burning.png";
  }

  @Override
  public String getOverlayIdentifier() {
    return "ForestDayBurning";
  }

  @Override
  public void loadTransferRects() {
	    transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(86, 26, 22, 13), "ForestDayBurning", new Object[0]));
  }

  @Override
  public void loadCraftingRecipes(ItemStack result) {
    if(result == null) {
      return;
    }

    List<HeatGeneratorRecipe> recipes = HeatGeneratorRecipeManager.getInstance().getRecipes();
    for (HeatGeneratorRecipe recipe : recipes) {
      ItemStack output = recipe.getOutput();
      if(result.getItem() == output.getItem() && result.getItemDamage() == output.getItemDamage()) {
    	  BurningCachedRecipe res = new BurningCachedRecipe(recipe.getInput1(), output);
        arecipes.add(res);
      }
    }

  }

  @Override
  public void loadCraftingRecipes(String outputId, Object... results) {
    if(outputId.equals("ForestDayBurning") && getClass() == BurningHandler.class) {
        List<HeatGeneratorRecipe> recipes = HeatGeneratorRecipeManager.getInstance().getRecipes();
      for (HeatGeneratorRecipe recipe : recipes) {
        ItemStack output = recipe.getOutput();
        BurningCachedRecipe res = new BurningCachedRecipe(recipe.getInput1(), output);
        arecipes.add(res);
      }
    } else {
      super.loadCraftingRecipes(outputId, results);
    }
  }

  @Override
  public void loadUsageRecipes(ItemStack ingredient) {
      List<HeatGeneratorRecipe> recipes = HeatGeneratorRecipeManager.getInstance().getRecipes();
    for (HeatGeneratorRecipe recipe : recipes) {
  	  ItemStack output = recipe.getOutput();
	  BurningCachedRecipe res = new BurningCachedRecipe(recipe.getInput1(), output);
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
  }
  
  @Override
	public void drawExtras(int recipe) {
	  drawProgressBar(86, 26, 166, 0, 22, 13, 48, 3);
	}

  public class BurningCachedRecipe extends TemplateRecipeHandler.CachedRecipe {

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

    public BurningCachedRecipe(ItemStack input, ItemStack output) {
      this.input = new ArrayList<PositionedStack>();
      if(input != null) {
        this.input.add(new PositionedStack(input, 50, 24));
      }
      if(output != null) {
        this.output = new PositionedStack(output, 118, 24);
      }
    
  }
    
  }

}
