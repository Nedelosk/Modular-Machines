package nedelosk.modularmachines.api.inventory.slots;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.inventory.SlotModular;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.modules.fluids.IModuleTank;
import nedelosk.modularmachines.api.modules.managers.fluids.IModuleTankManager;
import nedelosk.modularmachines.api.modules.managers.fluids.IModuleTankManagerSaver;
import nedelosk.modularmachines.api.utils.ModuleCategoryUIDs;
import nedelosk.modularmachines.api.utils.ModuleRegistry;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class SlotTankManager<M extends IModuleTankManager> extends SlotModular<M> {

	protected int ID;

	public SlotTankManager(IInventory inv, int p_i1824_2_, int x, int y, ModuleStack stack, int ID) {
		super(inv, p_i1824_2_, x, y, stack);
		this.ID = ID;
	}

	@Override
	public int getSlotStackLimit() {
		return 1;
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		ModuleStack stackModule = ModuleRegistry.getModuleFromItem(stack).moduleStack;
		if (stackModule != null && stackModule.getModule() != null && stackModule.getModule() instanceof IModuleTank) {
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
		if (module != null && module.getModule() != null && module.getModule() instanceof IModuleTank) {
			((IModuleTank) module.getModule()).setStorageFluid(getSaver().getData(ID).getTank().getFluid(), module, stack);
			moduleStack.getModule().addTank(ID, null, moduleStack);
			modular.getMultiModule(ModuleCategoryUIDs.TANKS).addStack(ID, null);
			tileModular.getWorldObj().markBlockForUpdate(tileModular.getXCoord(), tileModular.getYCoord(), tileModular.getZCoord());
		}
	}

	@Override
	public void putStack(ItemStack stack) {
		super.putStack(stack);
		IModularTileEntity tileModular = (IModularTileEntity) inventory;
		IModular modular = tileModular.getModular();
		ModuleStack module = ModuleRegistry.getModuleFromItem(stack).moduleStack;
		if (module != null && module.getModule() != null && module.getModule() instanceof IModuleTank) {
			moduleStack.getModule().addTank(ID, (IModuleTank) module.getModule(), moduleStack);
			getSaver().getData(ID).getTank().setFluid(((IModuleTank) module.getModule()).getStorageFluid(module, stack));
			modular.getMultiModule(ModuleCategoryUIDs.TANKS).addStack(ID, module);
			tileModular.getWorldObj().markBlockForUpdate(tileModular.getXCoord(), tileModular.getYCoord(), tileModular.getZCoord());
		}
	}

	private IModuleTankManagerSaver getSaver() {
		return (IModuleTankManagerSaver) moduleStack.getSaver();
	}
}
