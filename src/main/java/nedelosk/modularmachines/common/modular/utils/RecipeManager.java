package nedelosk.modularmachines.common.modular.utils;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.recipes.IRecipeManager;
import nedelosk.modularmachines.api.recipes.RecipeInput;
import nedelosk.modularmachines.api.recipes.RecipeItem;
import nedelosk.modularmachines.api.recipes.RecipeRegistry;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.ForgeDirection;

public class RecipeManager implements IRecipeManager {

	public String recipeName;
	public int energyModifier;
	public RecipeInput[] inputs;
	public int speedModifier;
	public IModular modular;
	
	public RecipeManager(IModular modular, String recipeName, RecipeInput[] inputs) {
		this(modular, recipeName, 0, inputs);	
	}
	
	private RecipeManager() {	
	}
	
	public RecipeManager(IModular modular, String recipeName, int energyModifier, RecipeInput[] inputs) {
		this.inputs = inputs;
		this.recipeName = recipeName;
		this.energyModifier = energyModifier;
		this.modular = modular;
		
		this.speedModifier = RecipeRegistry.getRecipe(recipeName, inputs).getRequiredSpeedModifier();
	}
	
	@Override
	public RecipeItem[] getOutputs()
	{
		if(RecipeRegistry.getRecipe(recipeName, inputs) == null)
			return null;
		return RecipeRegistry.getRecipe(recipeName, inputs).getOutputs();
	}
	
	@Override
	public RecipeInput[] getInputs() {
		return inputs;
	}
	
	@Override
	public boolean removeEnergy()
	{
		if(modular.getManager().getEnergyHandler().extractEnergy(ForgeDirection.UNKNOWN, energyModifier, false) > 0)
		{
			return true;
		}
		else
			return  false;
	}
	
	@Override
	public int getSpeedModifier() {
		return speedModifier;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		nbt.setString("RecipeName", recipeName);
		nbt.setInteger("EnergyModifier", energyModifier);
		nbt.setInteger("SpeedModifier", speedModifier);
		NBTTagList list = new NBTTagList();
		for(RecipeInput input : inputs)
		{
			NBTTagCompound nbtTag = new NBTTagCompound();
			input.writeToNBT(nbtTag);
			list.appendTag(nbtTag);
		}
		nbt.setTag("RecipeInput", list);
	}
	
	@Override
	public IRecipeManager readFromNBT(NBTTagCompound nbt, IModular modular)
	{
		String recipeName = nbt.getString("RecipeName");
		int energyModifier = nbt.getInteger("EnergyModifier");
		int speedModifier = nbt.getInteger("SpeedModifier");
		NBTTagList list = nbt.getTagList("RecipeInput", 10);
		RecipeInput[] inputs = new RecipeInput[list.tagCount()];
		for(int i = 0;i < list.tagCount();i++)
		{
			NBTTagCompound nbtTag = list.getCompoundTagAt(i);
			inputs[i] = RecipeInput.readFromNBT(nbtTag);
		}
		return new RecipeManager(modular, recipeName, energyModifier, inputs);
	}
	
	public static IRecipeManager loadManagerFromNBT(NBTTagCompound nbt, IModular modular)
	{
		IRecipeManager manager = new RecipeManager();
		manager.readFromNBT(nbt, modular);
		return manager;
	}
}
