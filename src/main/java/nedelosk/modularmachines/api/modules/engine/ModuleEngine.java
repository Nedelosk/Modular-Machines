package nedelosk.modularmachines.api.modules.engine;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.client.renderer.IModularRenderer;
import nedelosk.modularmachines.api.client.renderer.ModularMachineRenderer;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.integration.IWailaData;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.modules.IModuleSaver;
import nedelosk.modularmachines.api.modules.Module;
import nedelosk.modularmachines.api.modules.energy.IModuleBatterySaver;
import nedelosk.modularmachines.api.modules.machines.IModuleMachine;
import nedelosk.modularmachines.api.modules.machines.IModuleMachineSaver;
import nedelosk.modularmachines.api.modules.machines.recipe.IModuleMachineRecipe;
import nedelosk.modularmachines.api.packets.PacketHandler;
import nedelosk.modularmachines.api.packets.PacketProducerEngine;
import nedelosk.modularmachines.api.utils.ModularUtils;
import nedelosk.modularmachines.api.utils.ModuleCategoryUIDs;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.item.ItemStack;

public abstract class ModuleEngine extends Module implements IModuleEngine {

	protected final int speedModifier;
	protected final String type;

	public ModuleEngine(String moduleUID, int speedModifier, String type) {
		super(ModuleCategoryUIDs.ENGINE, moduleUID);
		this.speedModifier = speedModifier;
		this.type = type;
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
			PacketHandler.INSTANCE.sendToServer(new PacketProducerEngine(modular.getMachine(), saver.getProgress()));
		}
	}

	@Override
	public void updateServer(IModular modular, ModuleStack stack) {
		ModuleStack<IModuleMachine, IModuleMachineSaver> stackMachine = ModularUtils.getMachine(modular).getStack();
		IModuleEngineSaver saver = (IModuleEngineSaver) stack.getSaver();
		if (saver.getTimer() > saver.getTimerTotal()) {
			modular.getMachine().getWorldObj().markBlockForUpdate(modular.getMachine().getXCoord(), modular.getMachine().getYCoord(),
					modular.getMachine().getZCoord());
			saver.setTimer(0);
		} else {
			saver.addTimer(1);
			if (stackMachine.getModule() instanceof IModuleMachineRecipe) {
				IModuleMachineRecipe machine = (IModuleMachineRecipe) stackMachine.getModule();
				if (saver.getManager(stack) != null) {
					if (saver.getManager(stack).removeMaterial()) {
						saver.addBurnTime(1);
					}
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModularRenderer getMachineRenderer(IModular modular, ModuleStack moduleStack, IModularTileEntity tile) {
		return new ModularMachineRenderer.EngineRenderer(moduleStack, ModularUtils.getCasing(modular).getStack());
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModularRenderer getItemRenderer(IModular modular, ModuleStack moduleStack, ItemStack stack) {
		return new ModularMachineRenderer.EngineRenderer(moduleStack, ModularUtils.getCasing(modular).getStack());
	}

	@Override
	public int getSpeedModifier(ModuleStack stack) {
		return speedModifier;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public int getBurnTimeTotal(IModular modular, int speedModifier, ModuleStack<IModuleMachine, IModuleMachineSaver> stackMachine,
			ModuleStack<IModuleEngine, IModuleEngineSaver> stackEngine) {
		int speed = getSpeedModifier(stackEngine) * stackMachine.getModule().getSpeed(stackMachine) / 10;
		IModuleBatterySaver batterySaver = (IModuleBatterySaver) ModularUtils.getBattery(modular).getStack().getSaver();
		int burnTimeTotal = speed + (speed * batterySaver.getSpeedModifier() / 100);
		return burnTimeTotal + (burnTimeTotal * speedModifier / 100);
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
