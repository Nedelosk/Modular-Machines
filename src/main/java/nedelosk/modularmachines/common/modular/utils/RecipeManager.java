package nedelosk.modularmachines.common.modular.utils;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.recipes.IRecipeManager;
import nedelosk.modularmachines.api.recipes.RecipeInput;
import nedelosk.modularmachines.api.recipes.RecipeItem;
import nedelosk.modularmachines.api.recipes.RecipeRegistry;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class RecipeManager implements IRecipeManager {

	private String recipeName;
	private RecipeInput[] inputs;
	private int speedModifier;
	private IModular modular;
	
	public RecipeManager(IModular modular, String recipeName, RecipeInput[] inputs) {
		this.inputs = inputs;
		this.recipeName = recipeName;
		this.modular = modular;

		this.speedModifier = RecipeRegistry.getRecipe(recipeName, inputs).getRequiredSpeedModifier();
	}
	
	public RecipeManager() {
	}

	@Override
	public RecipeItem[] getOutputs() {
		if (RecipeRegistry.getRecipe(recipeName, inputs) == null)
			return null;
		return RecipeRegistry.getRecipe(recipeName, inputs).getOutputs();
	}

	@Override
	public RecipeInput[] getInputs() {
		return inputs;
	}

	@Override
	public boolean removeEnergy() {
		return true;
	}

	@Override
	public int getSpeedModifier() {
		return speedModifier;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) throws Exception {
		nbt.setString("RecipeName", recipeName);
		nbt.setInteger("SpeedModifier", speedModifier);
		NBTTagList list = new NBTTagList();
		for (RecipeInput input : inputs) {
			NBTTagCompound nbtTag = new NBTTagCompound();
			input.writeToNBT(nbtTag);
			list.appendTag(nbtTag);
		}
		nbt.setTag("RecipeInput", list);
	}

	@Override
	public IRecipeManager readFromNBT(NBTTagCompound nbt, IModular modular) throws Exception {
		String recipeName = nbt.getString("RecipeName");
		int energyModifier = nbt.getInteger("EnergyModifier");
		int speedModifier = nbt.getInteger("SpeedModifier");
		NBTTagList list = nbt.getTagList("RecipeInput", 10);
		RecipeInput[] inputs = new RecipeInput[list.tagCount()];
		for (int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound nbtTag = list.getCompoundTagAt(i);
			inputs[i] = RecipeInput.readFromNBT(nbtTag);
		}
		if (RecipeRegistry.getRecipe(recipeName, inputs) == null)
			return null;
		return new RecipeManager(modular, recipeName, inputs);
	}
}
