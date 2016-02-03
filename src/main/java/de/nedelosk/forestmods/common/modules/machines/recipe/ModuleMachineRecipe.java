package de.nedelosk.forestmods.common.modules.machines.recipe;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestcore.utils.WorldUtil;
import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modular.basic.IModularDefault;
import de.nedelosk.forestmods.api.modular.tile.IModularTileEntity;
import de.nedelosk.forestmods.api.modules.engine.IModuleEngine;
import de.nedelosk.forestmods.api.modules.engine.IModuleEngineSaver;
import de.nedelosk.forestmods.api.modules.fluids.TankData;
import de.nedelosk.forestmods.api.modules.gui.IModuleGui;
import de.nedelosk.forestmods.api.modules.inventory.IModuleInventory;
import de.nedelosk.forestmods.api.modules.machines.IModuleMachineSaver;
import de.nedelosk.forestmods.api.modules.machines.recipe.IModuleMachineRecipe;
import de.nedelosk.forestmods.api.modules.managers.fluids.IModuleTankManager.TankMode;
import de.nedelosk.forestmods.api.recipes.IRecipe;
import de.nedelosk.forestmods.api.recipes.IRecipeManager;
import de.nedelosk.forestmods.api.recipes.RecipeItem;
import de.nedelosk.forestmods.api.recipes.RecipeRegistry;
import de.nedelosk.forestmods.api.utils.ModularUtils;
import de.nedelosk.forestmods.api.utils.ModuleRegistry;
import de.nedelosk.forestmods.api.utils.ModuleRegistry.ModuleItem;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.common.modules.machines.ModuleMachine;
import de.nedelosk.forestmods.common.network.PacketHandler;
import de.nedelosk.forestmods.common.network.packets.PacketModule;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class ModuleMachineRecipe extends ModuleMachine implements IModuleMachineRecipe {

	protected final int itemOutputs;
	protected final int itemInputs;
	protected final int fluidOutputs;
	protected final int fluidInputs;
	protected final int speed;

	public ModuleMachineRecipe(String moduleUID, String moduleModifier, int itemInputs, int itemOutputs, int speed) {
		super(moduleUID, moduleModifier);
		this.itemInputs = itemInputs;
		this.itemOutputs = itemOutputs;
		this.fluidInputs = 0;
		this.fluidOutputs = 0;
		this.speed = speed;
	}

	public ModuleMachineRecipe(String moduleUID, String moduleModifier, int itemInputs, int itemOutputs, int fluidInputs, int fluidOutputs, int speed) {
		super(moduleUID, moduleModifier);
		this.itemInputs = itemInputs;
		this.itemOutputs = itemOutputs;
		this.fluidInputs = fluidInputs;
		this.fluidOutputs = fluidOutputs;
		this.speed = speed;
	}

	/* RECIPE */
	public RecipeItem[] getInputItems(IModular modular, ModuleStack stackProducer) {
		IModularTileEntity<IModularDefault> tile = modular.getMachine();
		RecipeItem[] inputs = new RecipeItem[getItemInputs(stackProducer)];
		for ( int i = 0; i < getItemInputs(stackProducer); i++ ) {
			ItemStack stack = tile.getModular().getInventoryManager().getStackInSlot(i, stackProducer);
			inputs[i] = new RecipeItem(i, stack);
		}
		return inputs;
	}

	public RecipeItem[] getInputFluids(IModular modular, ModuleStack stack) {
		IModularTileEntity<IModularDefault> tile = modular.getMachine();
		List<TankData> datas = ModularUtils.getTankManager(modular).getModule().getDatas(modular, stack, TankMode.INPUT);
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
		IModularTileEntity<IModularDefault> tile = modular.getMachine();
		IRecipe recipe = RecipeRegistry.getRecipe(getRecipeName(stack), getInputs(modular, stack), getCraftingModifiers(modular, stack));
		for ( int i = 0; i < getInputs(modular, stack).length; i++ ) {
			RecipeItem input = getInputs(modular, stack)[i];
			RecipeItem[] inputs = recipe.getInputs();
			if (input != null) {
				if (!inputs[i].isFluid()) {
					if (inputs[i].isOre()) {
						tile.getModular().getInventoryManager().decrStackSize(input.slotIndex, inputs[i].ore.stackSize, stack);
					} else {
						tile.getModular().getInventoryManager().decrStackSize(input.slotIndex, inputs[i].item.stackSize, stack);
					}
					continue;
				} else {
					ModularUtils.getTankManager(modular).getModule().drain(ForgeDirection.UNKNOWN, input.fluid, true, stack, modular, true);
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
	public void updateClient(IModular modular, ModuleStack stack) {
	}

	@Override
	public void updateServer(IModular modular, ModuleStack stack) {
		if (!modular.isAssembled()) {
			return;
		}
		IModularTileEntity<IModularDefault> tile = modular.getMachine();
		ModuleStack<IModuleEngine, IModuleEngineSaver> engineStack = ModularUtils.getEngine(modular).getStack();
		IModuleMachineSaver saver = (IModuleMachineSaver) stack.getSaver();
		if (engineStack != null && tile.getEnergyStored(null) > 0 && saver != null) {
			IModuleEngineSaver engineSaver = engineStack.getSaver();
			IModuleEngine engine = engineStack.getModule();
			int burnTime = engineSaver.getBurnTime(engineStack);
			int burnTimeTotal = engineSaver.getBurnTimeTotal(engineStack);
			IRecipeManager manager = engineSaver.getManager(engineStack);
			if (burnTime >= burnTimeTotal || burnTimeTotal == 0) {
				IRecipe recipe = RecipeRegistry.getRecipe(getRecipeName(stack), getInputs(modular, stack), getCraftingModifiers(modular, stack));
				if (manager != null) {
					if (addOutput(modular, stack)) {
						engineSaver.setManager(null);
						engineSaver.setBurnTimeTotal(0);
						engineSaver.setBurnTime(0);
						engineSaver.setIsWorking(false);
					}
				} else if (getInputs(modular, stack) != null && recipe != null) {
					if (ModularUtils.getMachine(modular) == null) {
						return;
					}
					ModuleItem item = ModuleRegistry.getModuleFromItem(ModularUtils.getMachine(modular).getStack().getItemStack());
					int burnTimeEngine = engine.getBurnTimeTotal(modular, recipe.getRequiredSpeedModifier(), stack, engineStack) / item.material.getTier();
					engineSaver.setManager(engine.creatRecipeManager(modular, recipe.getRecipeName(), recipe.getRequiredMaterial() / burnTimeEngine,
							getInputs(modular, stack), getCraftingModifiers(modular, stack)));
					if (!removeInput(modular, stack)) {
						engineSaver.setManager(null);
						return;
					}
					engineSaver.setIsWorking(true);
					engineSaver.setBurnTimeTotal(
							engine.getBurnTimeTotal(modular, recipe.getRequiredSpeedModifier(), stack, engineStack) / item.material.getTier());
				}
				if (saver.getTimer() > saver.getTimerTotal()) {
					PacketHandler.INSTANCE.sendTo(new PacketModule((TileEntity) modular.getMachine(), stack, true),
							(EntityPlayerMP) WorldUtil.getPlayer(modular.getMachine().getWorldObj(), modular.getMachine().getOwner()));
					saver.setTimer(0);
				} else {
					saver.addTimer(1);
				}
			} else {
				if (saver.getTimer() > saver.getTimerTotal()) {
					PacketHandler.INSTANCE.sendTo(new PacketModule((TileEntity) modular.getMachine(), stack, true),
							(EntityPlayerMP) WorldUtil.getPlayer(modular.getMachine().getWorldObj(), modular.getMachine().getOwner()));
					saver.setTimer(0);
				} else {
					saver.addTimer(1);
				}
			}
		}
	}

	@Override
	public boolean addOutput(IModular modular, ModuleStack stack) {
		IModularTileEntity<IModularDefault> tile = modular.getMachine();
		ModuleStack<IModuleEngine, IModuleEngineSaver> engineStack = ModularUtils.getEngine(modular).getStack();
		IModuleEngineSaver engineSaver = engineStack.getSaver();
		if (engineSaver.getManager(engineStack).getOutputs() != null) {
			for ( RecipeItem item : engineSaver.getManager(engineStack).getOutputs() ) {
				if (item != null) {
					if (!item.isFluid()) {
						if (tile.getModular().getInventoryManager().addToOutput(item.item.copy(), getItemInputs(stack),
								getItemInputs(stack) + getItemOutputs(stack), stack)) {
							item = null;
							continue;
						} else {
							return false;
						}
					} else if (ModularUtils.getTankManager(modular).getModule().fill(ForgeDirection.UNKNOWN, item.fluid.copy(), true, stack, modular,
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
		engineSaver.setManager(null);
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
	public IModuleGui createGui(ModuleStack stack) {
		return new ModuleMachineRecipeGui(getUID());
	}

	@Override
	public abstract IModuleInventory createInventory(ModuleStack stack);
}
