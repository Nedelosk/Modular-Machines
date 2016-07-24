package de.nedelosk.modularmachines.common.modular;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import de.nedelosk.modularmachines.api.energy.IEnergyInterface;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModularAssembler;
import de.nedelosk.modularmachines.api.modular.ModularManager;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.IModule;
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
import de.nedelosk.modularmachines.api.modules.storage.IPositionedModuleStorage;
import de.nedelosk.modularmachines.api.modules.storaged.EnumPosition;
import de.nedelosk.modularmachines.client.gui.GuiModular;
import de.nedelosk.modularmachines.common.inventory.ContainerModular;
import de.nedelosk.modularmachines.common.modular.assembler.ModularAssembler;
import de.nedelosk.modularmachines.common.modular.handlers.EnergyHandler;
import de.nedelosk.modularmachines.common.modular.handlers.ItemHandler;
import de.nedelosk.modularmachines.common.modules.storage.PositionedModuleStorage;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import de.nedelosk.modularmachines.common.network.packets.PacketSelectModule;
import de.nedelosk.modularmachines.common.network.packets.PacketSelectModulePage;
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
	protected int index;
	protected IModuleState currentModule;
	protected IModulePage currentPage;
	protected EnergyHandler energyHandler;
	protected FluidHandlerConcatenate fluidHandler;
	protected ItemHandler itemHandler;
	protected EnumMap<EnumPosition, IPositionedModuleStorage> storages = new EnumMap(EnumPosition.class);

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
			deserializeNBT(nbt);
		}
		assembleModular();
	}

	@Override
	public void assembleModular() {
		fluidHandler = new FluidHandlerConcatenate(getTanks());
		itemHandler = new ItemHandler(getInventorys());
		energyHandler = new EnergyHandler(getInterfaces());
		if(currentModule == null && getFirstGui() != null){
			currentModule = getFirstGui();
			setCurrentPage(((IModulePage)currentModule.getPages().get(0)).getPageID());
		}
	}

	private IModuleState getFirstGui() {
		for(IModuleState module : getModules()) {
			if (!module.getPages().isEmpty()) {
				return module;
			}
		}
		return null;
	}

	@Override
	public void update(boolean isServer) {
		tickCount++;
		for(IModuleState moduleState : getModules()) {
			if (moduleState != null && moduleState.getModule() instanceof IModuleTickable) {
				MinecraftForge.EVENT_BUS.post(new ModuleEvents.ModuleUpdateEvent(moduleState, isServer ? Side.SERVER : Side.CLIENT));
				IModuleTickable module = (IModuleTickable)moduleState.getModule();
				if (isServer) {
					module.updateServer(moduleState, tickCount);
				} else {
					module.updateClient(moduleState, tickCount);
				}
			}
		}
	}

	@Override
	public final boolean updateOnInterval(int tickInterval) {
		return tickCount % tickInterval == 0;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		NBTTagList nbtList = nbt.getTagList("Storages", 10);
		for(int i = 0; i < nbtList.tagCount(); i++) {
			NBTTagCompound moduleTag = nbtList.getCompoundTagAt(i);
			EnumPosition position = EnumPosition.values()[moduleTag.getShort("Position")];
			IPositionedModuleStorage storage = new PositionedModuleStorage(this, position);
			storage.deserializeNBT(moduleTag);
			storages.put(position, storage);
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
	public NBTTagCompound serializeNBT() {
		NBTTagCompound nbt = new NBTTagCompound();
		NBTTagList nbtList = new NBTTagList();
		for(IPositionedModuleStorage storage : storages.values()) {
			if(storage != null){
				NBTTagCompound nbtTag = storage.serializeNBT();
				nbtTag.setShort("Position", (short) storage.getPosition().ordinal());
				nbtList.appendTag(nbtTag);
			}
		}
		nbt.setTag("Storages", nbtList);
		if (currentModule != null) {
			nbt.setInteger("CurrentModule", currentModule.getIndex());
			if (currentPage != null) {
				nbt.setString("CurrentPage", currentPage.getPageID());
			}
		}
		return nbt;
	}

	@Override
	public IModularAssembler disassemble() {
		ItemStack[] moduleStacks = new ItemStack[26];
		for(IPositionedModuleStorage moduleStorage : storages.values()){
			if(moduleStorage != null){
				EnumPosition position = moduleStorage.getPosition();
				int index = 0;
				for(IModuleState state : moduleStorage.getModules()) {
					moduleStacks[position.startSlotIndex + index] = ModularManager.saveModuleState(state);
					index++;
				}
			}
		}
		return new ModularAssembler(modularHandler, moduleStacks);
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
		ArrayList<IModuleState> wailaModuleStates = Lists.newArrayList();
		for(IPositionedModuleStorage moduleStorage : storages.values()){
			if(moduleStorage != null){
				for(IModuleState state : moduleStorage.getModules()) {
					if (state.getModule() instanceof IWailaProvider) {
						wailaModuleStates.add(state);
					}
				}
			}
		}
		return wailaModuleStates;
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
	public GuiContainer createGui(IModularHandler tile, InventoryPlayer inventory) {
		if(currentPage != null){
			return new GuiModular(tile, inventory, currentPage);
		}
		return null;

	}

	@Override
	public Container createContainer(IModularHandler tile, InventoryPlayer inventory) {
		if(currentPage != null){
			return new ContainerModular(tile, inventory, currentPage);
		}
		return null;
	}

	@Override
	public IModuleState addModule(ItemStack itemStack, IModuleState state) {
		return null;
	}

	@Override
	public IPositionedModuleStorage getModuleStorage(EnumPosition position) {
		return storages.get(position);
	}

	@Override
	public void setModuleStorage(EnumPosition position, IPositionedModuleStorage storage) {
		storages.put(position, storage);
	}

	@Override
	public List<IModuleState> getModuleStates() {
		List<IModuleState> modules = new ArrayList<>();
		for(IPositionedModuleStorage moduleStorage : storages.values()){
			if(moduleStorage != null){
				modules.addAll(moduleStorage.getModules());
			}
		}
		return modules;
	}

	@Override
	public <M extends IModule> IModuleState<M> getModule(int index) {
		for(IPositionedModuleStorage moduleStorage : storages.values()){
			if(moduleStorage != null){
				IModuleState state = moduleStorage.getModule(index);
				if(state != null){
					return state;
				}
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
		for(IPositionedModuleStorage moduleStorage : storages.values()){
			if(moduleStorage != null){
				modules.addAll(moduleStorage.getModules(moduleClass));
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
		for(IModuleState state : getModules()) {
			IModuleContentHandler inventory = state.getContentHandler(IModuleInventory.class);
			if(inventory instanceof IModuleInventory){
				handlers.add((IItemHandler) inventory);
			}
		}
		return handlers;
	}

	protected List<IFluidHandler> getTanks() {
		List<IFluidHandler> fluidHandlers = Lists.newArrayList();
		for(IModuleState state : getModules()) {
			IModuleContentHandler handler = state.getContentHandler(IModuleTank.class);
			if(handler instanceof IModuleTank){
				fluidHandlers.add((IFluidHandler) handler);
			}
		}
		return fluidHandlers;
	}

	protected List<IEnergyInterface> getInterfaces(){
		List<IEnergyInterface> handlers = Lists.newArrayList();
		for(IModuleState state : getModules()) {
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
		return new Modular(serializeNBT(), handler);
	}

	@Override
	public List<IModuleState> getModules() {
		List<IModuleState> modules = Lists.newArrayList();
		for(IPositionedModuleStorage moduleStorage : storages.values()){
			if(moduleStorage != null){
				modules.addAll(moduleStorage.getModules());
			}
		}
		return modules;
	}

	@Override
	public int getNextIndex() {
		return index++;
	}
}