package nedelosk.forestday.common.plugins.minetweaker.handler;

import java.util.ArrayList;
import java.util.List;

import nedelosk.forestday.common.machines.wood.workbench.WorkbenchRecipe;
import nedelosk.forestday.common.machines.wood.workbench.WorkbenchRecipeManager;
import nedelosk.forestday.structure.alloysmelter.crafting.AlloySmelterRecipe;
import nedelosk.forestday.structure.alloysmelter.crafting.AlloySmelterRecipeManager;
import net.minecraft.item.ItemStack;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import minetweaker.api.oredict.IOreDict;
import minetweaker.api.oredict.IOreDictEntry;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.forday.AlloySmelter")
public class AlloySmelterRecipeHandler {
	
	@ZenMethod
    public static void add(IIngredient output, IIngredient input1, IIngredient input2, int minHeat, int maxHeat) {
        ItemStack resultToAdd = MineTweakerMC.getItemStack(output);
        ItemStack input1ToAdd = MineTweakerMC.getItemStack(input1);
        ItemStack input2ToAdd = MineTweakerMC.getItemStack(input2);
        
        MineTweakerAPI.apply(new AddAction(input1ToAdd, input2ToAdd, resultToAdd, minHeat, maxHeat));
    }

    @ZenMethod
    public static void remove(IItemStack result) {
        ItemStack resultToRemove = MineTweakerMC.getItemStack(result);
        MineTweakerAPI.apply(new RemoveAction(resultToRemove));

    }

    private static class AddAction implements IUndoableAction {

        private final AlloySmelterRecipe recipe;
        
        public AddAction(ItemStack parent1ToAdd, ItemStack parent2ToAdd, ItemStack output, int minHeat, int maxHeat) {
            recipe = new AlloySmelterRecipe(parent1ToAdd, parent2ToAdd, output, minHeat, maxHeat);
        }

        @Override
        public void apply() {
        	AlloySmelterRecipeManager.addRecipe(recipe);
        }

        @Override
        public boolean canUndo() {
            return true;
        }

        @Override
        public void undo() {
        	AlloySmelterRecipeManager.removeRecipe(recipe);
        }

        @Override
        public String describe() {
            return "Adding AlloySmelter Recipe";
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
        private List<AlloySmelterRecipe> removedRecipes = new ArrayList<AlloySmelterRecipe>();

        public RemoveAction(ItemStack result) {
            this.result = result;
        }

        @Override
        public void apply() {
            removedRecipes = AlloySmelterRecipeManager.removeRecipes(result);
            
        }

        @Override
        public boolean canUndo() {
            return true;
        }

        @Override
        public void undo() {
        	AlloySmelterRecipeManager.addAllRecipes(removedRecipes);
        }

        @Override
        public String describe() {
            return "Removing all AlloySmelter Recipe where '" + result.getDisplayName() + "' is the result.";
        }

        @Override
        public String describeUndo() {
            return "Adding back in all AlloySmelter Recipe where '" + result.getDisplayName() + "' is the result.";
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }
    }
}
