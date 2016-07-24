package de.nedelosk.modularmachines.api.modules.handlers;

import java.util.EnumMap;
import java.util.List;

import javax.annotation.Nonnull;

import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.recipes.RecipeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

public interface IModuleContentHandlerAdvanced<C,M extends IModule> extends IModuleContentHandler {

	/**
	 * @return The insert filters of the handler.
	 */
	@Nonnull
	IContentFilter<C, M> getInsertFilter();

	/**
	 * @return The extract filters of the handler.
	 */
	@Nonnull
	IContentFilter<C, M> getExtractFilter();

	@Nonnull
	EnumMap<EnumFacing, boolean[]> getConfigurations();

	@Nonnull
	ContentInfo getInfo(int index);

	@Nonnull
	ContentInfo[] getContentInfos();

	boolean isInput(int index);

	int getInputs();

	int getOutputs();

	RecipeItem[] getInputItems();

	void removeRecipeInputs(int chance, RecipeItem[] inputs);

	boolean canRemoveRecipeInputs(int chance, RecipeItem[] inputs);

	void addRecipeOutputs(int chance, RecipeItem[] outputs);

	boolean canAddRecipeOutputs(int chance, RecipeItem[] outputs);

	@Nonnull
	List<ItemStack> getDrops();
}
