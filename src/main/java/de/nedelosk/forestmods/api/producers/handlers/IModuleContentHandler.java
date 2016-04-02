package de.nedelosk.forestmods.api.producers.handlers;

import de.nedelosk.forestmods.api.recipes.RecipeItem;
import net.minecraft.nbt.NBTTagCompound;

public interface IModuleContentHandler<C> extends IModuleHandler {

	IContentFilter<C> getInsertFilter();

	IContentFilter<C> getExtractFilter();

	int getInputs();

	int getOutputs();

	RecipeItem[] getInputItems();

	void removeRecipeInputs(RecipeItem[] inputs);

	void addRecipeOutputs(RecipeItem[] outputs);

	boolean canRemoveRecipeInputs(RecipeItem[] inputs);

	boolean canAddRecipeOutputs(RecipeItem[] outputs);

	void readFromNBT(NBTTagCompound nbt);

	void writeToNBT(NBTTagCompound nbt);
}
