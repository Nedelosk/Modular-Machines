package de.nedelosk.modularmachines.api.modules;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import de.nedelosk.modularmachines.api.modular.AssemblerException;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModularAssembler;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.handlers.IModuleContentHandler;
import de.nedelosk.modularmachines.api.modules.handlers.IModulePage;
import de.nedelosk.modularmachines.api.modules.models.IModelHandler;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.state.IModuleStateClient;
import de.nedelosk.modularmachines.api.modules.storage.IPositionedModuleStorage;
import de.nedelosk.modularmachines.api.modules.storaged.EnumModuleSize;
import de.nedelosk.modularmachines.api.modules.storaged.EnumPosition;
import de.nedelosk.modularmachines.api.modules.storaged.EnumWallType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IModule extends IForgeRegistryEntry<IModule> {

	EnumPosition getPosition(IModuleContainer container);

	/**
	 * The size of the module.
	 */
	EnumModuleSize getSize();

	@SideOnly(Side.CLIENT)
	EnumWallType getWallType(IModuleState state);

	@SideOnly(Side.CLIENT)
	@Nullable
	ResourceLocation getWindowLocation(IModuleContainer container);

	String getUnlocalizedName(IModuleContainer container);

	String getDisplayName(IModuleContainer container);

	String getDescription(IModuleContainer container);

	int getComplexity(IModuleState state);

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
	 * To transfer items into slots. Only for modules with inventory.
	 */
	boolean transferInput(IModularHandler tile, IModuleState state, EntityPlayer player, int slotID, Container container, ItemStack stackItem);

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

	void assembleModule(IModularAssembler assembler, IModular modular, IPositionedModuleStorage storage, IModuleState state) throws AssemblerException;

	/* MODULE CONTAINERS */
	@Nullable
	@SideOnly(Side.CLIENT)
	List<IModelInitHandler> getInitModelHandlers(@Nullable IModuleContainer container);

	/**
	 * Add a tooltip to a item that are registered for a module container with this module.
	 */
	@SideOnly(Side.CLIENT)
	void addTooltip(List<String> tooltip, IModuleContainer container);
}
