package de.nedelosk.modularmachines.api.modules;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModularHandler;
import de.nedelosk.modularmachines.api.modular.IModularLogic;
import de.nedelosk.modularmachines.api.modular.IModuleIndexStorage;
import de.nedelosk.modularmachines.api.modules.handlers.IModuleContentHandler;
import de.nedelosk.modularmachines.api.modules.handlers.IModulePage;
import de.nedelosk.modularmachines.api.modules.models.IModelHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;

public interface IModule extends IForgeRegistryEntry<IModule> {

	String getUnlocalizedName(IModuleContainer container);

	String getDisplayName(IModuleContainer container);

	int getComplexity(IModuleState state);

	void updateServer(IModuleState<IModule> state, int tickCount);

	void updateClient(IModuleState<IModule> state, int tickCount);

	/**
	 * @return A new list of all content handler of the module that are not empty.
	 */
	List<IModuleContentHandler> createContentHandlers(IModuleState state);

	@Nullable
	@SideOnly(Side.CLIENT)
	IModelHandler createModelHandler(@Nonnull IModuleState state);

	@SideOnly(Side.CLIENT)
	boolean needHandlerReload(IModuleStateClient state);

	/**
	 * @return The IModuleModelHandler that is used to init the models.
	 */
	@Nullable
	@SideOnly(Side.CLIENT)
	List<IModelInitHandler> getInitModelHandlers(@Nullable IModuleContainer container);

	@SideOnly(Side.CLIENT)
	void addTooltip(List<String> tooltip, IModuleContainer container);

	/**
	 * To transfer items into slots. Only for modules with inventory.
	 */
	boolean transferInput(IModularHandler tile, IModuleState state, EntityPlayer player, int slotID, Container container, ItemStack stackItem);

	/**
	 * @return The item that the module drop.
	 */
	ItemStack getDropItem(IModuleState state);

	/**
	 * To load datas from the item into the state.
	 */
	IModuleState loadStateFromItem(IModuleState state, ItemStack stack);

	/**
	 * Create the ModulePages for the module.
	 */
	@Nonnull
	List<IModulePage> createPages(IModuleState state);

	@Nonnull
	List<IModularLogic> createLogic(IModuleState state);

	/**
	 * Crate a new state for the module.
	 */
	IModuleState createState(IModular modular, IModuleContainer container);

	boolean assembleModule(IItemHandler itemHandler, IModular modular, IModuleState state, IModuleIndexStorage storage);

}
