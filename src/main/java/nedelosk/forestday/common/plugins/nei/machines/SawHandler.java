package nedelosk.forestday.common.plugins.nei.machines;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import nedelosk.forestday.client.machines.brick.gui.GuiKiln;
import nedelosk.forestday.client.machines.iron.gui.GuiSaw;
import nedelosk.forestday.common.machines.iron.saw.SawRecipe;
import nedelosk.forestday.common.machines.iron.saw.SawRecipeManager;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class SawHandler extends TemplateRecipeHandler {

  @Override
  public String getRecipeName() {
    return StatCollector.translateToLocal("forestday.nei.file.auto");
  }

  @Override
  public String getGuiTexture() {
    return "forestday:textures/gui/nei/auto_file.png";
  }
  
  @Override
	public Class<? extends GuiContainer> getGuiClass() {
		return GuiSaw.class;
	}

  @Override
  public String getOverlayIdentifier() {
    return "ForestDayAutoFile";
  }

  @Override
  public void loadTransferRects() {
    transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(77, 8, 20, 42), "ForestDayAutoFile", new Object[0]));
  }

  @Override
  public void loadCraftingRecipes(ItemStack result) {
    if(result == null) {
      return;
    }

    List<SawRecipe > recipes = SawRecipeManager.getInstance().getRecipe();
    for (SawRecipe recipe : recipes) {
      ItemStack output = recipe.getOutput();
      if(result.getItem() == output.getItem() && result.getItemDamage() == output.getItemDamage()) {
    	  AutoFileCachedRecipe res = new AutoFileCachedRecipe(recipe.getInput(), output);
        arecipes.add(res);
      }
    }

  }

  @Override
  public void loadCraftingRecipes(String outputId, Object... results) {
    if(outputId.equals("ForestDayAutoFile") && getClass() == SawHandler.class) {
      List<SawRecipe> recipes = SawRecipeManager.getInstance().getRecipe();
      for (SawRecipe recipe : recipes) {
        ItemStack output = recipe.getOutput();
        AutoFileCachedRecipe res = new AutoFileCachedRecipe(recipe.getInput(), output);
        arecipes.add(res);
      }
    } else {
      super.loadCraftingRecipes(outputId, results);
    }
  }

  @Override
  public void loadUsageRecipes(ItemStack ingredient) {
    List<SawRecipe> recipes = SawRecipeManager.getInstance().getRecipe();
    for (SawRecipe recipe : recipes) {
  	  ItemStack output = recipe.getOutput();
  	  AutoFileCachedRecipe res = new AutoFileCachedRecipe(recipe.getInput(), output);
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

  public class AutoFileCachedRecipe extends TemplateRecipeHandler.CachedRecipe {

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

    public AutoFileCachedRecipe(ItemStack input, ItemStack output) {
      this.input = new ArrayList<PositionedStack>();
      if(input != null) {
        this.input.add(new PositionedStack(input, 50, 24));
      if(output != null) {
        this.output = new PositionedStack(output, 118, 24);
      }
    }

    }
    
  }

}
