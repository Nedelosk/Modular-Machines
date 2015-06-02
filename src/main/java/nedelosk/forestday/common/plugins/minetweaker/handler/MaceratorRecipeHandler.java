package nedelosk.forestday.common.plugins.minetweaker.handler;

import java.util.ArrayList;
import java.util.List;

import nedelosk.forestday.common.machines.wood.workbench.WorkbenchRecipe;
import nedelosk.forestday.common.machines.wood.workbench.WorkbenchRecipeManager;
import nedelosk.forestday.structure.alloysmelter.crafting.AlloySmelterRecipe;
import nedelosk.forestday.structure.alloysmelter.crafting.AlloySmelterRecipeManager;
import nedelosk.forestday.structure.macerator.crafting.MaceratorRecipe;
import nedelosk.forestday.structure.macerator.crafting.MaceratorRecipeManager;
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

@ZenClass("mods.forday.Macerator")
public class MaceratorRecipeHandler {
	
	@ZenMethod
    public static void add(IIngredient input, IIngredient output, int minRoughness, int maxRoughness, int burnTime, boolean isNEI) {
        ItemStack resultToAdd = MineTweakerMC.getItemStack(output);
        ItemStack input1ToAdd = MineTweakerMC.getItemStack(input);
        
        MineTweakerAPI.apply(new AddAction(input1ToAdd, resultToAdd, minRoughness, maxRoughness, burnTime, isNEI));
    }

    @ZenMethod
    public static void remove(IItemStack result) {
        ItemStack resultToRemove = MineTweakerMC.getItemStack(result);
        MineTweakerAPI.apply(new RemoveAction(resultToRemove));

    }

    private static class AddAction implements IUndoableAction {

        private final MaceratorRecipe recipe;
        
        public AddAction(ItemStack input, ItemStack output, int minRoughness, int maxRoughness, int burnTime, boolean isNEI) {
            recipe = new MaceratorRecipe(input, output, minRoughness, maxRoughness, burnTime, isNEI);
        }

        @Override
        public void apply() {
        	MaceratorRecipeManager.addRecipe(recipe);
        }

        @Override
        public boolean canUndo() {
            return true;
        }

        @Override
        public void undo() {
        	MaceratorRecipeManager.removeRecipe(recipe);
        }

        @Override
        public String describe() {
            return "Adding Macerator Recipe";
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
        private List<MaceratorRecipe> removedRecipes = new ArrayList<MaceratorRecipe>();

        public RemoveAction(ItemStack result) {
            this.result = result;
        }

        @Override
        public void apply() {
            removedRecipes = MaceratorRecipeManager.removeRecipes(result);
            
        }

        @Override
        public boolean canUndo() {
            return true;
        }

        @Override
        public void undo() {
        	MaceratorRecipeManager.addAllRecipes(removedRecipes);
        }

        @Override
        public String describe() {
            return "Removing all Macerator Recipe where '" + result.getDisplayName() + "' is the result.";
        }

        @Override
        public String describeUndo() {
            return "Adding back in all Macerator Recipe where '" + result.getDisplayName() + "' is the result.";
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }
    }
}
