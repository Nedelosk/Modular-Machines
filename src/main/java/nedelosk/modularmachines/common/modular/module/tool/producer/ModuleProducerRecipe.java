package nedelosk.modularmachines.common.modular.module.tool.producer;

import java.util.ArrayList;

import org.lwjgl.opengl.AMDDebugOutput;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.module.IModuleProducer;
import nedelosk.modularmachines.api.modular.module.IModuleProducerRecipe;
import nedelosk.modularmachines.api.modular.module.Module;
import nedelosk.modularmachines.api.modular.module.recipes.IRecipe;
import nedelosk.modularmachines.api.modular.module.recipes.IRecipeManager;
import nedelosk.modularmachines.api.modular.module.recipes.RecipeInput;
import nedelosk.modularmachines.api.modular.module.recipes.RecipeItem;
import nedelosk.modularmachines.api.modular.module.recipes.RecipeRegistry;
import nedelosk.modularmachines.common.blocks.tile.TileModularMachine;
import nedelosk.modularmachines.common.modular.ModularMachine;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class ModuleProducerRecipe extends ModuleProducer implements IModuleProducerRecipe {
	
	public IRecipeManager manager;
	public int outputs;
	public int inputs;
	
	public ModuleProducerRecipe(NBTTagCompound nbt) {
		super(nbt);
	}
	
	public ModuleProducerRecipe(String modifier, int inputs, int outputs) {
		super(modifier);
		this.inputs = inputs;
		this.outputs = outputs;
	}

	@Override
	public int getBurnTimeTotal(IModular modular)
	{
		int burnTimeTotal = modular.getEngine().getSpeedModifier() * getSpeedModifier() / 10;
		int burnTimeTotal2 = burnTimeTotal + (burnTimeTotal * modular.getEnergyManger().getSpeedModifier() / 100);
		return burnTimeTotal2;
	}
	
	public int getBurnTimeTotal(IModular modular, int speedModifier)
	{
		int burnTimeTotal = modular.getEngine().getSpeedModifier() * getSpeedModifier() / 10;
		int burnTimeTotal2 = burnTimeTotal + (burnTimeTotal * modular.getEnergyManger().getSpeedModifier() / 100);
		return burnTimeTotal2 + (burnTimeTotal2 * speedModifier / 100);
	}
	
	public IRecipeManager getRecipeManager() {
		return manager;
	}
	
	public RecipeInput[] getInputItems(IModular modular)
	{
		TileModularMachine tile = ((TileModularMachine)modular.getMachine());
		RecipeInput[] inputs = new RecipeInput[this.inputs];
		for(int i = 0;i < inputs.length;i++)
		{
			ItemStack stack = tile.getStackInSlot(getName(), i);
			inputs[i] = new RecipeInput(i, stack);
		}
		return inputs;
	}
	
	public boolean removeInput(IModular modular){
		TileModularMachine tile = ((TileModularMachine)modular.getMachine());
		for(int i = 0;i < getInputs(modular).length;i++)
		{
			RecipeInput input = getInputs(modular)[i];
			if(input != null)
				if(!input.isFluid())
				{
					tile.decrStackSize(getName(), input.slotIndex, RecipeRegistry.getRecipe(getRecipeName(), getInputs(modular)).getInputs()[i].item.stackSize);
					continue;
				}
				else
				{
					tile.machine.fluidHandler.drain(ForgeDirection.UNKNOWN, input.fluid, true);
					continue;
				}
			else
				return false;		
		}
		return true;
	}
	
	@Override
	public void update(IModular modular) {
		super.update(modular);
		TileModularMachine tile = ((TileModularMachine)modular.getMachine());
		if(tile.getEnergyStored(null) > 0)
		{
		if(burnTime >= burnTimeTotal || burnTimeTotal == 0)
		{
			if(manager != null)
			{
				if(addOutput(modular))
					manager = null;
			}
			else if(getInputs(modular) != null && RecipeRegistry.getRecipe(getRecipeName(), getInputs(modular)) != null)
			{
				IRecipe recipe = RecipeRegistry.getRecipe(getRecipeName(), getInputs(modular));
				manager = new RecipeManager(modular, recipe.getRecipeName(), recipe.getRequiredEnergy() / getBurnTimeTotal(modular, RecipeRegistry.getRecipe(getRecipeName(), getInputs(modular)).getRequiredSpeedModifier()), getInputs(modular));
				if(!removeInput(modular))
				{
					manager = null;
					return;
				}
				burnTimeTotal = getBurnTimeTotal(modular);
				burnTime = 0;
			}
			if(timer > timerTotal)
			{
			modular.getMachine().getWorldObj().markBlockForUpdate(modular.getMachine().getXCoord(), modular.getMachine().getYCoord(), modular.getMachine().getZCoord());
			timer = 0;
			}
			else
			{
				timer++;
			}
		}
		else
		{
			if(timer > timerTotal)
			{
				modular.getMachine().getWorldObj().markBlockForUpdate(modular.getMachine().getXCoord(), modular.getMachine().getYCoord(), modular.getMachine().getZCoord());
				timer = 0;
			}
			else
			{
				timer++;
				
				if(manager != null)
					if(manager.removeEnergy())
						burnTime++;
			}
		}
		}
	}
	
	public boolean addOutput(IModular modular)
	{
		TileModularMachine tile = ((TileModularMachine)modular.getMachine());
		for(RecipeItem item : manager.getOutputs())
		{
			if(item != null)
			{
			if(!item.isFluid())
				if(tile.addToOutput(item.item.copy(), this.inputs, this.inputs + this.outputs, getName()))
				{
					item = null;
					continue;
				}
				else
					return false;
			else
				if(tile.machine.fluidHandler.fill(ForgeDirection.UNKNOWN, item.fluid.copy(), true) != 0)
				{
					item = null;
					continue;
				}
				else
					return false;
			}
		}
		return true;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		if(manager != null)
		{
			NBTTagCompound nbtTag = new NBTTagCompound();
			manager.writeToNBT(nbtTag);
			nbt.setTag("Manager", nbtTag);
		}
		
		nbt.setInteger("Inputs", inputs);
		nbt.setInteger("Outputs", outputs);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular) {
		super.readFromNBT(nbt, modular);
		
		if(nbt.hasKey("Manager"))
			manager = RecipeManager.loadManagerFromNBT(nbt.getCompoundTag("Manager"), modular);
		
		inputs = nbt.getInteger("Inputs");
		outputs = nbt.getInteger("Outputs");
	}

}
