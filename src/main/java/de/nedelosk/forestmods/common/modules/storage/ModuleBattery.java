package de.nedelosk.forestmods.common.modules.storage;

import java.util.ArrayList;
import java.util.List;

import cofh.api.energy.EnergyStorage;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestcore.gui.IGuiBase;
import de.nedelosk.forestcore.gui.Widget;
import de.nedelosk.forestcore.inventory.IContainerBase;
import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modular.managers.IModularModuleManager;
import de.nedelosk.forestmods.api.modular.managers.IModularUtilsManager;
import de.nedelosk.forestmods.api.modular.renderer.IRenderState;
import de.nedelosk.forestmods.api.modular.renderer.ISimpleRenderer;
import de.nedelosk.forestmods.api.modules.special.IModuleController;
import de.nedelosk.forestmods.api.modules.storage.battery.IModuleBattery;
import de.nedelosk.forestmods.api.producers.handlers.IModulePage;
import de.nedelosk.forestmods.api.producers.handlers.gui.IModuleGuiBuilder;
import de.nedelosk.forestmods.api.producers.handlers.inventory.IModuleInventoryBuilder;
import de.nedelosk.forestmods.api.producers.handlers.inventory.slots.SlotModule;
import de.nedelosk.forestmods.api.producers.handlers.tank.IModuleTankBuilder;
import de.nedelosk.forestmods.api.utils.ModularException;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.client.gui.widgets.WidgetEnergyField;
import de.nedelosk.forestmods.client.render.modules.BatteryRenderer;
import de.nedelosk.forestmods.common.modular.handlers.EnergyHandler;
import de.nedelosk.forestmods.common.producers.Producer;
import de.nedelosk.forestmods.common.producers.handlers.ProducerPage;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class ModuleBattery extends Producer implements IModuleBattery {

	protected int batteryCapacity;
	protected int speedModifier;
	protected int energyModifier;
	protected EnergyStorage storage;
	protected final EnergyStorage defaultStorage;

	public ModuleBattery(EnergyStorage defaultStorage) {
		this.defaultStorage = defaultStorage;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt, IModular modular) {
		nbt.setInteger("BatteryCapacity", batteryCapacity);
		nbt.setInteger("speedModifier", speedModifier);
		nbt.setInteger("energyModifier", energyModifier);
		NBTTagCompound nbtTag = new NBTTagCompound();
		storage.writeToNBT(nbtTag);
		nbtTag.setInteger("Capacity", storage.getMaxEnergyStored());
		nbtTag.setInteger("MaxReceive", storage.getMaxReceive());
		nbtTag.setInteger("MaxExtract", storage.getMaxExtract());
		nbt.setTag("EnergyStorage", nbtTag);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular) {
		batteryCapacity = nbt.getInteger("BatteryCapacity");
		speedModifier = nbt.getInteger("speedModifier");
		energyModifier = nbt.getInteger("energyModifier");
		NBTTagCompound nbtTag = nbt.getCompoundTag("EnergyStorage");
		storage = new EnergyStorage(nbtTag.getInteger("Capacity"), nbtTag.getInteger("MaxReceive"), nbtTag.getInteger("MaxExtract"));
		storage.readFromNBT(nbtTag);
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
	public ISimpleRenderer getRenderer(ModuleStack moduleStack, IRenderState state) {
		return new BatteryRenderer(moduleStack, state.getModular());
	}

	@Override
	public void onModularAssembled(IModular modular, ModuleStack stack, ModuleStack<IModuleController> controller, List<ModuleStack> modules)
			throws ModularException {
		ItemStack itemStack = modular.getManager(IModularModuleManager.class).getItemStack(stack.getUID());
		int energy = getStorageEnergy(stack, itemStack.copy());
		EnergyStorage storage = new EnergyStorage(getDefaultStorage().getMaxEnergyStored(), getDefaultStorage().getMaxReceive(),
				getDefaultStorage().getMaxExtract());
		storage.setEnergyStored(energy);
		setStorage(storage);
		modular.getManager(IModularUtilsManager.class).setEnergyHandler(new EnergyHandler(modular));
	}

	@Override
	public ItemStack getDropItem(ModuleStack stackModule, IModular modular) {
		ItemStack stack = modular.getManager(IModularModuleManager.class).getItemStack(stackModule.getUID());
		setStorageEnergy(stackModule, modular.getManager(IModularUtilsManager.class).getEnergyHandler().getEnergyStored(ForgeDirection.UNKNOWN), stack);
		return stack;
	}

	@Override
	protected IModulePage[] createPages() {
		IModulePage[] pages = new IModulePage[] { new BatteryPage(0, modular, moduleStack) };
		return null;
	}

	public static class BatteryPage extends ProducerPage<IModuleBattery> {

		public BatteryPage(int pageID, IModular modular, ModuleStack moduleStack) {
			super(pageID, modular, moduleStack);
		}

		@Override
		public void updateGui(IGuiBase base, int x, int y) {
			super.updateGui(base, x, y);
			for ( Widget widget : (ArrayList<Widget>) base.getWidgetManager().getWidgets() ) {
				if (widget instanceof WidgetEnergyField) {
					((WidgetEnergyField) widget).storage = moduleStack.getModule().getStorage();
				}
			}
		}

		@Override
		public void createHandlers(IModuleInventoryBuilder invBuilder, IModuleTankBuilder tankBuilder) {
		}

		@Override
		public void createSlots(IContainerBase container, List<SlotModule> modularSlots) {
		}

		@SideOnly(Side.CLIENT)
		@Override
		public void createGui(IModuleGuiBuilder guiBuilder) {
			guiBuilder.addWidget(new WidgetEnergyField(moduleStack.getModule().getStorage(), 55, 15));
		}
	}
}