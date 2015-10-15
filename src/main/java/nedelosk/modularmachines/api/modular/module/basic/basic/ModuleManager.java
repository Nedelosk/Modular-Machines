package nedelosk.modularmachines.api.modular.module.basic.basic;

import nedelosk.modularmachines.api.modular.module.basic.inventory.ModuleInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class ModuleManager extends ModuleInventory implements IModuleManager {

	public ForgeDirection side;

	public ModuleManager() {
		super();
	}
	
	public ModuleManager(ForgeDirection side) {
		super();
		this.side = side;
	}
	
	public ModuleManager(ForgeDirection side, String modifier) {
		super(modifier);
		this.side = side;
	}
	
	public ModuleManager(NBTTagCompound nbt) {
		super(nbt);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("Side", side.ordinal());
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		side = ForgeDirection.values()[nbt.getInteger("Side")];
	}
	
	@Override
	public ForgeDirection getSide() {
		return side;
	}
	
}
