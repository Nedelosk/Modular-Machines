package de.nedelosk.modularmachines.api.modular;

import java.util.List;

import javax.annotation.Nonnull;

import de.nedelosk.modularmachines.api.energy.IEnergyInterface;
import de.nedelosk.modularmachines.api.energy.IHeatSource;
import de.nedelosk.modularmachines.api.integration.IWailaState;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.handlers.IModulePage;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storage.IModuleStorage;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IModular extends IModuleStorage, ICapabilityProvider {

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

	//NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, BlockPos pos);

	void assembleModular() throws AssemblerException;

	IModular copy(IModularHandler handler);

	IHeatSource getHeatSource();
}
