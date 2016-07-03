package de.nedelosk.modularmachines.api.modules.handlers;

import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.recipes.RecipeItem;
import net.minecraft.nbt.NBTTagCompound;

public interface IModuleContentHandler<C,M extends IModule> extends IModuleHandler {

	/**
	 * @return The insert filters of the handler.
	 */
	IContentFilter<C, M> getInsertFilter();

	/**
	 * @return The extract filters of the handler.
	 */
	IContentFilter<C, M> getExtractFilter();

	int getInputs();

	int getOutputs();

	RecipeItem[] getInputItems();

	void removeRecipeInputs(int chance, RecipeItem[] inputs);

	void addRecipeOutputs(int chance, RecipeItem[] outputs);

	boolean canRemoveRecipeInputs(int chance, RecipeItem[] inputs);

	boolean canAddRecipeOutputs(int chance, RecipeItem[] outputs);

	void readFromNBT(NBTTagCompound nbt);

	NBTTagCompound writeToNBT(NBTTagCompound nbt);

	Class<C> getContentClass();

	String getHandlerUID();
}
