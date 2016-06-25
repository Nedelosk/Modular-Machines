package de.nedelosk.modularmachines.api.modular;

import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import de.nedelosk.modularmachines.api.modular.renderer.IRenderState;
import de.nedelosk.modularmachines.api.modular.renderer.ISimpleRenderer;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.handlers.IModulePage;
import de.nedelosk.modularmachines.api.modules.integration.IWailaState;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IModular {

	/**
	 * @return The tier from of the modular
	 */
	int getTier();

	void update(boolean isServer);

	IModulePage getCurrentPage();

	IModuleState getCurrentModuleState();

	void setCurrentModuleState(IModuleState state);

	void setCurrentPage(int pageID);

	IModularTileEntity getTile();

	void setTile(IModularTileEntity tile);

	boolean addModule(ItemStack itemStack, IModule module);

	<M extends IModule> List<IModuleState<M>> getModules(Class<? extends M> moduleClass);

	<M extends IModule> IModuleState<M> getModule(int index);

	<M extends IModule> IModuleState<M> getModule(ItemStack stack);

	void onAssembleModular();

	@Nonnull
	Map<IModularLogicType, List<IModularLogic>> getLogics();

	/**
	 * @return All modules as ModuleStack
	 */
	List<IModuleState> getModuleStates();

	IFluidHandler getFluidHandler();

	<E extends IEnergyProvider & IEnergyReceiver> E getEnergyHandler();

	void setFluidHandler(IFluidHandler fluidHandler);

	<E extends IEnergyProvider & IEnergyReceiver> void setEnergyHandler(E energyHandler);

	/* NBT */
	void readFromNBT(NBTTagCompound nbt);

	void writeToNBT(NBTTagCompound nbt);

	/* Renderer */
	@SideOnly(Side.CLIENT)
	ISimpleRenderer getRenderer(IRenderState state);

	@SideOnly(Side.CLIENT)
	GuiContainer getGUIContainer(IModularTileEntity tile, InventoryPlayer inventory);

	Container getContainer(IModularTileEntity tile, InventoryPlayer inventory);

	/* Waila */
	List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaState data);

	List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaState data);

	List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaState data);
}
