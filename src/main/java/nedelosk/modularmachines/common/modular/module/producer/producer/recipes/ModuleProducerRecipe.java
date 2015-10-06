package nedelosk.modularmachines.common.modular.module.producer.producer.recipes;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.machines.basic.IModularInventory;
import nedelosk.modularmachines.api.modular.machines.basic.IModularTileEntity;
import nedelosk.modularmachines.api.modular.module.basic.energy.IModuleEngine;
import nedelosk.modularmachines.api.modular.module.producer.producer.recipe.IModuleProducerRecipe;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.modularmachines.api.recipes.IRecipe;
import nedelosk.modularmachines.api.recipes.IRecipeManager;
import nedelosk.modularmachines.api.recipes.RecipeInput;
import nedelosk.modularmachines.api.recipes.RecipeItem;
import nedelosk.modularmachines.api.recipes.RecipeRegistry;
import nedelosk.modularmachines.common.modular.utils.ModularUtils;
import nedelosk.modularmachines.common.modular.utils.RecipeManager;
import net.minecraft.item.ItemStack;
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
		ModuleStack<IModuleEngine> engine = ModularUtils.getModuleStackEngine(modular);
		int burnTimeTotal = engine.getModule().getSpeedModifier(engine.getTier()) * getSpeedModifier() / 10;
		int burnTimeTotal2 = burnTimeTotal + (burnTimeTotal * ModularUtils.getModuleBattery(modular).getSpeedModifier() / 100);
		return burnTimeTotal2;
	}
	
	public int getBurnTimeTotal(IModular modular, int speedModifier)
	{
		ModuleStack<IModuleEngine> engine = ModularUtils.getModuleStackEngine(modular);
		int burnTimeTotal = engine.getModule().getSpeedModifier(engine.getTier()) * getSpeedModifier() / 10;
		int burnTimeTotal2 = burnTimeTotal + (burnTimeTotal * ModularUtils.getModuleBattery(modular).getSpeedModifier() / 100);
		return burnTimeTotal2 + (burnTimeTotal2 * speedModifier / 100);
	}
	
	@Override
	public IRecipeManager getRecipeManager() {
		return manager;
	}
	
	public RecipeInput[] getInputItems(IModular modular)
	{
		IModularTileEntity<IModularInventory> tile = modular.getMachine();
		RecipeInput[] inputs = new RecipeInput[this.inputs];
		for(int i = 0;i < inputs.length;i++)
		{
			ItemStack stack = tile.getModular().getInventoryManager().getStackInSlot(getName(), i);
			inputs[i] = new RecipeInput(i, stack);
		}
		return inputs;
	}
	
	@Override
	public boolean removeInput(IModular modular){
		IModularTileEntity<IModularInventory> tile = modular.getMachine();
		for(int i = 0;i < getInputs(modular).length;i++)
		{
			RecipeInput input = getInputs(modular)[i];
			if(input != null)
				if(!input.isFluid())
				{
					tile.getModular().getInventoryManager().decrStackSize(getName(), input.slotIndex, RecipeRegistry.getRecipe(getRecipeName(), getInputs(modular)).getInputs()[i].item.stackSize);
					continue;
				}
				else
				{
					tile.getModular().getManager().getFluidHandler().drain(ForgeDirection.UNKNOWN, input.fluid, true);
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
		IModularTileEntity<IModularInventory> tile = modular.getMachine();
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
				burnTimeTotal = getBurnTimeTotal(modular, RecipeRegistry.getRecipe(getRecipeName(), getInputs(modular)).getRequiredSpeedModifier()) / ModularUtils.getModuleStackProducer(modular).getTier();
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
	
	@Override
	public boolean addOutput(IModular modular)
	{
		IModularTileEntity<IModularInventory> tile = modular.getMachine();
		for(RecipeItem item : manager.getOutputs())
		{
			if(item != null)
			{
				if(!item.isFluid())
					if(tile.getModular().getInventoryManager().addToOutput(item.item.copy(), this.inputs, this.inputs + this.outputs, getName()))
					{
						item = null;
						continue;
					}
					else
						return false;
				else
					if(tile.getModular().getManager().getFluidHandler().fill(ForgeDirection.UNKNOWN, item.fluid.copy(), true) != 0)
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
