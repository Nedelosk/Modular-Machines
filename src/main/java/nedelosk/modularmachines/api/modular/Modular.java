package nedelosk.modularmachines.api.modular;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import nedelosk.modularmachines.api.modular.basic.container.gui.IMultiGuiContainer;
import nedelosk.modularmachines.api.modular.basic.container.module.IModuleContainer;
import nedelosk.modularmachines.api.modular.basic.container.module.IMultiModuleContainer;
import nedelosk.modularmachines.api.modular.basic.container.module.ISingleModuleContainer;
import nedelosk.modularmachines.api.modular.basic.container.module.ModuleContainer;
import nedelosk.modularmachines.api.modular.basic.container.module.MultiModuleContainer;
import nedelosk.modularmachines.api.modular.basic.managers.IModularUtilsManager;
import nedelosk.modularmachines.api.modular.basic.managers.ModularUtilsManager;
import nedelosk.modularmachines.api.modular.integration.IWailaData;
import nedelosk.modularmachines.api.modular.integration.IWailaProvider;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.modular.type.Materials;
import nedelosk.modularmachines.api.modular.type.Materials.Material;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.modules.fluids.IModuleWithFluid;
import nedelosk.modularmachines.api.modules.integration.IModuleWaila;
import nedelosk.modularmachines.api.utils.ModuleRegistry;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public abstract class Modular implements IModular, IWailaProvider {

	protected HashMap<String, IModuleContainer> modules = Maps.newHashMap();
	protected IModularTileEntity machine;
	protected IModularUtilsManager utils;
	protected String page;
	protected Material material;

	public Modular() {
		utils = new ModularUtilsManager();
		utils.setModular(this);
	}

	public Modular(NBTTagCompound nbt) {
		try {
			readFromNBT(nbt);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(boolean isServer) {
		List<ModuleStack> stacks = getStacks();
		for ( ModuleStack stack : stacks ) {
			if (stack != null && stack.getModule() != null) {
				if (isServer) {
					stack.getModule().updateServer(this, stack);
				} else {
					stack.getModule().updateClient(this, stack);
				}
			}
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) throws Exception {
		if (utils == null) {
			utils = new ModularUtilsManager();
		}
		utils.readFromNBT(nbt);
		utils.setModular(this);
		if (modules == null) {
			modules = Maps.newHashMap();
		}
		NBTTagList listTag = nbt.getTagList("Modules", 10);
		for ( int i = 0; i < listTag.tagCount(); i++ ) {
			NBTTagCompound modulesTag = listTag.getCompoundTagAt(i);
			IModuleContainer container;
			IModule producer;
			if (modulesTag.getBoolean("IsMulti")) {
				container = new MultiModuleContainer();
			} else {
				container = new ModuleContainer();
			}
			container.readFromNBT(modulesTag.getCompoundTag("Container"), this);
			if (modulesTag.getBoolean("IsMulti")) {
				if (((IMultiModuleContainer) container).getStacks().isEmpty() || ((IMultiModuleContainer) container).getStack(0) == null) {
					continue;
				}
				producer = ((IMultiModuleContainer) container).getStack(0).getModule();
			} else {
				if (((ISingleModuleContainer) container).getStack() == null) {
					continue;
				}
				producer = ((ISingleModuleContainer) container).getStack().getModule();
			}
			if (producer == null) {
				continue;
			}
			modules.put(producer.getCategoryUID(), container);
		}
		material = Materials.getMaterial(nbt.getString("Material"));
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) throws Exception {
		if (utils == null) {
			utils = new ModularUtilsManager();
			utils.setModular(this);
		}
		utils.writeToNBT(nbt);
		NBTTagList listTag = new NBTTagList();
		for ( Entry<String, IModuleContainer> module : modules.entrySet() ) {
			NBTTagCompound modulesTag = new NBTTagCompound();
			IModuleContainer container = module.getValue();
			container.writeToNBT(modulesTag, this);
			modulesTag.setBoolean("IsMulti", container instanceof IMultiModuleContainer);
			modulesTag.setString("key", module.getKey());
			listTag.appendTag(modulesTag);
		}
		nbt.setTag("Modules", listTag);
		nbt.setString("Material", material.getName());
	}

	@Override
	public Material getMaterial() {
		return material;
	}

	@Override
	public void initModular() {
	}

	@Override
	public HashMap<String, IModuleContainer> getModuleContainers() {
		return modules;
	}

	@Override
	public void setModules(HashMap<String, IModuleContainer> modules) {
		this.modules = modules;
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
	public boolean addModule(ModuleStack stack) {
		if (stack == null) {
			return false;
		}
		IModule module = stack.getModule();
		if (module == null) {
			return false;
		}
		if (modules.get(module.getCategoryUID()) == null) {
			Class<? extends IModuleContainer> containerClass = ModuleRegistry.getCategory(module.getCategoryUID()).getModuleContainerClass();
			IModuleContainer container;
			try {
				if (containerClass.isInterface()) {
					return false;
				}
				container = containerClass.newInstance();
			} catch (Exception e) {
				return false;
			}
			if (container instanceof ISingleModuleContainer) {
				((ISingleModuleContainer) container).setStack(stack);
			} else if (container instanceof IMultiModuleContainer) {
				((IMultiModuleContainer) container).addStack(stack);
			}
			modules.put(module.getCategoryUID(), container);
		} else {
			IModuleContainer container = modules.get(module.getCategoryUID());
			if (container instanceof IMultiGuiContainer) {
				((IMultiModuleContainer) container).addStack(stack);
			}
		}
		return true;
	}

	@Override
	public ISingleModuleContainer getSingleModule(String moduleName) {
		IModuleContainer conatiner = getModule(moduleName);
		if (conatiner == null) {
			return null;
		}
		if (!(conatiner instanceof ISingleModuleContainer)) {
			return null;
		}
		return (ISingleModuleContainer) conatiner;
	}

	@Override
	public IMultiModuleContainer getMultiModule(String moduleName) {
		IModuleContainer conatiner = getModule(moduleName);
		if (conatiner == null) {
			return null;
		}
		if (!(conatiner instanceof IMultiModuleContainer)) {
			return null;
		}
		return (IMultiModuleContainer) conatiner;
	}

	@Override
	public IModuleContainer getModule(String moduleName) {
		return modules.get(moduleName);
	}

	@Override
	public ModuleStack getModuleFromUID(String UID) {
		if (UID == null) {
			return null;
		}
		String[] uids = UID.split(":");
		if (uids == null || uids.length < 1 || 2 < uids.length) {
			return null;
		}
		IModuleContainer container = getModule(uids[0]);
		if (container != null) {
			if (container instanceof ISingleModuleContainer) {
				return ((ISingleModuleContainer) container).getStack();
			} else if (container instanceof IMultiModuleContainer) {
				return ((IMultiModuleContainer) container).getStack(uids[1]);
			}
		}
		return null;
	}

	@Override
	public IModularUtilsManager getManager() {
		return utils;
	}

	@Override
	public List<ModuleStack<IModuleWithFluid>> getFluidProducers() {
		List<ModuleStack<IModuleWithFluid>> stacks = new ArrayList();
		for ( ModuleStack stack : getStacks() ) {
			if (stack != null && stack.getModule() != null && stack.getModule() instanceof IModuleWithFluid) {
				if (((IModuleWithFluid) stack.getModule()).useFluids(stack)) {
					stacks.add(stack);
				}
			}
		}
		return stacks;
	}

	private ArrayList<ModuleStack> getWailaModules() {
		ArrayList<ModuleStack> wailaModules = Lists.newArrayList();
		for ( ModuleStack stack : getStacks() ) {
			if (stack.getModule() != null && stack.getModule() instanceof IWailaProvider) {
				wailaModules.add(stack);
			}
		}
		return wailaModules;
	}

	@Override
	public List getWailaBody(ItemStack itemStack, List currenttip, IWailaData data) {
		for ( ModuleStack<IModuleWaila> stack : getWailaModules() ) {
			currenttip = stack.getModule().getWailaBody(itemStack, currenttip, data);
		}
		return currenttip;
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaData data) {
		for ( ModuleStack<IModuleWaila> stack : getWailaModules() ) {
			currenttip = stack.getModule().getWailaHead(itemStack, currenttip, data);
		}
		return currenttip;
	}

	@Override
	public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaData data) {
		for ( ModuleStack<IModuleWaila> stack : getWailaModules() ) {
			currenttip = stack.getModule().getWailaTail(itemStack, currenttip, data);
		}
		return currenttip;
	}

	@Override
	public IWailaProvider getWailaProvider(IModularTileEntity tile, IWailaData data) {
		return this;
	}

	private List<ModuleStack> getStacks() {
		List<ModuleStack> stacks = new ArrayList();
		for ( IModuleContainer module : modules.values() ) {
			if (module instanceof ISingleModuleContainer) {
				stacks.add(((ISingleModuleContainer) module).getStack());
			} else if (module instanceof IMultiModuleContainer) {
				stacks.addAll(((IMultiModuleContainer) module).getStacks());
			}
		}
		return stacks;
	}
}
