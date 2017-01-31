package modularmachines.api.modules.assemblers;

import java.util.Collection;
import java.util.List;

import modularmachines.api.IGuiProvider;
import modularmachines.api.modules.logic.IModuleLogic;
import modularmachines.api.modules.storages.IStoragePosition;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public interface IAssembler extends IGuiProvider {

    NBTTagCompound writeToNBT(NBTTagCompound compound);
    
    void readFromNBT(NBTTagCompound compound);

	void setSelectedPosition(IStoragePosition position);

	IStoragePosition getSelectedPosition();

	int getComplexity();

	int getAllowedComplexity();

	Collection<IStoragePage> getPages();
	
	void disassemble(IModuleLogic logic, EntityPlayer player);
	
	IStoragePage getPage(IStoragePosition position);
	
	List<AssemblerError> canAssemble();

	void updatePages();

	void onStorageSlotChange();
	
	List<IStoragePosition> getPositions();
	
	IStoragePosition getPosition(int index);
	
	int getIndex(IStoragePosition position);
	
}
