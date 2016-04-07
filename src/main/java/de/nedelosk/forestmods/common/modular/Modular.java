package de.nedelosk.forestmods.common.modular;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;

import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestmods.api.integration.IWailaProvider;
import de.nedelosk.forestmods.api.integration.IWailaState;
import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modular.IModularTileEntity;
import de.nedelosk.forestmods.api.modular.renderer.IRenderState;
import de.nedelosk.forestmods.api.modular.renderer.ISimpleRenderer;
import de.nedelosk.forestmods.api.modules.IModule;
import de.nedelosk.forestmods.api.modules.handlers.IModulePage;
import de.nedelosk.forestmods.api.modules.special.IModuleController;
import de.nedelosk.forestmods.api.utils.ModularException;
import de.nedelosk.forestmods.api.utils.ModuleManager;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.api.utils.ModuleUID;
import de.nedelosk.forestmods.client.gui.GuiModular;
import de.nedelosk.forestmods.client.render.modules.ModularRenderer;
import de.nedelosk.forestmods.common.inventory.ContainerModular;
import de.nedelosk.forestmods.common.modular.handlers.EnergyHandler;
import de.nedelosk.forestmods.common.modular.handlers.FluidHandler;
import de.nedelosk.forestmods.common.network.PacketHandler;
import de.nedelosk.forestmods.common.network.packets.PacketSelectModulePage;
import de.nedelosk.forestmods.common.network.packets.PacketSelectModuleStack;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.IFluidHandler;

public class Modular implements IModular, IWailaProvider {

	protected IModularTileEntity tileEntity;
	protected List<ModuleStack> moduleStacks = new ArrayList();
	protected List<ItemStack> itemStacks = new ArrayList();
	protected int tier;
	private boolean isAssembled;
	protected ModularException lastException;
	protected ModuleStack currentStack;
	protected IModulePage currentPage;
	protected EnergyHandler energyHandler;
	protected FluidHandler fluidHandler;

	public Modular() {
		isAssembled = false;
	}

	public Modular(NBTTagCompound nbt, IModularTileEntity machine) {
		setTile(machine);
		readFromNBT(nbt);
	}

	@Override
	public void assemble() throws ModularException {
		ModuleStack<IModuleController> controller = null;
		for(ModuleStack stack : moduleStacks) {
			if (stack.getModule() instanceof IModuleController) {
				if (controller != null) {
					throw new ModularException(StatCollector.translateToLocal("modular.ex.controller.more"));
				}
				controller = stack;
			}
		}
		if (controller == null) {
			throw new ModularException(StatCollector.translateToLocal("modular.ex.controller.find"));
		}
		for(ModuleStack stack : moduleStacks) {
			stack.getModule().canAssembleModular(controller);
		}
		controller.getModule().canAssembleModular();
		for(ModuleStack stack : moduleStacks) {
			stack.getModule().onModularAssembled(controller);
		}
		onAssembleModular();
	}

	public void onAssembleModular() {
		fluidHandler = new FluidHandler(this);
		moduleStacks = Collections.unmodifiableList(moduleStacks);
		itemStacks = Collections.unmodifiableList(itemStacks);
		currentStack = getFirstGui();
		setCurrentPage(0);
		isAssembled = true;
	}

	private ModuleStack getFirstGui() {
		for(ModuleStack stack : moduleStacks) {
			if (!stack.getModule().isHandlerDisabled(ModuleManager.guiType) && stack.getModule().getPages() != null) {
				return stack;
			}
		}
		return null;
	}

