package nedelosk.nedeloskcore.common.nei;

import java.util.ArrayList;
import java.util.List;

import nedelosk.nedeloskcore.api.plan.PlanRecipe;
import nedelosk.nedeloskcore.common.plan.PlanRecipeManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class PlanHandler extends TemplateRecipeHandler {

  @Override
  public String getRecipeName() {
    return StatCollector.translateToLocal("nei.nedeloskcore.plan");
  }

  @Override
  public String getGuiTexture() {
    return "nedeloskcore:textures/gui/nei/plan.png";
  }

  @Override
  public String getOverlayIdentifier() {
    return "NedeloskCorePlan";
  }

  @Override
  public void loadCraftingRecipes(ItemStack result) {
    if(result == null) {
      return;
    }
    List<PlanRecipe> recipes = PlanRecipeManager.recipes;
    for(PlanRecipe recipe : recipes)
    {
    	ItemStack outputBlock = recipe.outputBlock.copy();
    	if(result.getItem() == outputBlock.getItem() && result.getItemDamage() == outputBlock.getItemDamage())
    	{
    		ArrayList<ItemStack> list = new ArrayList<ItemStack>();
    		for(ItemStack[] stacks : recipe.input)
    		{
    			for(int i = 0;i < 4;i++)
    			{
    				if(i < stacks.length && stacks[i] != null)
    				{
    				list.add(stacks[i]);
    				}
    				else
    					list.add(null);
    			}
    		}
    		PlanCachedRecipe res = new PlanCachedRecipe((recipe.updateBlock != null) ? recipe.updateBlock.copy() : null, outputBlock, list);
    		arecipes.add(res);
    	}
    }
  }

  @Override
  public void loadCraftingRecipes(String outputId, Object... results) {
    if(outputId.equals("NedeloskCorePlan") && getClass() == PlanHandler.class) {
        List<PlanRecipe> recipes = PlanRecipeManager.recipes;
      for (PlanRecipe recipe : recipes) {
  		ArrayList<ItemStack> list = new ArrayList<ItemStack>();
  		for(ItemStack[] stacks : recipe.input)
  		{
			for(int i = 0;i < 4;i++)
			{
				if(i < stacks.length && stacks[i] != null)
				{
				list.add(stacks[i]);
				}				if(i < stacks.length && stacks[i] != null)
				{
				list.add(stacks[i]);
				}
				else
					list.add(null);
			}
  		}
        PlanCachedRecipe res = new PlanCachedRecipe((recipe.updateBlock != null) ? recipe.updateBlock.copy() : null, recipe.outputBlock.copy(), list);
        arecipes.add(res);
      }
    } else {
      super.loadCraftingRecipes(outputId, results);
    }
  }

  @Override
  public void loadUsageRecipes(ItemStack ingredient) {
	  List<PlanRecipe> recipes = PlanRecipeManager.recipes;
    for (PlanRecipe recipe : recipes) {
 		ArrayList<ItemStack> list = new ArrayList<ItemStack>();
		for(ItemStack[] stacks : recipe.input)
		{
			for(int i = 0;i < 4;i++)
			{
				if(i < stacks.length && stacks[i] != null)
				{
				list.add(stacks[i]);
				}
				else
					list.add(null);
			}
		}
	  PlanCachedRecipe res = new PlanCachedRecipe((recipe.updateBlock != null) ? recipe.updateBlock.copy() : null, recipe.outputBlock.copy(), list);
      if(res.contains(res.input, ingredient)) {
        res.setIngredientPermutation(res.input, ingredient);
        arecipes.add(res);
      }
    }
  }

  @Override
	public int recipiesPerPage() {
		return 1;
	}
  
  @Override
  public void drawBackground(int recipeIndex) {
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    GuiDraw.changeTexture(getGuiTexture());
    GuiDraw.drawTexturedModalRect(0, 0, 0, 0, 166, 130);
  }

  public class PlanCachedRecipe extends TemplateRecipeHandler.CachedRecipe {

    private ArrayList<PositionedStack> input;
    private PositionedStack outputBlock;
    private PositionedStack updateBlock;

    @Override
    public List<PositionedStack> getIngredients() {
      return getCycledIngredients(cycleticks / 20, input);
    }

    @Override
    public PositionedStack getResult() {
      return outputBlock;
    }
    
    @Override
    public PositionedStack getOtherStack() {
    	return updateBlock;
    }
    
    public ArrayList<PositionedStack> getInput() {
		return input;
	}

    public PlanCachedRecipe(ItemStack updateBlock, ItemStack outputBlock, ArrayList<ItemStack> input) {
      this.input = new ArrayList<PositionedStack>();
      int x = 0;
      int y = 0;
      if(input != null) {
    	  for(int i = 0;i < input.size();i++)
    	  {
    			if(input.get(i) != null)
    			{
    		  this.input.add(new PositionedStack(input.get(i), 49 + x * 18, 9 + (y * 18 + ((y != 0) ? 6 : 0))));
    			}
    		  x++;
    		  if(i == 3 || i == 7 || i == 11 || i == 15)
    		  {
    			  y++;
    			  x = 0;
    		  }
    	  }
      if(outputBlock != null) {
        this.outputBlock = new PositionedStack(outputBlock, 139, 57);
      }
      if(updateBlock != null)
      {
    	  this.updateBlock = new PositionedStack(updateBlock, 13, 57);
    }

    }
    
  }
    
  }

}
