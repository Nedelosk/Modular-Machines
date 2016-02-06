package de.nedelosk.forestmods.common.modules.storage;

import cofh.api.energy.EnergyStorage;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestmods.api.client.IModularRenderer;
import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modular.tile.IModularTileEntity;
import de.nedelosk.forestmods.api.modules.IModuleSaver;
import de.nedelosk.forestmods.api.modules.gui.IModuleGui;
import de.nedelosk.forestmods.api.modules.storage.battery.IModuleBattery;
import de.nedelosk.forestmods.api.modules.storage.battery.IModuleBatterySaver;
import de.nedelosk.forestmods.api.utils.ModularException;
import de.nedelosk.forestmods.api.utils.ModuleCategoryUIDs;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.client.render.modules.ModuleRenderer;
import de.nedelosk.forestmods.common.modular.handlers.EnergyHandler;
import de.nedelosk.forestmods.common.modules.ModuleAddable;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class ModuleBattery extends ModuleAddable implements IModuleBattery {

	public final EnergyStorage defaultStorage;

	public ModuleBattery(String moduleUID, EnergyStorage defaultStorage) {
		super(ModuleCategoryUIDs.BATTERY, moduleUID);
		this.defaultStorage = defaultStorage;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModularRenderer getMachineRenderer(IModular modular, ModuleStack moduleStack, IModularTileEntity tile) {
		return new ModuleRenderer.BatteryRenderer(moduleStack, modular);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModularRenderer getItemRenderer(IModular modular, ModuleStack moduleStack, ItemStack stack) {
		return new ModuleRenderer.BatteryRenderer(moduleStack, modular);
	}

	@Override
	public void onAddInModular(IModular modular, ModuleStack stack) throws ModularException {
		IModuleBatterySaver saver = (IModuleBatterySaver) stack.getSaver();
		int energy = getStorageEnergy(stack, stack.getItemStack().copy());
		EnergyStorage storage = new EnergyStorage(defaultStorage.getMaxEnergyStored(), defaultStorage.getMaxReceive(), defaultStorage.getMaxExtract());
		storage.setEnergyStored(energy);
		modular.getUtilsManager().setEnergyHandler(new EnergyHandler(storage));
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
