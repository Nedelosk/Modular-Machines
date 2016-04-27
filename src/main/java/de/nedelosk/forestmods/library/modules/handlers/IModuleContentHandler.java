package de.nedelosk.forestmods.library.modules.handlers;

import de.nedelosk.forestmods.library.modules.IModule;
import de.nedelosk.forestmods.library.recipes.RecipeItem;
import net.minecraft.nbt.NBTTagCompound;

public interface IModuleContentHandler<C,M extends IModule> extends IModuleHandler {

	IContentFilter<C, M> getInsertFilter();

	IContentFilter<C, M> getExtractFilter();

	int getInputs();

	int getOutputs();

	RecipeItem[] getInputItems();

	void removeRecipeInputs(int chance, RecipeItem[] inputs);

	void addRecipeOutputs(int chance, RecipeItem[] outputs);

	boolean canRemoveRecipeInputs(int chance, RecipeItem[] inputs);

	boolean canAddRecipeOutputs(int chance, RecipeItem[] outputs);

	void readFromNBT(NBTTagCompound nbt);

	void writeToNBT(NBTTagCompound nbt);
}
