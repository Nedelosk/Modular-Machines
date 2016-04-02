package de.nedelosk.forestmods.common.producers;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestmods.api.integration.IWailaProvider;
import de.nedelosk.forestmods.api.integration.IWailaState;
import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modular.IModularTileEntity;
import de.nedelosk.forestmods.api.modular.managers.IModularModuleManager;
import de.nedelosk.forestmods.api.modular.renderer.IRenderState;
import de.nedelosk.forestmods.api.modular.renderer.ISimpleRenderer;
import de.nedelosk.forestmods.api.modules.special.IModuleController;
import de.nedelosk.forestmods.api.producers.IModule;
import de.nedelosk.forestmods.api.producers.IProducerWithRenderer;
import de.nedelosk.forestmods.api.producers.handlers.IModuleContentHandler;
import de.nedelosk.forestmods.api.producers.handlers.IModulePage;
import de.nedelosk.forestmods.api.producers.handlers.gui.IModuleGui;
import de.nedelosk.forestmods.api.producers.handlers.gui.IModuleGuiBuilder;
import de.nedelosk.forestmods.api.producers.handlers.inventory.IModuleInventory;
import de.nedelosk.forestmods.api.producers.handlers.inventory.IModuleInventoryBuilder;
import de.nedelosk.forestmods.api.producers.handlers.tank.IModuleTank;
import de.nedelosk.forestmods.api.producers.handlers.tank.IModuleTankBuilder;
import de.nedelosk.forestmods.api.utils.ModularException;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.common.producers.handlers.guis.ProducerGuiBuilder;
import de.nedelosk.forestmods.common.producers.handlers.inventorys.ProducerInventoryBuilder;
import de.nedelosk.forestmods.common.producers.handlers.tanks.ProducerTankBuilder;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class Producer implements IModule, IProducerWithRenderer, IWailaProvider {

	protected IModuleInventory inventory;
	protected IModuleTank tank;
	@SideOnly(Side.CLIENT)
	protected IModuleGui gui;
	protected IModulePage[] pages;
	protected IModulePage currentPage;
	protected ModuleStack moduleStack;
	protected IModular modular;

	public Producer() {
	}

	@Override
	public String getFilePath(ModuleStack stack) {
		return stack.getUID().getCategoryUID() + "/" + stack.getUID().getModuleUID();
	}

	@Override
	public ItemStack getDropItem(ModuleStack stack, IModular modular) {
		return modular.getManager(IModularModuleManager.class).getItemStack(stack.getUID());
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addTooltip(ModuleStack stack, List<String> tooltip) {
	}

	@Override
	public void addRequiredModules(List<Class<? extends IModule>> requiredModules) {
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaState data) {
		return currenttip;
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaState data) {
		return currenttip;
	}

	@Override
	public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaState data) {
		return currenttip;
	}

	@Override
	public void canAssembleModular(IModular modular, ModuleStack stack, ModuleStack<IModuleController> controller, List<ModuleStack> modules)
			throws ModularException {
	}

	@Override
	public void onModularAssembled(IModular modular, ModuleStack stack, ModuleStack<IModuleController> controller, List<ModuleStack> modules)
			throws ModularException {
	}

	@Override
	public void onAddInModular(IModular modular, ModuleStack stack) throws ModularException {
		this.modular = modular;
		this.moduleStack = stack;
		pages = createPages();
	}

	@Override
	public String getUnlocalizedName(ModuleStack stack) {
		return null;
	}

	@Override
	public void getHandlers(List<IModuleContentHandler> handlers) {
		handlers.add(inventory);
		handlers.add(tank);
	}

	@Override
	public void createHandlers() {
		IModuleInventoryBuilder invBuilder = new ProducerInventoryBuilder();
		IModuleTankBuilder tankBuilder = new ProducerTankBuilder();
		invBuilder.setModular(modular);
		invBuilder.setModuleStack(moduleStack);
		tankBuilder.setModular(modular);
		tankBuilder.setModuleStack(moduleStack);
		for ( IModulePage page : getPages() ) {
			page.createHandlers(invBuilder, tankBuilder);
		}
		inventory = invBuilder.build();
		tank = tankBuilder.build();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModuleGui createGui() {
		IModuleGuiBuilder guiBuilder = new ProducerGuiBuilder();
		guiBuilder.setModular(modular);
		guiBuilder.setModuleStack(moduleStack);
		currentPage.createGui(guiBuilder);
		return gui = guiBuilder.build();
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
		return null;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt, IModular modular) {
		NBTTagCompound nbtInventory = new NBTTagCompound();
		inventory.writeToNBT(nbtInventory);
		nbt.setTag("Inventory", nbtInventory);
		NBTTagCompound nbtTank = new NBTTagCompound();
		tank.writeToNBT(nbtTank);
		nbt.setTag("Tank", nbtTank);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular) {
		NBTTagCompound nbtInventory = nbt.getCompoundTag("Inventory");
		inventory.readFromNBT(nbt);
		NBTTagCompound nbtTank = nbt.getCompoundTag("Tank");
		tank.readFromNBT(nbtTank);
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

	@SideOnly(Side.CLIENT)
	@Override
	public IModuleGui getGui() {
		return gui;
	}

	@Override
	public void setCurrentPage(int newPage) {
		currentPage = pages[newPage];
	}

	@Override
	public IModulePage getCurrentPage() {
		return currentPage;
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
