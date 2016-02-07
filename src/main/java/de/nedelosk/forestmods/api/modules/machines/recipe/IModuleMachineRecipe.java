package de.nedelosk.forestmods.api.modules.machines.recipe;

import com.google.gson.JsonObject;

import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modules.machines.IModuleMachine;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import net.minecraft.nbt.NBTTagCompound;

public interface IModuleMachineRecipe extends IModuleMachine {

	Object[] getCraftingModifiers(IModular modular, ModuleStack stack);

	void writeCraftingModifiers(NBTTagCompound nbt, Object[] craftingModifiers);

	Object[] readCraftingModifiers(NBTTagCompound nbt);

	Object[] parseCraftingModifiers(JsonObject object);

	JsonObject writeCraftingModifiers(Object[] objects);

	String getRecipeCategory(ModuleStack stack);
}
