package modularmachines.api.modules.assemblers;

import java.util.Collection;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import modularmachines.api.IGuiProvider;
import modularmachines.api.modules.logic.IModuleLogic;
import modularmachines.api.modules.storages.IStoragePosition;

public interface IAssembler extends IGuiProvider {

    NBTTagCompound writeToNBT(NBTTagCompound compound);
    
    void readFromNBT(NBTTagCompound compound);

	void setSelectedPosition(IStoragePosition position);

	IStoragePosition getSelectedPosition();

	int getComplexity();

	int getAllowedComplexity();

	Collection<IStoragePage> getPages();
	
	void disassemble(IModuleLogic logic, EntityPlayer player);
	
	void clear();
	
	IStoragePage getPage(IStoragePosition position);
	
	List<AssemblerError> canAssemble();

	void updatePages();

	void onStorageSlotChange();
	
	boolean hasChange();
	
	void setHasChange(boolean hasChange);
	
	List<IStoragePosition> getPositions();
	
	IStoragePosition getPosition(int index);
	
	int getIndex(IStoragePosition position);
	
}
