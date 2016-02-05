package de.nedelosk.forestmods.api.modules.machines.recipe;

import com.google.gson.JsonObject;

import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modules.machines.IModuleMachine;
import de.nedelosk.forestmods.api.recipes.RecipeItem;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import net.minecraft.nbt.NBTTagCompound;

public interface IModuleMachineRecipe extends IModuleMachine {

	boolean addOutput(IModular modular, ModuleStack stack);

	boolean removeInput(IModular modular, ModuleStack stack);

	Object[] getCraftingModifiers(IModular modular, ModuleStack stack);

	void writeCraftingModifiers(NBTTagCompound nbt, Object[] craftingModifiers);

	Object[] readCraftingModifiers(NBTTagCompound nbt);

	Object[] parseCraftingModifiers(JsonObject object);

	JsonObject writeCraftingModifiers(Object[] objects);

	String getRecipeCategory(ModuleStack stack);

	RecipeItem[] getInputs(IModular modular, ModuleStack stack);

	int getItemInputs(ModuleStack stack);

	int getItemOutputs(ModuleStack stack);
}
