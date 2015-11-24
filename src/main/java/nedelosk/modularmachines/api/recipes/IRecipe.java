package nedelosk.modularmachines.api.recipes;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.world.World;

public interface IRecipe {

	Object[] getModifiers();

	Object getModifier(int modifierID);

	RecipeItem[] getInputs();

	RecipeItem[] getOutputs();

	int getRequiredSpeedModifier();
	
    boolean matches(Object[] craftingModifiers);

	int getRequiredMaterial();

	String getRecipeName();

}
