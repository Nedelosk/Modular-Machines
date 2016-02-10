package de.nedelosk.forestmods.common.modules.storage;

import cofh.api.energy.EnergyStorage;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modular.IModularRenderer;
import de.nedelosk.forestmods.api.modular.tile.IModularTileEntity;
import de.nedelosk.forestmods.api.modules.IModuleSaver;
import de.nedelosk.forestmods.api.modules.gui.IModuleGui;
import de.nedelosk.forestmods.api.modules.storage.battery.IModuleBattery;
import de.nedelosk.forestmods.api.modules.storage.battery.IModuleBatterySaver;
import de.nedelosk.forestmods.api.modules.storage.battery.IModuleBatteryType;
import de.nedelosk.forestmods.api.utils.ModularException;
import de.nedelosk.forestmods.api.utils.ModuleCategoryUIDs;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.client.render.modules.BatteryRenderer;
import de.nedelosk.forestmods.common.modular.handlers.EnergyHandler;
import de.nedelosk.forestmods.common.modules.ModuleAddable;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class ModuleBattery extends ModuleAddable implements IModuleBattery {

	public ModuleBattery(String moduleUID) {
		super(ModuleCategoryUIDs.BATTERY, moduleUID);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModularRenderer getMachineRenderer(IModular modular, ModuleStack moduleStack, IModularTileEntity tile) {
		return new BatteryRenderer(moduleStack, modular);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModularRenderer getItemRenderer(IModular modular, ModuleStack moduleStack, ItemStack stack) {
		return new BatteryRenderer(moduleStack, modular);
	}

	@Override
	public void onAddInModular(IModular modular, ModuleStack stack) throws ModularException {
		IModuleBatterySaver saver = (IModuleBatterySaver) stack.getSaver();
		IModuleBatteryType type = (IModuleBatteryType) stack.getType();
		int energy = getStorageEnergy(stack, stack.getItemStack().copy());
		EnergyStorage storage = new EnergyStorage(type.getDefaultStorage().getMaxEnergyStored(), type.getDefaultStorage().getMaxReceive(),
				type.getDefaultStorage().getMaxExtract());
		storage.setEnergyStored(energy);
		saver.setStorage(storage);
		modular.getUtilsManager().setEnergyHandler(new EnergyHandler(modular));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModuleGui createGui(ModuleStack stack) {
		return new ModuleBatteryGui(getUID());
	}

	@Override
	public IModuleSaver createSaver(ModuleStack stack) {
		return new ModuleBatterySaver();
	}

	@Override
	public ItemStack onDropItem(ModuleStack stackModule, IModular modular) {
		ItemStack stack = stackModule.getItemStack().copy();
		setStorageEnergy(modular.getUtilsManager().getEnergyHandler().getEnergyStored(ForgeDirection.UNKNOWN), stackModule, stack);
		return stack;
	}
}
