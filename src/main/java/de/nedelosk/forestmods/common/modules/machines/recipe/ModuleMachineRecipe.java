package de.nedelosk.forestmods.common.modules.machines.recipe;

import java.util.List;

import com.google.gson.JsonObject;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modular.basic.IModularDefault;
import de.nedelosk.forestmods.api.modular.tile.IModularTileEntity;
import de.nedelosk.forestmods.api.modules.IModuleSaver;
import de.nedelosk.forestmods.api.modules.engine.IModuleEngine;
import de.nedelosk.forestmods.api.modules.engine.IModuleEngineSaver;
import de.nedelosk.forestmods.api.modules.fluids.TankData;
import de.nedelosk.forestmods.api.modules.gui.IModuleGui;
import de.nedelosk.forestmods.api.modules.machines.recipe.IMachineRecipeHandler;
import de.nedelosk.forestmods.api.modules.machines.recipe.IModuleMachineRecipe;
import de.nedelosk.forestmods.api.modules.machines.recipe.IModuleMachineRecipeSaver;
import de.nedelosk.forestmods.api.modules.managers.fluids.IModuleTankManager.TankMode;
import de.nedelosk.forestmods.api.recipes.IRecipe;
import de.nedelosk.forestmods.api.recipes.IRecipeHandler;
import de.nedelosk.forestmods.api.recipes.RecipeItem;
import de.nedelosk.forestmods.api.recipes.RecipeRegistry;
import de.nedelosk.forestmods.api.utils.ModularUtils;
import de.nedelosk.forestmods.api.utils.ModuleRegistry;
import de.nedelosk.forestmods.api.utils.ModuleRegistry.ModuleItem;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.common.modules.machines.ModuleMachine;
import de.nedelosk.forestmods.common.network.PacketHandler;
import de.nedelosk.forestmods.common.network.packets.PacketModule;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class ModuleMachineRecipe extends ModuleMachine implements IModuleMachineRecipe {

	public ModuleMachineRecipe(String moduleUID, int itemInputs, int itemOutputs) {
		super(moduleUID, itemInputs, itemOutputs);
		RecipeRegistry.registerRecipeHandler(getRecipeCategory(null), new ModuleRecipeHandler());
	}

	public ModuleMachineRecipe(String moduleUID, int itemInputs, int itemOutputs, int fluidInputs, int fluidOutputs) {
		super(moduleUID, itemInputs, itemOutputs, fluidInputs, fluidOutputs);
		RecipeRegistry.registerRecipeHandler(getRecipeCategory(null), new ModuleRecipeHandler());
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
		IRecipe recipe = RecipeRegistry.getRecipe(getRecipeCategory(stack), getInputs(modular, stack), getCraftingModifiers(modular, stack));
		RecipeItem[] recipeInputs = recipe.getInputs();
		for ( int i = 0; i < recipeInputs.length; i++ ) {
			RecipeItem recipeInput = recipeInputs[i];
			if (recipeInput != null) {
				if (!recipeInput.isFluid()) {
					if (recipeInput.isOre()) {
						tile.getModular().getInventoryManager().decrStackSize(recipeInput.slotIndex, recipeInput.ore.stackSize, stack);
					} else {
						tile.getModular().getInventoryManager().decrStackSize(recipeInput.slotIndex, recipeInput.item.stackSize, stack);
					}
					continue;
				} else {
					ModularUtils.getTankManager(modular).getModule().drain(ForgeDirection.UNKNOWN, recipeInput.fluid, true, stack, modular, true);
					continue;
				}
			} else {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean addOutput(IModular modular, ModuleStack stack) {
		IModularTileEntity<IModularDefault> tile = modular.getMachine();
		IModuleMachineRecipeSaver saver = (IModuleMachineRecipeSaver) stack.getSaver();
		if (saver.getRecipeManager().getOutputs() != null) {
			for ( RecipeItem item : saver.getRecipeManager().getOutputs() ) {
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
		return false;
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
		IModuleMachineRecipeSaver saver = (IModuleMachineRecipeSaver) stack.getSaver();
		if (engineStack != null && tile.getEnergyStored(null) > 0) {
			IModuleEngineSaver engineSaver = engineStack.getSaver();
			IModuleEngine engine = engineStack.getModule();
			int burnTime = engineSaver.getBurnTime(engineStack);
			int burnTimeTotal = engineSaver.getBurnTimeTotal(engineStack);
			IMachineRecipeHandler manager = saver.getRecipeManager();
			if (burnTime >= burnTimeTotal || burnTimeTotal == 0) {
				RecipeItem[] inputs = getInputs(modular, stack);
				IRecipe recipe = RecipeRegistry.getRecipe(getRecipeCategory(stack), inputs, getCraftingModifiers(modular, stack));
				if (manager != null) {
					if (addOutput(modular, stack)) {
						saver.setRecipeManager(null);
						engineSaver.setBurnTimeTotal(0);
						engineSaver.setBurnTime(0);
						engineSaver.setIsWorking(false);
						PacketHandler.INSTANCE.sendToAll(new PacketModule((TileEntity & IModularTileEntity) tile, engineStack, true));
					}
				} else if (recipe != null) {
					if (ModularUtils.getMachine(modular) == null) {
						return;
					}
					ModuleItem item = ModuleRegistry.getModuleFromItem(ModularUtils.getMachine(modular).getStack().getItemStack());
					int burnTimeEngine = engine.getBurnTimeTotal(modular, recipe.getRequiredSpeedModifier(), stack, engineStack) / item.material.getTier();
					saver.setRecipeManager(new MachineRecipeHandler(modular, recipe.getRecipeCategory(), recipe.getRequiredMaterial() / burnTimeEngine,
							recipe.getInputs().clone(), getCraftingModifiers(modular, stack)));
					if (!removeInput(modular, stack)) {
						saver.setRecipeManager(null);
						return;
					}
					engineSaver.setIsWorking(true);
					engineSaver.setBurnTimeTotal(
							engine.getBurnTimeTotal(modular, recipe.getRequiredSpeedModifier(), stack, engineStack) / item.material.getTier());
				}
			}
		}
	}

	@Override
	public void writeCraftingModifiers(NBTTagCompound nbt, Object[] craftingModifiers) {
	}

	@Override
	public Object[] readCraftingModifiers(NBTTagCompound nbt) {
		return null;
	}

	@Override
	public Object[] parseCraftingModifiers(JsonObject object) {
		return null;
	}

	@Override
	public JsonObject writeCraftingModifiers(Object[] objects) {
		return null;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModuleGui createGui(ModuleStack stack) {
		return new ModuleMachineRecipeGui(getUID());
	}

	@Override
	public IModuleSaver createSaver(ModuleStack stack) {
		return new ModuleMachineRecipeSaver();
	}

	public abstract Class<? extends IRecipe> getRecipeClass();

	public class ModuleRecipeHandler implements IRecipeHandler {

		@Override
		public JsonObject writeCraftingModifiers(Object[] objects) {
			return ModuleMachineRecipe.this.writeCraftingModifiers(objects);
		}

		@Override
		public Object[] parseCraftingModifiers(JsonObject object) {
			return ModuleMachineRecipe.this.parseCraftingModifiers(object);
		}

		@Override
		public void writeCraftingModifiers(NBTTagCompound nbt, Object[] craftingModifiers) {
			ModuleMachineRecipe.this.writeCraftingModifiers(nbt, craftingModifiers);
		}

		@Override
		public Object[] readCraftingModifiers(NBTTagCompound nbt) {
			return ModuleMachineRecipe.this.readCraftingModifiers(nbt);
		}

		@Override
		public String getRecipeCategory() {
			return ModuleMachineRecipe.this.getRecipeCategory(null);
		}

		@Override
		public Class<? extends IRecipe> getRecipeClass() {
			return ModuleMachineRecipe.this.getRecipeClass();
		}
	}
}
