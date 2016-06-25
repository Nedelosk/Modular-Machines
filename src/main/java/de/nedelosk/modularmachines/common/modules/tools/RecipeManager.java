package de.nedelosk.modularmachines.common.modules.tools;

import de.nedelosk.modularmachines.api.modules.IRecipeManager;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.recipes.IRecipeHandler;
import de.nedelosk.modularmachines.api.recipes.RecipeItem;
import de.nedelosk.modularmachines.api.recipes.RecipeRegistry;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class RecipeManager implements IRecipeManager {

	protected String recipeCategory;
	protected int materialToRemove;
	protected RecipeItem[] inputs;
	protected int speedModifier;
	protected Object[] craftingModifiers;

	public RecipeManager() {
	}

	public RecipeManager(String recipeCategory, int materialToRemove, RecipeItem[] inputs, Object... craftingModifiers) {
		this.inputs = inputs;
		this.recipeCategory = recipeCategory;
		if (materialToRemove == 0) {
			materialToRemove = 1;
		}
		this.materialToRemove = materialToRemove;
		this.craftingModifiers = craftingModifiers;
		this.speedModifier = RecipeRegistry.getRecipe(recipeCategory, inputs, craftingModifiers).getRequiredSpeedModifier();
	}

	@Override
	public RecipeItem[] getOutputs() {
		if (RecipeRegistry.getRecipe(recipeCategory, inputs, craftingModifiers) == null) {
			return null;
		}
		return RecipeRegistry.getRecipe(recipeCategory, inputs, craftingModifiers).getOutputs();
	}

	@Override
	public RecipeItem[] getInputs() {
		return inputs;
	}

	@Override
	public int getSpeedModifier() {
		return speedModifier;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt, IModuleState state) {
		nbt.setString("RecipeCategory", recipeCategory);
		nbt.setInteger("MaterialModifier", materialToRemove);
		nbt.setInteger("SpeedModifier", speedModifier);
		IRecipeHandler handler = RecipeRegistry.getRecipeHandler(recipeCategory);
		if (handler != null && handler.getNBTSerialize() != null) {
			handler.getNBTSerialize().serializeNBT(nbt, craftingModifiers);
		}
		NBTTagList list = new NBTTagList();
		for(RecipeItem input : inputs) {
			NBTTagCompound nbtTag = new NBTTagCompound();
			input.writeToNBT(nbtTag);
			list.appendTag(nbtTag);
		}
		nbt.setTag("RecipeInput", list);
	}

	@Override
	public IRecipeManager readFromNBT(NBTTagCompound nbt, IModuleState state) {
		String recipeCategory = nbt.getString("RecipeCategory");
		int materialModifier = nbt.getInteger("MaterialModifier");
		int speedModifier = nbt.getInteger("SpeedModifier");
		NBTTagList list = nbt.getTagList("RecipeInput", 10);
		RecipeItem[] inputs = new RecipeItem[list.tagCount()];
		for(int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound nbtTag = list.getCompoundTagAt(i);
			inputs[i] = RecipeItem.loadFromNBT(nbtTag);
		}
		IRecipeHandler handler = RecipeRegistry.getRecipeHandler(recipeCategory);
		Object[] craftingModifiers = null;
		if (handler != null && handler.getNBTSerialize() != null) {
			craftingModifiers = handler.getNBTSerialize().deserializeNBT(nbt);
		}
		if (RecipeRegistry.getRecipe(recipeCategory, inputs, craftingModifiers) == null) {
			return null;
		}
		return new RecipeManager(recipeCategory, materialModifier, inputs, craftingModifiers);
	}

	@Override
	public int getMaterialToRemove() {
		return materialToRemove;
	}
}
