package de.nedelosk.modularmachines.api.modular;

import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import de.nedelosk.modularmachines.api.energy.IEnergyBuffer;
import de.nedelosk.modularmachines.api.energy.IHeatSource;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.handlers.IModulePage;
import de.nedelosk.modularmachines.api.modules.handlers.block.IBlockModificator;
import de.nedelosk.modularmachines.api.modules.position.IStoragePosition;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storage.IStorage;
import de.nedelosk.modularmachines.api.modules.storage.module.IBasicModuleStorage;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IModular extends IBasicModuleStorage, ICapabilityProvider {

	/* GUI */
	/**
	 * Set the current selected module state of the modular.
	 */
	void setCurrentModule(IModuleState state);

	/**
	 * @return The current selected module state.
	 */
	IModuleState getCurrentModule();

	/**
	 * Set the current selected page of the modular.
	 */
	void setCurrentPage(String pageID);

	/**
	 * @return The current selected page.
	 */
	IModulePage getCurrentPage();

	@SideOnly(Side.CLIENT)
	GuiContainer createGui(IModularHandler tile, InventoryPlayer inventory);

	Container createContainer(IModularHandler tile, InventoryPlayer inventory);

	/* UPDATE TICK */
	void update(boolean isServer);

	boolean updateOnInterval(int tickInterval);

	/* HANDLERS */
	@Nonnull
	IModularHandler getHandler();

	/**
	 * Set the modular handler of the modular.
	 */
	void setHandler(@Nullable IModularHandler tile);

	IHeatSource getHeatSource();

	IEnergyBuffer getEnergyBuffer();

	@Nullable
	IBlockModificator getBlockModificator();

	boolean addStorage(IStorage storage);

	Map<IStoragePosition, IStorage> getStorages();

	@Nonnull
	IModular copy(IModularHandler handler);

	/**
	 * Disassemble the modular.
	 */
	@Nonnull
	IModularAssembler disassemble();

	/**
	 * @return The next valid index for a new module state.
	 */
	int getNextIndex();

	/**
	 * Called at the point at that the assembler assemble the modular.
	 */
	void onModularAssembled() throws AssemblerException;
}
