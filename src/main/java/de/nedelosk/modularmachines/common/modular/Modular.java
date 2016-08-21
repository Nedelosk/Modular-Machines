package de.nedelosk.modularmachines.common.modular;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import de.nedelosk.modularmachines.api.energy.HeatBuffer;
import de.nedelosk.modularmachines.api.energy.IEnergyInterface;
import de.nedelosk.modularmachines.api.energy.IHeatSource;
import de.nedelosk.modularmachines.api.integration.IWailaState;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.IModuleTickable;
import de.nedelosk.modularmachines.api.modules.ModuleEvents;
import de.nedelosk.modularmachines.api.modules.handlers.BlockModificator;
import de.nedelosk.modularmachines.api.modules.handlers.IBlockModificator;
import de.nedelosk.modularmachines.api.modules.handlers.IModuleContentHandler;
import de.nedelosk.modularmachines.api.modules.handlers.IModulePage;
import de.nedelosk.modularmachines.api.modules.handlers.energy.IModuleEnergyInterface;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventory;
import de.nedelosk.modularmachines.api.modules.handlers.tank.IModuleTank;
import de.nedelosk.modularmachines.api.modules.integration.IModuleWaila;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storaged.IModuleModuleStorage;
import de.nedelosk.modularmachines.api.modules.storaged.drives.heaters.IModuleHeater;
import de.nedelosk.modularmachines.client.gui.GuiPage;
import de.nedelosk.modularmachines.common.inventory.ContainerModular;
import de.nedelosk.modularmachines.common.modular.handlers.EnergyHandler;
import de.nedelosk.modularmachines.common.modular.handlers.ItemHandler;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import de.nedelosk.modularmachines.common.network.packets.PacketSyncHeatBuffer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
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

public abstract class Modular implements IModular {

	protected IModularHandler modularHandler;
	protected int index;
	protected IModuleState currentModule;
	protected IModulePage currentPage;
	protected EnergyHandler energyHandler;
	protected FluidHandlerConcatenate fluidHandler;
	protected ItemHandler itemHandler;
	protected IBlockModificator blockModificator;
	protected HeatBuffer heatSource;

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
			if(nbt.hasKey("HeatBuffer")){
				createHeatBuffer();
				heatSource.deserializeNBT(nbt.getCompoundTag("HeatBuffer"));
			}
		}
		assembleModular();
	}

	@Override
	public void assembleModular() {
		createHeatBuffer();
		fluidHandler = new FluidHandlerConcatenate(getTanks());
		itemHandler = new ItemHandler(getInventorys());
		energyHandler = new EnergyHandler(getInterfaces());
		if(currentModule == null && getFirstGui() != null){
			currentModule = getFirstGui();
			setCurrentPage(((IModulePage)currentModule.getPages().get(0)).getPageID());
		}
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
					heatSource.reduceHeat(2);
					PacketHandler.INSTANCE.sendToAll(new PacketSyncHeatBuffer(modularHandler));
				}
			}
		}
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
	public List getWailaBody(ItemStack itemStack, List currenttip, IWailaState data) {
		for(IModuleState state : getWailaModules()) {
			if (state.getModule() instanceof IModuleWaila) {
				data.setState(state);
				List<String> tip = ((IModuleWaila) state.getModule()).getWailaBody(itemStack, currenttip, data);
				if (tip != null) {
					currenttip = tip;
				}
			}
		}
		return currenttip;
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaState data) {
		for(IModuleState state : getWailaModules()) {
			if (state.getModule() instanceof IModuleWaila) {
				data.setState(state);
				List<String> tip = ((IModuleWaila) state.getModule()).getWailaHead(itemStack, currenttip, data);
				if (tip != null) {
					currenttip = tip;
				}
			}
		}
		return currenttip;
	}

	@Override
	public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaState data) {
		for(IModuleState state : getWailaModules()) {
			if (state.getModule() instanceof IModuleWaila) {
				data.setState(state);
				List<String> tip = ((IModuleWaila) state.getModule()).getWailaTail(itemStack, currenttip, data);
				if (tip != null) {
					currenttip = tip;
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

	@Override
	public IEnergyInterface getEnergyInterface() {
		return energyHandler;
	}

	protected IBlockModificator buildBlockModificator(){
		int modificators = 0;
		int maxHeat = 0;
		float resistance = 0F;
		float hardness = 0F;
		int harvestLevel = 0;
		String harvestTool = null;
		boolean hasModificator = false;
		for(IModuleState state : getModules()){
			IModuleContentHandler handler = state.getContentHandler(IBlockModificator.class);
			if(handler instanceof IBlockModificator){
				hasModificator = true;
				IBlockModificator modificator = (IBlockModificator) handler;
				modificators++;
				maxHeat+=modificator.getMaxHeat();
				resistance+=modificator.getResistance();
				hardness+=modificator.getHardness();
				harvestLevel+=modificator.getHarvestLevel();
				String mHarvestTool = modificator.getHarvestTool();
				if(mHarvestTool.equals("wrench")){
					harvestTool = mHarvestTool;
				}else if(mHarvestTool.equals("pickaxe")){
					if(harvestTool == null || !harvestTool.equals("wrench")){
						harvestTool = mHarvestTool;
					}
				}else if(mHarvestTool.equals("axe")){
					if(harvestTool == null || !harvestTool.equals("wrench") && !harvestTool.equals("pickaxe")){
						harvestTool = mHarvestTool;
					}
				}else{
					if(harvestTool == null || !harvestTool.equals("wrench") && !harvestTool.equals("pickaxe") && !harvestTool.equals("axe")){
						harvestTool = mHarvestTool;
					}
				}
			}
		}
		if(!hasModificator){
			return null;
		}
		return new BlockModificator(null, maxHeat / modificators, resistance / modificators, hardness / modificators, harvestTool, harvestLevel / modificators);
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
	public <M extends IModule> IModuleState<M> getModule(int index) {
		for(IModuleState state : getModules()){
			if(state != null){
				if(state.getIndex() == index){
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
		for(IModuleState module : getModules()) {
			if (moduleClass.isAssignableFrom(module.getModule().getClass())) {
				modules.add(module);
			}
		}
		return modules;
	}

	@Override
	public <M extends IModule> IModuleState<M> getModule(Class<? extends M> moduleClass) {
		if (moduleClass == null) {
			return null;
		}
		List modules = getModules(moduleClass);
		if(modules.isEmpty()){
			return null;
		}
		return (IModuleState<M>) modules.get(0);
	}

	@Override
	public int getComplexity(boolean withStorage) {
		int complexity = 0;
		for(IModuleState state : getModules()){
			if(state != null){	
				if(state.getModule() instanceof IModuleModuleStorage && !withStorage){
					continue;
				}
				complexity +=state.getModule().getComplexity(state.getContainer());
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
	public IBlockModificator getBlockModificator() {
		return blockModificator;
	}
}