package nedelosk.modularmachines.common.modular.module.tool.producer.machine;

import java.util.ArrayList;

import codechicken.nei.recipe.GuiCraftingRecipe;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.forestday.api.guis.IGuiBase;
import nedelosk.forestday.api.guis.Widget;
import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.machines.basic.IModularInventory;
import nedelosk.modularmachines.api.modular.machines.basic.IModularTileEntity;
import nedelosk.modularmachines.api.modular.module.basic.IModule;
import nedelosk.modularmachines.api.modular.module.tool.producer.energy.IProducerEngine;
import nedelosk.modularmachines.api.modular.module.tool.producer.machine.IProducerMachineRecipe;
import nedelosk.modularmachines.api.modular.utils.ModuleUtils;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.modularmachines.api.recipes.IRecipe;
import nedelosk.modularmachines.api.recipes.IRecipeManager;
import nedelosk.modularmachines.api.recipes.RecipeInput;
import nedelosk.modularmachines.api.recipes.RecipeItem;
import nedelosk.modularmachines.api.recipes.RecipeRegistry;
import nedelosk.modularmachines.client.gui.widget.WidgetProgressBar;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;

public abstract class ProducerMachineRecipe extends ProducerMachine implements IProducerMachineRecipe {
	
	public int outputs;
	public int inputs;
	protected int speed;

	public ProducerMachineRecipe(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		super(nbt, modular, stack);
	}

	public ProducerMachineRecipe(String modifier, int inputs, int outputs, int speed) {
		super(modifier);
		this.inputs = inputs;
		this.outputs = outputs;
		this.speed = speed;
	}

	public RecipeInput[] getInputItems(IModular modular, ModuleStack moduleStack) {
		IModularTileEntity<IModularInventory> tile = modular.getMachine();
		RecipeInput[] inputs = new RecipeInput[this.inputs];
		for (int i = 0; i < inputs.length; i++) {
			ItemStack stack = tile.getModular().getInventoryManager()
					.getStackInSlot(moduleStack.getModule().getName(moduleStack, false), i);
			inputs[i] = new RecipeInput(i, stack);
		}
		return inputs;
	}

	public RecipeInput[] getInputFluids(IModular modular, ModuleStack moduleStack) {
		IModularTileEntity<IModularInventory> tile = modular.getMachine();
		RecipeInput[] inputs = new RecipeInput[this.inputs];
		for (int i = 0; i < inputs.length; i++) {
			FluidTankInfo[] infos = tile.getModular().getManager().getFluidHandler()
					.getTankInfo(ForgeDirection.UNKNOWN);
			FluidStack stack = infos[i].fluid.copy();
			inputs[i] = new RecipeInput(i, stack);
		}
		return inputs;
	}

