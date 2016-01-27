package nedelosk.modularmachines.api.modules.machines.recipe;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.basic.IModularInventory;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.modules.IModuleGui;
import nedelosk.modularmachines.api.modules.engine.IModuleEngine;
import nedelosk.modularmachines.api.modules.fluids.TankData;
import nedelosk.modularmachines.api.modules.inventory.IModuleInventory;
import nedelosk.modularmachines.api.modules.machines.ModuleMachine;
import nedelosk.modularmachines.api.modules.managers.fluids.IModuleTankManager.TankMode;
import nedelosk.modularmachines.api.recipes.IRecipe;
import nedelosk.modularmachines.api.recipes.IRecipeManager;
import nedelosk.modularmachines.api.recipes.RecipeItem;
import nedelosk.modularmachines.api.recipes.RecipeRegistry;
import nedelosk.modularmachines.api.utils.ModuleStack;
import nedelosk.modularmachines.api.utils.ModuleUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class ModuleMachineRecipe<S extends IModuleMachineRecipeSaver> extends ModuleMachine<S> implements IModuleMachineRecipe<S> {

	protected final int itemOutputs;
	protected final int itemInputs;
	protected final int fluidOutputs;
	protected final int fluidInputs;
	protected final int speed;

	public ModuleMachineRecipe(String modifier, int itemInputs, int itemOutputs, int speed) {
		super(modifier);
		this.itemInputs = itemInputs;
		this.itemOutputs = itemOutputs;
		this.fluidInputs = 0;
		this.fluidOutputs = 0;
		this.speed = speed;
	}

	public ModuleMachineRecipe(String modifier, int itemInputs, int itemOutputs, int fluidInputs, int fluidOutputs, int speed) {
		super(modifier);
		this.itemInputs = itemInputs;
		this.itemOutputs = itemOutputs;
		this.fluidInputs = fluidInputs;
		this.fluidOutputs = fluidOutputs;
		this.speed = speed;
	}

	/* RECIPE */
	public RecipeItem[] getInputItems(IModular modular, ModuleStack stackProducer) {
		IModularTileEntity<IModularInventory> tile = modular.getMachine();
		RecipeItem[] inputs = new RecipeItem[getItemInputs(stackProducer)];
		for ( int i = 0; i < getItemInputs(stackProducer); i++ ) {
			ItemStack stack = tile.getModular().getInventoryManager().getStackInSlot(getName(stackProducer), i);
			inputs[i] = new RecipeItem(i, stack);
		}
		return inputs;
	}

	public RecipeItem[] getInputFluids(IModular modular, ModuleStack stack) {
		IModularTileEntity<IModularInventory> tile = modular.getMachine();
		List<TankData> datas = modular.getTankManeger().getStack().getModule().getDatas(modular, stack, TankMode.INPUT);
		RecipeItem[] fluidInputs = new RecipeItem[getFluidInputs(stack)];
		int inputs = fluidInputs.length;
		if (datas.size() < fluidInputs.length) {
			inputs = datas.size();
		}
		for ( int ID = 0; ID < inputs; ID++ ) {
			TankData data = datas.get(ID);
			if (data != null && data.getTank() != null && !data.getTank().isEmpty()) {
				fluidInputs[ID] = new RecipeItem(ID, data.getTank().getFluid().copy());
			}
		}
		return fluidInputs;
	}

	@Override
	public boolean removeInput(IModular modular, ModuleStack stack) {
		IModularTileEntity<IModularInventory> tile = modular.getMachine();
		IRecipe recipe = RecipeRegistry.getRecipe(getRecipeName(stack), getInputs(modular, stack), getCraftingModifiers(modular, stack));
		for ( int i = 0; i < getInputs(modular, stack).length; i++ ) {
			RecipeItem input = getInputs(modular, stack)[i];
			RecipeItem[] inputs = recipe.getInputs();
			if (input != null) {
				if (!inputs[i].isFluid()) {
					if (inputs[i].isOre()) {
						tile.getModular().getInventoryManager().decrStackSize(getName(stack), input.slotIndex, inputs[i].ore.stackSize);
					} else {
						tile.getModular().getInventoryManager().decrStackSize(getName(stack), input.slotIndex, inputs[i].item.stackSize);
					}
					continue;
				} else {
					tile.getModular().getTankManeger().getStack().getModule().drain(ForgeDirection.UNKNOWN, input.fluid, true, stack, modular, true);
					continue;
				}
			} else {
				return false;
			}
		}
		return true;
	}

	@Override
	public Object[] getCraftingModifiers(IModular modular, ModuleStack stack) {
		return null;
	}

	@Override
	public void updateServer(IModular modular, ModuleStack stack) {
		IModularTileEntity<IModularInventory> tile = modular.getMachine();
		ModuleStack<IModuleEngine> engineStack = ModuleUtils.getEngine(modular).getStack();
		S saver = (S) stack.getSaver();
		if (engineStack != null && tile.getEnergyStored(null) > 0) {
			IModuleEngine engine = engineStack.getModule();
			int burnTime = engine.getBurnTime(engineStack);
			int burnTimeTotal = engine.getBurnTimeTotal(engineStack);
			IRecipeManager manager = engine.getManager(engineStack);
			if (burnTime >= burnTimeTotal || burnTimeTotal == 0) {
				IRecipe recipe = RecipeRegistry.getRecipe(getRecipeName(stack), getInputs(modular, stack), getCraftingModifiers(modular, stack));
				if (manager != null) {
					if (addOutput(modular, stack)) {
						engine.setManager(null);
						engine.setBurnTimeTotal(0);
						engine.setBurnTime(0);
						engine.setIsWorking(false);
					}
				} else if (getInputs(modular, stack) != null && recipe != null) {
					if (ModuleUtils.getMachine(modular) == null) {
						return;
					}
					engine.setManager(engine.creatRecipeManager(modular, recipe.getRecipeName(),
							recipe.getRequiredMaterial() / engine.getBurnTimeTotal(modular, recipe.getRequiredSpeedModifier(), stack, engineStack),
							getInputs(modular, stack), getCraftingModifiers(modular, stack)));
					if (!removeInput(modular, stack)) {
						engine.setManager(null);
						return;
					}
					engine.setIsWorking(true);
					engine.setBurnTimeTotal(engine.getBurnTimeTotal(modular, recipe.getRequiredSpeedModifier(), stack, engineStack)
							/ ModuleUtils.getMachine(modular).getStack().getMaterial().getTier());
				}
				if (saver.getTimer() > saver.getTimerTotal()) {
					modular.getMachine().getWorldObj().markBlockForUpdate(modular.getMachine().getXCoord(), modular.getMachine().getYCoord(),
							modular.getMachine().getZCoord());
					saver.setTimer(0);
				} else {
					saver.addTimer(1);
				}
			} else {
				if (saver.getTimer() > saver.getTimerTotal()) {
					modular.getMachine().getWorldObj().markBlockForUpdate(modular.getMachine().getXCoord(), modular.getMachine().getYCoord(),
							modular.getMachine().getZCoord());
					saver.setTimer(0);
				} else {
					saver.addTimer(1);
				}
			}
		}
	}

	@Override
	public boolean addOutput(IModular modular, ModuleStack stack) {
		IModularTileEntity<IModularInventory> tile = modular.getMachine();
		ModuleStack<IModuleEngine> engineStack = ModuleUtils.getEngine(modular).getStack();
		IModuleEngine engine = engineStack.getModule();
		if (engine.getManager(engineStack).getOutputs() != null) {
			for ( RecipeItem item : engine.getManager(engineStack).getOutputs() ) {
				if (item != null) {
					if (!item.isFluid()) {
						if (tile.getModular().getInventoryManager().addToOutput(item.item.copy(), getItemInputs(stack),
								getItemInputs(stack) + getItemOutputs(stack), getName(stack))) {
							item = null;
							continue;
						} else {
							return false;
						}
					} else if (tile.getModular().getTankManeger().getStack().getModule().fill(ForgeDirection.UNKNOWN, item.fluid.copy(), true, stack, modular,
							true) != 0) {
						item = null;
						continue;
					} else {
						return false;
					}
				}
			}
			return true;
		}
		engine.setManager(null);
		return false;
	}

	@Override
	public void writeCraftingModifiers(NBTTagCompound nbt, IModular modular, Object[] craftingModifiers) {
	}

	@Override
	public Object[] readCraftingModifiers(NBTTagCompound nbt, IModular modular) {
		return null;
	}

	@Override
	public int getSpeed(ModuleStack stack) {
		return speed;
	}

	@Override
	public int getItemInputs(ModuleStack stack) {
		return itemInputs;
	}

	@Override
	public int getItemOutputs(ModuleStack stack) {
		return itemOutputs;
	}

	@Override
	public int getFluidInputs(ModuleStack stack) {
		return fluidInputs;
	}

	@Override
	public int getFluidOutputs(ModuleStack stack) {
		return fluidOutputs;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModuleGui getGui(ModuleStack stack) {
		return new ModuleMachineRecipeGui(getCategoryUID(), getName(stack));
	}

	@Override
	public abstract IModuleInventory getInventory(ModuleStack stack);
}
