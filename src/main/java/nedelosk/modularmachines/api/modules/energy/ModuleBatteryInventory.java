package nedelosk.modularmachines.api.modules.energy;

import java.util.ArrayList;
import java.util.List;

import nedelosk.forestcore.library.inventory.IContainerBase;
import nedelosk.modularmachines.api.inventory.slots.SlotBattery;
import nedelosk.modularmachines.api.modular.basic.IModularInventory;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.modules.inventory.ModuleInventory;
import nedelosk.modularmachines.api.utils.ModuleRegistry;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ModuleBatteryInventory<M extends IModuleBattery> extends ModuleInventory<M> {

	public ModuleBatteryInventory(String categoryUID, String moduleUID, int slots) {
		super(categoryUID, moduleUID, slots);
	}

	@Override
	public List<Slot> addSlots(IContainerBase container, IModularInventory modular, ModuleStack<M> stack) {
		ArrayList<Slot> list = new ArrayList<Slot>();
		list.add(new SlotBattery(modular.getMachine(), 0, 143, 17, stack));
		list.add(new SlotBattery(modular.getMachine(), 1, 143, 35, stack));
		list.add(new SlotBattery(modular.getMachine(), 2, 143, 53, stack));
		return list;
	}

	@Override
	public boolean transferInput(ModuleStack<M> stackModule, IModularTileEntity tile, EntityPlayer player, int slotID, Container container,
			ItemStack stackItem) {
		ModuleStack<IModule> stack = ModuleRegistry.getModuleFromItem(stackItem).moduleStack;
		if (stack != null && stack.getModule() != null && stack.getModule() instanceof IModuleCapacitor) {
			if (mergeItemStack(stackItem, 36, 39, false, container)) {
				return true;
			}
		}
		return false;
	}
}
