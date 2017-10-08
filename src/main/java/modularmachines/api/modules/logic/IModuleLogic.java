package modularmachines.api.modules.logic;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import modularmachines.api.IGuiProvider;
import modularmachines.api.ILocatable;
import modularmachines.api.modules.Module;
import modularmachines.api.modules.assemblers.IAssembler;
import modularmachines.api.modules.storages.IStorage;
import modularmachines.api.modules.storages.IStoragePosition;

public interface IModuleLogic extends IGuiProvider {
	
	/**
	 * 
	 * @return A collection of all modules that this handler does handle.
	 */
	Collection<Module> getModules();
	
	/**
	 * 
	 * @return A module that is at a special position in this handler.
	 */
	Module getModule(int index);
	
	List<IStoragePosition> getValidPositions();
	
	void assemble(IAssembler assembler, EntityPlayer player);
	
	void clear();
	
    NBTTagCompound writeToNBT(NBTTagCompound compound);
    
    void readFromNBT(NBTTagCompound compound);
	
	/**
	 * 
	 * @return  A collection of all storages that this handler contains.
	 */
	Collection<IStorage> getStorages();
	
	@Nullable
	IStorage getStorage(IStoragePosition position);
	
	void addComponent(String identifier, LogicComponent component);
	
	<T extends LogicComponent> T getComponent(String identifier);
	
	Map<String, LogicComponent> getComponents();
	
	default boolean isItem(){
		return getLocatable() == null;
	}
	
	/**
	 * @return only null if the logic is a item.
	 */
	@Override
	@Nullable
	ILocatable getLocatable();
}
