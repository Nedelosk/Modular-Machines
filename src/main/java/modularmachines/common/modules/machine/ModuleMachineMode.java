package modularmachines.common.modules.machine;

import modularmachines.api.modules.IModuleStorage;
import modularmachines.api.recipes.IMode;
import modularmachines.common.modules.IModuleMode;
import net.minecraft.nbt.NBTTagCompound;

public abstract class ModuleMachineMode extends ModuleMachine implements IModuleMode {

	public final IMode defaultMode;
	public IMode mode;

	public ModuleMachineMode(IModuleStorage storage, int workTimeModifier, IMode defaultMode) {
		super(storage, workTimeModifier);
		this.defaultMode = defaultMode;
		this.mode = defaultMode;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("Mode", mode.ordinal());
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		setCurrentMode(compound.getInteger("Mode"));
	}

	@Override
	public IMode getCurrentMode() {
		return mode;
	}

	@Override
	public void setCurrentMode(int ordinal) {
		mode = mode.getMode(ordinal);
	}

	@Override
	public IMode getDefaultMode() {
		return defaultMode;
	}
}
