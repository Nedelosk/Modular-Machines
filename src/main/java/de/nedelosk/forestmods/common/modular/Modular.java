package de.nedelosk.forestmods.common.modular;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestmods.api.integration.IWailaProvider;
import de.nedelosk.forestmods.api.integration.IWailaState;
import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modular.IModularTileEntity;
import de.nedelosk.forestmods.api.modular.managers.IModularGuiManager;
import de.nedelosk.forestmods.api.modular.managers.IModularInventoryManager;
import de.nedelosk.forestmods.api.modular.managers.IModularManager;
import de.nedelosk.forestmods.api.modular.managers.IModularModuleManager;
import de.nedelosk.forestmods.api.modular.managers.IModularUtilsManager;
import de.nedelosk.forestmods.api.modules.special.IModuleController;
import de.nedelosk.forestmods.api.utils.ModularException;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.common.modular.managers.ModularGuiManager;
import de.nedelosk.forestmods.common.modular.managers.ModularInventoryManager;
import de.nedelosk.forestmods.common.modular.managers.ModularModuleManager;
import de.nedelosk.forestmods.common.modular.managers.ModularUtilsManager;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.StatCollector;

public abstract class Modular implements IModular, IWailaProvider {

	protected IModularTileEntity machine;
	@SideOnly(Side.CLIENT)
	protected IModularGuiManager guiManager;
	protected IModularUtilsManager utilsManager;
	protected IModularModuleManager moduleManager;
	protected IModularInventoryManager inventoryManager;
	protected String page;
	protected int tier;
	private boolean isAssembled;
	protected ModularException lastException;

	public Modular() {
		utilsManager = getUtilsManager();
		moduleManager = getModuleManager();
		isAssembled = false;
		inventoryManager = new ModularInventoryManager();
		inventoryManager.setModular(this);
		if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
			guiManager = new ModularGuiManager();
			guiManager.setModular(this);
		}
	}

	public Modular(NBTTagCompound nbt, IModularTileEntity machine) {
		setTile(machine);
		readFromNBT(nbt);
	}

	public Modular(NBTTagCompound nbt) {
		readFromNBT(nbt);
	}

	@Override
	public void assemble() {
		ModuleStack<IModuleController> controller = null;
		lastException = null;
		for ( ModuleStack stack : (List<ModuleStack>) moduleManager.getModuleStacks() ) {
			if (stack.getModule() instanceof IModuleController) {
				controller = stack;
			}
		}
		if (controller == null) {
			lastException = new ModularException(StatCollector.translateToLocal("modular.ex.find.controller"));
		}
		for ( ModuleStack stack : (List<ModuleStack>) moduleManager.getModuleStacks() ) {
			try {
				stack.getModule().canAssembleModular(this, stack, controller, moduleManager.getModuleStacks());
			} catch (ModularException e) {
				lastException = e;
			}
		}
		if (lastException == null && controller.getModule().canAssembleModular(this, controller)) {
			for ( ModuleStack stack : (List<ModuleStack>) moduleManager.getModuleStacks() ) {
				try {
					stack.getModule().onModularAssembled(this, stack, controller, moduleManager.getModuleStacks());
				} catch (ModularException e) {
					lastException = e;
					return;
				}
			}
			if (lastException == null) {
				isAssembled = true;
			}
		}
	}

	@Override
	public void update(boolean isServer) {
		if (isAssembled) {
			List<ModuleStack> stacks = moduleManager.getModuleStacks();
			for ( ModuleStack stack : stacks ) {
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
		getModuleManager().readFromNBT(nbt.getCompoundTag("ModuleManager"));
		getUtilsManager().readFromNBT(nbt.getCompoundTag("UtilsManager"));
		getInventoryManager().readFromNBT(nbt.getCompoundTag("InventoryManager"));
		if (nbt.hasKey("GuiManager")) {
			getGuiManager().readFromNBT(nbt.getCompoundTag("GuiManager"));
		}
		isAssembled = nbt.getBoolean("isAssembled");
		NBTTagList listTag = nbt.getTagList("Modules", 10);
		tier = nbt.getInteger("Tier");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		NBTTagCompound nbtTagUtils = new NBTTagCompound();
		getUtilsManager().writeToNBT(nbtTagUtils);
		nbt.setTag("UtilsManager", nbtTagUtils);
		NBTTagCompound module = new NBTTagCompound();
		getModuleManager().writeToNBT(module);
		nbt.setTag("ModuleManager", module);
		NBTTagCompound inventory = new NBTTagCompound();
		getInventoryManager().writeToNBT(inventory);
		nbt.setTag("InventoryManager", inventory);
		if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
			NBTTagCompound gui = new NBTTagCompound();
			getGuiManager().writeToNBT(gui);
			nbt.setTag("GuiManager", gui);
		}
		nbt.setBoolean("isAssembled", isAssembled);
		nbt.setInteger("Tier", tier);
	}

	@Override
	public int getTier() {
		if (tier == 0) {
			int tiers = 0;
			for ( ModuleStack stack : (List<ModuleStack>) moduleManager.getModuleStacks() ) {
				tiers += stack.getMaterial().getTier();
			}
			tier = tiers / moduleManager.getModuleStacks().size();
		}
		return tier;
	}

	@Override
	public void initModular() {
	}

	@Override
	public void setTile(IModularTileEntity machine) {
		this.machine = machine;
	}

	@Override
	public IModularTileEntity getTile() {
		return machine;
	}

	public IModularUtilsManager getUtilsManager() {
		return getManager(IModularUtilsManager.class);
	}

	public IModularModuleManager getModuleManager() {
		return getManager(IModularModuleManager.class);
	}

	public IModularInventoryManager getInventoryManager() {
		return getManager(IModularInventoryManager.class);
	}

	public IModularGuiManager getGuiManager() {
		return getManager(IModularGuiManager.class);
	}

	@Override
	public <M extends IModularManager> M getManager(Class<? extends M> managerClass) {
		if (managerClass.isAssignableFrom(IModularUtilsManager.class)) {
			if (utilsManager == null) {
				utilsManager = new ModularUtilsManager();
				utilsManager.setModular(this);
			}
			return (M) utilsManager;
		} else if (managerClass.isAssignableFrom(IModularModuleManager.class)) {
			if (moduleManager == null) {
				moduleManager = new ModularModuleManager();
				moduleManager.setModular(this);
			}
			return (M) moduleManager;
		} else if (managerClass.isAssignableFrom(IModularInventoryManager.class)) {
			if (inventoryManager == null) {
				inventoryManager = new ModularInventoryManager();
				inventoryManager.setModular(this);
			}
			return (M) inventoryManager;
		} else if (managerClass.isAssignableFrom(IModularGuiManager.class)) {
			if (guiManager == null) {
				guiManager = new ModularGuiManager();
				guiManager.setModular(this);
			}
			return (M) guiManager;
		}
		return null;
	}

	private ArrayList<ModuleStack> getWailaModules() {
		ArrayList<ModuleStack> wailaModules = Lists.newArrayList();
		for ( ModuleStack stack : (List<ModuleStack>) moduleManager.getModuleStacks() ) {
			if (stack.getModule() != null && stack.getModule() instanceof IWailaProvider) {
				wailaModules.add(stack);
			}
		}
		return wailaModules;
	}

	@Override
	public List getWailaBody(ItemStack itemStack, List currenttip, IWailaState data) {
		for ( ModuleStack stack : getWailaModules() ) {
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
		for ( ModuleStack stack : getWailaModules() ) {
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
		for ( ModuleStack stack : getWailaModules() ) {
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
}
