package modularmachines.common.modules.logic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

import com.google.common.base.Preconditions;
import modularmachines.api.ILocatable;
import modularmachines.api.modules.IModuleStorage;
import modularmachines.api.modules.Module;
import modularmachines.api.modules.ModuleData;
import modularmachines.api.modules.ModuleHelper;
import modularmachines.api.modules.assemblers.IAssembler;
import modularmachines.api.modules.assemblers.IStoragePage;
import modularmachines.api.modules.logic.IModuleLogic;
import modularmachines.api.modules.logic.LogicComponent;
import modularmachines.api.modules.storages.IStorage;
import modularmachines.api.modules.storages.IStoragePosition;
import modularmachines.client.gui.GuiModuleLogic;
import modularmachines.common.containers.ContainerModuleLogic;
import modularmachines.common.network.PacketHandler;
import modularmachines.common.network.packets.PacketSyncHandlerState;
import modularmachines.common.utils.ContainerUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModuleLogic implements IModuleLogic {
	
	protected final Map<String, LogicComponent> componentMap;
	protected final List<IStorage> storages = new ArrayList<>();
	protected final List<IStoragePosition> positions;
	protected final ILocatable locatable;
	
	public ModuleLogic(ILocatable locatable, List<IStoragePosition> positions) {
		Preconditions.checkNotNull(locatable);
		Preconditions.checkNotNull(positions);
		this.locatable = locatable;
		this.positions = positions;
		this.componentMap = new LinkedHashMap<>();
		addComponent(LogicComponent.ENERGY, new EnergyStorageComponent());
		addComponent(LogicComponent.UPDATE, new UpdateComponent());
		addComponent(LogicComponent.HEAT, new HeatComponent());
	}
	
	@Override
	public void addComponent(String identifier, LogicComponent component) {
		Preconditions.checkNotNull(component, "Can't have a null logic component!");
		component.setLogic(this);
		this.componentMap.put(identifier, component);
	}

	@Override
	public Map<String, LogicComponent> getComponents() {
		return this.componentMap;
	}

	@Override
	public <T extends LogicComponent> T getComponent(String identifier) {
		return (T) this.componentMap.get(identifier);
	}

	public boolean hasComponent(String identifier) {
		return this.componentMap.containsKey(identifier);
	}
	
    @Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
    	NBTTagList storageList = new NBTTagList();
    	for(IStorage storage : storages){
    		NBTTagCompound tagCompound = new NBTTagCompound();
    		Module module = storage.getModule();
    		tagCompound.setString("ModuleData", module.getData().getRegistryName().toString());
    		tagCompound.setInteger("Pos", positions.indexOf(storage.getPosition()));
    		storage.writeToNBT(tagCompound);
    		storageList.appendTag(tagCompound);
    	}
    	compound.setTag("Storages", storageList);
    	return compound;
    }
    
    @Override
	public void readFromNBT(NBTTagCompound compound) {
    	storages.clear();
    	NBTTagList storageList = compound.getTagList("Storages", 10);
    	for(int i = 0;i < storageList.tagCount();i++){
    		NBTTagCompound tagCompound = storageList.getCompoundTagAt(i);
    		String registryName = tagCompound.getString("ModuleData");
    		IStoragePosition position = positions.get(tagCompound.getInteger("Pos"));
    		ModuleData moduleData = GameRegistry.findRegistry(ModuleData.class).getValue(new ResourceLocation(registryName));
    		IStorage storage = moduleData.createStorage(this, position, null);
    		if(storage != null){
    			storage.readFromNBT(tagCompound);
        		storages.add(storage);
    		}
    	}
    	for(IStorage storage : storages){
    		for(Module module : storage.getStorage().getModules()){
    			module.onLoad();
    		}
    	}
    }
    
	@Override
	public Module getModule(int index) {
		for (IStorage storage : storages) {
			Module module = storage.getStorage().getModuleForIndex(index);
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
			if (!page.isEmpty()) {
				IStorage storage = page.assemble(assembler, this);
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
				module.onAssemble(assembler, this, storage);
			}
		}
		onAssembled();
		assembler.clear();
		World world = locatable.getWorldObj();
		BlockPos pos = locatable.getCoordinates();
		if (world.isRemote) {
			world.markBlockRangeForRenderUpdate(pos, pos);
		} else {
			if(world instanceof WorldServer){
				WorldServer server = (WorldServer) world;
				PacketHandler.sendToNetwork(new PacketSyncHandlerState(this, true), pos, server);
				IBlockState blockState = server.getBlockState(pos);
				server.notifyBlockUpdate(pos, blockState, blockState, 3);
				ContainerUtil.openOrCloseGuiSave(assembler, 1, !ModuleHelper.getPageModules(this).isEmpty());
			}
		}
	}
	
	@Override
	public void clear() {
		storages.clear();
	}

	@Override
	public List<IStoragePosition> getValidPositions() {
		return positions;
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
	
	@Override
	public ILocatable getLocatable() {
		return locatable;
	}
	
	@Override
	public Container createContainer(InventoryPlayer inventory) {
		return new ContainerModuleLogic(this, inventory);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public GuiContainer createGui(InventoryPlayer inventory) {
		return new GuiModuleLogic(this, inventory);
	}
}
