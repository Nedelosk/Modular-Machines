package de.nedelosk.forestmods.common.modules.engine;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modular.IModularRenderer;
import de.nedelosk.forestmods.api.modular.integration.IWailaData;
import de.nedelosk.forestmods.api.modular.tile.IModularTileEntity;
import de.nedelosk.forestmods.api.modules.IModuleSaver;
import de.nedelosk.forestmods.api.modules.engine.IModuleEngine;
import de.nedelosk.forestmods.api.modules.engine.IModuleEngineSaver;
import de.nedelosk.forestmods.api.modules.engine.IModuleEngineType;
import de.nedelosk.forestmods.api.modules.machines.IModuleMachine;
import de.nedelosk.forestmods.api.modules.machines.recipe.IMachineRecipeHandler;
import de.nedelosk.forestmods.api.modules.machines.recipe.IModuleMachineRecipe;
import de.nedelosk.forestmods.api.modules.machines.recipe.IModuleMachineRecipeSaver;
import de.nedelosk.forestmods.api.modules.machines.recipe.IModuleMachineRecipeType;
import de.nedelosk.forestmods.api.modules.storage.battery.IModuleBatterySaver;
import de.nedelosk.forestmods.api.utils.ModularUtils;
import de.nedelosk.forestmods.api.utils.ModuleCategoryUIDs;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.client.render.modules.EngineRenderer;
import de.nedelosk.forestmods.common.modules.ModuleAddable;
import de.nedelosk.forestmods.common.network.PacketHandler;
import de.nedelosk.forestmods.common.network.packets.PacketModule;
import de.nedelosk.forestmods.common.network.packets.PacketSyncEngineProgress;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class ModuleEngine extends ModuleAddable implements IModuleEngine {

	public ModuleEngine(String moduleUID) {
		super(ModuleCategoryUIDs.ENGINE, moduleUID);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void updateClient(IModular modular, ModuleStack stack) {
		IModuleEngineSaver saver = (IModuleEngineSaver) stack.getSaver();
		if (saver.isWorking()) {
			saver.addProgress(0.05F);
			if (saver.getProgress() > 1) {
				saver.setProgress(0);
			}
			PacketHandler.INSTANCE.sendToServer(new PacketSyncEngineProgress(modular.getMachine(), saver.getProgress()));
		}
	}

	@Override
	public void updateServer(IModular modular, ModuleStack stack) {
		if (ModularUtils.getMachine(modular) == null) {
			return;
		}
		ModuleStack<IModuleMachine, IModuleMachineRecipeSaver> stackMachine = ModularUtils.getMachine(modular).getStack();
		IModuleEngineSaver saver = (IModuleEngineSaver) stack.getSaver();
		IModuleMachineRecipeSaver machineSaver = stackMachine.getSaver();
		if (stackMachine.getModule() instanceof IModuleMachineRecipe) {
			IModuleMachineRecipe machine = (IModuleMachineRecipe) stackMachine.getModule();
			if (machineSaver.getRecipeManager() != null && saver.getBurnTime(stack) <= saver.getBurnTimeTotal(stack)) {
				if (removeMaterial(modular, stack, stackMachine)) {
					saver.addBurnTime(1);
					PacketHandler.INSTANCE.sendToAll(new PacketModule((TileEntity & IModularTileEntity) modular.getMachine(), stack, true));
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModularRenderer getMachineRenderer(IModular modular, ModuleStack moduleStack, IModularTileEntity tile) {
		return new EngineRenderer(moduleStack, ModularUtils.getCasing(modular).getStack());
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModularRenderer getItemRenderer(IModular modular, ModuleStack moduleStack, ItemStack stack) {
		return new EngineRenderer(moduleStack, ModularUtils.getCasing(modular).getStack());
	}

	@Override
	public int getBurnTimeTotal(IModular modular, int speedModifier, ModuleStack<IModuleMachine, IModuleMachineRecipeSaver> stackMachine,
			ModuleStack<IModuleEngine, IModuleEngineSaver> stackEngine) {
		int speed = ((IModuleEngineType) stackEngine.getType()).getSpeedModifier(stackEngine)
				* ((IModuleMachineRecipeType) stackMachine.getType()).getSpeed(stackMachine) / 10;
		IModuleBatterySaver batterySaver = (IModuleBatterySaver) ModularUtils.getBattery(modular).getStack().getSaver();
		int burnTimeTotal = speed + (speed * batterySaver.getSpeedModifier() / 100);
		return burnTimeTotal + (burnTimeTotal * speedModifier / 100);
	}

	@Override
	public boolean removeMaterial(IModular modular, ModuleStack<IModuleEngine, IModuleEngineSaver> engineStack,
			ModuleStack<IModuleMachine, IModuleMachineRecipeSaver> machineStack) {
		if (modular == null || modular.getUtilsManager() == null || modular.getUtilsManager().getEnergyHandler() == null) {
			return false;
		}
		IMachineRecipeHandler handler = machineStack.getSaver().getRecipeManager();
		if (modular.getUtilsManager().getEnergyHandler().extractEnergy(ForgeDirection.UNKNOWN, handler.getMaterialToRemove(), false) > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaData data) {
		IModularTileEntity tile = (IModularTileEntity) data.getTileEntity();
		ModuleStack<IModuleEngine, IModuleEngineSaver> stackEngine = ModularUtils.getEngine(tile.getModular()).getStack();
		if (stackEngine != null) {
			IModuleEngineSaver engine = stackEngine.getSaver();
			currenttip.add(engine.getBurnTime(stackEngine) + " / " + engine.getBurnTimeTotal(stackEngine));
		}
		return currenttip;
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaData data) {
		return currenttip;
	}

	@Override
	public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaData data) {
		return currenttip;
	}

	@Override
	public IModuleSaver createSaver(ModuleStack stack) {
		return new ModuleEngineSaver();
	}
}
