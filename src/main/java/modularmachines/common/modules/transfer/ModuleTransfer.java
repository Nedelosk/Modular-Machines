package modularmachines.common.modules.transfer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

import modularmachines.api.modules.Module;
import modularmachines.api.modules.pages.ModuleComponent;
import modularmachines.common.modules.logic.UpdateComponent;
import modularmachines.common.utils.ModuleUtil;

public abstract class ModuleTransfer<H> extends Module implements ITickable, IModuleTransfer<H> {

	protected List<ITransferCycle<H>> transferCycles;
	public boolean wasCreated;
	public boolean wasInited;
	protected final Map<Integer, ITransferHandlerWrapper<H>> moduleWrappers = new HashMap<>();
	protected final Map<EnumFacing, ITransferHandlerWrapper<H>> tileWrappers = new HashMap<>();
	
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
		createWrappers();
		transferCycles.clear();
		NBTTagList list = compound.getTagList("Cycles", 10);
		for(int i = 0;i < list.tagCount();i++){
			transferCycles.add(getCycle(list.getCompoundTagAt(i)));
		}
	}
	
	/*@Override
	public void onAssemble(IAssembler assembler, IModuleLogic logic, IStorage storage) {
		createWrappers();
	}*/
	
	private void createWrappers(){
		if(wasCreated){
			return;
		}
		for (EnumFacing facing : EnumFacing.VALUES) {
			if(!tileWrappers.containsKey(facing)){
				tileWrappers.put(facing, createTileWrapper(facing));
			}
		}
		for (Module module : container.getModules()) {
			int index = module.getIndex();
			if(!moduleWrappers.containsKey(index)){
				moduleWrappers.put(index, createModuleWrapper(index));
			}
		}
		wasCreated = true;
	}
	
	public void addCycle(ITransferCycle<H> cycle){
		transferCycles.add(cycle);
		Collections.sort(transferCycles);
		updateCycleWidgets();
	}
	
	public void removeCycles(int index){
		if(index >= transferCycles.size()){
			return;
		}
		transferCycles.remove(index);
		if(!transferCycles.isEmpty()) {
			Collections.sort(transferCycles);
		}
		updateCycleWidgets();
	}
	
	public void updateCycleWidgets(){
		ModuleComponent page = getComponent(0);
		if(page instanceof ModuleComponentTransfer){
			ModuleComponentTransfer transferPage = (ModuleComponentTransfer) page;
			
		}
	}
	
	public void initWrappers(){
		if(wasInited){
			return;
		}
		for(ITransferHandlerWrapper wrapper : getWrappers()){
			wrapper.init(container);
		}
		wasInited = true;
	}
	
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
			initWrappers();
			wasInited = true;
		}
		UpdateComponent update = ModuleUtil.getUpdate(container);
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
	protected void createComponents() {
		super.createComponents();
		transferCycles = new ArrayList<>();
		addComponent(createComponent(this, 0));
	}
	
	protected abstract ModuleComponentTransfer createComponent(ModuleTransfer<H> parent, int index);
	
	public List<ITransferCycle<H>> getTransferCycles() {
		return transferCycles;
	}
}
