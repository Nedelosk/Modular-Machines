package de.nedelosk.forestmods.common.modular;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modular.integration.IWailaData;
import de.nedelosk.forestmods.api.modular.integration.IWailaProvider;
import de.nedelosk.forestmods.api.modular.managers.IModularModuleManager;
import de.nedelosk.forestmods.api.modular.managers.IModularUtilsManager;
import de.nedelosk.forestmods.api.modular.tile.IModularTileEntity;
import de.nedelosk.forestmods.api.modules.IModuleSaver;
import de.nedelosk.forestmods.api.modules.basic.IModuleUpdatable;
import de.nedelosk.forestmods.api.modules.integration.IModuleWaila;
import de.nedelosk.forestmods.api.modules.special.IModuleController;
import de.nedelosk.forestmods.api.utils.ModularException;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.common.modular.managers.ModularModuleManager;
import de.nedelosk.forestmods.common.modular.managers.ModularUtilsManager;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.StatCollector;

public abstract class Modular implements IModular, IWailaProvider {

	protected IModularTileEntity machine;
	protected IModularUtilsManager utilsManager;
	protected IModularModuleManager moduleManager;
	protected String page;
	protected int tier;
	private boolean isAssembled;
	protected ModularException lastException;

	public Modular() {
		utilsManager = getUtilsManager();
		moduleManager = getModuleManager();
		isAssembled = false;
	}

	public Modular(NBTTagCompound nbt, IModularTileEntity machine) {
		setMachine(machine);
		readFromNBT(nbt);
	}

	@Override
	public void assemble() {
		ModuleStack<IModuleController, IModuleSaver> controller = null;
		for ( ModuleStack stack : (List<ModuleStack>) moduleManager.getModuleStacks() ) {
			if (stack.getModule() instanceof IModuleController) {
				controller = stack;
			}
		}
		if (controller == null) {
			lastException = new ModularException(StatCollector.translateToLocal("modular.ex.find.controller"));
		}
		List<ModuleStack> modules = moduleManager.getModuleStacks();
		for ( ModuleStack stack : (List<ModuleStack>) moduleManager.getModuleStacks() ) {
			try {
				stack.getModule().canAssembleModular(this, stack, controller, modules);
			} catch (ModularException e) {
				lastException = e;
			}
		}
		if (controller.getModule().canAssembleModular(this, controller)) {
			isAssembled = true;
			lastException = null;
		}
	}

	@Override
	public void update(boolean isServer) {
		if (isAssembled) {
			List<ModuleStack> stacks = moduleManager.getModuleStacks();
			for ( ModuleStack stack : stacks ) {
				if (stack != null && stack.getModule() != null && stack.getModule() instanceof IModuleUpdatable) {
					if (isServer) {
						((IModuleUpdatable) stack.getModule()).updateServer(this, stack);
					} else {
						((IModuleUpdatable) stack.getModule()).updateClient(this, stack);
					}
				}
			}
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		getModuleManager().readFromNBT(nbt.getCompoundTag("ModuleManager"));
		getUtilsManager().readFromNBT(nbt.getCompoundTag("UtilsManager"));
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
	public void setMachine(IModularTileEntity machine) {
		this.machine = machine;
	}

	@Override
	public IModularTileEntity getMachine() {
		return machine;
	}

	@Override
	public IModularUtilsManager getUtilsManager() {
		if (utilsManager == null) {
			utilsManager = new ModularUtilsManager();
			utilsManager.setModular(this);
		}
		return utilsManager;
	}

	@Override
	public IModularModuleManager getModuleManager() {
		if (moduleManager == null) {
			moduleManager = new ModularModuleManager();
			moduleManager.setModular(this);
		}
		return moduleManager;
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
	public List getWailaBody(ItemStack itemStack, List currenttip, IWailaData data) {
		for ( ModuleStack<IModuleWaila, IModuleSaver> stack : getWailaModules() ) {
			if (stack.getModule().getWailaBody(itemStack, currenttip, data) != null) {
				currenttip = stack.getModule().getWailaBody(itemStack, currenttip, data);
			}
		}
		return currenttip;
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaData data) {
		for ( ModuleStack<IModuleWaila, IModuleSaver> stack : getWailaModules() ) {
			if (stack.getModule().getWailaHead(itemStack, currenttip, data) != null) {
				currenttip = stack.getModule().getWailaHead(itemStack, currenttip, data);
			}
		}
		return currenttip;
	}

	@Override
	public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaData data) {
		for ( ModuleStack<IModuleWaila, IModuleSaver> stack : getWailaModules() ) {
			if (stack.getModule().getWailaTail(itemStack, currenttip, data) != null) {
				currenttip = stack.getModule().getWailaTail(itemStack, currenttip, data);
			}
		}
		return currenttip;
	}

	@Override
	public IWailaProvider getWailaProvider(IModularTileEntity tile, IWailaData data) {
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
