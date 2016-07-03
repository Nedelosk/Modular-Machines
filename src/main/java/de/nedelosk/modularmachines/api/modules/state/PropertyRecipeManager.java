package de.nedelosk.modularmachines.api.modules.state;

import de.nedelosk.modularmachines.api.modules.IRecipeManager;
import de.nedelosk.modularmachines.api.property.PropertyBase;
import de.nedelosk.modularmachines.common.modules.tools.RecipeManager;
import net.minecraft.nbt.NBTTagCompound;

public class PropertyRecipeManager extends PropertyBase<IRecipeManager, NBTTagCompound, IModuleState> {

	public PropertyRecipeManager(String name, IRecipeManager defaultValue) {
		super(name, IRecipeManager.class, defaultValue);
	}

	@Override
	public NBTTagCompound writeToNBT(IModuleState state, IRecipeManager value) {
		NBTTagCompound nbtTag = new NBTTagCompound();
		value.writeToNBT(nbtTag, state);
		return nbtTag;
	}

	@Override
	public IRecipeManager readFromNBT(NBTTagCompound nbt, IModuleState state) {
		IRecipeManager recipeManager = new RecipeManager();
		recipeManager.readFromNBT(nbt, state);
		return recipeManager;
	}

}
