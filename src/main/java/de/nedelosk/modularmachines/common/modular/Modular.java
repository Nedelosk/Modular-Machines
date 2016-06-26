package de.nedelosk.modularmachines.common.modular;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModularHandler;
import de.nedelosk.modularmachines.api.modular.IModularLogic;
import de.nedelosk.modularmachines.api.modular.IModularLogicType;
import de.nedelosk.modularmachines.api.modular.renderer.IRenderState;
import de.nedelosk.modularmachines.api.modular.renderer.ISimpleRenderer;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.ModuleManager;
import de.nedelosk.modularmachines.api.modules.handlers.IModuleContentHandler;
import de.nedelosk.modularmachines.api.modules.handlers.IModulePage;
import de.nedelosk.modularmachines.api.modules.handlers.tank.IModuleTank;
import de.nedelosk.modularmachines.api.modules.integration.IWailaProvider;
import de.nedelosk.modularmachines.api.modules.integration.IWailaState;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.client.gui.GuiModular;
import de.nedelosk.modularmachines.client.render.modules.ModularRenderer;
import de.nedelosk.modularmachines.common.inventory.ContainerModular;
import de.nedelosk.modularmachines.common.modular.handlers.EnergyHandler;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import de.nedelosk.modularmachines.common.network.packets.PacketSelectModule;
import de.nedelosk.modularmachines.common.network.packets.PacketSelectModulePage;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidHandlerConcatenate;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Modular implements IModular {

	protected IModularHandler modularHandler;
	protected List<IModuleState> modules = new ArrayList();
	protected int tier;
	protected int index;
	protected IModuleState currentModule;
	protected IModulePage currentPage;
	protected EnergyHandler energyHandler;
	protected FluidHandlerConcatenate fluidHandler;
	protected Map<IModularLogicType, List<IModularLogic>> logics;

	public Modular() {
		logics = new HashMap<>();
	}

	public Modular(NBTTagCompound nbt, IModularHandler machine) {
		this();
		if(machine != null){
			setHandler(machine);
		}
		if(nbt != null){
			readFromNBT(nbt);
		}
	}

	@Override
	public void onAssembleModular() {
		fluidHandler = new FluidHandlerConcatenate(getTanks());
		modules = Collections.unmodifiableList(modules);
		currentModule = getFirstGui();
		setCurrentPage(0);

		logics.clear();

		for(IModuleState moduleState : modules){
			if(moduleState != null){
				for(IModularLogic logic : moduleState.getModule().createLogic(moduleState)){
					if(logic != null){
						int typeSize = 0;
						List<IModularLogic> logics =  this.logics.get(logic.getType());
						if(logics != null){
							typeSize = logics.size();
						}
						if(logic.getType().getMaxLogics() >= typeSize){
							if(this.logics.get(logic.getType()) == null){
								this.logics.put(logic.getType(), new ArrayList<>());
							}
							this.logics.get(logic.getType()).add(logic);
						}
					}
				}
			}
		}
	}

	private IModuleState getFirstGui() {
		for(IModuleState module : modules) {
			if (module.getPages() != null) {
				return module;
			}
		}
		return null;
	}

	@Override
	public void update(boolean isServer) {
		for(IModuleState moduleState : modules) {
			if (moduleState != null) {
				if (isServer) {
					moduleState.getModule().updateServer(moduleState);
				} else {
					moduleState.getModule().updateClient(moduleState);
				}
			}
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		NBTTagList nbtList = nbt.getTagList("Modules", 10);
		for(int i = 0; i < nbtList.tagCount(); i++) {
			NBTTagCompound moduleTag = nbtList.getCompoundTagAt(i);
			NBTTagCompound nbtTagContainer = moduleTag.getCompoundTag("Container");
			ItemStack moduleItem = ItemStack.loadItemStackFromNBT(nbtTagContainer);
			IModuleContainer container = ModuleManager.getContainerFromItem(moduleItem);
			IModuleState state = container.getModule().createState(this, container);
			state.readFromNBT(moduleTag, this);
			modules.add(state);
		}
		tier = nbt.getInteger("Tier");
		if (nbt.hasKey("CurrentModule")) {
			currentModule = getModule(nbt.getInteger("CurrentModule"));
			if (nbt.hasKey("CurrentPage")) {
				currentPage = currentModule.getPages()[nbt.getInteger("CurrentPage")];
			}
		}
		if (nbt.getBoolean("EH")) {
			energyHandler = new EnergyHandler(this);
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		NBTTagList nbtList = new NBTTagList();
		for(IModuleState module : modules) {
			NBTTagCompound nbtTag = new NBTTagCompound();
			module.writeToNBT(nbtTag, this);
			NBTTagCompound nbtTagContainer = new NBTTagCompound();
			module.getContainer().getItemStack().writeToNBT(nbtTagContainer);
			nbtTag.setTag("Container", nbtTagContainer);
			nbtList.appendTag(nbtTag);
		}
		nbt.setTag("Modules", nbtList);
		nbt.setInteger("Tier", tier);
		if (currentModule != null) {
			nbt.setInteger("CurrentModule", currentModule.getIndex());
			if (currentPage != null) {
				nbt.setInteger("CurrentPage", currentPage.getPageID());
			}
		}
		if (energyHandler != null) {
			nbt.setBoolean("EH", true);
		} else {
			nbt.setBoolean("EH", false);
		}
		return nbt;
	}

	@Override
	public int getTier() {
		if (tier == 0) {
			int tiers = 0;
			for(IModuleState module : modules) {
				tiers += module.getContainer().getMaterial().getTier();
			}
			tier = tiers / modules.size();
		}
		return tier;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public ISimpleRenderer getRenderer(IRenderState state) {
		return new ModularRenderer();
	}

	@Override
	public void setHandler(IModularHandler tileEntity) {
		this.modularHandler = tileEntity;
	}

	@Override
	public IModularHandler getHandler() {
		return modularHandler;
	}

	private ArrayList<IModuleState> getWailaModules() {
		ArrayList<IModuleState> wailaModules = Lists.newArrayList();
		for(IModuleState module : modules) {
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
		this.currentPage = currentModule.getPages()[0];
		if (getHandler().getWorld().isRemote) {
			PacketHandler.INSTANCE.sendToServer(new PacketSelectModule((TileEntity) getHandler(), module));
			PacketHandler.INSTANCE.sendToServer(new PacketSelectModulePage((TileEntity) getHandler(), 0));
		}
	}

	@Override
	public void setCurrentPage(int pageID) {
		if(currentModule != null){
			this.currentPage = currentModule.getPages()[pageID];
			if(getHandler() != null){
				if (getHandler().getWorld().isRemote) {
					PacketHandler.INSTANCE.sendToServer(new PacketSelectModulePage((TileEntity) getHandler(), pageID));
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public GuiContainer getGUIContainer(IModularHandler tile, InventoryPlayer inventory) {
		return new GuiModular(tile, inventory, currentPage);
	}

	@Override
	public Container getContainer(IModularHandler tile, InventoryPlayer inventory) {
		return new ContainerModular(tile, inventory, currentPage);
	}

	@Override
	public boolean addModule(ItemStack itemStack, IModule module) {
		if (module == null) {
			return false;
		}
		IModuleState state = module.createState(this, ModuleManager.getContainerFromItem(itemStack));
		state = module.loadStateFromItem(state, itemStack);
		if (modules.add(state)) {
			state.setIndex(index++);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<IModuleState> getModuleStates() {
		return modules;
	}

	@Override
	public <M extends IModule> IModuleState<M> getModule(int index) {
		for(IModuleState module : modules) {
			if (module.getIndex() == index) {
				return module;
			}
		}
		return null;
	}

	@Override
	public <M extends IModule> IModuleState<M> getModule(ItemStack stack) {
		for(IModuleState module : this.modules) {
			IModuleContainer container = module.getContainer();
			if (container.getItemStack() != null && container.getItemStack().getItem() != null && stack.getItem() == container.getItemStack().getItem()
					&& stack.getItemDamage() == container.getItemStack().getItemDamage()) {
				if (container.ignorNBT() || ItemStack.areItemStackTagsEqual(stack, container.getItemStack())) {
					return module;
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
		for(IModuleState module : this.modules) {
			if (moduleClass.isAssignableFrom(module.getModule().getClass())) {
				modules.add(module);
			}
		}
		return modules;
	}

	@Override
	public EnergyHandler getEnergyHandler() {
		return energyHandler;
	}

	@Override
	public IFluidHandler getFluidHandler() {
		return fluidHandler;
	}

	@Override
	public <E extends IEnergyProvider & IEnergyReceiver> void setEnergyHandler(E energyHandler) {
		this.energyHandler = (EnergyHandler) energyHandler;
	}

	@Override
	public Map<IModularLogicType, List<IModularLogic>> getLogics() {
		return logics;
	}

	protected List<IFluidHandler> getTanks() {
		List<IFluidHandler> fluidHandlers = Lists.newArrayList();
		for(IModuleState state : modules) {
			IModuleContentHandler handler = state.getContentHandler(FluidStack.class);
			if(handler instanceof IModuleTank){
				fluidHandlers.add((IModuleTank) handler);
			}
		}
		return fluidHandlers;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY){
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(fluidHandler);
		}
		return null;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY){
			return true;
		}
		return false;
	}
}