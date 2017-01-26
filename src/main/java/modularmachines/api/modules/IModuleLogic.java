package modularmachines.api.modules;

import java.util.Collection;
import java.util.List;
import javax.annotation.Nullable;

import modularmachines.api.ILocatableSource;
import modularmachines.api.modules.assemblers.IAssembler;
import modularmachines.api.modules.storages.IStorage;
import modularmachines.api.modules.storages.IStoragePosition;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IModuleLogic extends ILocatableSource {
	
	/**
	 * 
	 * @return A collection of all modules that this handler does handle.
	 */
	Collection<Module> getModules();
	
	/**
	 * 
	 * @return A module that is at a speciel position in this handler.
	 */
	Module getModule(int index);
	
	List<IStoragePosition> getValidPositions();
	
	void assemble(IAssembler assembler, EntityPlayer player);
	
    NBTTagCompound writeToNBT(NBTTagCompound compound);
    
    void readFromNBT(NBTTagCompound compound);
	
	/**
	 * 
	 * @return  A collection of all storages that this handler contains.
	 */
	Collection<IStorage> getStorages();
	
	@Nullable
	IStorage getStorage(IStoragePosition position);
	
	@SideOnly(Side.CLIENT)
	void updateRendering();
	
	@SideOnly(Side.CLIENT)
	IModuleGuiLogic getGuiLogic();
	
}
