package de.nedelosk.modularmachines.common.modules;

import java.util.ArrayList;
import java.util.List;

import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModularAssembler;
import de.nedelosk.modularmachines.api.modules.IModelInitHandler;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.models.IModelHandler;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storage.IModuleStorage;
import de.nedelosk.modularmachines.api.modules.storage.IPositionedModuleStorage;
import de.nedelosk.modularmachines.api.modules.storaged.EnumModuleSize;
import de.nedelosk.modularmachines.api.modules.storaged.EnumStoragePosition;
import de.nedelosk.modularmachines.api.modules.storaged.IModuleModuleStorage;
import de.nedelosk.modularmachines.api.property.PropertyEnum;
import de.nedelosk.modularmachines.client.modules.ModelHandlerDrawer;
import de.nedelosk.modularmachines.common.utils.Translator;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModuleModuleStorage extends Module implements IModuleModuleStorage {

	public final PropertyEnum<EnumStoragePosition> STORAGEDPOSITION = new PropertyEnum("storagedPosition", EnumStoragePosition.class, null);
	public final int allowedStoragedComplexity;
	public final EnumModuleSize size;
	public final int complexity;

	public ModuleModuleStorage(int complexity, int allowedStoragedComplexity, EnumModuleSize size) {
		super("drawer");
		this.complexity = complexity;
		this.allowedStoragedComplexity = allowedStoragedComplexity;
		this.size = size;
	}

	@Override
	public IModuleState createState(IModular modular, IModuleContainer container) {
		return super.createState(modular, container).register(STORAGEDPOSITION);
	}

	@Override
	protected boolean showSize(IModuleContainer container) {
		return false;
	}

	@Override
	public void addTooltip(List<String> tooltip, ItemStack stack, IModuleContainer container) {
		List<String> positions = new ArrayList<>();
		if(size == EnumModuleSize.LARGE) {
			positions.add(EnumStoragePosition.LEFT.getLocName());
			positions.add(EnumStoragePosition.RIGHT.getLocName());
		}else if(size == EnumModuleSize.SMALL) {
			positions.add(EnumStoragePosition.TOP.getLocName());
			positions.add(EnumStoragePosition.BACK.getLocName());
		}
		tooltip.add(Translator.translateToLocal("mm.module.tooltip.storage.position") + positions.toString().replace("[", "").replace("]", ""));
		super.addTooltip(tooltip, stack, container);
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
	public int getAllowedComplexity(IModuleContainer container) {
		return allowedStoragedComplexity;
	}

	@Override
	public void assembleModule(IModularAssembler assembler, IModular modular, IModuleStorage storage, IModuleState state) throws ArithmeticException {
		if(storage instanceof IPositionedModuleStorage){
			state.set(STORAGEDPOSITION, ((IPositionedModuleStorage)storage).getPosition());
		}
	}

	@Override
	public EnumStoragePosition getCurrentPosition(IModuleState state) {
		return state.get(STORAGEDPOSITION);
	}

	@Override
	public EnumStoragePosition getPosition(IModuleContainer container) {
		return null;
	}

	@Override
	public EnumModuleSize getSize(IModuleContainer container) {
		return size;
	}

	@Override
	public int getComplexity(IModuleContainer container) {
		return complexity;
	}

	@Override
	public boolean canUseFor(EnumStoragePosition position, IModuleContainer container) {
		return (size == EnumModuleSize.LARGE) ? position == EnumStoragePosition.LEFT || position == EnumStoragePosition.RIGHT : position == EnumStoragePosition.TOP || position == EnumStoragePosition.BACK;
	}
}
