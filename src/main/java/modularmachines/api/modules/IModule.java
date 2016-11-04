package modularmachines.api.modules;

import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import modularmachines.api.modular.AssemblerException;
import modularmachines.api.modular.IModular;
import modularmachines.api.modular.IModularAssembler;
import modularmachines.api.modular.assembler.SlotAssembler;
import modularmachines.api.modular.assembler.SlotAssemblerStorage;
import modularmachines.api.modules.containers.IModuleContainer;
import modularmachines.api.modules.containers.IModuleItemContainer;
import modularmachines.api.modules.containers.IModuleProvider;
import modularmachines.api.modules.handlers.IModuleContentHandler;
import modularmachines.api.modules.models.IModelHandler;
import modularmachines.api.modules.position.IStoragePosition;
import modularmachines.api.modules.state.IModuleState;
import modularmachines.api.modules.state.IModuleStateClient;
import modularmachines.api.modules.storage.IStorage;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IModule extends IForgeRegistryEntry<IModule>, IModuleProperties {

	@SideOnly(Side.CLIENT)
	EnumWallType getWallType(IModuleState state);

	@SideOnly(Side.CLIENT)
	@Nullable
	ResourceLocation getWindowLocation(IModuleItemContainer container);

	String getUnlocalizedName(IModuleContainer container);

	String getDisplayName(IModuleContainer container);

	String getDescription(IModuleContainer container);

	/**
	 * @return A new list of all content handler of the module that are not
	 *         empty.
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
	void saveDataToItem(ItemStack itemStack, IModuleState state);

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
	IModuleState createState(IModuleProvider provider, IModuleContainer container);

	void assembleModule(IModularAssembler assembler, IModular modular, IStorage storage, IModuleState state) throws AssemblerException;

	boolean isValid(@Nonnull IModularAssembler assembler, @Nonnull IStoragePosition position, @Nonnull ItemStack stack, @Nullable SlotAssembler slot, @Nonnull SlotAssemblerStorage storageSlot);

	/* MODULE CONTAINERS */
	@Nullable
	@SideOnly(Side.CLIENT)
	Map<ResourceLocation, ResourceLocation> getModelLocations(@Nullable IModuleItemContainer container);

	/**
	 * Add a tooltip to a item that are registered for a module container with
	 * this module.
	 */
	@SideOnly(Side.CLIENT)
	void addTooltip(List<String> tooltip, ItemStack stack, IModuleContainer container);

	boolean isClean(IModuleState state);

	void onModularAssembled(IModuleState state);

	void sendModuleUpdate(IModuleState state);
}
