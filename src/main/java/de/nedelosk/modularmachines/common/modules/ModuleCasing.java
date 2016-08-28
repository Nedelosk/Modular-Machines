package de.nedelosk.modularmachines.common.modules;

import java.util.ArrayList;
import java.util.List;

import de.nedelosk.modularmachines.api.modules.EnumModulePosition;
import de.nedelosk.modularmachines.api.modules.EnumModuleSize;
import de.nedelosk.modularmachines.api.modules.EnumStoragePosition;
import de.nedelosk.modularmachines.api.modules.IModelInitHandler;
import de.nedelosk.modularmachines.api.modules.IModuleBlockModificatorProperties;
import de.nedelosk.modularmachines.api.modules.IModuleCasing;
import de.nedelosk.modularmachines.api.modules.IModuleModuleStorageProperties;
import de.nedelosk.modularmachines.api.modules.IModuleProperties;
import de.nedelosk.modularmachines.api.modules.Module;
import de.nedelosk.modularmachines.api.modules.handlers.IModuleContentHandler;
import de.nedelosk.modularmachines.api.modules.handlers.block.IBlockModificator;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.models.IModelHandler;
import de.nedelosk.modularmachines.api.modules.models.ModelHandlerCasing;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.common.config.Config;
import de.nedelosk.modularmachines.common.utils.Translator;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModuleCasing extends Module implements IModuleCasing {

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
	public void addTooltip(List<String> tooltip, ItemStack stack, IModuleContainer container) {
		tooltip.add(Translator.translateToLocal("mm.module.tooltip.storage.position") + EnumStoragePosition.INTERNAL.getLocName());
		tooltip.add(Translator.translateToLocal("mm.module.allowed.complexity") + getAllowedComplexity(container));		
		super.addTooltip(tooltip, stack, container);
	}

	@Override
	public EnumModulePosition getPosition(IModuleContainer container) {
		return null;
	}

	@Override
	public EnumModuleSize getSize(IModuleContainer container) {
		return EnumModuleSize.LARGE;
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
	public EnumStoragePosition getCurrentPosition(IModuleState state) {
		return EnumStoragePosition.INTERNAL;
	}

	@Override
	public boolean isValidForPosition(EnumStoragePosition position, IModuleContainer container) {
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
}