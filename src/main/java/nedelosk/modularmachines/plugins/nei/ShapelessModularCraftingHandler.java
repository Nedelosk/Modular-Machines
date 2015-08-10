package nedelosk.modularmachines.plugins.nei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import nedelosk.modularmachines.api.ModularMachinesApi;
import nedelosk.modularmachines.api.modular.crafting.IModularCraftingRecipe;
import nedelosk.modularmachines.api.modular.crafting.ShapelessModularCraftingRecipe;
import nedelosk.modularmachines.api.techtree.TechTreeManager;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class ShapelessModularCraftingHandler extends TemplateRecipeHandler {
	
  @Override
  public String getRecipeName() {
    return StatCollector.translateToLocal("modularmachines.shapelessmodularcrafting");
  }
  
  @Override
  public int recipiesPerPage() {
	return 1;
  }
  
  @Override
  public void loadTransferRects() {
	    transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(109, 57, 22, 15), "ShapelessModularCrafting", new Object[0]));
  }

  @Override
  public String getOverlayIdentifier() {
    return "ShapelessModularCrafting";
  }

  @Override
  public void loadCraftingRecipes(ItemStack result) {

    List recipes = ModularMachinesApi.getModularRecipes();
    for (IModularCraftingRecipe recipe : (ArrayList<IModularCraftingRecipe>) recipes) {
    	if(recipe instanceof ShapelessModularCraftingRecipe)
    	{
    		if(recipe.getEntrys() == null || TechTreeManager.isEntryComplete(Minecraft.getMinecraft().thePlayer, recipe.getEntrys()))
    		{
    			if(recipe.getRecipeOutput().isItemEqual(result))
    			{
    				if(checkDupe((ShapelessModularCraftingRecipe) recipe))
    				{
						CachedShapelessModularCraftingRecipe r = new CachedShapelessModularCraftingRecipe((ShapelessModularCraftingRecipe) recipe);
						arecipes.add(r);
    				}
    			}
    		}
    	}
    }

  }
  
	private boolean checkDupe(ShapelessModularCraftingRecipe recipe) {
		for (Object o : this.arecipes.toArray()){
			if (o instanceof CachedShapelessModularCraftingRecipe){
				CachedShapelessModularCraftingRecipe r = (CachedShapelessModularCraftingRecipe) o;
				if (r.recipe.getInput() == recipe.getInput()){
					if (r.recipe.getRecipeOutput().isItemEqual(recipe.getRecipeOutput())) {
						return false;
					}
				}
			}
		}
		return true;
	}

  @Override
  public void loadCraftingRecipes(String outputId, Object... results) {
    if(outputId.equals("ShapelessModularCrafting") && getClass() == ShapelessModularCraftingHandler.class) {
	   List recipes = ModularMachinesApi.getModularRecipes();
      if(recipes != null)
      {
      for (IModularCraftingRecipe recipe : (ArrayList<IModularCraftingRecipe>) recipes) {
    	  if(recipe instanceof ShapelessModularCraftingRecipe)
    	  {
    		  if(recipe.getEntrys() == null || TechTreeManager.isEntryComplete(Minecraft.getMinecraft().thePlayer, recipe.getEntrys()))
    		  {
    			  CachedShapelessModularCraftingRecipe r = new CachedShapelessModularCraftingRecipe((ShapelessModularCraftingRecipe) recipe);
    			  arecipes.add(r);
    		  }
    	  }
      }
      }
    } else {
      super.loadCraftingRecipes(outputId, results);
    }
  }

  @Override
  public void loadUsageRecipes(ItemStack ingredient) {
	    List recipes = ModularMachinesApi.getModularRecipes();
	    for (IModularCraftingRecipe recipe : (ArrayList<IModularCraftingRecipe>) recipes) {
	    	if(recipe instanceof ShapelessModularCraftingRecipe)
	    	{
	    		if(recipe.getEntrys() == null || TechTreeManager.isEntryComplete(Minecraft.getMinecraft().thePlayer, recipe.getEntrys()))
	    		{
					for (Object o : ((ShapelessModularCraftingRecipe) recipe).getInput()) {
						if (o instanceof ItemStack) {
							ItemStack item = (ItemStack) o;
							if (item.isItemEqual(ingredient)) {
								if (checkDupe((ShapelessModularCraftingRecipe) recipe)) {
									CachedShapelessModularCraftingRecipe r = new CachedShapelessModularCraftingRecipe((ShapelessModularCraftingRecipe) recipe);
									r.setIngredientPermutation(r.input ,ingredient);
									this.arecipes.add(r);
								}
							}
						}
					}
	    		}
	    	}
	    }
  }

  @Override
  public void drawBackground(int recipeIndex) {
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    GuiDraw.changeTexture(getGuiTexture());
    GuiDraw.drawTexturedModalRect(10, 20, 7, 7, 154, 90);
  }
  
  public class CachedShapelessModularCraftingRecipe extends TemplateRecipeHandler.CachedRecipe {

	    private ArrayList<PositionedStack> input;
	    private PositionedStack output;
	    public int energy;
	    public ShapelessModularCraftingRecipe recipe;

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

	    public CachedShapelessModularCraftingRecipe(ShapelessModularCraftingRecipe recipe) {
	        this.input = new ArrayList<PositionedStack>();
	        output = new PositionedStack(recipe.getRecipeOutput(), 143, 57);
	        this.recipe = recipe;
	        int i = 0;
	        for (int l = 0; l < 5; ++l)
	        {
	            for (int i1 = 0; i1 < 5; ++i1)
	            {
	            	if(i < recipe.getInput().size())
	            	{
	            	Object o = recipe.getInput().get(i1 + l * 5);
	            	if(o != null)
	            		input.add(new PositionedStack(o, 11 + i1 * 18, 21 + l * 18));
	            	i++;
	            	}
	            }
	        }
	    }
	    
	  }

  @Override
  public String getGuiTexture() {
	  return "modularmachines:textures/gui/gui_modularworkbench.png";
  }

}
