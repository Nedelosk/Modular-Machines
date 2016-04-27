package de.nedelosk.forestmods.library.modular;

import java.util.List;

import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestmods.library.integration.IWailaProvider;
import de.nedelosk.forestmods.library.modular.renderer.IRenderState;
import de.nedelosk.forestmods.library.modular.renderer.ISimpleRenderer;
import de.nedelosk.forestmods.library.modules.IModule;
import de.nedelosk.forestmods.library.modules.ModuleUID;
import de.nedelosk.forestmods.library.modules.handlers.IModulePage;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.IFluidHandler;

public interface IModular {

	/**
	 * @return The tier from of the modular
	 */
	int getTier();

	void update(boolean isServer);

	void initModular();

	IModulePage getCurrentPage();

	IModule getCurrentModule();

	void setCurrentModule(IModule stack);

	void setCurrentPage(int pageID);

	IModularTileEntity getTile();

	void setTile(IModularTileEntity tile);

	boolean addModule(ItemStack itemStack, IModule module);

	<M extends IModule> List<M> getModules(Class<? extends M> moduleClass);

	<M extends IModule> M getModule(ModuleUID moduleUID);

	/**
	 * @return All modules as ModuleStack
	 */
	List<IModule> getModules();

	IFluidHandler getFluidHandler();

	<E extends IEnergyProvider & IEnergyReceiver> E getEnergyHandler();

	void setFluidHandler(IFluidHandler fluidHandler);

	<E extends IEnergyProvider & IEnergyReceiver> void setEnergyHandler(E energyHandler);

	/* BUILD */
	void assemble() throws ModularException;

	boolean isAssembled();

	/* NBT */
	void readFromNBT(NBTTagCompound nbt);

	void writeToNBT(NBTTagCompound nbt);

	/* Renderer */
	@SideOnly(Side.CLIENT)
	ISimpleRenderer getRenderer(IRenderState state);

	/* Waila */
	IWailaProvider getWailaProvider(IModularTileEntity tile);

	ModularException getLastException();

	void setLastException(ModularException exception);

	@SideOnly(Side.CLIENT)
	GuiContainer getGUIContainer(IModularTileEntity tile, InventoryPlayer inventory);

	Container getContainer(IModularTileEntity tile, InventoryPlayer inventory);
}
