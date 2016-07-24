package de.nedelosk.modularmachines.common.modules;

import java.util.ArrayList;
import java.util.List;

import de.nedelosk.modularmachines.api.Translator;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModularAssembler;
import de.nedelosk.modularmachines.api.modules.IModelInitHandler;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.models.IModelHandler;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storage.IPositionedModuleStorage;
import de.nedelosk.modularmachines.api.modules.storaged.EnumModuleSize;
import de.nedelosk.modularmachines.api.modules.storaged.EnumPosition;
import de.nedelosk.modularmachines.api.modules.storaged.IModuleModuleStorage;
import de.nedelosk.modularmachines.api.property.PropertyEnum;
import de.nedelosk.modularmachines.client.modules.ModelHandlerDrawer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModuleModuleStorage extends Module implements IModuleModuleStorage {

	public final PropertyEnum<EnumPosition> STORAGEDPOSITION = new PropertyEnum("storagedPosition", EnumPosition.class, null);
	public final int allowedStoragedComplexity;
	public final EnumModuleSize size;

	public ModuleModuleStorage(int complexity, int allowedStoragedComplexity, EnumModuleSize size) {
		super("drawer", complexity);
		this.allowedStoragedComplexity = allowedStoragedComplexity;
		this.size = size;
	}

	@Override
	public IModuleState createState(IModular modular, IModuleContainer container) {
		return super.createState(modular, container).register(STORAGEDPOSITION);
	}

	@Override
	public void addTooltip(List<String> tooltip, IModuleContainer container) {
		tooltip.add(Translator.translateToLocal("mm.module.tooltip.size") + getSize().getLocalizedName());
		tooltip.add(Translator.translateToLocal("mm.module.tooltip.complexity") + complexity);
		List<String> positions = new ArrayList<>();
		if(size == EnumModuleSize.LARGE) {
			positions.add(Translator.translateToLocal("module.storage." + EnumPosition.LEFT.getName() + ".name"));
			positions.add(Translator.translateToLocal("module.storage." + EnumPosition.RIGHT.getName() + ".name"));
		}else if(size == EnumModuleSize.SMALL) {
			positions.add(Translator.translateToLocal("module.storage." + EnumPosition.TOP.getName() + ".name"));
			positions.add(Translator.translateToLocal("module.storage." + EnumPosition.BACK.getName() + ".name"));
		}
		tooltip.add(Translator.translateToLocal("mm.module.tooltip.position.can.use") + positions.toString().replace("[", "").replace("]", ""));
	}

	protected ModelHandlerDrawer createModelHandler(IModuleContainer container){
		return new ModelHandlerDrawer(container);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModelHandler createModelHandler(IModuleState state) {
		return createModelHandler(state.getContainer());
	}

	@SideOnly(Side.CLIENT)
	@Override
	public List<IModelInitHandler> getInitModelHandlers(IModuleContainer container) {
		List<IModelInitHandler> handlers = new ArrayList<>();
		handlers.add(createModelHandler(container));
		return handlers;
	}

	@Override
	public int getAllowedComplexity(IModuleState state) {
		return allowedStoragedComplexity;
	}

	@Override
	public void assembleModule(IModularAssembler assembler, IModular modular, IPositionedModuleStorage storage, IModuleState state) throws ArithmeticException {
		state.set(STORAGEDPOSITION, storage.getPosition());
	}

	@Override
	public EnumPosition getCurrentPosition(IModuleState state) {
		return state.get(STORAGEDPOSITION);
	}

	@Override
	public EnumPosition getPosition(IModuleContainer container) {
		return null;
	}

	@Override
	public EnumModuleSize getSize() {
		return size;
	}

	@Override
	public boolean canUseFor(EnumPosition position, IModuleContainer container) {
		return (size == EnumModuleSize.LARGE) ? position == EnumPosition.LEFT || position == EnumPosition.RIGHT : position == EnumPosition.TOP || position == EnumPosition.BACK;
	}
}
