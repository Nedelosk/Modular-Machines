package de.nedelosk.modularmachines.common.modular;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import com.google.common.collect.Lists;

import de.nedelosk.modularmachines.api.ItemUtil;
import de.nedelosk.modularmachines.api.energy.HeatBuffer;
import de.nedelosk.modularmachines.api.energy.IEnergyBuffer;
import de.nedelosk.modularmachines.api.energy.IHeatSource;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModularAssembler;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandlerTileEntity;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.IModuleProperties;
import de.nedelosk.modularmachines.api.modules.ITickable;
import de.nedelosk.modularmachines.api.modules.ModuleEvents;
import de.nedelosk.modularmachines.api.modules.ModuleManager;
import de.nedelosk.modularmachines.api.modules.containers.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.containers.IModuleItemContainer;
import de.nedelosk.modularmachines.api.modules.containers.IModuleProvider;
import de.nedelosk.modularmachines.api.modules.containers.ModuleProvider;
import de.nedelosk.modularmachines.api.modules.handlers.IModuleContentHandler;
import de.nedelosk.modularmachines.api.modules.handlers.IModuleContentProvider;
import de.nedelosk.modularmachines.api.modules.handlers.IModulePage;
import de.nedelosk.modularmachines.api.modules.handlers.block.BlockModificator;
import de.nedelosk.modularmachines.api.modules.handlers.block.IBlockModificator;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventory;
import de.nedelosk.modularmachines.api.modules.handlers.tank.IModuleTank;
import de.nedelosk.modularmachines.api.modules.heaters.IModuleHeater;
import de.nedelosk.modularmachines.api.modules.integration.IModuleWaila;
import de.nedelosk.modularmachines.api.modules.position.IStoragePosition;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storage.IStorage;
import de.nedelosk.modularmachines.api.modules.storage.IStorageModule;
import de.nedelosk.modularmachines.api.modules.storage.IStoragePage;
import de.nedelosk.modularmachines.api.modules.storage.module.IBasicModuleStorage;
import de.nedelosk.modularmachines.api.modules.storage.module.IModuleModuleStorage;
import de.nedelosk.modularmachines.api.modules.storage.module.IModuleStorage;
import de.nedelosk.modularmachines.client.gui.GuiPage;
import de.nedelosk.modularmachines.common.inventory.ContainerModular;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import de.nedelosk.modularmachines.common.network.packets.PacketSyncHeatBuffer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
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

	protected Map<IStoragePosition, IStorage> storages;
	protected IModularHandler modularHandler;
	protected int index;
	protected IModuleState currentModule;
	protected IModulePage currentPage;
	protected FluidHandlerConcatenate fluidHandler;
	protected ItemHandler itemHandler;
	protected IBlockModificator blockModificator;
	protected HeatBuffer heatSource;
	protected ModularEnergyBuffer energyBuffer;

	// Ticks
	private static final Random rand = new Random();
	private int tickCount = rand.nextInt(256);

	public Modular(IModularHandler handler) {
		storages = new HashMap<>();
		if(handler != null){
			setHandler(handler);
		}
	}

	public Modular(IModularHandler handler, NBTTagCompound nbt) {
		this(handler);
		if(nbt != null){
			deserializeNBT(nbt);
			if(nbt.hasKey("HeatBuffer")){
				createHeatBuffer();
				heatSource.deserializeNBT(nbt.getCompoundTag("HeatBuffer"));
			}
		}

		onModularAssembled();
	}

	@Override
	public void onModularAssembled() {
		createHeatBuffer();
		fluidHandler = new FluidHandlerConcatenate(getTanks());
		itemHandler = new ItemHandler(getInventorys());
		if(!getEnergyBuffers().isEmpty()){
			energyBuffer = new ModularEnergyBuffer(getEnergyBuffers());
		}
		if(currentModule == null && getFirstGui() != null){
			currentModule = getFirstGui();
			setCurrentPage(((IModulePage)currentModule.getPages().get(0)).getPageID());
		}
		for(IModuleState state : getModules()){
			if(state != null){
				state.getModule().onModularAssembled(state);
			}
		}
		MinecraftForge.EVENT_BUS.post(new ModuleEvents.ModularAssembledEvent(this));
	}

	private void createHeatBuffer(){
		if(blockModificator == null){
			blockModificator = buildBlockModificator();
		}
		if(heatSource == null){
			int maxHeat = 500;
			if(blockModificator != null){
				maxHeat = blockModificator.getMaxHeat();
			}
			heatSource = new HeatBuffer(maxHeat, 15F);
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
		if(isServer){
			if(updateOnInterval(10)){
				boolean oneHeaterWork = false;
				List<IModuleState<IModuleHeater>> heaters = getModules(IModuleHeater.class);
				for(IModuleState<IModuleHeater> heater : heaters){
					if(heater.getModule().isWorking(heater)){
						oneHeaterWork = true;
					}
				}
				if(!oneHeaterWork){
					double oldHeat = heatSource.getHeatStored();
					heatSource.reduceHeat(2);
					if(oldHeat != heatSource.getHeatStored()){
						PacketHandler.INSTANCE.sendToAll(new PacketSyncHeatBuffer(modularHandler));
					}
				}
			}
		}
		for(IModuleState moduleState : getModules()) {
			if (moduleState != null) {
				if(moduleState.getModule() instanceof ITickable){
					MinecraftForge.EVENT_BUS.post(new ModuleEvents.ModuleUpdateEvent(moduleState, isServer ? Side.SERVER : Side.CLIENT));
					ITickable module = (ITickable)moduleState.getModule();
					if (isServer) {
						module.updateServer(moduleState, tickCount);
					} else {
						module.updateClient(moduleState, tickCount);
					}
				}
				for(IModulePage page : (List<IModulePage>)moduleState.getPages()){
					if(page instanceof ITickable){
						MinecraftForge.EVENT_BUS.post(new ModuleEvents.ModulePageUpdateEvent(moduleState, page, isServer ? Side.SERVER : Side.CLIENT));
						if (isServer) {
							((ITickable)page).updateServer(moduleState, tickCount);
						} else {
							((ITickable)page).updateClient(moduleState, tickCount);
						}
					}
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
		NBTTagList list = nbt.getTagList("Storages", 10);
		for(int i = 0;i < list.tagCount();i++){
			try{
				NBTTagCompound tagCompound = list.getCompoundTagAt(i);
				IModuleItemContainer itemContainer = ModuleManager.MODULE_CONTAINERS.getValue(new ResourceLocation(tagCompound.getString("Container")));
				ItemStack itemStack = null;
				if(tagCompound.hasKey("Item")){
					itemStack = ItemStack.loadItemStackFromNBT(tagCompound.getCompoundTag("Item"));
				}
				IModuleProvider provider = new ModuleProvider(itemContainer, this, itemStack);
				provider.deserializeNBT(tagCompound.getCompoundTag("Provider"));
				IStoragePosition position = (IStoragePosition) modularHandler.getStoragePositions().get(tagCompound.getInteger("Position"));
				IModuleState<IStorageModule> moduleState = ModuleManager.getStorageState(provider, position);
				if(moduleState != null && moduleState.getModule() != null){
					IStorage storage = moduleState.getModule().createStorage(provider, position);
					storage.deserializeNBT(tagCompound.getCompoundTag("Storage"));
					storages.put(position, storage);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		if (nbt.hasKey("CurrentModule")) {
			currentModule = getModule(nbt.getInteger("CurrentModule"));
			if (currentModule != null && nbt.hasKey("CurrentPage")) {
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
		if (currentModule != null) {
			nbt.setInteger("CurrentModule", currentModule.getIndex());
			if (currentPage != null) {
				nbt.setString("CurrentPage", currentPage.getPageID());
			}
		}
		if(heatSource != null){
			nbt.setTag("HeatBuffer", heatSource.serializeNBT());
		}
		NBTTagList list = new NBTTagList();
		for(Entry<IStoragePosition, IStorage> entry : storages.entrySet()){
			if(entry.getValue() != null){
				IStorage storage = entry.getValue();
				NBTTagCompound tagCompound = new NBTTagCompound();
				IModuleProvider provider = storage.getProvider();
				if(!ItemUtil.isIdenticalItem(provider.getItemStack(), provider.getContainer().getItemStack())){
					tagCompound.setTag("Item", provider.getItemStack().serializeNBT());
				}
				tagCompound.setString("Container", provider.getContainer().getRegistryName().toString());
				tagCompound.setTag("Provider", provider.serializeNBT());
				tagCompound.setTag("Storage", storage.serializeNBT());
				tagCompound.setInteger("Position", modularHandler.getStoragePositions().indexOf(entry.getKey()));
				list.appendTag(tagCompound);
			}
		}
		nbt.setTag("Storages", list);
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
		ArrayList<IModuleState> wailaModuleStates = Lists.newArrayList();
		for(IModuleState state : getModules()) {
			if (state.getModule() instanceof IModuleWaila) {
				wailaModuleStates.add(state);
			}
		}
		return wailaModuleStates;
	}

	@Override
	public IModulePage getCurrentPage() {
		return currentPage;
	}

	@Override
	public IModuleState getCurrentModule() {
		return currentModule;
	}

	@Override
	public void setCurrentModule(IModuleState module) {
		this.currentModule = module;
		this.currentPage = (IModulePage) currentModule.getPages().get(0);
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
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public GuiContainer createGui(IModularHandler tile, InventoryPlayer inventory) {
		if(currentPage != null){
			return new GuiPage(tile, inventory, currentPage);
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

	protected IBlockModificator buildBlockModificator(){
		int modificators = 0;
		int maxHeat = 0;
		float resistance = 0F;
		float hardness = 0F;
		boolean hasModificator = false;
		for(IModuleState state : getModules()){
			IBlockModificator modificator = state.getContentHandler(IBlockModificator.class);
			if(modificator != null){
				hasModificator = true;
				modificators++;
				maxHeat+=modificator.getMaxHeat();
				resistance+=modificator.getResistance();
				hardness+=modificator.getHardness();
			}
		}
		if(!hasModificator){
			return null;
		}
		return new BlockModificator(null, maxHeat / modificators, resistance / modificators, hardness / modificators / modificators);
	}

	protected List<IItemHandler> getInventorys(){
		List<IItemHandler> handlers = Lists.newArrayList();
		for(IModuleState state : getModules()) {
			addInventory(state, handlers);
			for(IModulePage page : (List<IModulePage>)state.getPages()){
				if(page != null){
					addInventory(page, handlers);
				}
			}
		}
		return handlers;
	}

	private void addInventory(IModuleContentProvider provider, List<IItemHandler> handlers){
		IModuleContentHandler inventory = provider.getContentHandler(IModuleInventory.class);
		if(inventory instanceof IModuleInventory){
			handlers.add((IModuleInventory) inventory);
		}
	}

	protected List<IFluidHandler> getTanks() {
		List<IFluidHandler> fluidHandlers = Lists.newArrayList();
		for(IModuleState state : getModules()) {
			addTank(state, fluidHandlers);
			for(IModulePage page : (List<IModulePage>)state.getPages()){
				if(page != null){
					addTank(page, fluidHandlers);
				}
			}
		}
		return fluidHandlers;
	}

	private void addTank(IModuleContentProvider provider, List<IFluidHandler> handlers){
		IModuleContentHandler tank = provider.getContentHandler(IModuleTank.class);
		if(tank instanceof IModuleTank){
			handlers.add((IModuleTank) tank);
		}
	}

	protected List<IEnergyBuffer> getEnergyBuffers() {
		List<IEnergyBuffer> handlers = Lists.newArrayList();
		for(IModuleState state : getModules()) {
			IModuleContentHandler rnergyInterface = (IModuleContentHandler) state.getContentHandler(IEnergyBuffer.class);
			if(rnergyInterface instanceof IEnergyBuffer){
				handlers.add((IEnergyBuffer) rnergyInterface);
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
		return null;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
			return true;
		}
		return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
	}

	@Override
	public List<IModuleState> getModules() {
		List<IModuleState> modules = new ArrayList<>();
		for(IStorage storage : storages.values()){
			if(storage != null){
				modules.addAll(storage.getProvider().getModuleStates());
				if(storage instanceof IModuleStorage){
					IModuleStorage moduleStorage = (IModuleStorage) storage;
					List<IModuleState> moduleStates = moduleStorage.getModules();
					if(!moduleStates.isEmpty()){
						modules.addAll(moduleStates);
					}
				}
			}
		}
		return modules;
	}

	@Override
	public List<IModuleProvider> getProviders() {
		List<IModuleProvider> providers = new ArrayList<>();
		for(IStorage storage : storages.values()){
			if(storage != null){
				providers.add(storage.getProvider());
				if(storage instanceof IModuleStorage){
					IModuleStorage moduleStorage = (IModuleStorage) storage;
					List<IModuleProvider> moduleProviders = moduleStorage.getProviders();
					if(!moduleProviders.isEmpty()){
						providers.addAll(moduleProviders);
					}
				}
			}
		}
		return providers;
	}

	@Override
	public <M extends IModule> IModuleState<M> getModule(int index) {
		for(IStorage storage : storages.values()){
			for(IModuleState state : storage.getProvider().getModuleStates()){
				if (state.getIndex() == index) {
					return state;
				}
			}
			if(storage instanceof IModuleStorage){
				IModuleState<M> state = ((IModuleStorage) storage).getModule(index);
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
		for(IStorage storage : storages.values()){
			for(IModuleState state : storage.getProvider().getModuleStates()){
				if (moduleClass.isAssignableFrom(state.getModule().getClass())) {
					modules.add(state);
				}
			}
			if(storage instanceof IModuleStorage){
				modules.addAll(((IModuleStorage) storage).getModules(moduleClass));
			}
		}
		return modules;
	}

	@Override
	public <M extends IModule> IModuleState<M> getModule(Class<? extends M> moduleClass) {
		if (moduleClass == null) {
			return null;
		}
		for(IStorage storage : storages.values()){
			for(IModuleState state : storage.getProvider().getModuleStates()){
				if (moduleClass.isAssignableFrom(state.getModule().getClass())) {
					return state;
				}
			}
			if(storage instanceof IModuleStorage){
				IModuleState<M> state = ((IModuleStorage) storage).getModule(moduleClass);
				if(state != null){
					return state;
				}
			}
		}
		return null;
	}

	@Override
	public int getComplexity(boolean withStorage) {
		int complexity = 0;
		for(IStorage storage : storages.values()){
			if(storage instanceof IModuleStorage){
				if(storage instanceof IBasicModuleStorage){
					complexity+=((IBasicModuleStorage) storage).getComplexity(withStorage);
				}else{
					for(IModuleState state : ((IModuleStorage) storage).getModules()){
						if(state != null){	
							if(state.getModule() instanceof IModuleModuleStorage && !withStorage){
								continue;
							}
							complexity +=state.getModule().getComplexity(state.getContainer());
						}
					}
				}
			}
		}
		return complexity;
	}

	@Override
	public int getNextIndex() {
		return index++;
	}

	@Override
	public IHeatSource getHeatSource() {
		return heatSource;
	}

	@Override
	public IEnergyBuffer getEnergyBuffer() {
		return energyBuffer;
	}

	@Override
	public IBlockModificator getBlockModificator() {
		return blockModificator;
	}

	@Override
	public boolean addStorage(IStorage storage) {
		if(!storages.containsKey(storage.getPosition()) && storage.getProvider() != null){
			storages.put(storage.getPosition(), storage);
			return true;
		}
		return false;
	}

	@Override
	public Map<IStoragePosition, IStorage> getStorages() {
		return Collections.unmodifiableMap(storages);
	}

	@Override
	public IModular copy(IModularHandler handler) {
		return new Modular(handler, serializeNBT());
	}

	@Override
	public IModularAssembler disassemble() {
		if(modularHandler instanceof IModularHandlerTileEntity){
			((IModularHandlerTileEntity)modularHandler).invalidate();
		}
		ItemStack[] stacks = new ItemStack[modularHandler.getStoragePositions().size()];
		Map<IStoragePosition, IStoragePage> pages = new HashMap<>();
		for(IStoragePosition position : (List<IStoragePosition>)modularHandler.getStoragePositions()){
			IStorage storage = storages.get(position);
			if(storage != null){
				IModuleProvider provider = storage.getProvider();
				stacks[modularHandler.getStoragePositions().indexOf(position)] = ModuleManager.saveModuleStateToItem(provider);
				IModuleState<IStorageModule> state = ModuleManager.getStorageState(provider, position);
				IModuleContainer<IStorageModule, IModuleProperties> moduleContainer = state.getContainer();
				IStoragePage page = state.getModule().createPage(null, this, storage, position);
				pages.put(position, page);
				IStoragePosition secondPos = moduleContainer.getModule().getSecondPosition(moduleContainer, position);
				if(secondPos != null && secondPos != position){
					IStoragePage secondPage = moduleContainer.getModule().createSecondPage(position);
					page.addChild(secondPage);
					pages.put(secondPos, secondPage);
				}
			}else{
				pages.put(position, null);
			}
		}
		return new ModularAssembler(modularHandler, stacks, pages);
	}
}