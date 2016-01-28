package nedelosk.modularmachines.api.inventory.slots;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.inventory.SlotModular;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.modules.energy.IModuleCapacitor;
import nedelosk.modularmachines.api.utils.ModuleCategoryUIDs;
import nedelosk.modularmachines.api.utils.ModuleRegistry;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class SlotBattery extends SlotModular {

	public SlotBattery(IInventory inv, int ID, int x, int y, ModuleStack stack) {
		super(inv, ID, x, y, stack);
	}

	@Override
	public int getSlotStackLimit() {
		return 1;
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		ModuleStack stackModule = ModuleRegistry.getModuleFromItem(stack).moduleStack;
		if (stackModule != null && stackModule.getModule() != null && stackModule.getModule() instanceof IModuleCapacitor) {
			return true;
		}
		return false;
	}

	@Override
	public void onPickupFromSlot(EntityPlayer player, ItemStack stack) {
		super.onPickupFromSlot(player, stack);
		IModularTileEntity tileModular = (IModularTileEntity) inventory;
		IModular modular = tileModular.getModular();
		ModuleStack module = ModuleRegistry.getModuleFromItem(stack).moduleStack;
		if (module != null && module.getModule() != null && module.getModule() instanceof IModuleCapacitor) {
			modular.getMultiModule(ModuleCategoryUIDs.TANKS).addStack(getSlotIndex(), null);
			tileModular.getWorldObj().markBlockForUpdate(tileModular.getXCoord(), tileModular.getYCoord(), tileModular.getZCoord());
		}
	}

	@Override
	public void putStack(ItemStack stack) {
		super.putStack(stack);
		IModularTileEntity tileModular = (IModularTileEntity) inventory;
		IModular modular = tileModular.getModular();
		ModuleStack module = ModuleRegistry.getModuleFromItem(stack).moduleStack;
		if (module != null && module.getModule() != null && module.getModule() instanceof IModuleCapacitor) {
			modular.getMultiModule(ModuleCategoryUIDs.TANKS).addStack(getSlotIndex(), module);
			tileModular.getWorldObj().markBlockForUpdate(tileModular.getXCoord(), tileModular.getYCoord(), tileModular.getZCoord());
		}
	}
}
