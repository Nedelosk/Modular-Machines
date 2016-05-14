package de.nedelosk.forestmods.library.modules;

import de.nedelosk.forestmods.library.modular.IModular;
import de.nedelosk.forestmods.library.modules.handlers.IModulePage;
import de.nedelosk.forestmods.library.modules.handlers.inventory.IModuleInventory;
import de.nedelosk.forestmods.library.modules.handlers.tank.IModuleTank;
import net.minecraft.nbt.NBTTagCompound;

public interface IModuleState<M extends IModule> {
	
	int getIndex();
	
	void setIndex(int index);
	
	IModuleInventory<M> getInventory();

	IModuleTank<M> getTank();
	
	IModulePage[] getPages();
	
	IModuleContainer getContainer();
	
	void writeToNBT(NBTTagCompound nbt, IModular modular);

	void readFromNBT(NBTTagCompound nbt, IModular modular);
	
}
