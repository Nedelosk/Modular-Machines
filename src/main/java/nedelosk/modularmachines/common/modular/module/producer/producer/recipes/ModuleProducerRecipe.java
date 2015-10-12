package nedelosk.modularmachines.common.modular.module.producer.producer.recipes;

import java.util.ArrayList;

import codechicken.nei.NEIModContainer;
import codechicken.nei.recipe.GuiCraftingRecipe;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder.NotEnoughDataDecoderException;
import nedelosk.modularmachines.api.materials.Material;
import nedelosk.modularmachines.api.materials.stats.MachineState;
import nedelosk.modularmachines.api.materials.stats.Stats;
import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.machines.basic.IModularInventory;
import nedelosk.modularmachines.api.modular.machines.basic.IModularTileEntity;
import nedelosk.modularmachines.api.modular.module.basic.IModule;
import nedelosk.modularmachines.api.modular.module.basic.energy.IModuleEngine;
import nedelosk.modularmachines.api.modular.module.producer.producer.recipe.IModuleProducerRecipe;
import nedelosk.modularmachines.api.modular.utils.ModularUtils;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.modularmachines.api.parts.IMachinePartModules;
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
		int burnTimeTotal = engine.getModule().getSpeedModifier(engine.getTier()) * getSpeed() / 10;
		int burnTimeTotal2 = burnTimeTotal + (burnTimeTotal * ModularUtils.getModuleBattery(modular).getSpeedModifier() / 100);
		return burnTimeTotal2;
	}
	
	public int getBurnTimeTotal(IModular modular, int speedModifier)
	{
		ModuleStack<IModuleEngine> engine = ModularUtils.getModuleStackEngine(modular);
		int burnTimeTotal = engine.getModule().getSpeedModifier(engine.getTier()) * getSpeed() / 10;
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
		IRecipe recipe = RecipeRegistry.getRecipe(getRecipeName(), getInputs(modular));
		for(int i = 0;i < getInputs(modular).length;i++)
		{
			RecipeInput input = getInputs(modular)[i];
			RecipeItem[] inputs = recipe.getInputs();
			if(input != null){
				if(!inputs[i].isFluid())
				{
					if(inputs[i].isOre())
						tile.getModular().getInventoryManager().decrStackSize(getName(), input.slotIndex, inputs[i].ore.stackSize);
					else
						tile.getModular().getInventoryManager().decrStackSize(getName(), input.slotIndex, inputs[i].item.stackSize);
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
	public void update(IModular modular) {
		IModularTileEntity<IModularInventory> tile = modular.getMachine();
		if(tile.getEnergyStored(null) > 0)
		{
			if(burnTime >= burnTimeTotal || burnTimeTotal == 0)
			{
				IRecipe recipe = RecipeRegistry.getRecipe(getRecipeName(), getInputs(modular));
				if(manager != null)
				{
					if(addOutput(modular)){
						manager = null;
						burnTimeTotal = 0;
						burnTime = 0;
					}
				}
				else if(getInputs(modular) != null && RecipeRegistry.getRecipe(getRecipeName(), getInputs(modular)) != null)
				{
					if(ModularUtils.getModuleStackProducer(modular) == null)
						return;
					manager = new RecipeManager(modular, recipe.getRecipeName(), recipe.getRequiredEnergy() / getBurnTimeTotal(modular, RecipeRegistry.getRecipe(getRecipeName(), getInputs(modular)).getRequiredSpeedModifier()), getInputs(modular));
					if(!removeInput(modular))
					{
						manager = null;
						return;
					}
					burnTimeTotal = getBurnTimeTotal(modular, recipe.getRequiredSpeedModifier()) / ModularUtils.getModuleStackProducer(modular).getTier();
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
		if(manager.getOutputs() != null){
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
	
	@Override
	public ModuleStack creatModule(ItemStack stack) {
		IMachinePartModules producer = (IMachinePartModules) stack.getItem();
		Material[] materials = producer.getPartMaterials(stack);
		int size;
		int tiers = 0;
		for(size = 0;size < materials.length;size++){
			if(!materials[size].hasStats(Stats.MACHINE))
				return null;
			tiers += ((MachineState)materials[size].getStats(Stats.MACHINE)).tier();
		}
		int speedModifier = getSpeedModifier()/tiers*size;
		return new ModuleStack(stack, getModule(speedModifier), tiers/size, false);
	}
	
	public abstract int getSpeedModifier();
	
	public abstract IModule getModule(int speedModifier);

}
