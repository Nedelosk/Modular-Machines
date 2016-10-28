package de.nedelosk.modularmachines.common.modules;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.nedelosk.modularmachines.api.modular.ExpandedStoragePositions;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModularAssembler;
import de.nedelosk.modularmachines.api.modules.EnumModuleSizes;
import de.nedelosk.modularmachines.api.modules.IModuleCasing;
import de.nedelosk.modularmachines.api.modules.IModuleProperties;
import de.nedelosk.modularmachines.api.modules.containers.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.containers.IModuleItemContainer;
import de.nedelosk.modularmachines.api.modules.containers.IModuleProvider;
import de.nedelosk.modularmachines.api.modules.handlers.IModuleContentHandler;
import de.nedelosk.modularmachines.api.modules.handlers.block.IBlockModificator;
import de.nedelosk.modularmachines.api.modules.models.IModelHandler;
import de.nedelosk.modularmachines.api.modules.models.ModelHandlerCasing;
import de.nedelosk.modularmachines.api.modules.models.ModuleModelLoader;
import de.nedelosk.modularmachines.api.modules.position.IStoragePosition;
import de.nedelosk.modularmachines.api.modules.properties.IModuleBlockModificatorProperties;
import de.nedelosk.modularmachines.api.modules.properties.IModuleModuleStorageProperties;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storage.IStorage;
import de.nedelosk.modularmachines.api.modules.storage.IStoragePage;
import de.nedelosk.modularmachines.api.modules.storage.StorageModule;
import de.nedelosk.modularmachines.api.modules.storage.module.IDefaultModuleStorage;
import de.nedelosk.modularmachines.api.modules.storage.module.ModuleStorage;
import de.nedelosk.modularmachines.api.modules.storage.module.ModuleStoragePage;
import de.nedelosk.modularmachines.common.config.Config;
import de.nedelosk.modularmachines.common.utils.Translator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModuleCasing extends StorageModule implements IModuleCasing<ModuleStorage> {

	public ModuleCasing() {
		super("casing");
	}

	@Override
	public List<IModuleContentHandler> createHandlers(IModuleState state) {
		List<IModuleContentHandler> handlers = super.createHandlers(state);
		IBlockModificator blockModificator = createBlockModificator(state);
		if (blockModificator != null) {
			handlers.add((IModuleContentHandler) blockModificator);
		}
		return handlers;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModelHandler createModelHandler(IModuleState state) {
		IModuleItemContainer container = state.getContainer().getItemContainer();
		return new ModelHandlerCasing(ModuleModelLoader.getModelLocation(getRegistryName().getResourceDomain(), container.getMaterial().getName(), "casings", "casing"),
				ModuleModelLoader.getModelLocation(getRegistryName().getResourceDomain(), container.getMaterial().getName(), "casings", "side_left"),
				ModuleModelLoader.getModelLocation(getRegistryName().getResourceDomain(), container.getMaterial().getName(), "casings", "side_right"));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Map<ResourceLocation, ResourceLocation> getModelLocations(IModuleItemContainer container) {
		Map<ResourceLocation, ResourceLocation> locations = new HashMap<>();
		locations.put(ModuleModelLoader.getModelLocation(getRegistryName().getResourceDomain(), container.getMaterial().getName(), "casings", "casing"),
				ModuleModelLoader.getModelLocation(getRegistryName().getResourceDomain(), "default", "casings", "casing"));
		locations.put(ModuleModelLoader.getModelLocation(getRegistryName().getResourceDomain(), container.getMaterial().getName(), "casings", "side_left"),
				ModuleModelLoader.getModelLocation(getRegistryName().getResourceDomain(), "default", "casings", "side_left"));
		locations.put(ModuleModelLoader.getModelLocation(getRegistryName().getResourceDomain(), container.getMaterial().getName(), "casings", "side_right"),
				ModuleModelLoader.getModelLocation(getRegistryName().getResourceDomain(), "default", "casings", "side_right"));
		return locations;
	}

	@Override
	protected boolean showSize(IModuleContainer container) {
		return false;
	}

	@Override
	protected boolean showPosition(IModuleContainer container) {
		return false;
	}

	@Override
	public void addTooltip(List<String> tooltip, ItemStack stack, IModuleContainer container) {
		tooltip.add(Translator.translateToLocal("mm.module.tooltip.storage.position") + ExpandedStoragePositions.CASING.getLocName());
		tooltip.add(Translator.translateToLocal("mm.module.allowed.complexity") + getAllowedComplexity(container));
		super.addTooltip(tooltip, stack, container);
	}

	@Override
	public int getAllowedComplexity(IModuleContainer container) {
		IModuleProperties properties = container.getProperties();
		if (properties instanceof IModuleModuleStorageProperties) {
			return ((IModuleModuleStorageProperties) properties).getAllowedComplexity(container);
		}
		return Config.defaultAllowedCasingComplexity;
	}

	@Override
	public <B extends IBlockModificator & IModuleContentHandler> B createBlockModificator(IModuleState state) {
		IModuleProperties properties = state.getModuleProperties();
		if (properties instanceof IModuleBlockModificatorProperties) {
			IModuleBlockModificatorProperties blockModificator = (IModuleBlockModificatorProperties) properties;
			return blockModificator.createBlockModificator(state);
		}
		return null;
	}

	@Override
	public ModuleStorage createStorage(IModuleProvider provider, IStoragePosition position) {
		return new ModuleStorage(position, provider, EnumModuleSizes.LARGEST, true);
	}

	@Override
	public IStoragePage createPage(IModularAssembler assembler, IModular modular, IStorage storage, IStoragePosition position) {
		if (storage instanceof IDefaultModuleStorage) {
			return new ModuleStoragePage(assembler, (IDefaultModuleStorage) storage);
		}
		return new ModuleStoragePage(assembler, EnumModuleSizes.LARGEST, position);
	}
}