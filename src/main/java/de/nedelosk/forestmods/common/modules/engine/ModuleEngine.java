package de.nedelosk.forestmods.common.modules.engine;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestcore.utils.WorldUtil;
import de.nedelosk.forestmods.api.client.IModularRenderer;
import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modular.integration.IWailaData;
import de.nedelosk.forestmods.api.modular.tile.IModularTileEntity;
import de.nedelosk.forestmods.api.modules.IModuleSaver;
import de.nedelosk.forestmods.api.modules.engine.IModuleEngine;
import de.nedelosk.forestmods.api.modules.engine.IModuleEngineSaver;
import de.nedelosk.forestmods.api.modules.machines.IModuleMachine;
import de.nedelosk.forestmods.api.modules.machines.IModuleMachineSaver;
import de.nedelosk.forestmods.api.modules.machines.recipe.IModuleMachineRecipe;
import de.nedelosk.forestmods.api.modules.storage.battery.IModuleBatterySaver;
import de.nedelosk.forestmods.api.utils.ModularUtils;
import de.nedelosk.forestmods.api.utils.ModuleCategoryUIDs;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.client.render.modules.ModuleRenderer;
import de.nedelosk.forestmods.common.modules.ModuleAddable;
import de.nedelosk.forestmods.common.network.PacketHandler;
import de.nedelosk.forestmods.common.network.packets.PacketModule;
import de.nedelosk.forestmods.common.network.packets.PacketSyncEngineProgress;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public abstract class ModuleEngine extends ModuleAddable implements IModuleEngine {

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
			PacketHandler.INSTANCE.sendToServer(new PacketSyncEngineProgress(modular.getMachine(), saver.getProgress()));
		}
	}

	@Override
	public void updateServer(IModular modular, ModuleStack stack) {
		if (ModularUtils.getMachine(modular) == null) {
			return;
		}
		ModuleStack<IModuleMachine, IModuleMachineSaver> stackMachine = ModularUtils.getMachine(modular).getStack();
		IModuleEngineSaver saver = (IModuleEngineSaver) stack.getSaver();
		if (saver.getTimer() > saver.getTimerTotal()) {
			PacketHandler.INSTANCE.sendTo(new PacketModule((TileEntity) modular.getMachine(), stack, true),
					(EntityPlayerMP) WorldUtil.getPlayer(modular.getMachine().getWorldObj(), modular.getMachine().getOwner()));
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
		return new ModuleRenderer.EngineRenderer(moduleStack, ModularUtils.getCasing(modular).getStack());
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModularRenderer getItemRenderer(IModular modular, ModuleStack moduleStack, ItemStack stack) {
		return new ModuleRenderer.EngineRenderer(moduleStack, ModularUtils.getCasing(modular).getStack());
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
