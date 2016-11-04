package modularmachines.api.property;

import modularmachines.api.recipes.IRecipe;
import modularmachines.api.recipes.RecipeRegistry;
import net.minecraft.nbt.NBTTagCompound;

public class PropertyRecipe extends PropertyBase<IRecipe, NBTTagCompound, IPropertyProvider> {

	public PropertyRecipe(String name) {
		super(name, IRecipe.class, null);
	}

	@Override
	public NBTTagCompound writeToNBT(IPropertyProvider state, IRecipe value) {
		return RecipeRegistry.writeRecipeToNBT(value);
	}

	@Override
	public IRecipe readFromNBT(NBTTagCompound nbt, IPropertyProvider state) {
		return RecipeRegistry.readRecipeFromNBT(nbt);
	}
}