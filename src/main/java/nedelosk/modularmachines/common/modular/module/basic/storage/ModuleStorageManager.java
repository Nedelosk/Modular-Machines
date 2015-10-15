package nedelosk.modularmachines.common.modular.module.basic.storage;

import java.util.ArrayList;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.module.basic.basic.ModuleManager;
import nedelosk.modularmachines.api.modular.module.basic.storage.IModuleStorageManager;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.nedeloskcore.api.machines.IContainerBase;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public class ModuleStorageManager extends ModuleManager implements IModuleStorageManager {

	public ModuleStorageManager() {
		super();
	}
	
	public ModuleStorageManager(ForgeDirection side) {
		super(side);
	}
	
	public ModuleStorageManager(ForgeDirection side, String modifier) {
		super(side, modifier);
	}
	
	public ModuleStorageManager(NBTTagCompound nbt) {
		super(nbt);
	}

	@Override
	public String getModuleName() {
		return "StorageManager";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return true;
	}

	@Override
	public int getColor() {
		return 0xC78C2D;
	}

	@Override
	public ArrayList<Slot> addSlots(IContainerBase container, IModular modular, ModuleStack stack) {
		return null;
	}

	@Override
	public int getSizeInventory(ModuleStack stack) {
		return 0;
	}

}
