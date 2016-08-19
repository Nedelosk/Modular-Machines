package de.nedelosk.modularmachines.common.modules.storaged.drives.turbines;

import java.util.ArrayList;
import java.util.List;

import de.nedelosk.modularmachines.api.energy.IKineticSource;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modules.IModelInitHandler;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.IModuleProperties;
import de.nedelosk.modularmachines.api.modules.handlers.IModuleContentHandler;
import de.nedelosk.modularmachines.api.modules.handlers.energy.ModuleKineticHandler;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.models.IModelHandler;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storaged.EnumStoragePosition;
import de.nedelosk.modularmachines.api.modules.storaged.EnumWallType;
import de.nedelosk.modularmachines.api.modules.storaged.drives.IModuleKineticProperties;
import de.nedelosk.modularmachines.api.modules.storaged.drives.IModuleTurbine;
import de.nedelosk.modularmachines.api.property.PropertyBool;
import de.nedelosk.modularmachines.client.modules.ModelHandler;
import de.nedelosk.modularmachines.client.modules.ModelHandlerDefault;
import de.nedelosk.modularmachines.common.config.Config;
import de.nedelosk.modularmachines.common.modules.Module;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import de.nedelosk.modularmachines.common.network.packets.PacketSyncModule;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ModuleTurbine extends Module implements IModuleTurbine{

	public static final PropertyBool WORKING = new PropertyBool("isWorking", false);
	protected static final float turgineKineticModifier = Config.turbineKineticOutput;

	public ModuleTurbine(String name) {
		super(name);
	}

	@Override
	public int getMaxKineticEnergy(IModuleState state) {
		IModuleProperties properties = state.getModuleProperties();
		if(properties instanceof IModuleKineticProperties){
			return ((IModuleKineticProperties) properties).getMaxKineticEnergy(state);
		}
		return 0;
	}

	@Override
	public int getMaterialPerWork(IModuleState state) {
		IModuleProperties properties = state.getModuleProperties();
		if(properties instanceof IModuleKineticProperties){
			return ((IModuleKineticProperties) properties).getMaterialPerWork(state);
		}
		return 0;
	}

	@Override
	public double getKineticModifier(IModuleState state) {
		IModuleProperties properties = state.getModuleProperties();
		if(properties instanceof IModuleKineticProperties){
			return ((IModuleKineticProperties) properties).getKineticModifier(state);
		}
		return 0;
	}

	@Override
	public EnumStoragePosition getPosition(IModuleContainer container) {
		return EnumStoragePosition.RIGHT;
	}

	@Override
	public EnumWallType getWallType(IModuleState state) {
		return EnumWallType.WINDOW;
	}

	@Override
	public List<IModuleContentHandler> createHandlers(IModuleState state) {
		IModuleContainer container = state.getContainer();
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
		IModuleContainer container = state.getContainer();
		IModular modular = state.getModular();
		boolean isWorking = state.get(WORKING);
		ModuleKineticHandler kineticHandler = (ModuleKineticHandler) state.getContentHandler(ModuleKineticHandler.class);
		boolean needUpdate = false;

		if (canWork(state)) {
			if (removeMaterial(state)) {
				if(!isWorking){
					state.set(WORKING, true);
				}
				kineticHandler.increaseKineticEnergy(getKineticModifier(state) * turgineKineticModifier);
				needUpdate = true;
			}
		}else if(isWorking){
			if(kineticHandler.getKineticEnergyStored() > 0){
				kineticHandler.reduceKineticEnergy(getKineticModifier(state) * turgineKineticModifier);
			}else{
				state.set(WORKING, false);
			}
			needUpdate = true;
		}

		if(needUpdate){
			PacketHandler.INSTANCE.sendToAll(new PacketSyncModule(modular.getHandler(), state));
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void updateClient(IModuleState<IModule> state, int tickCount) {
	}

	protected abstract boolean canWork(IModuleState state);

	protected abstract boolean removeMaterial(IModuleState state);

	@SideOnly(Side.CLIENT)
	@Override
	public IModelHandler createModelHandler(IModuleState state) {
		return new ModelHandlerDefault("turbines", state.getContainer(), ModelHandler.getModelLocation(state.getContainer(), "turbines", getSize(state.getContainer())));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public List<IModelInitHandler> getInitModelHandlers(IModuleContainer container) {
		List<IModelInitHandler> handlers = new ArrayList<>();
		handlers.add(new ModelHandlerDefault("turbines", container, ModelHandler.getModelLocation(container, "turbines", getSize(container))));
		return handlers;
	}

	@Override
	public IModuleState createState(IModular modular, IModuleContainer container) {
		return super.createState(modular, container).register(WORKING);
	}

	@Override
	public boolean isWorking(IModuleState state) {
		return state.get(WORKING);
	}

	protected void setIsWorking(IModuleState state, boolean isWorking) {
		state.set(WORKING, isWorking);
	}
}
