package de.nedelosk.modularmachines.common.modules.storages;

import java.util.ArrayList;
import java.util.List;

import de.nedelosk.modularmachines.api.energy.IEnergyBuffer;
import de.nedelosk.modularmachines.api.gui.IContainerBase;
import de.nedelosk.modularmachines.api.gui.Widget;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandlerTileEntity;
import de.nedelosk.modularmachines.api.modules.EnumModulePosition;
import de.nedelosk.modularmachines.api.modules.IModuleProperties;
import de.nedelosk.modularmachines.api.modules.Module;
import de.nedelosk.modularmachines.api.modules.handlers.IModuleContentHandler;
import de.nedelosk.modularmachines.api.modules.handlers.IModulePage;
import de.nedelosk.modularmachines.api.modules.handlers.energy.ModuleEnergyBuffer;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.slots.SlotModule;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storages.IModuleBattery;
import de.nedelosk.modularmachines.api.modules.storages.IModuleBatteryProperties;
import de.nedelosk.modularmachines.client.gui.widgets.WidgetEnergyField;
import de.nedelosk.modularmachines.common.modules.handlers.ModulePage;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import de.nedelosk.modularmachines.common.network.packets.PacketSyncModule;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ModuleBattery extends Module implements IModuleBattery {

	private static final String[] tiers = new  String[]{"LV", "MV", "HV", "EV"};

	public ModuleBattery(String name) {
		super(name);
	}

	@Override
	public void sendModuleUpdate(IModuleState state){
		IModularHandler handler = state.getModular().getHandler();
		if(handler instanceof IModularHandlerTileEntity){
			PacketHandler.sendToNetwork(new PacketSyncModule(handler, state), ((IModularHandlerTileEntity)handler).getPos(), (WorldServer) handler.getWorld());
		}
	}


	@Override
	public EnumModulePosition getPosition(IModuleContainer container) {
		return EnumModulePosition.INTERNAL;
	}

	@Override
	public void addTooltip(List<String> tooltip, ItemStack stack, IModuleContainer container) {
		super.addTooltip(tooltip, stack, container);
		tooltip.add(I18n.translateToLocal("mm.module.tooltip.tier") + getTier(container));
	}

	@Override
	public int getCapacity(IModuleState state) {
		IModuleProperties properties = state.getContainer().getProperties();
		if(properties instanceof IModuleBatteryProperties){
			return ((IModuleBatteryProperties) properties).getCapacity(state);
		}
		return 0;
	}

	@Override
	public int getMaxReceive(IModuleState state) {
		IModuleProperties properties = state.getContainer().getProperties();
		if(properties instanceof IModuleBatteryProperties){
			return ((IModuleBatteryProperties) properties).getMaxReceive(state);
		}
		return 0;
	}

	@Override
	public int getMaxExtract(IModuleState state) {
		IModuleProperties properties = state.getContainer().getProperties();
		if(properties instanceof IModuleBatteryProperties){
			return ((IModuleBatteryProperties) properties).getMaxExtract(state);
		}
		return 0;
	}

	@Override
	public int getTier(IModuleContainer container) {
		IModuleProperties properties = container.getProperties();
		if(properties instanceof IModuleBatteryProperties){
			return ((IModuleBatteryProperties) properties).getTier(container);
		}
		return 0;
	}

	@Override
	public List<IModuleContentHandler> createHandlers(IModuleState state) {
		List<IModuleContentHandler> handlers = super.createHandlers(state);
		handlers.add(new ModuleEnergyBuffer(state, getCapacity(state), getMaxReceive(state), getMaxExtract(state), getTier(state.getContainer())));
		return handlers;
	}

	@Override
	public IModuleState loadStateFromItem(IModuleState state, ItemStack stack) {
		IEnergyBuffer energyBuffer = (IEnergyBuffer) state.getContentHandler(IEnergyBuffer.class);
		if(energyBuffer != null){
			energyBuffer.loadEnergy(getStorageEnergy(state, stack));
		}
		return state;
	}

	@Override
	public ItemStack saveDataToItem(IModuleState state) {
		ItemStack stack = super.saveDataToItem(state);
		IEnergyBuffer energyBuffer = (IEnergyBuffer) state.getContentHandler(IEnergyBuffer.class);
		if(energyBuffer != null){
			saveEnergy(state, energyBuffer.getEnergyStored(), stack);
		}
		return stack;
	}

	@Override
	public List<IModulePage> createPages(IModuleState state) {
		List<IModulePage> pages = super.createPages(state);
		pages.add(new BatteryPage("Basic", state));
		return pages;
	}

	public class BatteryPage extends ModulePage<IModuleBattery> {

		public BatteryPage(String pageID, IModuleState<IModuleBattery> state) {
			super(pageID, "battery", state);
		}

		@SideOnly(Side.CLIENT)
		@Override
		public void updateGui() {
			super.updateGui();
			for(Widget widget : (ArrayList<Widget>) gui.getWidgetManager().getWidgets()) {
				if (widget instanceof WidgetEnergyField) {
					((WidgetEnergyField) widget).energyBuffer = state.getContentHandler(IEnergyBuffer.class);
				}
			}
		}

		@Override
		public void createSlots(IContainerBase<IModularHandler> container, List<SlotModule> modularSlots) {
		}

		@SideOnly(Side.CLIENT)
		@Override
		public void addWidgets() {
			gui.getWidgetManager().add(new WidgetEnergyField(state.getContentHandler(IEnergyBuffer.class), 55, 15));
		}
	}
}