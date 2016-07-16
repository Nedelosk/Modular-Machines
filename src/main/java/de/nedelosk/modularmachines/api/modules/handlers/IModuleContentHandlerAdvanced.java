package de.nedelosk.modularmachines.api.modules.handlers;

import java.util.EnumMap;

import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.recipes.RecipeItem;
import net.minecraft.util.EnumFacing;

public interface IModuleContentHandlerAdvanced<C,M extends IModule> extends IModuleContentHandler {

	/**
	 * @return The insert filters of the handler.
	 */
	IContentFilter<C, M> getInsertFilter();

	/**
	 * @return The extract filters of the handler.
	 */
	IContentFilter<C, M> getExtractFilter();

	EnumMap<EnumFacing, boolean[]> getConfigurations();

	ContentInfo getInfo(int index);

	ContentInfo[] getContentInfos();

	boolean isInput(int index);

	int getInputs();

	int getOutputs();

	RecipeItem[] getInputItems();

	void removeRecipeInputs(int chance, RecipeItem[] inputs);

	boolean canRemoveRecipeInputs(int chance, RecipeItem[] inputs);

	void addRecipeOutputs(int chance, RecipeItem[] outputs);

	boolean canAddRecipeOutputs(int chance, RecipeItem[] outputs);
}
