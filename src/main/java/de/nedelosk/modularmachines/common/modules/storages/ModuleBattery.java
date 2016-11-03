package de.nedelosk.modularmachines.common.modules.storages;

import java.util.Collections;
import java.util.List;

import de.nedelosk.modularmachines.api.energy.IEnergyBuffer;
import de.nedelosk.modularmachines.api.modular.ExpandedStoragePositions;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandlerTileEntity;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.IModulePage;
import de.nedelosk.modularmachines.api.modules.IModuleProperties;
import de.nedelosk.modularmachines.api.modules.ITickable;
import de.nedelosk.modularmachines.api.modules.containers.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.controller.IModuleControl;
import de.nedelosk.modularmachines.api.modules.controller.IModuleControlled;
import de.nedelosk.modularmachines.api.modules.controller.IModuleController;
import de.nedelosk.modularmachines.api.modules.controller.ModuleControl;
import de.nedelosk.modularmachines.api.modules.handlers.IModuleContentHandler;
import de.nedelosk.modularmachines.api.modules.handlers.energy.ModuleEnergyBuffer;
import de.nedelosk.modularmachines.api.modules.position.IStoragePosition;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storage.StorageModule;
import de.nedelosk.modularmachines.api.modules.storage.energy.IModuleBattery;
import de.nedelosk.modularmachines.api.modules.storage.energy.IModuleBatteryProperties;
import de.nedelosk.modularmachines.common.modules.pages.BatteryPage;
import de.nedelosk.modularmachines.common.modules.pages.ControllerPage;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import de.nedelosk.modularmachines.common.network.packets.PacketSyncModule;
import de.nedelosk.modularmachines.common.utils.EnergyUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ModuleBattery extends StorageModule implements IModuleBattery, IModuleControlled, ITickable {

	private static final String[] tiers = new String[] { "LV", "MV", "HV", "EV" };

	public ModuleBattery(String name) {
		super(name);
	}

	@Override
	protected IStoragePosition[] getPositions(IModuleContainer container) {
		return new IStoragePosition[] { ExpandedStoragePositions.LEFT, ExpandedStoragePositions.RIGHT, ExpandedStoragePositions.BACK };
	}

	@Override
	public void sendModuleUpdate(IModuleState state) {
		IModularHandler handler = state.getModular().getHandler();
		if (handler instanceof IModularHandlerTileEntity) {
			PacketHandler.sendToNetwork(new PacketSyncModule(state), ((IModularHandlerTileEntity) handler).getPos(), (WorldServer) handler.getWorld());
		}
	}

	@Override
	public void updateServer(IModuleState<IModule> state, int tickCount) {
		IModuleState<IModuleController> controller = state.getModular().getModule(IModuleController.class);
		if (controller != null && controller.getModule().canWork(controller, state) && tickCount % 2 == 0) {
			IModularHandler modularHandler = state.getModular().getHandler();
			if (modularHandler instanceof IModularHandlerTileEntity) {
				EnergyUtil.transferEnergy(modularHandler.getWorld(), ((IModularHandlerTileEntity) modularHandler).getPos(), state.getContentHandler(IEnergyBuffer.class), getCapacity(state) / 10000, false);
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void updateClient(IModuleState<IModule> state, int tickCount) {
	}

	@Override
	public void addTooltip(List<String> tooltip, ItemStack stack, IModuleContainer container) {
		super.addTooltip(tooltip, stack, container);
		tooltip.add(I18n.translateToLocal("mm.module.tooltip.tier") + getTier(container));
	}

	@Override
	public int getCapacity(IModuleState state) {
		IModuleProperties properties = state.getContainer().getProperties();
		if (properties instanceof IModuleBatteryProperties) {
			return ((IModuleBatteryProperties) properties).getCapacity(state);
		}
		return 0;
	}

	@Override
	public int getMaxReceive(IModuleState state) {
		IModuleProperties properties = state.getContainer().getProperties();
		if (properties instanceof IModuleBatteryProperties) {
			return ((IModuleBatteryProperties) properties).getMaxReceive(state);
		}
		return 0;
	}

	@Override
	public int getMaxExtract(IModuleState state) {
		IModuleProperties properties = state.getContainer().getProperties();
		if (properties instanceof IModuleBatteryProperties) {
			return ((IModuleBatteryProperties) properties).getMaxExtract(state);
		}
		return 0;
	}

	@Override
	public int getTier(IModuleContainer container) {
		IModuleProperties properties = container.getProperties();
		if (properties instanceof IModuleBatteryProperties) {
			return ((IModuleBatteryProperties) properties).getTier(container);
		}
		return 0;
	}

	@Override
	public List<IModuleContentHandler> createHandlers(IModuleState state) {
		List<IModuleContentHandler> handlers = super.createHandlers(state);
		handlers.add(new ModuleEnergyBuffer(state, getCapacity(state), getMaxReceive(state), getMaxExtract(state), getTier(state.getContainer())));
		handlers.add(new ModuleControl(state));
		return handlers;
	}

	@Override
	public IModuleState loadStateFromItem(IModuleState state, ItemStack stack) {
		state = super.loadStateFromItem(state, stack);
		IEnergyBuffer energyBuffer = state.getContentHandler(IEnergyBuffer.class);
		if (energyBuffer != null) {
			energyBuffer.setEnergy(loadEnergy(state, stack));
		}
		return state;
	}

	@Override
	public void saveDataToItem(ItemStack itemStack, IModuleState state) {
		super.saveDataToItem(itemStack, state);
		IEnergyBuffer energyBuffer = state.<IEnergyBuffer> getContentHandler(IEnergyBuffer.class);
		if (energyBuffer != null) {
			saveEnergy(state, energyBuffer.getEnergyStored(), itemStack);
		}
	}

	@Override
	public List<IModulePage> createPages(IModuleState state) {
		List<IModulePage> pages = super.createPages(state);
		pages.add(new BatteryPage(state));
		return pages;
	}

	@Override
	public IModuleControl getModuleControl(IModuleState state) {
		return state.getContentHandler(IModuleControl.class);
	}

	@Override
	public void onModularAssembled(IModuleState state) {
		super.onModularAssembled(state);
		if (state.getModular().getModule(IModuleController.class) != null) {
			state.addPage(new ControllerPage(state));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public List<IModuleState> getUsedModules(IModuleState state) {
		return Collections.emptyList();
	}
}