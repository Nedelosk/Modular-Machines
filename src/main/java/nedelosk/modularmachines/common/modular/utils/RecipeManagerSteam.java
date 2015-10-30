package nedelosk.modularmachines.common.modular.utils;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.recipes.IRecipeManager;
import nedelosk.modularmachines.api.recipes.RecipeInput;
import nedelosk.modularmachines.api.recipes.RecipeItem;
import nedelosk.modularmachines.api.recipes.RecipeRegistry;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

public class RecipeManagerSteam implements IRecipeManager {

	private String recipeName;
	public int steamModifier;
	private RecipeInput[] inputs;
	private int speedModifier;
	private IModular modular;
	
	public RecipeManagerSteam(IModular modular, String recipeName, int steamModifier, RecipeInput[] inputs) {
		this.inputs = inputs;
		this.recipeName = recipeName;
		this.modular = modular;
		if (steamModifier == 0)
			steamModifier = 1;
		this.steamModifier = steamModifier;

		this.speedModifier = RecipeRegistry.getRecipe(recipeName, inputs).getRequiredSpeedModifier();
	}
	
	public RecipeManagerSteam() {
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
	public boolean removeMaterial() {
		if (modular == null || modular.getManager() == null || modular.getManager().getFluidHandler() == null)
			return false;
		FluidStack drain = modular.getManager().getFluidHandler().drain(ForgeDirection.UNKNOWN, steamModifier, true);
		if (drain.getFluid() != null && drain.amount > 0) {
			return true;
		} else
			return false;
	}

	@Override
	public int getSpeedModifier() {
		return speedModifier;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) throws Exception {
		nbt.setString("RecipeName", recipeName);
		nbt.setInteger("EnergyModifier", steamModifier);
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
		int steamModifier = nbt.getInteger("SteamModifier");
		int speedModifier = nbt.getInteger("SpeedModifier");
		NBTTagList list = nbt.getTagList("RecipeInput", 10);
		RecipeInput[] inputs = new RecipeInput[list.tagCount()];
		for (int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound nbtTag = list.getCompoundTagAt(i);
			inputs[i] = RecipeInput.readFromNBT(nbtTag);
		}
		if (RecipeRegistry.getRecipe(recipeName, inputs) == null)
			return null;
		return new RecipeManagerSteam(modular, recipeName, steamModifier, inputs);
	}
}
