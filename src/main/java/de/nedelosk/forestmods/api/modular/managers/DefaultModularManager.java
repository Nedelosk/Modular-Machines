package de.nedelosk.forestmods.api.modular.managers;

import de.nedelosk.forestmods.api.modular.IModular;
import net.minecraft.nbt.NBTTagCompound;

public abstract class DefaultModularManager<M extends IModular> implements IModularManager<M> {

	protected M modular;

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
	}

	@Override
	public M getModular() {
		return modular;
	}

	@Override
	public void setModular(M modular) {
		this.modular = modular;
	}

	@Override
	public void onModularAssembled() {
	}
}