	@Override
	public void update(boolean isServer) {
		if (isAssembled) {
			for(ModuleStack stack : moduleStacks) {
				if (stack != null && stack.getModule() != null) {
					if (isServer) {
						stack.getModule().updateServer();
					} else {
						stack.getModule().updateClient();
					}
				}
			}
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		isAssembled = nbt.getBoolean("isAssembled");
		NBTTagList nbtList = nbt.getTagList("Modules", 10);
		NBTTagList nbtListItems = nbt.getTagList("Items", 10);
		for(int i = 0; i < nbtList.tagCount(); i++) {
			NBTTagCompound moduleTag = nbtList.getCompoundTagAt(i);
			NBTTagCompound itemTag = nbtListItems.getCompoundTagAt(i);
			ModuleStack moduleStack = ModuleStack.loadFromNBT(moduleTag, this);
			ItemStack itemStack = ItemStack.loadItemStackFromNBT(itemTag);
			moduleStacks.add(moduleStack);
			itemStacks.add(itemStack);
		}
		if (isAssembled) {
			moduleStacks = Collections.unmodifiableList(moduleStacks);
			itemStacks = Collections.unmodifiableList(itemStacks);
		}
		tier = nbt.getInteger("Tier");
		if (nbt.hasKey("CurrentUID")) {
			currentStack = getModuleStack(new ModuleUID(nbt.getString("CurrentUID")));
			if (nbt.hasKey("CurrentPage")) {
				currentPage = currentStack.getModule().getPages()[nbt.getInteger("CurrentPage")];
			}
		}
		if (nbt.getBoolean("EH")) {
			energyHandler = new EnergyHandler(this);
		}
		if (nbt.getBoolean("FH")) {
			fluidHandler = new FluidHandler(this);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		NBTTagList nbtList = new NBTTagList();
		for(ModuleStack stack : moduleStacks) {
			NBTTagCompound nbtTag = new NBTTagCompound();
			stack.writeToNBT(nbtTag, this);
			nbtList.appendTag(nbtTag);
		}
		nbt.setTag("Modules", nbtList);
		NBTTagList nbtListItems = new NBTTagList();
		for(ItemStack stack : itemStacks) {
			NBTTagCompound nbtTag = new NBTTagCompound();
			stack.writeToNBT(nbtTag);
			nbtListItems.appendTag(nbtTag);
		}
		nbt.setTag("Items", nbtListItems);
		nbt.setBoolean("isAssembled", isAssembled);
		nbt.setInteger("Tier", tier);
		if (currentStack != null) {
			nbt.setString("CurrentUID", currentStack.getUID().toString());
			if (currentPage != null) {
				nbt.setInteger("CurrentPage", currentPage.getPageID());
			}
		}
		if (energyHandler != null) {
			nbt.setBoolean("EH", true);
		} else {
			nbt.setBoolean("EH", false);
		}
		if (fluidHandler != null) {
			nbt.setBoolean("FH", true);
		} else {
			nbt.setBoolean("FH", false);
		}
	}

	@Override
	public int getTier() {
		if (tier == 0) {
			int tiers = 0;
			for(ModuleStack stack : moduleStacks) {
				tiers += stack.getMaterial().getTier();
			}
			tier = tiers / moduleStacks.size();
		}
		return tier;
	}

	@Override
	public void initModular() {
	}

	@SideOnly(Side.CLIENT)
	@Override
	public ISimpleRenderer getRenderer(IRenderState state) {
		return new ModularRenderer();
	}

	@Override
	public void setTile(IModularTileEntity tileEntity) {
		this.tileEntity = tileEntity;
	}

	@Override
	public IModularTileEntity getTile() {
		return tileEntity;
	}

	private ArrayList<ModuleStack> getWailaModules() {
		ArrayList<ModuleStack> wailaModules = Lists.newArrayList();
		for(ModuleStack stack : moduleStacks) {
			if (stack.getModule() != null && stack.getModule() instanceof IWailaProvider) {
				wailaModules.add(stack);
			}
		}
		return wailaModules;
	}

	@Override
	public List getWailaBody(ItemStack itemStack, List currenttip, IWailaState data) {
		for(ModuleStack stack : getWailaModules()) {
			if (stack.getModule() instanceof IWailaProvider) {
				if (((IWailaProvider) stack.getModule()).getWailaBody(itemStack, currenttip, data) != null) {
					currenttip = ((IWailaProvider) stack.getModule()).getWailaBody(itemStack, currenttip, data);
				}
			}
		}
		return currenttip;
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaState data) {
		for(ModuleStack stack : getWailaModules()) {
			if (stack.getModule() instanceof IWailaProvider) {
				if (((IWailaProvider) stack.getModule()).getWailaHead(itemStack, currenttip, data) != null) {
					currenttip = ((IWailaProvider) stack.getModule()).getWailaHead(itemStack, currenttip, data);
				}
			}
		}
		return currenttip;
	}

	@Override
	public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaState data) {
		for(ModuleStack stack : getWailaModules()) {
			if (stack.getModule() instanceof IWailaProvider) {
				if (((IWailaProvider) stack.getModule()).getWailaTail(itemStack, currenttip, data) != null) {
					currenttip = ((IWailaProvider) stack.getModule()).getWailaTail(itemStack, currenttip, data);
				}
			}
		}
		return currenttip;
	}

	@Override
	public IWailaProvider getWailaProvider(IModularTileEntity tile) {
		return this;
	}

	@Override
	public boolean isAssembled() {
		return isAssembled;
	}

	@Override
	public ModularException getLastException() {
		return lastException;
	}

	@Override
	public void setLastException(ModularException lastException) {
		this.lastException = lastException;
	}

	@Override
	public IModulePage getCurrentPage() {
		return currentPage;
	}

	@Override
	public ModuleStack getCurrentStack() {
		return currentStack;
	}

	@Override
	public void setCurrentStack(ModuleStack stack) {
		this.currentStack = stack;
		this.currentPage = currentStack.getModule().getPages()[0];
		if (getTile().getWorld().isRemote) {
			PacketHandler.INSTANCE.sendToServer(new PacketSelectModuleStack((TileEntity) getTile(), stack));
			PacketHandler.INSTANCE.sendToServer(new PacketSelectModulePage((TileEntity) getTile(), 0));
		}
	}

	@Override
	public void setCurrentPage(int pageID) {
		this.currentStack.getModule().setCurrentPage(pageID);
		this.currentPage = currentStack.getModule().getCurrentPage();
		if (getTile().getWorld().isRemote) {
			PacketHandler.INSTANCE.sendToServer(new PacketSelectModulePage((TileEntity) getTile(), pageID));
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public GuiContainer getGUIContainer(IModularTileEntity tile, InventoryPlayer inventory) {
		return new GuiModular(tile, inventory, currentPage);
	}

	@Override
	public Container getContainer(IModularTileEntity tile, InventoryPlayer inventory) {
		return new ContainerModular(tile, inventory, currentPage);
	}

	@Override
	public boolean addModule(ItemStack itemStack, ModuleStack stack) {
		if (stack == null) {
			return false;
		}
		IModule module = stack.getModule();
		if (module == null) {
			return false;
		}
		ModuleStack<IModuleController> controller = null;
		for(ModuleStack moduleStack : moduleStacks) {
			if (moduleStack.getModule() instanceof IModuleController) {
				controller = moduleStack;
			}
		}
		if (controller == null) {
			if (stack.getModule() instanceof IModuleController) {
				if (moduleStacks.add(stack.copy()) & itemStacks.add(itemStack.copy())) {
					return true;
				} else {
					return false;
				}
			}
			return false;
		} else if (stack.getModule() instanceof IModuleController) {
			return false;
		}
		List<Class<? extends IModule>> allowedModules = controller.getModule().getAllowedModules();
		for(ModuleStack<IModule> moduleStack : moduleStacks) {
			Iterator<Class<? extends IModule>> allowedModulesIterator = allowedModules.iterator();
			while (allowedModulesIterator.hasNext()) {
				Class<? extends IModule> allowedModule = allowedModulesIterator.next();
				if (allowedModule.isAssignableFrom(moduleStack.getModule().getClass())) {
					allowedModulesIterator.remove();
					continue;
				}
			}
		}
		for(Class<? extends IModule> allowedModule : allowedModules) {
			if (allowedModule.isAssignableFrom(stack.getModule().getClass())) {
				if (moduleStacks.add(stack.copy()) & itemStacks.add(itemStack.copy())) {
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}

	@Override
	public List<ModuleStack> getModuleStacks() {
		return moduleStacks;
	}

	@Override
	public ModuleStack getModuleStack(ModuleUID UID) {
		for(ModuleStack stack : moduleStacks) {
			if (stack.getUID().equals(UID)) {
				return stack;
			}
		}
		return null;
	}

	@Override
	public ItemStack getItemStack(ModuleUID UID) {
		ModuleStack stack = getModuleStack(UID);
		return itemStacks.get(moduleStacks.indexOf(stack));
	}

	@Override
	public List getModuleSatcks(Class moduleClass) {
		if (moduleClass == null) {
			return null;
		}
		List<ModuleStack> stackList = Lists.newArrayList();
		for(ModuleStack stack : moduleStacks) {
			if (moduleClass.isAssignableFrom(stack.getModule().getClass())) {
				stackList.add(stack);
			}
		}
		return stackList;
	}

	@Override
	public IEnergyHandler getEnergyHandler() {
		return energyHandler;
	}

	@Override
	public IFluidHandler getFluidHandler() {
		return fluidHandler;
	}

	@Override
	public void setFluidHandler(IFluidHandler fluidHandler) {
		this.fluidHandler = (FluidHandler) fluidHandler;
	}

	@Override
	public <E extends IEnergyProvider & IEnergyReceiver> void setEnergyHandler(E energyHandler) {
		this.energyHandler = (EnergyHandler) energyHandler;
	}
}