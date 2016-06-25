package de.nedelosk.modularmachines.api.modules;

import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import akka.japi.Pair;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModularLogic;
import de.nedelosk.modularmachines.api.modular.IModularTileEntity;
import de.nedelosk.modularmachines.api.modular.assembler.IAssemblerGroup;
import de.nedelosk.modularmachines.api.modular.assembler.IAssemblerSlot;
import de.nedelosk.modularmachines.api.modular.renderer.IRenderState;
import de.nedelosk.modularmachines.api.modular.renderer.ISimpleRenderer;
import de.nedelosk.modularmachines.api.modules.casing.IModuleCasing;
import de.nedelosk.modularmachines.api.modules.handlers.IModuleContentHandler;
import de.nedelosk.modularmachines.api.modules.handlers.IModulePage;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IModule extends IForgeRegistryEntry<IModule> {

	void updateServer(IModuleState<IModule> state);

	void updateClient(IModuleState<IModule> state);

	/**
	 * @return A new list of all content handler of the module that are not empty.
	 */
	List<IModuleContentHandler> createContentHandlers(IModuleState state);

	@Deprecated
	@SideOnly(Side.CLIENT)
	ISimpleRenderer getRenderer(IRenderState state);

	@Nullable
	@SideOnly(Side.CLIENT)
	IModuleModelHandler getModelHandler(IModuleState state);

	/**
	 * To transfer items into slots. Only for modules with inventory.
	 */
	boolean transferInput(IModularTileEntity tile, IModuleState state, EntityPlayer player, int slotID, Container container, ItemStack stackItem);

	/**
	 * @return The item that the module drop.
	 */
	ItemStack getDropItem(IModuleState state);

	/**
	 * @return True if the module can add to the modular.
	 */
	boolean onAssembleModule(IAssemblerGroup group, IModuleState state, IModuleState<IModuleCasing> casingState, Map<IAssemblerGroup, List<Pair<IAssemblerSlot, IModuleState>>> modules, boolean beforeAdd);

	/**
	 * To load datas from the item into the state.
	 */
	IModuleState loadStateFromItem(IModuleState state, ItemStack stack);

	/**
	 * Create the ModulePages for the module.
	 */
	@Nullable
	IModulePage[] createPages(IModuleState state);

	@Nonnull
	List<IModularLogic> createLogic(IModuleState state);

	/**
	 * Crate a new state for the module.
	 */
	IModuleState createState(IModular modular, IModuleContainer container);

	/* ASSEMBLER */

	/**
	 * @return True id the assembler con assemble the slot.
	 */
	boolean canAssembleModule(IAssemblerSlot slot, IModuleState<IModule> state);

	boolean onStatusChange(IAssemblerSlot slot, boolean isActive);

	/**
	 * To add slots to the group of the controller.
	 */
	void updateSlots(IAssemblerSlot slot);

	/**
	 * Test is a item valid for the assembler slot.
	 */
	boolean canInsertItem(IAssemblerSlot slot, ItemStack stack);

}
