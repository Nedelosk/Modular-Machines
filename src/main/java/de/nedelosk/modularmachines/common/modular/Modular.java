package de.nedelosk.modularmachines.common.modular;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import de.nedelosk.modularmachines.api.energy.IEnergyInterface;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModularHandler;
import de.nedelosk.modularmachines.api.modular.ModularManager;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.IModuleTickable;
import de.nedelosk.modularmachines.api.modules.ModuleEvents;
import de.nedelosk.modularmachines.api.modules.handlers.IModuleContentHandler;
import de.nedelosk.modularmachines.api.modules.handlers.IModulePage;
import de.nedelosk.modularmachines.api.modules.handlers.energy.IModuleEnergyInterface;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventory;
import de.nedelosk.modularmachines.api.modules.handlers.tank.IModuleTank;
import de.nedelosk.modularmachines.api.modules.integration.IWailaProvider;
import de.nedelosk.modularmachines.api.modules.integration.IWailaState;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.client.gui.GuiModular;
import de.nedelosk.modularmachines.common.inventory.ContainerModular;
import de.nedelosk.modularmachines.common.modular.handlers.EnergyHandler;
import de.nedelosk.modularmachines.common.modular.handlers.ItemHandler;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import de.nedelosk.modularmachines.common.network.packets.PacketSelectModule;
import de.nedelosk.modularmachines.common.network.packets.PacketSelectModulePage;
import de.nedelosk.modularmachines.common.utils.Log;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidHandlerConcatenate;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class Modular implements IModular {

	protected IModularHandler modularHandler;
	protected List<IModuleState> moduleStates = new ArrayList();
	protected int index;
	protected IModuleState currentModule;
	protected IModulePage currentPage;
	protected EnergyHandler energyHandler;
	protected FluidHandlerConcatenate fluidHandler;
	protected ItemHandler itemHandler;

	// Ticks
	private static final Random rand = new Random();
	private int tickCount = rand.nextInt(256);

	public Modular() {
	}

	public Modular(NBTTagCompound nbt, IModularHandler handler) {
		this();
		if(handler != null){
			setHandler(handler);
		}
		if(nbt != null){
			readFromNBT(nbt);
		}
		onAssembleModular();
	}

	@Override
	public void onAssembleModular() {
		fluidHandler = new FluidHandlerConcatenate(getTanks());
		itemHandler = new ItemHandler(getInventorys());
		energyHandler = new EnergyHandler(getInterfaces());
		moduleStates = Collections.unmodifiableList(moduleStates);
		if(currentModule == null && getFirstGui() != null){
			currentModule = getFirstGui();
			setCurrentPage(((IModulePage)currentModule.getPages().get(0)).getPageID());
		}
	}

	private IModuleState getFirstGui() {
		for(IModuleState module : moduleStates) {
			if (!module.getPages().isEmpty()) {
				return module;
			}
		}
		return null;
	}

	@Override
	public void update(boolean isServer) {
		tickCount++;
		for(IModuleState moduleState : moduleStates) {
			if (moduleState != null && moduleState.getModule() instanceof IModuleTickable) {
				MinecraftForge.EVENT_BUS.post(new ModuleEvents.ModuleUpdateEvent(moduleState, isServer ? Side.SERVER : Side.CLIENT));
				if (isServer) {
					((IModuleTickable)moduleState.getModule()).updateServer(moduleState, tickCount);
				} else {
					((IModuleTickable)moduleState.getModule()).updateClient(moduleState, tickCount);
				}
			}
		}
	}

	@Override
	public final boolean updateOnInterval(int tickInterval) {
		return tickCount % tickInterval == 0;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		NBTTagList nbtList = nbt.getTagList("Modules", 10);
		for(int i = 0; i < nbtList.tagCount(); i++) {
			NBTTagCompound moduleTag = nbtList.getCompoundTagAt(i);
			NBTTagCompound nbtTagContainer = moduleTag.getCompoundTag("Container");
			ItemStack moduleItem = ItemStack.loadItemStackFromNBT(nbtTagContainer);
			IModuleContainer container = ModularManager.getContainerFromItem(moduleItem);
			if(container != null){
				IModuleState state = container.getModule().createState(this, container);
				MinecraftForge.EVENT_BUS.post(new ModuleEvents.ModuleStateCreateEvent(state));
				state = state.build();
				state.readFromNBT(moduleTag);
				moduleStates.add(state);
			}else{
				Log.err("Remove module from modular, because the item of the module don't exist any more.");
			}
		}
		if (nbt.hasKey("CurrentModule")) {
			currentModule = getModule(nbt.getInteger("CurrentModule"));
			if (nbt.hasKey("CurrentPage")) {
				String pageID = nbt.getString("CurrentPage");
				IModulePage currentPage = null;
				for(IModulePage page : (List<IModulePage>)currentModule.getPages()){
					if(page.getPageID().equals(pageID)){
						currentPage = page;
						break;
					}
				}
				if(currentPage == null){
					currentPage = (IModulePage) currentModule.getPages().get(0);
				}
				this.currentPage = currentPage;
			}
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		NBTTagList nbtList = new NBTTagList();
		for(IModuleState module : moduleStates) {
			NBTTagCompound nbtTag = new NBTTagCompound();
			module.writeToNBT(nbtTag);
			NBTTagCompound nbtTagContainer = new NBTTagCompound();
			module.getContainer().getItemStack().writeToNBT(nbtTagContainer);
			nbtTag.setTag("Container", nbtTagContainer);
			nbtList.appendTag(nbtTag);
		}
		nbt.setTag("Modules", nbtList);
		if (currentModule != null) {
			nbt.setInteger("CurrentModule", currentModule.getIndex());
			if (currentPage != null) {
				nbt.setString("CurrentPage", currentPage.getPageID());
			}
		}
		return nbt;
	}

	@Override
	public void setHandler(IModularHandler handler) {
		this.modularHandler = handler;
	}

	@Override
	public IModularHandler getHandler() {
		return modularHandler;
	}

	private ArrayList<IModuleState> getWailaModules() {
		ArrayList<IModuleState> wailaModules = Lists.newArrayList();
		for(IModuleState module : moduleStates) {
			if (module instanceof IWailaProvider) {
				wailaModules.add(module);
			}
		}
		return wailaModules;
	}

	@Override
	public List getWailaBody(ItemStack itemStack, List currenttip, IWailaState data) {
		for(IModuleState state : getWailaModules()) {
			if (state.getModule() instanceof IWailaProvider) {
				if (((IWailaProvider) state.getModule()).getWailaBody(itemStack, currenttip, state, data) != null) {
					currenttip = ((IWailaProvider) state.getModule()).getWailaBody(itemStack, currenttip, state, data);
				}
			}
		}
		return currenttip;
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaState data) {
		for(IModuleState state : getWailaModules()) {
			if (state.getModule() instanceof IWailaProvider) {
				if (((IWailaProvider) state.getModule()).getWailaHead(itemStack, currenttip, state, data) != null) {
					currenttip = ((IWailaProvider) state.getModule()).getWailaHead(itemStack, currenttip, state, data);
				}
			}
		}
		return currenttip;
	}

	@Override
	public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaState data) {
		for(IModuleState state : getWailaModules()) {
			if (state.getModule() instanceof IWailaProvider) {
				if (((IWailaProvider) state.getModule()).getWailaTail(itemStack, currenttip, state, data) != null) {
					currenttip = ((IWailaProvider) state.getModule()).getWailaTail(itemStack, currenttip, state, data);
				}
			}
		}
		return currenttip;
	}

	@Override
	public IModulePage getCurrentPage() {
		return currentPage;
	}

	@Override
	public IModuleState getCurrentModuleState() {
		return currentModule;
	}

	@Override
	public void setCurrentModuleState(IModuleState module) {
		this.currentModule = module;
		this.currentPage = (IModulePage) currentModule.getPages().get(0);
		if (getHandler().getWorld().isRemote) {
			PacketHandler.INSTANCE.sendToServer(new PacketSelectModule(getHandler(), module));
			PacketHandler.INSTANCE.sendToServer(new PacketSelectModulePage(getHandler(), currentPage.getPageID()));
		}
	}

	@Override
	public void setCurrentPage(String pageID) {
		if(currentModule != null){
			IModulePage currentPage = null;
			for(IModulePage page : (List<IModulePage>)currentModule.getPages()){
				if(page.getPageID().equals(pageID)){
					currentPage = page;
					break;
				}
			}
			if(currentPage == null){
				currentPage = (IModulePage) currentModule.getPages().get(0);
			}
			this.currentPage = currentPage;
			if(getHandler() != null && getHandler().getWorld() != null){
				if (getHandler().getWorld().isRemote) {
					PacketHandler.INSTANCE.sendToServer(new PacketSelectModulePage(getHandler(), pageID));
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public GuiContainer getGUIContainer(IModularHandler tile, InventoryPlayer inventory) {
		if(currentPage != null){
			return new GuiModular(tile, inventory, currentPage);
		}
		return null;

	}

	@Override
	public Container getContainer(IModularHandler tile, InventoryPlayer inventory) {
		if(currentPage != null){
			return new ContainerModular(tile, inventory, currentPage);
		}
		return null;
	}

	@Override
	public IModuleState addModule(ItemStack itemStack, IModuleState state) {
		if (state == null) {
			return null;
		}

		if (moduleStates.add(state)) {
			state.setIndex(index++);
			return state;
		}
		return null;
	}

	@Override
	public List<IModuleState> getModuleStates() {
		return moduleStates;
	}

	@Override
	public <M extends IModule> IModuleState<M> getModule(int index) {
		for(IModuleState module : moduleStates) {
			if (module.getIndex() == index) {
				return module;
			}
		}
		return null;
	}

	@Override
	public <M extends IModule> List<IModuleState<M>> getModules(Class<? extends M> moduleClass) {
		if (moduleClass == null) {
			return null;
		}
		List<IModuleState<M>> modules = Lists.newArrayList();
		for(IModuleState module : this.moduleStates) {
			if (moduleClass.isAssignableFrom(module.getModule().getClass())) {
				modules.add(module);
			}
		}
		return modules;
	}

	@Override
	public IEnergyInterface getEnergyInterface() {
		return energyHandler;
	}

	@Override
	public IFluidHandler getFluidHandler() {
		return fluidHandler;
	}

	protected List<IItemHandler> getInventorys(){
		List<IItemHandler> handlers = Lists.newArrayList();
		for(IModuleState state : moduleStates){
			IModuleContentHandler inventory = state.getContentHandler(IModuleInventory.class);
			if(inventory instanceof IModuleInventory){
				handlers.add((IItemHandler) inventory);
			}
		}
		return handlers;
	}

	protected List<IFluidHandler> getTanks() {
		List<IFluidHandler> fluidHandlers = Lists.newArrayList();
		for(IModuleState state : moduleStates) {
			IModuleContentHandler handler = state.getContentHandler(IModuleTank.class);
			if(handler instanceof IModuleTank){
				fluidHandlers.add((IFluidHandler) handler);
			}
		}
		return fluidHandlers;
	}

	protected List<IEnergyInterface> getInterfaces(){
		List<IEnergyInterface> handlers = Lists.newArrayList();
		for(IModuleState state : moduleStates){
			IModuleContentHandler rnergyInterface = state.getContentHandler(IModuleEnergyInterface.class);
			if(rnergyInterface instanceof IModuleEnergyInterface){
				handlers.add((IModuleEnergyInterface) rnergyInterface);
			}
		}
		return handlers;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY){
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(fluidHandler);
		}else if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(itemHandler);
		}
		if(energyHandler != null){

		}
		return null;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
			return true;
		}
		if(energyHandler != null){

		}
		return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
	}

	@Override
	public IModular copy(IModularHandler handler) {
		NBTTagCompound nbtTag = new NBTTagCompound();
		writeToNBT(nbtTag);
		return new Modular(nbtTag, handler);
	}
}