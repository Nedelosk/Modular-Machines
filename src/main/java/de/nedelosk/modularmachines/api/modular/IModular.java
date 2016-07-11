package de.nedelosk.modularmachines.api.modular;

import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import de.nedelosk.modularmachines.api.modules.IModuleState;
import de.nedelosk.modularmachines.api.modules.handlers.IModulePage;
import de.nedelosk.modularmachines.api.modules.integration.IWailaState;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IModular extends ICapabilityProvider, IModuleStorage {

	IModular copy(IModularHandler handler);

	@Override
	boolean equals(Object obj);

	void update(boolean isServer);

	boolean updateOnInterval(int tickInterval);

	IModulePage getCurrentPage();

	IModuleState getCurrentModuleState();

	void setCurrentModuleState(IModuleState state);

	void setCurrentPage(String pageID);

	IModularHandler getHandler();

	void setHandler(IModularHandler tile);

	void onAssembleModular();

	/* NBT */
	void readFromNBT(NBTTagCompound nbt);

	NBTTagCompound writeToNBT(NBTTagCompound nbt);

	@Nonnull
	Map<IModularLogicType, List<IModularLogic>> getLogics();

	/**
	 * @return All modules as ModuleStack
	 */
	List<IModuleState> getModuleStates();

	IFluidHandler getFluidHandler();

	<E extends IEnergyProvider & IEnergyReceiver> E getEnergyHandler();

	<E extends IEnergyProvider & IEnergyReceiver> void setEnergyHandler(E energyHandler);

	@SideOnly(Side.CLIENT)
	GuiContainer getGUIContainer(IModularHandler tile, InventoryPlayer inventory);

	Container getContainer(IModularHandler tile, InventoryPlayer inventory);

	/* Waila */
	List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaState data);

	List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaState data);

	List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaState data);
}
