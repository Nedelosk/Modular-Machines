package nedelosk.nedeloskcore.common.plan;

import java.util.ArrayList;
import java.util.List;

import nedelosk.nedeloskcore.api.plan.PlanRecipe;
import net.minecraft.item.ItemStack;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.nc.Plan")
public class PlanRecipeHandler {
	
	@ZenMethod
    public static void add(IIngredient updateBlock, IIngredient outputBlock, int stages, IIngredient[]... input) {
        ItemStack resultToAdd = MineTweakerMC.getItemStack(outputBlock);
        ItemStack resultToAdd2 = MineTweakerMC.getItemStack(updateBlock);
        ItemStack[][] inputStack = new ItemStack[stages][4];
        for(int i = 0 ; i < input.length;i++)
        {
        	for(int r = 0;r < input[i].length;r++)
        	{
        		inputStack[i][r] = MineTweakerMC.getItemStack(input[i][r]);
        	}
        }
        
        MineTweakerAPI.apply(new AddAction(resultToAdd, resultToAdd2, stages, inputStack));
    }
	
	@ZenMethod
    public static void add(IIngredient outputBlock, int stages, IIngredient[]... input) {
        ItemStack resultToAdd = MineTweakerMC.getItemStack(outputBlock);
        ItemStack[][] inputStack = new ItemStack[stages][4];
        for(int i = 0 ; i < input.length;i++)
        {
        	for(int r = 0;r < input[i].length;r++)
        	{
        		inputStack[i][r] = MineTweakerMC.getItemStack(input[i][r]);
        	}
        }
        
        MineTweakerAPI.apply(new AddAction(resultToAdd, stages, inputStack));
    }

    @ZenMethod
    public static void remove(IItemStack result) {
        ItemStack resultToRemove = MineTweakerMC.getItemStack(result);
        MineTweakerAPI.apply(new RemoveAction(resultToRemove));

    }

    private static class AddAction implements IUndoableAction {

        private final PlanRecipe recipe;

        public AddAction(ItemStack outputBlock, int stages, ItemStack[][] input) {
            recipe = new PlanRecipe(outputBlock, stages, input);
        }
        
        public AddAction(ItemStack updateBlock, ItemStack outputBlock, int stages, ItemStack[][] input) {
            recipe = new PlanRecipe(updateBlock, outputBlock, stages, input);
        }

        @Override
        public void apply() {
            PlanRecipeManager.addRecipe(recipe);
        }

        @Override
        public boolean canUndo() {
            return true;
        }

        @Override
        public void undo() {
        	PlanRecipeManager.removeRecipe(recipe);
        }

        @Override
        public String describe() {
            return "Adding Plan Recipe";
        }

        @Override
        public String describeUndo() {
            return "Removing Recipe";
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }
    }

    private static class RemoveAction implements IUndoableAction {

        private final ItemStack result;
        private List<PlanRecipe> removedRecipes = new ArrayList<PlanRecipe>();

        public RemoveAction(ItemStack result) {
            this.result = result;
        }

        @Override
        public void apply() {
            removedRecipes = PlanRecipeManager.removeRecipes(result);
            
        }

        @Override
        public boolean canUndo() {
            return true;
        }

        @Override
        public void undo() {
        	PlanRecipeManager.addAllRecipes(removedRecipes);
        }

        @Override
        public String describe() {
            return "Removing all Plan Recipe where '" + result.getDisplayName() + "' is the result.";
        }

        @Override
        public String describeUndo() {
            return "Adding back in all Plan Recipe where '" + result.getDisplayName() + "' is the result.";
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }
    }
}
