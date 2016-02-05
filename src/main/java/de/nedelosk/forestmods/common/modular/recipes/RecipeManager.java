package de.nedelosk.forestmods.common.modular.recipes;

import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.recipes.IRecipeHandler;
import de.nedelosk.forestmods.api.recipes.IRecipeManager;
import de.nedelosk.forestmods.api.recipes.RecipeItem;
import de.nedelosk.forestmods.api.recipes.RecipeRegistry;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public abstract class RecipeManager implements IRecipeManager {

	protected String recipeName;
	protected int materialModifier;
	protected RecipeItem[] inputs;
	protected int speedModifier;
	protected IModular modular;
	protected Object[] craftingModifiers;

	public RecipeManager() {
	}

	public RecipeManager(IModular modular, String recipeName, RecipeItem[] inputs) {
		this(modular, recipeName, 0, inputs);
	}

	public RecipeManager(IModular modular, String recipeName, int materialModifier, RecipeItem[] inputs, Object... craftingModifiers) {
		this.inputs = inputs;
		this.recipeName = recipeName;
		if (materialModifier == 0) {
			materialModifier = 1;
		}
		this.materialModifier = materialModifier;
		this.modular = modular;
		this.craftingModifiers = craftingModifiers;
		this.speedModifier = RecipeRegistry.getRecipe(recipeName, inputs, craftingModifiers).getRequiredSpeedModifier();
	}

	@Override
	public RecipeItem[] getOutputs() {
		if (RecipeRegistry.getRecipe(recipeName, inputs, craftingModifiers) == null) {
			return null;
		}
		return RecipeRegistry.getRecipe(recipeName, inputs, craftingModifiers).getOutputs();
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
	public void writeToNBT(NBTTagCompound nbt, IModular modular) {
		nbt.setString("RecipeName", recipeName);
		nbt.setInteger("MaterialModifier", materialModifier);
		nbt.setInteger("SpeedModifier", speedModifier);
		IRecipeHandler handler = RecipeRegistry.getRecipeHandler(recipeName);
		if (handler != null) {
			handler.writeCraftingModifiers(nbt, craftingModifiers);
		}
		NBTTagList list = new NBTTagList();
		for ( RecipeItem input : inputs ) {
			NBTTagCompound nbtTag = new NBTTagCompound();
			input.writeToNBT(nbtTag);
			list.appendTag(nbtTag);
		}
		nbt.setTag("RecipeInput", list);
	}

	@Override
	public IRecipeManager readFromNBT(NBTTagCompound nbt, IModular modular) {
		String recipeName = nbt.getString("RecipeName");
		int materialModifier = nbt.getInteger("MaterialModifier");
		int speedModifier = nbt.getInteger("SpeedModifier");
		NBTTagList list = nbt.getTagList("RecipeInput", 10);
		RecipeItem[] inputs = new RecipeItem[list.tagCount()];
		for ( int i = 0; i < list.tagCount(); i++ ) {
			NBTTagCompound nbtTag = list.getCompoundTagAt(i);
			inputs[i] = RecipeItem.readFromNBT(nbtTag);
		}
		IRecipeHandler handler = RecipeRegistry.getRecipeHandler(recipeName);
		Object[] craftingModifiers = null;
		if (handler != null) {
			craftingModifiers = handler.readCraftingModifiers(nbt);
		}
		if (RecipeRegistry.getRecipe(recipeName, inputs, craftingModifiers) == null) {
			return null;
		}
		return createManager(modular, recipeName, speedModifier, materialModifier, inputs, craftingModifiers);
	}

	public abstract IRecipeManager createManager(IModular modular, String recipeName, int speedModifier, int materialModifier, RecipeItem[] inputs,
			Object... craftingModifiers);
}
