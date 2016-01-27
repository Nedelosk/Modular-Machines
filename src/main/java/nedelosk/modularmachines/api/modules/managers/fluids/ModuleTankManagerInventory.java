package nedelosk.modularmachines.api.modules.managers.fluids;

import java.util.ArrayList;
import java.util.List;

import nedelosk.forestcore.library.inventory.IContainerBase;
import nedelosk.modularmachines.api.inventory.slots.SlotTankManager;
import nedelosk.modularmachines.api.modular.basic.IModularInventory;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.modules.fluids.IModuleTank;
import nedelosk.modularmachines.api.modules.inventory.ModuleInventory;
import nedelosk.modularmachines.api.utils.ModuleRegistry;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ModuleTankManagerInventory<M extends IModuleTankManager> extends ModuleInventory<M> {

	public ModuleTankManagerInventory(String categoryUID, String moduleUID, int slots) {
		super(categoryUID, moduleUID, slots);
	}

	@Override
	public List<Slot> addSlots(IContainerBase container, IModularInventory modular, ModuleStack<M> stack) {
		ArrayList<Slot> slots = new ArrayList<Slot>();
		int tab = ((IModuleTankManagerSaver) stack.getSaver()).getTab();
		int i = 0;
		for ( int ID = tab * 3; ID < (tab + 1) * 3; ID++ ) {
			if (!(getSizeInventory(stack, modular) <= ID)) {
				slots.add(new SlotTankManager<M>(modular.getMachine(), ID, 37 + i * 51, 87, stack, ID));
				i++;
			}
		}
		return slots;
	}

	@Override
	public boolean transferInput(ModuleStack<M> stackModule, IModularTileEntity tile, EntityPlayer player, int slotID, Container container,
			ItemStack stackItem) {
		ModuleStack stack = ModuleRegistry.getModuleFromItem(stackItem).moduleStack;
		if (stack != null && stack.getModule() != null && stack.getModule() instanceof IModuleTank) {
			if (mergeItemStack(stackItem, 36, container.inventorySlots.size(), false, container)) {
				return true;
			}
		}
		return false;
	}
}
