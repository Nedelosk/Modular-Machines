package de.nedelosk.modularmachines.common.modules.storage;

import java.util.ArrayList;
import java.util.List;

import cofh.api.energy.EnergyStorage;
import de.nedelosk.modularmachines.api.inventory.IContainerBase;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModularTileEntity;
import de.nedelosk.modularmachines.api.modular.assembler.IAssembler;
import de.nedelosk.modularmachines.api.modular.assembler.IAssemblerGroup;
import de.nedelosk.modularmachines.api.modular.renderer.IRenderState;
import de.nedelosk.modularmachines.api.modular.renderer.ISimpleRenderer;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.handlers.IModulePage;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.slots.SlotModule;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.state.PropertyEnergyStorage;
import de.nedelosk.modularmachines.api.modules.storage.IModuleBattery;
import de.nedelosk.modularmachines.client.gui.Widget;
import de.nedelosk.modularmachines.client.gui.widgets.WidgetEnergyField;
import de.nedelosk.modularmachines.client.render.modules.BatteryRenderer;
import de.nedelosk.modularmachines.common.modular.assembler.AssemblerGroup;
import de.nedelosk.modularmachines.common.modular.assembler.AssemblerSlot;
import de.nedelosk.modularmachines.common.modular.handlers.EnergyHandler;
import de.nedelosk.modularmachines.common.modules.Module;
import de.nedelosk.modularmachines.common.modules.handlers.ModulePage;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ModuleBattery extends Module implements IModuleBattery {

	public static PropertyEnergyStorage storage = new PropertyEnergyStorage("storage");

	protected final EnergyStorage defaultStorage;

	public ModuleBattery(EnergyStorage defaultStorage) {
		this.defaultStorage = defaultStorage;
	}

	@Override
	public EnergyStorage getStorage(IModuleState state) {
		return (EnergyStorage) state.get(storage);
	}

	@Override
	public void setStorage(IModuleState state, EnergyStorage storage) {
		state.add(ModuleBattery.storage, storage);
	}

	@Override
	public EnergyStorage getDefaultStorage(IModuleState state) {
		return defaultStorage;
	}

	@Override
	public IModuleState createState(IModular modular, IModuleContainer container) {
		return super.createState(modular, container).add(storage, null);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public ISimpleRenderer getRenderer(IRenderState state) {
		return new BatteryRenderer(state.getModuleState().getContainer(), state.getModular());
	}

	@Override
	public IModuleState loadStateFromItem(IModuleState state, ItemStack stack) {
		int energy = getStorageEnergy(this, stack.copy());
		EnergyStorage storage = new EnergyStorage(getDefaultStorage(state).getMaxEnergyStored(), getDefaultStorage(state).getMaxReceive(),
				getDefaultStorage(state).getMaxExtract());
		storage.setEnergyStored(energy);
		setStorage(state, storage);
		state.getModular().setEnergyHandler(new EnergyHandler(state.getModular()));
		return state;
	}

	@Override
	public ItemStack getDropItem(IModuleState state) {
		ItemStack stack = super.getDropItem(state);
		if (state.getModular().getEnergyHandler() != null) {
			setStorageEnergy(this, state.getModular().getEnergyHandler().getEnergyStored(null), stack);
		}
		return stack;
	}

	@Override
	public IModulePage[] createPages(IModuleState state) {
		return new IModulePage[] { new BatteryPage(0, state) };
	}

	public static class BatteryPage extends ModulePage<IModuleBattery> {

		public BatteryPage(int pageID, IModuleState<IModuleBattery> state) {
			super(pageID, state);
		}

		@SideOnly(Side.CLIENT)
		@Override
		public void updateGui(int x, int y) {
			super.updateGui(x, y);
			for(Widget widget : (ArrayList<Widget>) gui.getWidgetManager().getWidgets()) {
				if (widget instanceof WidgetEnergyField) {
					((WidgetEnergyField) widget).storage = state.getModule().getStorage(state);
				}
			}
		}

		@Override
		public void createSlots(IContainerBase<IModularTileEntity> container, List<SlotModule> modularSlots) {
		}

		@SideOnly(Side.CLIENT)
		@Override
		public void addWidgets(List widgets) {
			super.addWidgets(widgets);
			widgets.add(new WidgetEnergyField(state.getModule().getStorage(state), 55, 15));
		}
	}

	@Override
	public boolean canAssembleGroup(IAssemblerGroup group) {
		return true;
	}

	@Override
	public IAssemblerGroup createGroup(IAssembler assembler, ItemStack stack, int groupID) {
		IAssemblerGroup group = new AssemblerGroup(assembler, groupID);
		group.setControllerSlot(new AssemblerSlot(group, 4, 4, assembler.getNextIndex(), "battery", IModuleBattery.class));
		return group;
	}
}