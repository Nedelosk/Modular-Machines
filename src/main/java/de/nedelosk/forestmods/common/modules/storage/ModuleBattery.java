package de.nedelosk.forestmods.common.modules.storage;

import java.util.ArrayList;
import java.util.List;

import cofh.api.energy.EnergyStorage;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestmods.client.gui.widgets.WidgetEnergyField;
import de.nedelosk.forestmods.client.render.modules.BatteryRenderer;
import de.nedelosk.forestmods.common.modular.assembler.AssemblerGroup;
import de.nedelosk.forestmods.common.modular.assembler.AssemblerSlot;
import de.nedelosk.forestmods.common.modular.handlers.EnergyHandler;
import de.nedelosk.forestmods.common.modules.Module;
import de.nedelosk.forestmods.common.modules.handlers.ModulePage;
import de.nedelosk.forestmods.library.gui.Widget;
import de.nedelosk.forestmods.library.inventory.IContainerBase;
import de.nedelosk.forestmods.library.modular.IModular;
import de.nedelosk.forestmods.library.modular.IModularTileEntity;
import de.nedelosk.forestmods.library.modular.assembler.IAssembler;
import de.nedelosk.forestmods.library.modular.assembler.IAssemblerGroup;
import de.nedelosk.forestmods.library.modular.renderer.IRenderState;
import de.nedelosk.forestmods.library.modular.renderer.ISimpleRenderer;
import de.nedelosk.forestmods.library.modules.IModuleContainer;
import de.nedelosk.forestmods.library.modules.handlers.IModulePage;
import de.nedelosk.forestmods.library.modules.handlers.inventory.IModuleInventoryBuilder;
import de.nedelosk.forestmods.library.modules.handlers.inventory.slots.SlotModule;
import de.nedelosk.forestmods.library.modules.handlers.tank.IModuleTankBuilder;
import de.nedelosk.forestmods.library.modules.storage.IModuleBattery;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class ModuleBattery extends Module implements IModuleBattery {

	protected int batteryCapacity;
	protected int speedModifier;
	protected int energyModifier;
	protected EnergyStorage storage;
	protected final EnergyStorage defaultStorage;

	public ModuleBattery(IModular modular, IModuleContainer container, EnergyStorage defaultStorage) {
		super(modular, container);
		this.defaultStorage = defaultStorage;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt, IModular modular) {
		super.writeToNBT(nbt, modular);
		nbt.setInteger("BatteryCapacity", batteryCapacity);
		nbt.setInteger("speedModifier", speedModifier);
		nbt.setInteger("energyModifier", energyModifier);
		if (storage != null) {
			NBTTagCompound nbtTag = new NBTTagCompound();
			storage.writeToNBT(nbtTag);
			nbtTag.setInteger("Capacity", storage.getMaxEnergyStored());
			nbtTag.setInteger("MaxReceive", storage.getMaxReceive());
			nbtTag.setInteger("MaxExtract", storage.getMaxExtract());
			nbt.setTag("EnergyStorage", nbtTag);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular) {
		super.readFromNBT(nbt, modular);
		batteryCapacity = nbt.getInteger("BatteryCapacity");
		speedModifier = nbt.getInteger("speedModifier");
		energyModifier = nbt.getInteger("energyModifier");
		if (nbt.hasKey("EnergyStorage")) {
			NBTTagCompound nbtTag = nbt.getCompoundTag("EnergyStorage");
			storage = new EnergyStorage(nbtTag.getInteger("Capacity"), nbtTag.getInteger("MaxReceive"), nbtTag.getInteger("MaxExtract"));
			storage.readFromNBT(nbtTag);
		}
	}

	@Override
	public int getSpeedModifier() {
		return speedModifier;
	}

	@Override
	public void setSpeedModifier(int speedModifier) {
		this.speedModifier = speedModifier;
	}

	@Override
	public int getBatteryCapacity() {
		return batteryCapacity;
	}

	@Override
	public void setBatteryCapacity(int batteryCapacity) {
		this.batteryCapacity = batteryCapacity;
	}

	@Override
	public int getEnergyModifier() {
		return energyModifier;
	}

	@Override
	public void setEnergyModifier(int energyModifier) {
		this.energyModifier = energyModifier;
	}

	@Override
	public EnergyStorage getStorage() {
		return storage;
	}

	@Override
	public void setStorage(EnergyStorage storage) {
		this.storage = storage;
	}

	@Override
	public EnergyStorage getDefaultStorage() {
		return defaultStorage;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public ISimpleRenderer getRenderer(IRenderState state) {
		return new BatteryRenderer(container, state.getModular());
	}

	@Override
	public void loadDataFromItem(ItemStack stack) {
		int energy = getStorageEnergy(this, stack.copy());
		EnergyStorage storage = new EnergyStorage(getDefaultStorage().getMaxEnergyStored(), getDefaultStorage().getMaxReceive(),
				getDefaultStorage().getMaxExtract());
		storage.setEnergyStored(energy);
		setStorage(storage);
		modular.setEnergyHandler(new EnergyHandler(modular));
	}

	@Override
	public ItemStack getDropItem() {
		ItemStack stack = super.getDropItem();
		if (modular.getEnergyHandler() != null) {
			setStorageEnergy(this, modular.getEnergyHandler().getEnergyStored(ForgeDirection.UNKNOWN), stack);
		}
		return stack;
	}

	@Override
	protected IModulePage[] createPages() {
		return new IModulePage[] { new BatteryPage(0, modular, this) };
	}

	public static class BatteryPage extends ModulePage<IModuleBattery> {

		public BatteryPage(int pageID, IModular modular, IModuleBattery module) {
			super(pageID, modular, module);
		}

		@SideOnly(Side.CLIENT)
		@Override
		public void updateGui(int x, int y) {
			super.updateGui(x, y);
			for(Widget widget : (ArrayList<Widget>) gui.getWidgetManager().getWidgets()) {
				if (widget instanceof WidgetEnergyField) {
					((WidgetEnergyField) widget).storage = module.getStorage();
				}
			}
		}

		@Override
		public void createHandlers(IModuleInventoryBuilder invBuilder, IModuleTankBuilder tankBuilder) {
		}

		@Override
		public void createSlots(IContainerBase<IModularTileEntity> container, List<SlotModule> modularSlots) {
		}

		@SideOnly(Side.CLIENT)
		@Override
		public void addWidgets(List widgets) {
			super.addWidgets(widgets);
			widgets.add(new WidgetEnergyField(module.getStorage(), 55, 15));
		}
	}

	@Override
	public boolean canAssembleGroup(IAssemblerGroup group) {
		return true;
	}

	@Override
	public IAssemblerGroup createGroup(IAssembler assembler, ItemStack stack, int groupID) {
		IAssemblerGroup group = new AssemblerGroup(assembler, groupID);
		group.setControllerSlot(new AssemblerSlot(group, 4, 4, assembler.getNextIndex(group), "battery", IModuleBattery.class));
		return group;
	}
}