package nedelosk.modularmachines.api.modular;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Vector;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import nedelosk.modularmachines.api.modular.basic.managers.IModularGuiManager;
import nedelosk.modularmachines.api.modular.basic.managers.IModularUtilsManager;
import nedelosk.modularmachines.api.modular.basic.managers.ModularGuiManager;
import nedelosk.modularmachines.api.modular.basic.managers.ModularUtilsManager;
import nedelosk.modularmachines.api.modular.integration.IWailaData;
import nedelosk.modularmachines.api.modular.integration.IWailaProvider;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.modules.casing.IModuleCasing;
import nedelosk.modularmachines.api.producers.IProducer;
import nedelosk.modularmachines.api.producers.energy.IProducerBattery;
import nedelosk.modularmachines.api.producers.fluids.IProducerWithFluid;
import nedelosk.modularmachines.api.producers.integration.IProducerWaila;
import nedelosk.modularmachines.api.producers.managers.fluids.IProducerTankManager;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public abstract class Modular implements IModular, IWailaProvider {

	protected HashMap<String, Vector<ModuleStack>> modules = Maps.newHashMap();
	protected IModularTileEntity machine;
	protected IModularUtilsManager utils;
	protected IModularGuiManager gui;
	protected String page;

	public Modular() {
		utils = new ModularUtilsManager();
		utils.setModular(this);
		gui = new ModularGuiManager();
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
		for ( Vector<ModuleStack> moduleV : modules.values() ) {
			for ( ModuleStack stack : moduleV ) {
				if (stack != null && stack.getModule() != null && stack.getProducer() != null) {
					if (isServer) {
						stack.getProducer().updateServer(this, stack);
					} else {
						stack.getProducer().updateClient(this, stack);
					}
				}
			}
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) throws Exception {
		utils = new ModularUtilsManager();
		utils.readFromNBT(nbt);
		utils.setModular(this);
		gui = new ModularGuiManager();
		gui.readFromNBT(nbt);
		gui.setModular(this);
		if (modules == null) {
			modules = Maps.newHashMap();
		}
		NBTTagList listTag = nbt.getTagList("Modules", 10);
		for ( int i = 0; i < listTag.tagCount(); i++ ) {
			NBTTagCompound modulesTag = listTag.getCompoundTagAt(i);
			NBTTagList modulesTagList = modulesTag.getTagList("modules", 10);
			String key = modulesTag.getString("key");
			Vector<ModuleStack> moduleV = new Vector<ModuleStack>();
			for ( int r = 0; r < modulesTagList.tagCount(); r++ ) {
				ModuleStack stack = new ModuleStack(modulesTagList.getCompoundTagAt(r), this);
				moduleV.add(stack);
			}
			modules.put(key, moduleV);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) throws Exception {
		if (utils == null) {
			utils = new ModularUtilsManager();
			utils.setModular(this);
		}
		utils.writeToNBT(nbt);
		if (gui == null) {
			gui = new ModularGuiManager();
		}
		gui.writeToNBT(nbt);
		NBTTagList listTag = new NBTTagList();
		for ( Entry<String, Vector<ModuleStack>> moduleV : modules.entrySet() ) {
			NBTTagCompound modulesTag = new NBTTagCompound();
			NBTTagList modulesTagList = new NBTTagList();
			for ( ModuleStack stack : moduleV.getValue() ) {
				if (stack != null) {
					NBTTagCompound nbtTag = new NBTTagCompound();
					stack.writeToNBT(nbtTag, this);
					modulesTagList.appendTag(nbtTag);
				}
			}
			modulesTag.setTag("modules", modulesTagList);
			modulesTag.setString("key", moduleV.getKey());
			listTag.appendTag(modulesTag);
		}
		nbt.setTag("Modules", listTag);
	}

	@Override
	public int getTier() {
		if (getCasing() != null) {
			int tiers = 0;
			int size = 0;
			for ( Vector<ModuleStack> modules : modules.values() ) {
				if (modules != null) {
					for ( ModuleStack module : modules ) {
						if (module != null) {
							tiers += module.getType().getTier();
							size++;
						}
					}
				}
			}
			return tiers / size;
		}
		return 1;
	}

	@Override
	public void initModular() {
	}

	@Override
	public HashMap<String, Vector<ModuleStack>> getModules() {
		return modules;
	}

	@Override
	public void setModules(HashMap<String, Vector<ModuleStack>> modules) {
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
	public ModuleStack<IModule, IProducerBattery> getBattery() {
		return getModule("Battery", 0);
	}

	@Override
	public ModuleStack<IModuleCasing, IProducer> getCasing() {
		return getModule("Casing", 0);
	}

	@Override
	public ModuleStack<IModule, IProducerTankManager> getTankManeger() {
		return getModule("TankManager", 0);
	}

	@Override
	public boolean addModule(ModuleStack stack) {
		if (stack == null || stack.getModule() == null) {
			return false;
		}
		if (modules.get(stack.getModule().getModuleName()) == null) {
			modules.put(stack.getModule().getModuleName(), new Vector<ModuleStack>());
		}
		modules.get(stack.getModule().getModuleName()).add(stack);
		return true;
	}

	@Override
	public ModuleStack getModule(String moduleName, int id) {
		if (getModule(moduleName) == null) {
			return null;
		}
		return getModule(moduleName).get(id);
	}

	@Override
	public Vector<ModuleStack> getModule(String moduleName) {
		if (modules.get(moduleName) == null) {
			return null;
		}
		return modules.get(moduleName);
	}

	@Override
	public IModularUtilsManager getManager() {
		return utils;
	}

	@Override
	public IModularGuiManager getGuiManager() {
		return gui;
	}

	@Override
	public List<ModuleStack> getFluidProducers() {
		List<ModuleStack> stacks = new ArrayList();
		for ( Vector<ModuleStack> stackV : modules.values() ) {
			for ( ModuleStack stack : stackV ) {
				if (stack != null && stack.getModule() != null && stack.getProducer() != null && stack.getProducer() instanceof IProducerWithFluid) {
					if (((IProducerWithFluid) stack.getProducer()).useFluids(stack)) {
						stacks.add(stack);
					}
				}
			}
		}
		return stacks;
	}

	public ArrayList<ModuleStack> getWailaModules() {
		ArrayList<ModuleStack> wailaModules = Lists.newArrayList();
		for ( Entry<String, Vector<ModuleStack>> entrys : modules.entrySet() ) {
			for ( ModuleStack stack : entrys.getValue() ) {
				if (stack.getProducer() != null && stack.getProducer() instanceof IWailaProvider) {
					wailaModules.add(stack);
				}
			}
		}
		return wailaModules;
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaData data) {
		for ( ModuleStack<IModule, IProducerWaila> stack : getWailaModules() ) {
			currenttip = stack.getProducer().getWailaBody(itemStack, currenttip, data);
		}
		return currenttip;
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaData data) {
		for ( ModuleStack<IModule, IProducerWaila> stack : getWailaModules() ) {
			currenttip = stack.getProducer().getWailaHead(itemStack, currenttip, data);
		}
		return currenttip;
	}

	@Override
	public IWailaProvider getWailaProvider(IModularTileEntity tile, IWailaData data) {
		return this;
	}

	@Override
	public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaData data) {
		for ( ModuleStack<IModule, IProducerWaila> stack : getWailaModules() ) {
			currenttip = stack.getProducer().getWailaTail(itemStack, currenttip, data);
		}
		return currenttip;
	}
}
