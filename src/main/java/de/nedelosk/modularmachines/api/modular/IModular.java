package de.nedelosk.modularmachines.api.modular;

import java.util.List;

import javax.annotation.Nonnull;

import de.nedelosk.modularmachines.api.energy.IEnergyInterface;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.handlers.IModulePage;
import de.nedelosk.modularmachines.api.modules.integration.IWailaState;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storage.IModuleStorage;
import de.nedelosk.modularmachines.api.modules.storage.IPositionedModuleStorage;
import de.nedelosk.modularmachines.api.modules.storaged.EnumPosition;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IModular extends IModuleStorage, ICapabilityProvider {

	IModular copy(IModularHandler handler);

	void update(boolean isServer);

	boolean updateOnInterval(int tickInterval);

	IModulePage getCurrentPage();

	IModuleState getCurrentModuleState();

	void setCurrentModuleState(IModuleState state);

	void setCurrentPage(String pageID);

	IModularHandler getHandler();

	void setHandler(IModularHandler tile);

	@Nonnull
	IModularAssembler disassemble();

	void setModuleStorage(EnumPosition position, IPositionedModuleStorage storage);

	IPositionedModuleStorage getModuleStorage(EnumPosition position);

	/**
	 * @return All modules as ModuleStack
	 */
	List<IModuleState> getModuleStates();

	IFluidHandler getFluidHandler();

	IEnergyInterface getEnergyInterface();

	@SideOnly(Side.CLIENT)
	GuiContainer createGui(IModularHandler tile, InventoryPlayer inventory);

	Container createContainer(IModularHandler tile, InventoryPlayer inventory);

	/**
	 * @return The next index for a module state.
	 */
	int getNextIndex();

	/* Waila */
	List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaState data);

	List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaState data);

	List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaState data);

	void assembleModular() throws AssemblerException;
}
