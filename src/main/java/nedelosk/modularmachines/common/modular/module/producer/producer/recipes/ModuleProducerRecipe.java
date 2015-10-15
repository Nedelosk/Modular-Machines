package nedelosk.modularmachines.common.modular.module.producer.producer.recipes;

import java.util.ArrayList;

import codechicken.nei.recipe.GuiCraftingRecipe;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.machines.basic.IModularInventory;
import nedelosk.modularmachines.api.modular.machines.basic.IModularTileEntity;
import nedelosk.modularmachines.api.modular.module.basic.IModule;
import nedelosk.modularmachines.api.modular.module.basic.energy.IModuleEngine;
import nedelosk.modularmachines.api.modular.module.producer.producer.recipe.IModuleProducerRecipe;
import nedelosk.modularmachines.api.modular.utils.ModularUtils;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.modularmachines.api.recipes.IRecipe;
import nedelosk.modularmachines.api.recipes.IRecipeManager;
import nedelosk.modularmachines.api.recipes.RecipeInput;
import nedelosk.modularmachines.api.recipes.RecipeItem;
import nedelosk.modularmachines.api.recipes.RecipeRegistry;
import nedelosk.modularmachines.client.gui.widget.WidgetProgressBar;
import nedelosk.modularmachines.common.modular.utils.RecipeManager;
import nedelosk.nedeloskcore.api.machines.IGuiBase;
import nedelosk.nedeloskcore.api.machines.Widget;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class ModuleProducerRecipe extends ModuleProducer implements IModuleProducerRecipe {
	
	public IRecipeManager manager;
	public int outputs;
	public int inputs;
	protected int speed;
	
	public ModuleProducerRecipe(NBTTagCompound nbt) {
		super(nbt);
	}
	
	public ModuleProducerRecipe(String modifier, int inputs, int outputs) {
		super(modifier);
		this.inputs = inputs;
		this.outputs = outputs;
	}
	
	public ModuleProducerRecipe(String modifier, int inputs, int outputs, int speed) {
		super(modifier);
		this.inputs = inputs;
		this.outputs = outputs;
		this.speed = speed;
	}

	@Override
	public int getBurnTimeTotal(IModular modular)
	{
		ModuleStack<IModuleEngine> engine = ModularUtils.getModuleStackEngine(modular);
		int burnTimeTotal = engine.getModule().getSpeedModifier(engine.getTier().getStage()) * getSpeed() / 10;
		int burnTimeTotal2 = burnTimeTotal + (burnTimeTotal * ModularUtils.getModuleBattery(modular).getSpeedModifier() / 100);
		return burnTimeTotal2;
	}
	
	public int getBurnTimeTotal(IModular modular, int speedModifier)
	{
		ModuleStack<IModuleEngine> engine = ModularUtils.getModuleStackEngine(modular);
		int burnTimeTotal = engine.getModule().getSpeedModifier(engine.getTier().getStage()) * getSpeed() / 10;
		int burnTimeTotal2 = burnTimeTotal + (burnTimeTotal * ModularUtils.getModuleBattery(modular).getSpeedModifier() / 100);
		return burnTimeTotal2 + (burnTimeTotal2 * speedModifier / 100);
	}
	
	@Override
	public IRecipeManager getRecipeManager() {
		return manager;
	}
	
	public RecipeInput[] getInputItems(IModular modular, ModuleStack moduleStack)
	{
		IModularTileEntity<IModularInventory> tile = modular.getMachine();
		RecipeInput[] inputs = new RecipeInput[this.inputs];
		for(int i = 0;i < inputs.length;i++)
		{
			ItemStack stack = tile.getModular().getInventoryManager().getStackInSlot(getName(moduleStack), i);
			inputs[i] = new RecipeInput(i, stack);
		}
		return inputs;
	}
	
	@Override
	public boolean removeInput(IModular modular, ModuleStack stack){
		IModularTileEntity<IModularInventory> tile = modular.getMachine();
		IRecipe recipe = RecipeRegistry.getRecipe(getRecipeName(), getInputs(modular, stack));
		for(int i = 0;i < getInputs(modular, stack).length;i++)
		{
			RecipeInput input = getInputs(modular, stack)[i];
			RecipeItem[] inputs = recipe.getInputs();
			if(input != null){
				if(!inputs[i].isFluid())
				{
					if(inputs[i].isOre())
						tile.getModular().getInventoryManager().decrStackSize(getName(stack), input.slotIndex, inputs[i].ore.stackSize);
					else
						tile.getModular().getInventoryManager().decrStackSize(getName(stack), input.slotIndex, inputs[i].item.stackSize);
					continue;
				}
				else
				{
					tile.getModular().getManager().getFluidHandler().drain(ForgeDirection.UNKNOWN, input.fluid, true);
					continue;
				}
			}
			else
				return false;		
		}
		return true;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void handleMouseClicked(IModularTileEntity tile, Widget widget, int mouseX, int mouseY, int mouseButton) {
		if(widget instanceof WidgetProgressBar){
			if(Loader.isModLoaded("NotEnoughItems")){
				GuiCraftingRecipe.openRecipeGui("ModularMachines" + getRecipeName());
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void updateGui(IGuiBase base, int x, int y, IModular modular) {
		ArrayList<Widget> widgets = base.getWidgetManager().getWidgets();
		for(Widget widget : widgets){
			if(widget instanceof WidgetProgressBar){
				((WidgetProgressBar)widget).burntime = burnTime;
				((WidgetProgressBar)widget).burntimeTotal = burnTimeTotal;
			}
		}
	}
	
	@Override
	public void update(IModular modular, ModuleStack stack) {
		IModularTileEntity<IModularInventory> tile = modular.getMachine();
		if(tile.getEnergyStored(null) > 0)
		{
			if(burnTime >= burnTimeTotal || burnTimeTotal == 0)
			{
				IRecipe recipe = RecipeRegistry.getRecipe(getRecipeName(), getInputs(modular, stack));
				if(manager != null)
				{
					if(addOutput(modular, stack)){
						manager = null;
						burnTimeTotal = 0;
						burnTime = 0;
					}
				}
				else if(getInputs(modular, stack) != null && RecipeRegistry.getRecipe(getRecipeName(), getInputs(modular, stack)) != null)
				{
					if(ModularUtils.getModuleStackProducer(modular) == null)
						return;
					manager = new RecipeManager(modular, recipe.getRecipeName(), recipe.getRequiredEnergy() / getBurnTimeTotal(modular, RecipeRegistry.getRecipe(getRecipeName(), getInputs(modular, stack)).getRequiredSpeedModifier()), getInputs(modular, stack));
					if(!removeInput(modular, stack))
					{
						manager = null;
						return;
					}
					burnTimeTotal = getBurnTimeTotal(modular, recipe.getRequiredSpeedModifier()) / ModularUtils.getModuleStackProducer(modular).getTier().getStage();
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
	public boolean addOutput(IModular modular, ModuleStack stack)
	{
		IModularTileEntity<IModularInventory> tile = modular.getMachine();
		if(manager.getOutputs() != null){
			for(RecipeItem item : manager.getOutputs())
			{
				if(item != null)
				{
					if(!item.isFluid())
						if(tile.getModular().getInventoryManager().addToOutput(item.item.copy(), this.inputs, this.inputs + this.outputs, getName(stack)))
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
		manager = null;
		return false;
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
		nbt.setInteger("Speed", speed);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular) {
		super.readFromNBT(nbt, modular);
		
		if(nbt.hasKey("Manager"))
			manager = RecipeManager.loadManagerFromNBT(nbt.getCompoundTag("Manager"), modular);
		
		inputs = nbt.getInteger("Inputs");
		outputs = nbt.getInteger("Outputs");
		speed = nbt.getInteger("Speed");
	}
	
	@Override
	public int getSpeed() {
		return speed;
	}
	
	public abstract int getSpeedModifier();
	
	public abstract IModule getModule(int speedModifier);

}
