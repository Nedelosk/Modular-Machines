package de.nedelosk.forestmods.common.modules;

import java.util.List;
import java.util.Map;

import akka.japi.Pair;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestmods.common.modules.handlers.inventorys.ModuleInventoryBuilder;
import de.nedelosk.forestmods.common.modules.handlers.tanks.ModuleTankBuilder;
import de.nedelosk.forestmods.library.modular.IModular;
import de.nedelosk.forestmods.library.modular.IModularTileEntity;
import de.nedelosk.forestmods.library.modular.assembler.IAssemblerGroup;
import de.nedelosk.forestmods.library.modular.assembler.IAssemblerSlot;
import de.nedelosk.forestmods.library.modular.renderer.IRenderState;
import de.nedelosk.forestmods.library.modular.renderer.ISimpleRenderer;
import de.nedelosk.forestmods.library.modules.IModule;
import de.nedelosk.forestmods.library.modules.IModuleContainer;
import de.nedelosk.forestmods.library.modules.ModuleManager;
import de.nedelosk.forestmods.library.modules.casing.IModuleCasing;
import de.nedelosk.forestmods.library.modules.handlers.IModuleContentHandler;
import de.nedelosk.forestmods.library.modules.handlers.IModulePage;
import de.nedelosk.forestmods.library.modules.handlers.inventory.IModuleInventory;
import de.nedelosk.forestmods.library.modules.handlers.inventory.IModuleInventoryBuilder;
import de.nedelosk.forestmods.library.modules.handlers.tank.IModuleTank;
import de.nedelosk.forestmods.library.modules.handlers.tank.IModuleTankBuilder;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class Module implements IModule {

	protected IModuleInventory inventory;
	protected IModuleTank tank;
	protected IModulePage[] pages;
	protected IModuleContainer container;
	protected IModular modular;
	protected int index;

	public Module(IModular modular, IModuleContainer container) {
		onCreateModel(modular, container);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public String getFilePath() {
		return container.getUID().getCategoryUID() + "/" + container.getUID().getModuleUID();
	}

	@Override
	public ItemStack getDropItem() {
		return container.getItemStack();
	}

	protected void onCreateModel(IModular modular, IModuleContainer moduleContainer) {
		this.modular = modular;
		this.container = moduleContainer;
		pages = createPages();
		createContentHandlers();
	}

	@Override
	public void getContentHandlers(List<IModuleContentHandler> handlers) {
		if (getInventory() != null) {
			handlers.add(getInventory());
		}
		if (getTank() != null) {
			handlers.add(getTank());
		}
	}

	@Override
	public void createContentHandlers() {
		IModuleInventoryBuilder invBuilder = new ModuleInventoryBuilder();
		IModuleTankBuilder tankBuilder = new ModuleTankBuilder();
		invBuilder.setModular(modular);
		invBuilder.setModule(this);
		tankBuilder.setModular(modular);
		tankBuilder.setModule(this);
		if (getPages() != null) {
			for(IModulePage page : getPages()) {
				page.createHandlers(invBuilder, tankBuilder);
			}
		}
		if (!isHandlerDisabled(ModuleManager.inventoryType)) {
			inventory = invBuilder.build();
		}
		if (!isHandlerDisabled(ModuleManager.tankType)) {
			tank = tankBuilder.build();
		}
	}

	@Override
	public boolean isHandlerDisabled(String handlerType) {
		if (handlerType == null || handlerType.isEmpty() || getDisabledHandlers() == null || getDisabledHandlers().length == 0) {
			return false;
		}
		for(String disabledHandlerType : getDisabledHandlers()) {
			if (disabledHandlerType != null && disabledHandlerType.equals(handlerType)) {
				return true;
			}
		}
		return false;
	}

	protected String[] getDisabledHandlers() {
		return new String[] { ModuleManager.tankType };
	}

	@Override
	public void updateServer() {
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void updateClient() {
	}

	@SideOnly(Side.CLIENT)
	@Override
	public ISimpleRenderer getRenderer(IRenderState state) {
		return null;
	}

	@Override
	public boolean transferInput(IModularTileEntity tile, EntityPlayer player, int slotID, Container container, ItemStack stackItem) {
		return false;
	}

	@Override
	public String getName(IModuleContainer container) {
		return container.getUID().getModuleUID();
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt, IModular modular) {
		if (inventory != null) {
			NBTTagCompound nbtInventory = new NBTTagCompound();
			inventory.writeToNBT(nbtInventory);
			nbt.setTag("Inventory", nbtInventory);
		}
		if (tank != null) {
			NBTTagCompound nbtTank = new NBTTagCompound();
			tank.writeToNBT(nbtTank);
			nbt.setTag("Tank", nbtTank);
		}
		nbt.setInteger("Index", index);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular) {
		if (nbt.hasKey("Inventory") && inventory != null) {
			NBTTagCompound nbtInventory = nbt.getCompoundTag("Inventory");
			inventory.readFromNBT(nbtInventory);
		}
		if (nbt.hasKey("Tank") && tank != null) {
			NBTTagCompound nbtTank = nbt.getCompoundTag("Tank");
			tank.readFromNBT(nbtTank);
		}
		index = nbt.getInteger("Index");
	}

	@Override
	public IModulePage[] getPages() {
		return pages;
	}

	protected abstract IModulePage[] createPages();

	@Override
	public IModuleTank getTank() {
		return tank;
	}

	@Override
	public IModuleInventory getInventory() {
		return inventory;
	}

	@Override
	public IModular getModular() {
		return modular;
	}

	@Override
	public IModuleContainer getModuleContainer() {
		return container;
	}

	@Override
	public String getUnlocalizedName() {
		return container.getUID().getModuleUID() + ".name";
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addTooltip(List<String> tooltip) {
	}

	@Override
	public void loadDataFromItem(ItemStack stack) {
	}

	@Override
	public boolean canAssembleSlot(IAssemblerSlot slot) {
		return true;
	}

	@Override
	public boolean onAddToModular(IAssemblerGroup group, IModuleCasing casing, Map<IAssemblerGroup, List<Pair<IAssemblerSlot, IModule>>> modules, boolean beforeAdd) {
		return true;
	}
	
	@Override
	public void setIndex(int index) {
		this.index = index;
	}
	
	@Override
	public int getIndex() {
		return index;
	}
}