	@Override
	public boolean removeInput(IModular modular, ModuleStack stack) {
		IModularTileEntity<IModularInventory> tile = modular.getMachine();
		IRecipe recipe = RecipeRegistry.getRecipe(getRecipeName(stack), getInputs(modular, stack));
		for (int i = 0; i < getInputs(modular, stack).length; i++) {
			RecipeInput input = getInputs(modular, stack)[i];
			RecipeItem[] inputs = recipe.getInputs();
			if (input != null) {
				if (!inputs[i].isFluid()) {
					if (inputs[i].isOre())
						tile.getModular().getInventoryManager().decrStackSize(stack.getModule().getName(stack, false),
								input.slotIndex, inputs[i].ore.stackSize);
					else
						tile.getModular().getInventoryManager().decrStackSize(stack.getModule().getName(stack, false),
								input.slotIndex, inputs[i].item.stackSize);
					continue;
				} else {
					tile.getModular().getManager().getFluidHandler().drain(ForgeDirection.UNKNOWN, input.fluid, true);
					continue;
				}
			} else
				return false;
		}
		return true;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void handleMouseClicked(IModularTileEntity tile, Widget widget, int mouseX, int mouseY, int mouseButton,
			ModuleStack stack) {
		if (widget instanceof WidgetProgressBar) {
			if (Loader.isModLoaded("NotEnoughItems")) {
				GuiCraftingRecipe.openRecipeGui("ModularMachines" + getRecipeName(stack));
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void updateGui(IGuiBase base, int x, int y, IModular modular, ModuleStack stack) {
		ArrayList<Widget> widgets = base.getWidgetManager().getWidgets();
		for (Widget widget : widgets) {
			if (widget instanceof WidgetProgressBar) {
				ModuleStack<IModule, IProducerEngine> engine = ModuleUtils.getModuleStackEngine(modular);
				if (engine != null)	{
					int burnTime = engine.getProducer().getBurnTime(engine);
					int burnTimeTotal = engine.getProducer().getBurnTimeTotal(engine);
					((WidgetProgressBar) widget).burntime = burnTime;
					((WidgetProgressBar) widget).burntimeTotal = burnTimeTotal;
				}
			}
		}
	}

	@Override
	public void updateServer(IModular modular, ModuleStack stack) {
		IModularTileEntity<IModularInventory> tile = modular.getMachine();
		ModuleStack<IModule, IProducerEngine> engineStack = ModuleUtils.getModuleStackEngine(modular);
		if (engineStack != null && tile.getEnergyStored(null) > 0) {
			IProducerEngine engine = engineStack.getProducer();
			int burnTime = engine.getBurnTime(engineStack);
			int burnTimeTotal = engine.getBurnTimeTotal(engineStack);
			IRecipeManager manager = engine.getManager(engineStack);
			if (burnTime >= burnTimeTotal || burnTimeTotal == 0) {
				IRecipe recipe = RecipeRegistry.getRecipe(getRecipeName(stack), getInputs(modular, stack));
				if (manager != null) {
					if (addOutput(modular, stack)) {
						engine.setManager(null);
						engine.setBurnTimeTotal(0);
						engine.setBurnTime(0);
						engine.setIsWorking(false);
					}
				} else if (getInputs(modular, stack) != null
						&& RecipeRegistry.getRecipe(getRecipeName(stack), getInputs(modular, stack)) != null) {
					if (ModuleUtils.getModuleStackMachine(modular) == null)
						return;
					engine.setManager(engine.creatRecipeManager(modular, recipe.getRecipeName(), recipe.getRequiredMaterial() / engine.getBurnTimeTotal(modular, RecipeRegistry.getRecipe(getRecipeName(stack), getInputs(modular, stack)).getRequiredSpeedModifier(), stack, engineStack), getInputs(modular, stack)));
					if (!removeInput(modular, stack)) {
						engine.setManager(null);
						return;
					}
					engine.setIsWorking(true);
					engine.setBurnTimeTotal(engine.getBurnTimeTotal(modular, RecipeRegistry.getRecipe(getRecipeName(stack), getInputs(modular, stack)).getRequiredSpeedModifier(), stack, engineStack) / ModuleUtils.getModuleStackMachine(modular).getType().getTier());
				}
				if (timer > timerTotal) {
					modular.getMachine().getWorldObj().markBlockForUpdate(modular.getMachine().getXCoord(), modular.getMachine().getYCoord(), modular.getMachine().getZCoord());
					timer = 0;
				} else {
					timer++;
				}
			} else {
				if (timer > timerTotal) {
					modular.getMachine().getWorldObj().markBlockForUpdate(modular.getMachine().getXCoord(), modular.getMachine().getYCoord(), modular.getMachine().getZCoord());
					timer = 0;
				} else {
					timer++;
				}
			}
		}
	}

	@Override
	public boolean addOutput(IModular modular, ModuleStack stack) {
		IModularTileEntity<IModularInventory> tile = modular.getMachine();
		ModuleStack<IModule, IProducerEngine> engineStack = ModuleUtils.getModuleStackEngine(modular);
		IProducerEngine engine = engineStack.getProducer();
		if (engine.getManager(engineStack).getOutputs() != null) {
			for (RecipeItem item : engine.getManager(engineStack).getOutputs()) {
				if (item != null) {
					if (!item.isFluid())
						if (tile.getModular().getInventoryManager().addToOutput(item.item.copy(), this.inputs,
								this.inputs + this.outputs, stack.getModule().getName(stack, false))) {
							item = null;
							continue;
						} else
							return false;
					else if (tile.getModular().getManager().getFluidHandler().fill(ForgeDirection.UNKNOWN,
							item.fluid.copy(), true) != 0) {
						item = null;
						continue;
					} else
						return false;
				}
			}
			return true;
		}
		engine.setManager(null);
		return false;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) throws Exception {
		super.writeToNBT(nbt, modular, stack);

		nbt.setInteger("Inputs", inputs);
		nbt.setInteger("Outputs", outputs);
		nbt.setInteger("Speed", speed);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) throws Exception {
		super.readFromNBT(nbt, modular, stack);

		inputs = nbt.getInteger("Inputs");
		outputs = nbt.getInteger("Outputs");
		speed = nbt.getInteger("Speed");
	}

	@Override
	public int getSpeed(ModuleStack stack) {
		return speed;
	}

	public abstract int getSpeedModifier();
}
