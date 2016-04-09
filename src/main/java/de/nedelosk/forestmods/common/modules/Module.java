package de.nedelosk.forestmods.common.modules;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modular.IModularTileEntity;
import de.nedelosk.forestmods.api.modular.renderer.IRenderState;
import de.nedelosk.forestmods.api.modular.renderer.ISimpleRenderer;
import de.nedelosk.forestmods.api.modules.IModule;
import de.nedelosk.forestmods.api.modules.handlers.IModuleContentHandler;
import de.nedelosk.forestmods.api.modules.handlers.IModulePage;
import de.nedelosk.forestmods.api.modules.handlers.inventory.IModuleInventory;
import de.nedelosk.forestmods.api.modules.handlers.inventory.IModuleInventoryBuilder;
import de.nedelosk.forestmods.api.modules.handlers.tank.IModuleTank;
import de.nedelosk.forestmods.api.modules.handlers.tank.IModuleTankBuilder;
import de.nedelosk.forestmods.api.modules.special.IModuleController;
import de.nedelosk.forestmods.api.utils.ModularException;
import de.nedelosk.forestmods.api.utils.ModuleManager;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.common.modules.handlers.inventorys.ModuleInventoryBuilder;
import de.nedelosk.forestmods.common.modules.handlers.tanks.ModuleTankBuilder;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class Module implements IModule {

	protected IModuleInventory inventory;
	protected IModuleTank tank;
	protected IModulePage[] pages;
	protected ModuleStack moduleStack;
	protected IModular modular;
	protected final String name;

	public Module(String name) {
		this.name = name;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public String getFilePath(ModuleStack stack) {
		return stack.getUID().getCategoryUID() + "/" + stack.getUID().getModuleUID();
	}

	@Override
	public ItemStack getDropItem() {
		return modular.getItemStack(moduleStack.getUID());
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addTooltip(ModuleStack stack, List<String> tooltip) {
	}

	@Override
	public void addRequiredModules(List<Class<? extends IModule>> requiredModules) {
	}

	@Override
	public void canAssembleModular(ModuleStack<IModuleController> controller) throws ModularException {
	}

	@Override
	public void onModularAssembled(ModuleStack<IModuleController> controller) throws ModularException {
	}

	@Override
	public void onAddInModular(IModular modular, ModuleStack stack) {
		this.modular = modular;
		this.moduleStack = stack;
		pages = createPages();
		createContentHandlers();
	}

	@Override
	public String getUnlocalizedName(ModuleStack stack) {
		return getName() + ".name";
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
		invBuilder.setModuleStack(moduleStack);
		tankBuilder.setModular(modular);
		tankBuilder.setModuleStack(moduleStack);
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
	public ISimpleRenderer getRenderer(ModuleStack moduleStack, IRenderState state) {
		return null;
	}

	@Override
	public boolean transferInput(IModularTileEntity tile, EntityPlayer player, int slotID, Container container, ItemStack stackItem) {
		return false;
	}

	@Override
	public String getName() {
		return name;
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
	public ModuleStack getModuleStack() {
		return moduleStack;
	}
}
