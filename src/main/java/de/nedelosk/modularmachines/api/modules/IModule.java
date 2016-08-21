package de.nedelosk.modularmachines.api.modules;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import de.nedelosk.modularmachines.api.modular.AssemblerException;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModularAssembler;
import de.nedelosk.modularmachines.api.modules.handlers.IModuleContentHandler;
import de.nedelosk.modularmachines.api.modules.handlers.IModulePage;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.models.IModelHandler;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.state.IModuleStateClient;
import de.nedelosk.modularmachines.api.modules.storage.IModuleStorage;
import de.nedelosk.modularmachines.api.modules.storaged.EnumStoragePosition;
import de.nedelosk.modularmachines.api.modules.storaged.EnumWallType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IModule extends IForgeRegistryEntry<IModule>, IModuleProperties {

	EnumStoragePosition getPosition(IModuleContainer container);

	@SideOnly(Side.CLIENT)
	EnumWallType getWallType(IModuleState state);

	@SideOnly(Side.CLIENT)
	@Nullable
	ResourceLocation getWindowLocation(IModuleContainer container);

	String getUnlocalizedName(IModuleContainer container);

	String getDisplayName(IModuleContainer container);

	String getDescription(IModuleContainer container);

	/**
	 * @return A new list of all content handler of the module that are not empty.
	 */
	List<IModuleContentHandler> createHandlers(IModuleState state);

	@Nullable
	@SideOnly(Side.CLIENT)
	IModelHandler createModelHandler(@Nonnull IModuleState state);

	@SideOnly(Side.CLIENT)
	boolean needHandlerReload(IModuleStateClient state);

	/**
	 * @return The item that the module drop.
	 */
	ItemStack saveDataToItem(IModuleState state);

	/**
	 * To load datas from the item into the state.
	 */
	IModuleState loadStateFromItem(IModuleState state, ItemStack stack);

	/**
	 * Create the ModulePages for the module.
	 */
	@Nonnull
	List<IModulePage> createPages(IModuleState state);

	/**
	 * Crate a new module state for the module.
	 */
	IModuleState createState(IModular modular, IModuleContainer container);

	void assembleModule(IModularAssembler assembler, IModular modular, IModuleStorage storage, IModuleState state) throws AssemblerException;

	/* MODULE CONTAINERS */
	@Nullable
	@SideOnly(Side.CLIENT)
	List<IModelInitHandler> getInitModelHandlers(@Nullable IModuleContainer container);

	/**
	 * Add a tooltip to a item that are registered for a module container with this module.
	 */
	@SideOnly(Side.CLIENT)
	void addTooltip(List<String> tooltip, ItemStack stack, IModuleContainer container);

	boolean isClean(IModuleState state);

	void sendModuleUpdate(IModuleState state);
}
