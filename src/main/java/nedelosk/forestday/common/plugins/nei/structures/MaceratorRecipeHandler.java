package nedelosk.forestday.common.plugins.nei.structures;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import nedelosk.forestday.structure.alloysmelter.crafting.AlloySmelterRecipe;
import nedelosk.forestday.structure.alloysmelter.crafting.AlloySmelterRecipeManager;
import nedelosk.forestday.structure.alloysmelter.gui.GuiAlloySmelterController;
import nedelosk.forestday.structure.macerator.crafting.MaceratorRecipe;
import nedelosk.forestday.structure.macerator.crafting.MaceratorRecipeManager;
import nedelosk.forestday.structure.macerator.gui.GuiMaceratorController;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.OreDictionary;

import org.lwjgl.opengl.GL11;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class MaceratorRecipeHandler extends TemplateRecipeHandler {

  @Override
  public String getRecipeName() {
    return StatCollector.translateToLocal("forestday.nei.macerator");
  }

  @Override
  public String getGuiTexture() {
    return "forestday:textures/gui/nei/macerator.png";
  }

  @Override
  public String getOverlayIdentifier() {
    return "ForestDayMacerator";
  }
  
  @Override
	public Class<? extends GuiContainer> getGuiClass() {
		return GuiMaceratorController.class;
	}

  @Override
  public void loadTransferRects() {
    transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(8, 5, 18, 18), "ForestDayMacerator", new Object[0]));
  }

  @Override
  public void loadCraftingRecipes(ItemStack result) {
    if(result == null) {
      return;
    }

    List<MaceratorRecipe> recipes = MaceratorRecipeManager.getInstance().getRecipe();
    for (MaceratorRecipe recipe : recipes) {
  	  if(recipe.isNEI())
  	  {
      ItemStack output = recipe.getOutput1();
      if(recipe.getInput() != null)
      {
      if(result.getItem() == output.getItem() && result.getItemDamage() == output.getItemDamage()) {
    	  MaceratorRecipeCachedRecipe res = new MaceratorRecipeCachedRecipe(recipe.getMinRoughness(), recipe.getMaxRoughness(), recipe.getInput(), result);
        arecipes.add(res);
      }
      }
      else if(result.getItem() == output.getItem() && result.getItemDamage() == output.getItemDamage())
      {
      	  List<ItemStack> list = OreDictionary.getOres(recipe.getOredictInput());
    	  MaceratorRecipeCachedRecipe res = new MaceratorRecipeCachedRecipe(recipe.getMinRoughness(), recipe.getMaxRoughness(), list, output);
    	  arecipes.add(res);
      }
  	  }
    }

  }

  @Override
  public void loadCraftingRecipes(String outputId, Object... results) {
    if(outputId.equals("ForestDayMacerator") && getClass() == MaceratorRecipeHandler.class) {
      List<MaceratorRecipe> recipes = MaceratorRecipeManager.getInstance().getRecipe();
      for (MaceratorRecipe recipe : recipes) {
          ItemStack output = recipe.getOutput1();
    	  if(recipe.isNEI())
    	  {
    	      if(recipe.getInput() != null)
    	      {
        MaceratorRecipeCachedRecipe res = new MaceratorRecipeCachedRecipe(recipe.getMinRoughness(), recipe.getMaxRoughness(), recipe.getInput(), output);
        arecipes.add(res);
    	      }
    	      else
    	      {
    	      List<ItemStack> list = OreDictionary.getOres(recipe.getOredictInput());
        	  MaceratorRecipeCachedRecipe res = new MaceratorRecipeCachedRecipe(recipe.getMinRoughness(), recipe.getMaxRoughness(), list, output);
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
    List<MaceratorRecipe> recipes = MaceratorRecipeManager.getInstance().getRecipe();
    for (MaceratorRecipe recipe : recipes) {
  	  if(recipe.isNEI())
  	  {
   	 ItemStack output = recipe.getOutput1();
   	 if(recipe.getInput() != null)
   	 {
   	MaceratorRecipeCachedRecipe res = new MaceratorRecipeCachedRecipe(recipe.getMinRoughness(), recipe.getMaxRoughness(), recipe.getInput(), output);
      if(res.contains(res.input, ingredient)) {
        res.setIngredientPermutation(res.input, ingredient);
        arecipes.add(res);
      }
   	 }
   	 else
   	 {
   		 List<ItemStack> list = OreDictionary.getOres(recipe.getOredictInput());
   	   	 MaceratorRecipeCachedRecipe res = new MaceratorRecipeCachedRecipe(recipe.getMinRoughness(), recipe.getMaxRoughness(), list, output);
   		 if(res.contains(res.input, ingredient))
   		 {
   	        res.setIngredientPermutation(res.input, ingredient);
   	        arecipes.add(res);
   		 }
   	 }
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
	    MaceratorRecipeCachedRecipe recipe = (MaceratorRecipeCachedRecipe) arecipes.get(recipeIndex);
	    int maxRoughness = recipe.getMaxRoughness();
	    int minRoughness = recipe.getMinRoughness();
	    GuiDraw.drawString("Min " + (StatCollector.translateToLocal("forestday.tooltip.roughness")) + minRoughness, 70, 45, 4210752, false);
	    GuiDraw.drawString("Max " + (StatCollector.translateToLocal("forestday.tooltip.roughness")) + maxRoughness, 70, 12, 4210752, false);
  }

  public class MaceratorRecipeCachedRecipe extends TemplateRecipeHandler.CachedRecipe {

    private ArrayList<PositionedStack> input;
    private PositionedStack output;
    private int minRoughness;
    private int maxRoughness;
    
    public int getMinRoughness() {
		return minRoughness;
	}
    
    public int getMaxRoughness() {
		return maxRoughness;
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

    public MaceratorRecipeCachedRecipe(int minHeat, int maxHeat, ItemStack input0, ItemStack output) {
      this.input = new ArrayList<PositionedStack>();
      if(input0 != null) {
        this.input.add(new PositionedStack(input0, 50, 24));
      if(output != null) {
        this.output = new PositionedStack(output, 118, 24);
      }
      this.minRoughness = minHeat;
      this.maxRoughness = maxHeat;
    }

    }
    
    public MaceratorRecipeCachedRecipe(int minHeat, int maxHeat, List<ItemStack> input0, ItemStack output) {
        this.input = new ArrayList<PositionedStack>();
        if(input0 != null) {
          this.input.add(new PositionedStack(input0, 50, 24));
        if(output != null) {
          this.output = new PositionedStack(output, 118, 24);
        }
        this.minRoughness = minHeat;
        this.maxRoughness = maxHeat;
      }

      }
    
  }

}
