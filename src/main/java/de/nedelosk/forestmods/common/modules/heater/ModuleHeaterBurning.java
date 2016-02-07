package de.nedelosk.forestmods.common.modules.heater;

import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modular.basic.IModularDefault;
import de.nedelosk.forestmods.api.modules.heater.IModuleHeaterSaver;
import de.nedelosk.forestmods.api.modules.inventory.IModuleInventory;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public class ModuleHeaterBurning extends ModuleHeater {

	public ModuleHeaterBurning(String categoryUID, String moduleUID) {
		super(categoryUID, moduleUID);
	}

	@Override
	public void updateServer(IModular modular, ModuleStack stack) {
		IModuleHeaterSaver saver = (IModuleHeaterSaver) stack.getSaver();
		if (saver.getBurnTime() > 0) {
			saver.addHeat(1);
			saver.addBurnTime(-10);
		} else {
			IModuleInventory inventory = ((IModularDefault) modular).getInventoryManager().getInventory(stack);
			ItemStack input = inventory.getStackInSlot(0, stack, (IModularDefault) modular);
			if (TileEntityFurnace.getItemBurnTime(input) > 0) {
				inventory.decrStackSize(0, 1, stack, (IModularDefault) modular);
				saver.setBurnTime(TileEntityFurnace.getItemBurnTime(input));
			}
		}
	}
}
