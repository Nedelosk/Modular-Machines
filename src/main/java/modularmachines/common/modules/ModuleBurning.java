package modularmachines.common.modules;

import modularmachines.api.modules.IModuleStorage;
import modularmachines.api.modules.Module;
import net.minecraft.nbt.NBTTagCompound;

public class ModuleBurning extends Module {

	private static final String BURNTIME_KEY = "BurnTime";
	
	protected int burnTime;
	protected int burnTimeTotal;
	
	public ModuleBurning(IModuleStorage storage) {
		super(storage);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger(BURNTIME_KEY, burnTime);
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		burnTime = compound.getInteger(BURNTIME_KEY);
	}
	
	public void addBurnTime(int burnTime) {
		this.burnTime += burnTime;
	}
	
	public void setBurnTime(int burnTime) {
		this.burnTime = burnTime;
	}
	
	public void setBurnTimeTotal(int burnTimeTotal) {
		this.burnTimeTotal = burnTimeTotal;
	}

	
	public int getBurnTime() {
		return burnTime;
	}
	
	public int getBurnTimeTotal() {
		return burnTimeTotal;
	}

}
