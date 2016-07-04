package de.nedelosk.modularmachines.api.recipes;

import java.util.Map;

import de.nedelosk.modularmachines.api.property.IProperty;
import de.nedelosk.modularmachines.api.property.IPropertyProvider;
import net.minecraft.nbt.NBTBase;

public interface IRecipe extends IPropertyProvider {
	
	RecipeItem[] getInputs();

	RecipeItem[] getOutputs();

	String getRecipeName();

	String getRecipeCategory();

	/*Object[] getModifiers();

	int getRequiredSpeedModifier();

	int getRequiredMaterial();*/
}
