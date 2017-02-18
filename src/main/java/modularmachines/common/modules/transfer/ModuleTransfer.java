package modularmachines.common.modules.transfer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import modularmachines.api.modules.IModuleStorage;
import modularmachines.api.modules.Module;
import modularmachines.api.modules.assemblers.IAssembler;
import modularmachines.api.modules.logic.IModuleLogic;
import modularmachines.api.modules.storages.IStorage;
import modularmachines.common.modules.logic.UpdateComponent;
import modularmachines.common.utils.ModuleUtil;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

public abstract class ModuleTransfer<H> extends Module implements ITickable {

	protected List<ITransferCycle<H>> transferCycles;
	public boolean wasInited;
	protected final Map<Integer, ITransferHandlerWrapper<H>> moduleWrappers = new HashMap<>();
	protected final Map<EnumFacing, ITransferHandlerWrapper<H>> tileWrappers = new HashMap<>();

	public ModuleTransfer(IModuleStorage storage) {
		super(storage);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		NBTTagList list = new NBTTagList();
		for(ITransferCycle cycle : transferCycles){
			list.appendTag(cycle.writeToNBT(new NBTTagCompound()));
		}
		compound.setTag("Cycles", list);
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		transferCycles.clear();
		NBTTagList list = compound.getTagList("Cycles", 10);
		for(int i = 0;i < list.tagCount();i++){
			transferCycles.add(getCycle(list.getCompoundTagAt(i)));
		}
	}
	
	@Override
	public void onAssemble(IAssembler assembler, IModuleLogic logic, IStorage storage) {
		if(tileWrappers.isEmpty()){
			for (EnumFacing facing : EnumFacing.VALUES) {
				if(!tileWrappers.containsKey(facing)){
					tileWrappers.put(facing, createTileWrapper(facing));
				}
			}
		}
		if(moduleWrappers.isEmpty()){
			for (Module module : logic.getModules()) {
				int index = module.getIndex();
				if(!moduleWrappers.containsKey(index)){
					moduleWrappers.put(index, createModuleWrapper(index));
				}
			}
		}
	}
	
	@Override
	public void onLoad() {
		if(tileWrappers.isEmpty()){
			for (EnumFacing facing : EnumFacing.VALUES) {
				if(!tileWrappers.containsKey(facing)){
					tileWrappers.put(facing, createTileWrapper(facing));
				}
			}
		}
		if(moduleWrappers.isEmpty()){
			for (Module module : logic.getModules()) {
				int index = module.getIndex();
				if(!moduleWrappers.containsKey(index)){
					moduleWrappers.put(index, createModuleWrapper(index));
				}
			}
		}
	}
	
	public void addCycle(ITransferCycle<H> cycle){
		transferCycles.add(cycle);
	}
	
	public abstract NBTBase writeWrapper(ITransferHandlerWrapper<H> wrapper);
	
	public abstract ITransferHandlerWrapper<H> getWrapper(NBTBase base);
	
	public abstract ITransferCycle<H> getCycle(NBTTagCompound compound);
	
	public abstract Class<H> getHandlerClass();
	
	public abstract H getHandler(ITransferHandlerWrapper<H> wrapper);
	
	public abstract ITransferHandlerWrapper<H> createModuleWrapper(int moduleIndex);
	
	public abstract ITransferHandlerWrapper<H> createTileWrapper(EnumFacing facing);
	
	protected abstract ModuleTransferPage createPage(ModuleTransfer<H> parent, int index);
	
	public List<ITransferHandlerWrapper<H>> getValidWrappers() {
		List<ITransferHandlerWrapper<H>> wrappers = getWrappers();
		Iterator<ITransferHandlerWrapper<H>> wrappersIterator = wrappers.iterator();
		while(wrappersIterator.hasNext()){
			ITransferHandlerWrapper<H> wrapper = wrappersIterator.next();
			if(!wrapper.isValid()){
				wrappersIterator.remove();
			}
		}
		return wrappers;
	}
	
	public List<ITransferHandlerWrapper<H>> getWrappers() {
		List<ITransferHandlerWrapper<H>> handlers = new ArrayList<>();
		handlers.addAll(tileWrappers.values());
		handlers.addAll(moduleWrappers.values());
		return handlers;
	}
	
	@Override
	public void update() {
		if(!wasInited){
			for(ITransferHandlerWrapper wrapper : getWrappers()){
				wrapper.init(logic);
			}
			wasInited = true;
		}
		UpdateComponent update = ModuleUtil.getUpdate(logic);
		try{
			for (ITransferCycle<H> cycle : getTransferCycles()) {
				if (cycle.canWork()) {
						cycle.work(update.getTickCount());
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	protected void initPages() {
		super.initPages();
		transferCycles = new ArrayList<>();
		addPage(createPage(this, 0));
		for(int i = 1;i < 5;i++){
			if(transferCycles.size() > i * 6){
				addPage(createPage(this, i));
			}
		}
	}
	
	public List<ITransferCycle<H>> getTransferCycles() {
		return transferCycles;
	}
}
