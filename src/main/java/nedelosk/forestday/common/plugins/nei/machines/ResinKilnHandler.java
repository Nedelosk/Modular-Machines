package nedelosk.forestday.common.plugins.nei.machines;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import nedelosk.forestday.common.crafting.KilnRecipe;
import nedelosk.forestday.common.crafting.KilnRecipeManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class ResinKilnHandler extends TemplateRecipeHandler {

  @Override
  public String getRecipeName() {
    return StatCollector.translateToLocal("forestday.nei.kiln.resin");
  }

  @Override
  public String getGuiTexture() {
    return "forestday:textures/gui/nei/kiln.png";
  }

  @Override
  public String getOverlayIdentifier() {
    return "ForestDayKilnResin";
  }

  @Override
  public void loadTransferRects() {
	    transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(60, 23, 22, 15), "ForestDayKilnResin", new Object[0]));
  }

  @Override
  public void loadCraftingRecipes(ItemStack result) {
    if(result == null) {
      return;
    }

	List<KilnRecipe> recipes = KilnRecipeManager.getRecipes();
    for (KilnRecipe recipe : recipes) {
      ItemStack output = recipe.getOutput1();
      if(result.getItem() == output.getItem() && result.getItemDamage() == output.getItemDamage()) {
    	  KilnResinCachedRecipe res = new KilnResinCachedRecipe(recipe.getInput1(), recipe.getInput2(), output);
        arecipes.add(res);
      }
    }

  }

  @Override
  public void loadCraftingRecipes(String outputId, Object... results) {
    if(outputId.equals("ForestDayKilnResin") && getClass() == ResinKilnHandler.class) {
		List<KilnRecipe> recipes = KilnRecipeManager.getRecipes();
      for (KilnRecipe recipe : recipes) {
        ItemStack output = recipe.getOutput1();
        KilnResinCachedRecipe res = new KilnResinCachedRecipe(recipe.getInput1(), recipe.getInput2(), output);
        arecipes.add(res);
      }
    } else {
      super.loadCraftingRecipes(outputId, results);
    }
  }

  @Override
  public void loadUsageRecipes(ItemStack ingredient) {
	List<KilnRecipe> recipes = KilnRecipeManager.getRecipes();
    for (KilnRecipe recipe : recipes) {
  	  ItemStack output = recipe.getOutput1();
	  KilnResinCachedRecipe res = new KilnResinCachedRecipe(recipe.getInput1(), recipe.getInput2(), output);
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

  public class KilnResinCachedRecipe extends TemplateRecipeHandler.CachedRecipe {

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

    public KilnResinCachedRecipe(ItemStack input, ItemStack input1, ItemStack output) {
      this.input = new ArrayList<PositionedStack>();
      if(input != null) {
        this.input.add(new PositionedStack(input, 41, 6));
        if(input1 != null) {
            this.input.add(new PositionedStack(input1, 41, 42));
      if(output != null) {
        this.output = new PositionedStack(output, 96, 24);
      }
    }

    }
    
  }
    
  }

}
