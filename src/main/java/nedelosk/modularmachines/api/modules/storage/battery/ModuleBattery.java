package nedelosk.modularmachines.api.modules.storage.battery;

import cofh.api.energy.EnergyStorage;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.client.renderer.IModularRenderer;
import nedelosk.modularmachines.api.client.renderer.ModularMachineRenderer;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.handlers.EnergyHandler;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.modules.IModuleSaver;
import nedelosk.modularmachines.api.modules.ModuleAddable;
import nedelosk.modularmachines.api.modules.gui.IModuleGui;
import nedelosk.modularmachines.api.utils.ModularException;
import nedelosk.modularmachines.api.utils.ModuleCategoryUIDs;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.item.ItemStack;

public class ModuleBattery extends ModuleAddable implements IModuleBattery {

	public final EnergyStorage storage;

	public ModuleBattery(String moduleUID, EnergyStorage storage) {
		super(ModuleCategoryUIDs.BATTERY, moduleUID);
		this.storage = storage;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModularRenderer getMachineRenderer(IModular modular, ModuleStack moduleStack, IModularTileEntity tile) {
		return new ModularMachineRenderer.BatteryRenderer(moduleStack, modular);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModularRenderer getItemRenderer(IModular modular, ModuleStack moduleStack, ItemStack stack) {
		return new ModularMachineRenderer.BatteryRenderer(moduleStack, modular);
	}

	@Override
	public void onAddInModular(IModular modular, ModuleStack stack) throws ModularException {
		IModuleBatterySaver saver = (IModuleBatterySaver) stack.getSaver();
		modular.getUtilsManager().setEnergyHandler(new EnergyHandler(saver.getStorage(stack)));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModuleGui createGui(ModuleStack stack) {
		return new ModuleBatteryGui(getUID());
	}

	@Override
	public IModuleSaver createSaver(ModuleStack stack) {
		return new ModuleBatterySaver(storage);
	}
}
