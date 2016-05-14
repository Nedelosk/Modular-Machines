package de.nedelosk.forestmods.common.modular;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestmods.client.gui.GuiModular;
import de.nedelosk.forestmods.client.render.modules.ModularRenderer;
import de.nedelosk.forestmods.common.inventory.ContainerModular;
import de.nedelosk.forestmods.common.modular.handlers.EnergyHandler;
import de.nedelosk.forestmods.common.modular.handlers.FluidHandler;
import de.nedelosk.forestmods.common.network.PacketHandler;
import de.nedelosk.forestmods.common.network.packets.PacketSelectModule;
import de.nedelosk.forestmods.common.network.packets.PacketSelectModulePage;
import de.nedelosk.forestmods.library.integration.IWailaProvider;
import de.nedelosk.forestmods.library.integration.IWailaState;
import de.nedelosk.forestmods.library.modular.IModular;
import de.nedelosk.forestmods.library.modular.IModularTileEntity;
import de.nedelosk.forestmods.library.modular.renderer.IRenderState;
import de.nedelosk.forestmods.library.modular.renderer.ISimpleRenderer;
import de.nedelosk.forestmods.library.modules.IModule;
import de.nedelosk.forestmods.library.modules.IModuleContainer;
import de.nedelosk.forestmods.library.modules.ModuleManager;
import de.nedelosk.forestmods.library.modules.ModuleUID;
import de.nedelosk.forestmods.library.modules.handlers.IModulePage;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.IFluidHandler;

public class Modular implements IModular, IWailaProvider {

	protected IModularTileEntity tileEntity;
	protected List<IModule> modules = new ArrayList();
	protected int tier;
	protected int index;
	protected IModule currentModule;
	protected IModulePage currentPage;
	protected EnergyHandler energyHandler;
	protected FluidHandler fluidHandler;

	public Modular() {
	}

	public Modular(NBTTagCompound nbt, IModularTileEntity machine) {
		setTile(machine);
		readFromNBT(nbt);
	}

	@Override
	public void onAssembleModular() {
		fluidHandler = new FluidHandler(this);
		modules = Collections.unmodifiableList(modules);
		currentModule = getFirstGui();
		setCurrentPage(0);
	}

	private IModule getFirstGui() {
		for(IModule module : modules) {
			if (!module.isHandlerDisabled(ModuleManager.guiType) && module.getPages() != null) {
				return module;
			}
		}
		return null;
	}

	@Override
	public void update(boolean isServer) {
		for(IModule module : modules) {
			if (module != null) {
				if (isServer) {
					module.updateServer();
				} else {
					module.updateClient();
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
			IModuleContainer container = ModuleManager.moduleRegistry.getContainerFromItem(moduleItem);
			IModule module = ModuleManager.moduleRegistry.createModule(this, container);
			module.readFromNBT(moduleTag, this);
			modules.add(module);
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
		if (nbt.getBoolean("FH")) {
			fluidHandler = new FluidHandler(this);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		NBTTagList nbtList = new NBTTagList();
		for(IModule module : modules) {
			NBTTagCompound nbtTag = new NBTTagCompound();
			module.writeToNBT(nbtTag, this);
			NBTTagCompound nbtTagContainer = new NBTTagCompound();
			module.getModuleContainer().getItemStack().writeToNBT(nbtTagContainer);
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
			for(IModule module : modules) {
				tiers += module.getModuleContainer().getMaterial().getTier();
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
	public void setTile(IModularTileEntity tileEntity) {
		this.tileEntity = tileEntity;
	}

	@Override
	public IModularTileEntity getTile() {
		return tileEntity;
	}

	private ArrayList<IModule> getWailaModules() {
		ArrayList<IModule> wailaModules = Lists.newArrayList();
		for(IModule module : modules) {
			if (module instanceof IWailaProvider) {
				wailaModules.add(module);
			}
		}
		return wailaModules;
	}

	@Override
	public List getWailaBody(ItemStack itemStack, List currenttip, IWailaState data) {
		for(IModule module : getWailaModules()) {
			if (module instanceof IWailaProvider) {
				if (((IWailaProvider) module).getWailaBody(itemStack, currenttip, data) != null) {
					currenttip = ((IWailaProvider) module).getWailaBody(itemStack, currenttip, data);
				}
			}
		}
		return currenttip;
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaState data) {
		for(IModule module : getWailaModules()) {
			if (module instanceof IWailaProvider) {
				if (((IWailaProvider) module).getWailaHead(itemStack, currenttip, data) != null) {
					currenttip = ((IWailaProvider) module).getWailaHead(itemStack, currenttip, data);
				}
			}
		}
		return currenttip;
	}

	@Override
	public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaState data) {
		for(IModule module : getWailaModules()) {
			if (module instanceof IWailaProvider) {
				if (((IWailaProvider) module).getWailaTail(itemStack, currenttip, data) != null) {
					currenttip = ((IWailaProvider) module).getWailaTail(itemStack, currenttip, data);
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
	public IModulePage getCurrentPage() {
		return currentPage;
	}

	@Override
	public IModule getCurrentModule() {
		return currentModule;
	}

	@Override
	public void setCurrentModule(IModule module) {
		this.currentModule = module;
		this.currentPage = currentModule.getPages()[0];
		if (getTile().getWorld().isRemote) {
			PacketHandler.INSTANCE.sendToServer(new PacketSelectModule((TileEntity) getTile(), module));
			PacketHandler.INSTANCE.sendToServer(new PacketSelectModulePage((TileEntity) getTile(), 0));
		}
	}

	@Override
	public void setCurrentPage(int pageID) {
		if(currentModule != null){
			this.currentPage = currentModule.getPages()[pageID];
			if(getTile() != null){
				if (getTile().getWorld().isRemote) {
					PacketHandler.INSTANCE.sendToServer(new PacketSelectModulePage((TileEntity) getTile(), pageID));
				}
			}
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
	public boolean addModule(ItemStack itemStack, IModule module) {
		if (module == null) {
			return false;
		}
		if (modules.add(module)) {
			module.setIndex(index++);
			module.loadDataFromItem(itemStack);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<IModule> getModules() {
		return modules;
	}

	@Override
	public <M extends IModule> M getModule(int index) {
		for(IModule module : modules) {
			if (module.getIndex() == index) {
				return (M) module;
			}
		}
		return null;
	}

	@Override
	public <M extends IModule> List<M> getModules(Class<? extends M> moduleClass) {
		if (moduleClass == null) {
			return null;
		}
		List<M> modules = Lists.newArrayList();
		for(IModule module : this.modules) {
			if (moduleClass.isAssignableFrom(module.getClass())) {
				modules.add((M) module);
			}
		}
		return modules;
	}

	@Override
	public EnergyHandler getEnergyHandler() {
		return energyHandler;
	}

	@Override
	public FluidHandler getFluidHandler() {
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