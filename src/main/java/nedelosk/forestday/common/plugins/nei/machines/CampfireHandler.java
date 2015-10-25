package nedelosk.forestday.common.plugins.nei.machines;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import nedelosk.forestday.common.crafting.CampfireRecipe;
import nedelosk.forestday.common.crafting.CampfireRecipeManager;
import nedelosk.forestday.common.crafting.KilnRecipeManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.FurnaceRecipeHandler;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class CampfireHandler extends FurnaceRecipeHandler {

  @Override
  public String getRecipeName() {
    return StatCollector.translateToLocal("forestday.nei.campfire");
  }

  @Override
  public String getGuiTexture() {
    return "forestday:textures/gui/nei/campfire.png";
  }

  @Override
  public String getOverlayIdentifier() {
    return "ForestDayCampfire";
  }

  @Override
  public void loadTransferRects() {
	    transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(99, 23, 22, 15), "ForestDayCampfire", new Object[0]));
	    transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(76, 23, 13, 13), "fuel", new Object[0]));
  }

  @Override
  public void loadCraftingRecipes(ItemStack result) {
    if(result == null) {
      return;
    }

    List<CampfireRecipe> recipes = CampfireRecipeManager.getRecipes();
    for (CampfireRecipe recipe : recipes) {
      ItemStack output = recipe.getOutput();
      if(result.getItem() == output.getItem() && result.getItemDamage() == output.getItemDamage()) {
    	  CampfireCachedRecipe res = new CampfireCachedRecipe(recipe.getInput(), recipe.getInput2(), output);
        arecipes.add(res);
      }
    }

  }

  @Override
  public void loadCraftingRecipes(String outputId, Object... results) {
    if(outputId.equals("ForestDayCampfire") && getClass() == CampfireHandler.class) {
      List<CampfireRecipe> recipes = CampfireRecipeManager.getRecipes();
      for (CampfireRecipe recipe : recipes) {
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
     KilnRecipeManager.getInstance();
     List<CampfireRecipe> recipes = CampfireRecipeManager.getRecipes();
    for (CampfireRecipe recipe : recipes) {
  	  ItemStack output = recipe.getOutput();
	  CampfireCachedRecipe res = new CampfireCachedRecipe(recipe.getInput(), recipe.getInput2(), output);
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
    	if(fuels == null)
    		fuels = cloneList(afuels);
        return fuels.get((cycleticks / 48) % afuels.size());
    }
    
    public ArrayList<PositionedStack> cloneList(List<FuelPair> list) {
    	ArrayList<PositionedStack> clone = new ArrayList(list.size());
        for(FuelPair item: list) clone.add(new PositionedStack(item.stack.items, 75, 40));
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
    	if(input != null) {
    		this.input.add(new PositionedStack(input, 22, 22));
    	}
    	if(input1 != null) {
    		this.input.add(new PositionedStack(input1, 40, 22));
    	}
    	if(output != null) {
    		this.output = new PositionedStack(output, 131, 22);
    	}
    
    }
    
  }

}
