package de.nedelosk.modularmachines.common.modules;

import java.util.ArrayList;
import java.util.List;

import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModularAssembler;
import de.nedelosk.modularmachines.api.modular.IModuleIndexStorage;
import de.nedelosk.modularmachines.api.modules.IModelInitHandler;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.models.IModelHandler;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storaged.EnumModuleSize;
import de.nedelosk.modularmachines.api.modules.storaged.EnumPosition;
import de.nedelosk.modularmachines.api.modules.storaged.IModuleModuleStorage;
import de.nedelosk.modularmachines.api.property.PropertyEnum;
import de.nedelosk.modularmachines.client.modules.ModelHandlerDrawer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;

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

	@SideOnly(Side.CLIENT)
	@Override
	public IModelHandler createModelHandler(IModuleState state) {
		IModuleContainer container = state.getContainer();
		return new ModelHandlerDrawer(
				new ResourceLocation("modularmachines:module/drawers/" + container.getMaterial().getName() + "/drawer"), 
				new ResourceLocation("modularmachines:module/drawers/" + container.getMaterial().getName() + "/wall"), 
				new ResourceLocation[]{ 
						new ResourceLocation("modularmachines:module/drawers/" + container.getMaterial().getName() + "/front_walls/stick_down"),
						new ResourceLocation("modularmachines:module/drawers/" + container.getMaterial().getName() + "/front_walls/stick_up"),
						new ResourceLocation("modularmachines:module/drawers/" + container.getMaterial().getName() + "/front_walls/small_down"),
						new ResourceLocation("modularmachines:module/drawers/" + container.getMaterial().getName() + "/front_walls/small_middle"),
						new ResourceLocation("modularmachines:module/drawers/" + container.getMaterial().getName() + "/front_walls/small_up"),
						new ResourceLocation("modularmachines:module/drawers/" + container.getMaterial().getName() + "/front_walls/middle_up"),
						new ResourceLocation("modularmachines:module/drawers/" + container.getMaterial().getName() + "/front_walls/middle_middle"),
						new ResourceLocation("modularmachines:module/drawers/" + container.getMaterial().getName() + "/front_walls/large")}
				);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public List<IModelInitHandler> getInitModelHandlers(IModuleContainer container) {
		List<IModelInitHandler> handlers = new ArrayList<>();
		handlers.add(new ModelHandlerDrawer(
				new ResourceLocation("modularmachines:module/drawers/" + container.getMaterial().getName() + "/drawer"), 
				new ResourceLocation("modularmachines:module/drawers/" + container.getMaterial().getName() + "/wall"), 
				new ResourceLocation[]{ 
						new ResourceLocation("modularmachines:module/drawers/" + container.getMaterial().getName() + "/front_walls/stick_down"),
						new ResourceLocation("modularmachines:module/drawers/" + container.getMaterial().getName() + "/front_walls/stick_up"),
						new ResourceLocation("modularmachines:module/drawers/" + container.getMaterial().getName() + "/front_walls/small_down"),
						new ResourceLocation("modularmachines:module/drawers/" + container.getMaterial().getName() + "/front_walls/small_middle"),
						new ResourceLocation("modularmachines:module/drawers/" + container.getMaterial().getName() + "/front_walls/small_up"),
						new ResourceLocation("modularmachines:module/drawers/" + container.getMaterial().getName() + "/front_walls/middle_up"),
						new ResourceLocation("modularmachines:module/drawers/" + container.getMaterial().getName() + "/front_walls/middle_middle"),
						new ResourceLocation("modularmachines:module/drawers/" + container.getMaterial().getName() + "/front_walls/large")}
				));
		return handlers;
	}

	@Override
	public int getAllowedComplexity(IModuleState state) {
		return allowedStoragedComplexity;
	}

	@Override
	public boolean assembleModule(IModularAssembler assembler, IModular modular, IModuleState state, IModuleIndexStorage storage) {
		int index = storage.getStateToSlotIndex().get(state);
		if(index == 9){
			state.set(STORAGEDPOSITION, true);
		}
		return super.assembleModule(assembler, modular, state, storage);
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
