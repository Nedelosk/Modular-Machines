package nedelosk.modularmachines.common.modular.utils;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.producers.machines.recipe.IProducerMachineRecipe;
import nedelosk.modularmachines.api.recipes.IRecipeManager;
import nedelosk.modularmachines.api.recipes.RecipeInput;
import nedelosk.modularmachines.api.recipes.RecipeItem;
import nedelosk.modularmachines.api.recipes.RecipeRegistry;
import nedelosk.modularmachines.api.utils.ModuleUtils;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public abstract class RecipeManager implements IRecipeManager {

	protected String recipeName;
	protected int materialModifier;
	protected RecipeInput[] inputs;
	protected int speedModifier;
	protected IModular modular;
	protected Object[] craftingModifiers;

	public RecipeManager() {
	}
	
	public RecipeManager(IModular modular, String recipeName, RecipeInput[] inputs) {
		this(modular, recipeName, 0, inputs);
	}

	public RecipeManager(IModular modular, String recipeName, int materialModifier, RecipeInput[] inputs, Object... craftingModifiers) {
		this.inputs = inputs;
		this.recipeName = recipeName;
		if (materialModifier == 0)
			materialModifier = 1;
		this.materialModifier = materialModifier;
		this.modular = modular;
		this.craftingModifiers = craftingModifiers;

		this.speedModifier = RecipeRegistry.getRecipe(recipeName, inputs, craftingModifiers).getRequiredSpeedModifier();
	}

	@Override
	public RecipeItem[] getOutputs() {
		if (RecipeRegistry.getRecipe(recipeName, inputs, craftingModifiers) == null)
			return null;
		return RecipeRegistry.getRecipe(recipeName, inputs, craftingModifiers).getOutputs();
	}

	@Override
	public RecipeInput[] getInputs() {
		return inputs;
	}

	@Override
	public int getSpeedModifier() {
		return speedModifier;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt, IModular modular) throws Exception {
		nbt.setString("RecipeName", recipeName);
		nbt.setInteger("MaterialModifier", materialModifier);
		nbt.setInteger("SpeedModifier", speedModifier);
		IProducerMachineRecipe machine = (IProducerMachineRecipe) ModuleUtils.getModuleStackMachine(modular).getProducer();
		machine.writeCraftingModifiers(nbt, modular, craftingModifiers);
		
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
		int materialModifier = nbt.getInteger("MaterialModifier");
		int speedModifier = nbt.getInteger("SpeedModifier");
		NBTTagList list = nbt.getTagList("RecipeInput", 10);
		RecipeInput[] inputs = new RecipeInput[list.tagCount()];
		for (int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound nbtTag = list.getCompoundTagAt(i);
			inputs[i] = RecipeInput.readFromNBT(nbtTag);
		}
		IProducerMachineRecipe machine = (IProducerMachineRecipe) ModuleUtils.getModuleStackMachine(modular).getProducer();
		Object[] craftingModifiers = machine.readCraftingModifiers(nbt, modular);
		if (RecipeRegistry.getRecipe(recipeName, inputs, craftingModifiers) == null)
			return null;
		return createManager(modular, recipeName, speedModifier, materialModifier, inputs, craftingModifiers);
	}
	
	public abstract IRecipeManager createManager(IModular modular, String recipeName, int speedModifier, int materialModifier, RecipeInput[] inputs, Object... craftingModifiers);
}
