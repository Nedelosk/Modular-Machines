package de.nedelosk.modularmachines.common.modules;

import java.util.ArrayList;
import java.util.List;

import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModularAssembler;
import de.nedelosk.modularmachines.api.modules.EnumModuleSizes;
import de.nedelosk.modularmachines.api.modules.IModuleCasing;
import de.nedelosk.modularmachines.api.modules.IModuleProperties;
import de.nedelosk.modularmachines.api.modules.handlers.IModuleContentHandler;
import de.nedelosk.modularmachines.api.modules.handlers.block.IBlockModificator;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.models.IModelHandler;
import de.nedelosk.modularmachines.api.modules.models.IModelInitHandler;
import de.nedelosk.modularmachines.api.modules.models.ModelHandlerCasing;
import de.nedelosk.modularmachines.api.modules.position.EnumStoragePositions;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModuleCasing extends StorageModule implements IModuleCasing<ModuleStorage> {

	public ModuleCasing() {
		super("casing");
	}

	@Override
	public  List<IModuleContentHandler> createHandlers(IModuleState state) {
		List<IModuleContentHandler> handlers = super.createHandlers(state);
		IBlockModificator blockModificator = createBlockModificator(state);
		if(blockModificator != null){
			handlers.add((IModuleContentHandler) blockModificator);
		}
		return handlers;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModelHandler createModelHandler(IModuleState state) {
		return new ModelHandlerCasing(state.getContainer());
	}

	@SideOnly(Side.CLIENT)
	@Override
	public List<IModelInitHandler> getInitModelHandlers(IModuleContainer container) {
		List<IModelInitHandler> handlers = new ArrayList<>();
		handlers.add(new ModelHandlerCasing(container));
		return handlers;
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
		tooltip.add(Translator.translateToLocal("mm.module.tooltip.storage.position") + EnumStoragePositions.CASING.getLocName());
		tooltip.add(Translator.translateToLocal("mm.module.allowed.complexity") + getAllowedComplexity(container));		
		super.addTooltip(tooltip, stack, container);
	}

	@Override
	public EnumModuleSizes getSize(IModuleContainer container) {
		return EnumModuleSizes.LARGE;
	}

	@Override
	public int getAllowedComplexity(IModuleContainer container) {
		IModuleProperties properties = container.getProperties();
		if(properties instanceof IModuleModuleStorageProperties){
			return ((IModuleModuleStorageProperties)properties).getAllowedComplexity(container);
		}
		return Config.defaultAllowedCasingComplexity;
	}

	@Override
	public EnumStoragePositions getCurrentPosition(IModuleState state) {
		return EnumStoragePositions.CASING;
	}

	@Override
	public boolean isValidForPosition(IStoragePosition position, IModuleContainer container) {
		IModuleProperties properties = container.getProperties();
		if(properties instanceof IModuleModuleStorageProperties){
			return ((IModuleModuleStorageProperties)properties).isValidForPosition(position, container);
		}
		return false;
	}

	@Override
	public <B extends IBlockModificator & IModuleContentHandler> B createBlockModificator(IModuleState state) {
		IModuleProperties properties = state.getModuleProperties();
		if(properties instanceof IModuleBlockModificatorProperties){
			IModuleBlockModificatorProperties blockModificator = (IModuleBlockModificatorProperties) properties;
			return blockModificator.createBlockModificator(state);
		}
		return null;
	}

	@Override
	public ModuleStorage createStorage(IModuleState state, IModular modular, IStoragePosition position) {
		return new ModuleStorage(modular, position, state, EnumModuleSizes.LARGEST, true);
	}

	@Override
	public IStoragePage createPage(IModularAssembler assembler, IModular modular, IStorage storage, IModuleState state, IStoragePosition position) {
		if(storage instanceof IDefaultModuleStorage){
			return new ModuleStoragePage(assembler, (IDefaultModuleStorage) storage);
		}
		return new ModuleStoragePage(assembler, EnumModuleSizes.LARGEST, position);
	}
}