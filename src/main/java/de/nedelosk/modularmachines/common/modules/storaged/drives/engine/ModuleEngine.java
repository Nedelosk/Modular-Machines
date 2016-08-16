package de.nedelosk.modularmachines.common.modules.storaged.drives.engine;

import java.util.ArrayList;
import java.util.List;

import de.nedelosk.modularmachines.api.energy.IKineticSource;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modules.IModelInitHandler;
import de.nedelosk.modularmachines.api.modules.IModuleProperties;
import de.nedelosk.modularmachines.api.modules.handlers.IModuleContentHandler;
import de.nedelosk.modularmachines.api.modules.handlers.energy.ModuleKineticHandler;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.models.IModelHandler;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storaged.EnumModuleSize;
import de.nedelosk.modularmachines.api.modules.storaged.EnumPosition;
import de.nedelosk.modularmachines.api.modules.storaged.EnumWallType;
import de.nedelosk.modularmachines.api.modules.storaged.drives.IModuleEngine;
import de.nedelosk.modularmachines.api.modules.storaged.drives.IModuleKineticProperties;
import de.nedelosk.modularmachines.api.property.PropertyBool;
import de.nedelosk.modularmachines.client.modules.ModelHandler;
import de.nedelosk.modularmachines.client.modules.ModelHandlerEngine;
import de.nedelosk.modularmachines.common.config.Config;
import de.nedelosk.modularmachines.common.modules.Module;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import de.nedelosk.modularmachines.common.network.packets.PacketSyncModule;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ModuleEngine extends Module implements IModuleEngine {

	protected static final float engineKineticModifier = Config.engineKineticOutput;

	public static final PropertyBool WORKING = new PropertyBool("isWorking", false);

	public ModuleEngine(String name) {
		super(name);
	}

	@Override
	public int getMaxKineticEnergy(IModuleState state) {
		IModuleProperties properties = state.getContainer().getProperties();
		if(properties instanceof IModuleKineticProperties){
			return ((IModuleKineticProperties) properties).getMaxKineticEnergy(state);
		}
		return 0;
	}

	@Override
	public int getMaterialPerWork(IModuleState state) {
		IModuleProperties properties = state.getContainer().getProperties();
		if(properties instanceof IModuleKineticProperties){
			return ((IModuleKineticProperties) properties).getMaterialPerWork(state);
		}
		return 0;
	}

	@Override
	public double getKineticModifier(IModuleState state) {
		IModuleProperties properties = state.getContainer().getProperties();
		if(properties instanceof IModuleKineticProperties){
			return ((IModuleKineticProperties) properties).getKineticModifier(state);
		}
		return 0;
	}

	@Override
	public List<IModuleContentHandler> createHandlers(IModuleState state) {
		List<IModuleContentHandler> handlers = super.createHandlers(state);
		handlers.add(new ModuleKineticHandler(state, getMaxKineticEnergy(state), 100));
		return handlers;
	}

	@Override
	public IKineticSource getKineticSource(IModuleState state) {
		return (IKineticSource) state.getContentHandler(ModuleKineticHandler.class);
	}

	@Override
	public void updateServer(IModuleState state, int tickCount) {
		IModular modular = state.getModular();
		boolean isWorking = state.get(WORKING);
		ModuleKineticHandler kineticHandler = (ModuleKineticHandler) state.getContentHandler(ModuleKineticHandler.class);
		boolean needUpdate = false;

		if (canWork(state)) {
			if (removeMaterial(state)) {
				if(!isWorking){
					state.set(WORKING, true);
				}
				kineticHandler.increaseKineticEnergy(getKineticModifier(state) * engineKineticModifier);
				needUpdate = true;
			}
		}else if(isWorking){
			if(kineticHandler.getKineticEnergyStored() > 0){
				kineticHandler.reduceKineticEnergy(getKineticModifier(state) * engineKineticModifier);
			}else{
				state.set(WORKING, false);
			}
			needUpdate = true;
		}

		if(needUpdate){
			PacketHandler.INSTANCE.sendToAll(new PacketSyncModule(modular.getHandler(), state));
		}
	}

	protected abstract boolean canWork(IModuleState state);

	protected abstract boolean removeMaterial(IModuleState state);

	@SideOnly(Side.CLIENT)
	@Override
	public void updateClient(IModuleState state, int tickCount) {
	}

	@Override
	public IModuleState createState(IModular modular, IModuleContainer container) {
		return super.createState(modular, container).register(WORKING);
	}

	@Override
	public EnumPosition getPosition(IModuleContainer container) {
		return EnumPosition.RIGHT;
	}

	@Override
	public boolean isWorking(IModuleState state) {
		return state.get(WORKING);
	}

	protected void setIsWorking(IModuleState state, boolean isWorking) {
		state.set(WORKING, isWorking);
	}

	@Override
	public EnumModuleSize getSize(IModuleContainer container) {
		return EnumModuleSize.SMALL;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModelHandler createModelHandler(IModuleState state) {
		return new ModelHandlerEngine(state.getContainer());
	}

	@SideOnly(Side.CLIENT)
	@Override
	public List<IModelInitHandler> getInitModelHandlers(IModuleContainer container) {
		List<IModelInitHandler> handlers = new ArrayList<>();
		handlers.add(new ModelHandlerEngine(container));
		return handlers;
	}

	@Override
	public EnumWallType getWallType(IModuleState state) {
		return EnumWallType.WINDOW;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public ResourceLocation getWindowLocation(IModuleContainer container) {
		return ModelHandler.getModelLocation(container, "windows",  getSize(container));
	}

}