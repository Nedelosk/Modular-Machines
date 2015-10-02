package nedelosk.modularmachines.plugins.nei;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.GuiCraftingRecipe;
import codechicken.nei.recipe.GuiUsageRecipe;
import codechicken.nei.recipe.TemplateRecipeHandler;
import nedelosk.modularmachines.api.modular.module.producer.producer.recipe.IModuleProducerRecipe;
import nedelosk.modularmachines.api.recipes.IRecipe;
import nedelosk.modularmachines.api.recipes.NeiStack;
import nedelosk.modularmachines.api.recipes.RecipeItem;
import nedelosk.modularmachines.api.recipes.RecipeRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.OreDictionary;

public class ModularMachinesHandler extends TemplateRecipeHandler {
	
	public ResourceLocation nei_widgets = new ResourceLocation("modularmachines:textures/gui/nei/nei_widgets.png");
	public String recipeName;
	public IModuleProducerRecipe module;
	
	public ModularMachinesHandler(IModuleProducerRecipe module) {
		this.recipeName = module.getRecipeName();
		this.module = module;
		if(!NEIConfig.isAdded)
		{
		      GuiCraftingRecipe.craftinghandlers.add(this);
		      GuiUsageRecipe.usagehandlers.add(this);
		}
	}
	
  @Override
  public String getRecipeName() {
    return StatCollector.translateToLocal("modularmachines." + recipeName);
  }

  @Override
  public String getGuiTexture() {
    return "modularmachines:textures/gui/nei/nei_background.png";
  }

  @Override
  public String getOverlayIdentifier() {
    return "ModularMachines" + recipeName;
  }

  @Override
  public void loadCraftingRecipes(ItemStack result) {
    if(result == null) {
      return;
    }

    List<IRecipe> recipes = RecipeRegistry.getRecipes().get(recipeName);
    if(recipes != null)
    {
    for (IRecipe recipe : recipes) {
      RecipeItem[] outputs = recipe.getOutputs();
      for(RecipeItem output : outputs)
      {
      if(result.getItem() == output.item.getItem() && result.getItemDamage() == output.item.getItemDamage()) {
    	  ModularCachedRecipe res = new ModularCachedRecipe(recipe.getInputs(), outputs);
    	  arecipes.add(res);
      }
      }
    }
    }

  }

  @Override
  public void loadCraftingRecipes(String outputId, Object... results) {
    if(outputId.equals("ModularMachines" + recipeName) && getClass() == ModularMachinesHandler.class) {
      List<IRecipe> recipes = RecipeRegistry.getRecipes().get(recipeName);
      if(recipes != null)
      {
      for (IRecipe recipe : recipes) {
       ModularCachedRecipe res = new ModularCachedRecipe(recipe.getInputs(), recipe.getOutputs());
       arecipes.add(res);
      }
      }
    } else {
      super.loadCraftingRecipes(outputId, results);
    }
  }

  @Override
  public void loadUsageRecipes(ItemStack ingredient) {
    List<IRecipe> recipes = RecipeRegistry.getRecipes().get(recipeName);
    if(recipes != null)
    {
    for (IRecipe recipe : recipes) {
    	ModularCachedRecipe res = new ModularCachedRecipe(recipe.getInputs(), recipe.getOutputs());
    	if(res.contains(res.input, ingredient)) {
    		res.setIngredientPermutation(res.input, ingredient);
    	    arecipes.add(res);	
    	}
    }
    }
  }
  
  @Override
  public TemplateRecipeHandler newInstance() {
	return new ModularMachinesHandler(this.module);
  }

  @Override
  public void drawBackground(int recipeIndex) {
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    GuiDraw.changeTexture(getGuiTexture());
    GuiDraw.drawTexturedModalRect(0, 0, 0, 0, 166, 65);
    
    GuiDraw.changeTexture(nei_widgets);
    ArrayList<PositionedStack> stacks = new ArrayList<PositionedStack>();
    stacks.add(getResultStack(recipeIndex));
    for(PositionedStack stack : getIngredientStacks(recipeIndex))
    	stacks.add(stack);
    if(getOtherStacks(recipeIndex) != null)
    	for(PositionedStack stack : getOtherStacks(recipeIndex))
    		if(stack != null)
    			stacks.add(stack);
    for(PositionedStack stack : stacks)
    	GuiDraw.drawTexturedModalRect(stack.relx - 1, stack.rely - 1, 0, 0, 18, 18);
  }
  
  @Override
  public void drawExtras(int recipe) {
  }
  
  public class ModularCachedRecipe extends TemplateRecipeHandler.CachedRecipe {

	    private ArrayList<PositionedStack> input;
	    private PositionedStack output;
	    private ArrayList<PositionedStack> outputs;
	    public int energy;

	    @Override
	    public List<PositionedStack> getIngredients() {
	      return getCycledIngredients(cycleticks / 20, input);
	    }

	    @Override
	    public PositionedStack getResult() {
	      return output;
	    }
	    
	    @Override
	    public List<PositionedStack> getOtherStacks() {
	    return outputs;
	    }
	    
	    public ArrayList<PositionedStack> getInput() {
			return input;
		}

	    public ModularCachedRecipe(RecipeItem[] inputs,RecipeItem[] outputs) {
	        this.input = new ArrayList<PositionedStack>();
	        this.outputs = new ArrayList<PositionedStack>();
	    	ArrayList<NeiStack> stacks = module.addNEIStacks();
	    	int input = 0;
	    	int output = 0;
	    	for(NeiStack stack : stacks)
	    	{
	    		if(stack.isInput)
	    		{
	    			if(inputs.length != input)
	    			{
	    			if(inputs[input].isItem())
	    				this.input.add(new PositionedStack(inputs[input].item, stack.x, stack.y));
	    			else if(inputs[input].isOre())
	    			{
	    				ArrayList<ItemStack> listOre = OreDictionary.getOres(inputs[input].ore.oreDict);
	    				for(ItemStack stackOre : listOre)
	    					stackOre.stackSize = inputs[input].ore.stackSize;
	    				this.input.add(new PositionedStack(listOre, stack.x, stack.y));
	    			}
	    			}
	    			input++;
	    		}
	    		else
	    		{
	    			if(output == 0)
	    				this.output = new PositionedStack(outputs[output].item, stack.x, stack.y);
	    			else
	    			{
	    				if(outputs.length - 1 >= output)
	    					this.outputs.add(new PositionedStack(outputs[output].item, stack.x, stack.y));
	    			}
	    			output++;
	    		}
	    	}
	    }
	    
	    @Override
	    public boolean equals(Object obj) {
	    	if(!(obj instanceof ModularCachedRecipe))
	    		return false;
	    	ModularCachedRecipe recipe = (ModularCachedRecipe) obj;
	    	output.equals(recipe.output);
	    	for(int i = 0;i < outputs.size();i++)
	    	{
	    		PositionedStack stack = outputs.get(i);
	    		if(!stack.equals(recipe.outputs.get(i)))
	    			return false;
	    	}
	    	for(int i = 0;i < input.size();i++)
	    	{
	    		PositionedStack stack = input.get(i);
	    		if(!stack.equals(recipe.input.get(i)))
	    			return false;
	    	}
	    	return true;
	    }
	    
	  }

}
