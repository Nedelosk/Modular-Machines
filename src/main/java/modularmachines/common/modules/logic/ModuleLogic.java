package modularmachines.common.modules.logic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.Nullable;

import modularmachines.api.modules.IModuleLogic;
import modularmachines.api.modules.IModuleStorage;
import modularmachines.api.modules.Module;
import modularmachines.api.modules.ModuleData;
import modularmachines.api.modules.assemblers.IAssembler;
import modularmachines.api.modules.assemblers.IStoragePage;
import modularmachines.api.modules.storages.IStorage;
import modularmachines.api.modules.storages.IStoragePosition;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ModuleLogic implements IModuleLogic {

	@SideOnly(Side.CLIENT)
	public final ModuleGuiLogic guiLogic = new ModuleGuiLogic(this);
	public final List<IStorage> storages = new ArrayList<>();
	
    @Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
    	NBTTagList storageList = new NBTTagList();
    	for(IStorage storage : storages){
    		NBTTagCompound tag = new NBTTagCompound();
    		tag.setString("ModuleData", storage.getModule().getData().getRegistryName().toString());
    		tag.setInteger("Pos", getValidPositions().indexOf(storage.getPosition()));
    		storage.writeToNBT(tag);
    	}
    	compound.setTag("Storages", storageList);
    	return compound;
    }
    
    @Override
	public void readFromNBT(NBTTagCompound compound) {
    	NBTTagList storageList = compound.getTagList("Storages", 10);
    	for(int i = 0;i < storageList.tagCount();i++){
    		NBTTagCompound tag = storageList.getCompoundTagAt(i);
    		String registryName = tag.getString("ModuleData");
    		IStoragePosition position = getValidPositions().get(tag.getInteger("Pos"));
    		ModuleData moduleData = GameRegistry.findRegistry(ModuleData.class).getValue(new ResourceLocation(registryName));
    		IStorage storage = moduleData.createStorage(this, position);
    		storages.add(storage);
    	}
    }
    
	@Override
	public Module getModule(int index) {
		for (IStorage storage : storages) {
			Module module = storage.getStorage().getModule(index);
			if (module != null) {
				return module;
			}
		}
		return null;
	}
	
	@Override
	public Collection<Module> getModules() {
		List<Module> modules = new ArrayList<>();
		for (IStorage storage : storages) {
			if (storage != null) {
				IModuleStorage moduleStorage = storage.getStorage();
				modules.addAll(moduleStorage.getModules());
			}
		}
		return modules;
	}
	
	@Override
	public void assemble(IAssembler assembler, EntityPlayer player) {
		int currentIndex = 0;
		for (IStoragePage page : assembler.getPages()) {
			if (page != null) {
				IStorage storage = page.assamble(assembler, this);
				if (storage != null) {
					for (Module module : storage.getStorage().getModules()) {
						module.setIndex(currentIndex++);
					}
					addStorage(storage);
				}
			}
		}
		for (IStorage storage : storages) {
			for (Module module : storage.getStorage().getModules()) {
				module.assembleModule(assembler, this, storage);
			}
		}
		onAssembled();
	}

	@Override
	public List<IStoragePosition> getValidPositions() {
		return null;
	}
	
	@Override
	public Collection<IStorage> getStorages() {
		return storages;
	}
	
	@Nullable
	@Override
	public IStorage getStorage(IStoragePosition position) {
		for(IStorage storage : storages){
			if(storage.getPosition() == position){
				return storage;
			}
		}
		return null;
	}
	
	protected void onAssembled(){
		
	}
	
	protected void addStorage(IStorage storage){
		storages.add(storage);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public ModuleGuiLogic getGuiLogic() {
		return guiLogic;
	}
	
}
