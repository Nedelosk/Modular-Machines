package de.nedelosk.modularmachines.common.modules.storaged.drives.engine;

import java.util.ArrayList;
import java.util.List;

import de.nedelosk.modularmachines.api.energy.IKineticSource;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modules.IModelInitHandler;
import de.nedelosk.modularmachines.api.modules.handlers.IModuleContentHandler;
import de.nedelosk.modularmachines.api.modules.handlers.energy.ModuleKineticHandler;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.models.IModelHandler;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storaged.EnumModuleSize;
import de.nedelosk.modularmachines.api.modules.storaged.EnumPosition;
import de.nedelosk.modularmachines.api.modules.storaged.EnumWallType;
import de.nedelosk.modularmachines.api.modules.storaged.drives.IModuleEngine;
import de.nedelosk.modularmachines.api.property.PropertyBool;
import de.nedelosk.modularmachines.api.property.PropertyFloat;
import de.nedelosk.modularmachines.client.modules.ModelHandler;
import de.nedelosk.modularmachines.client.modules.ModelHandlerEngine;
import de.nedelosk.modularmachines.common.config.Config;
import de.nedelosk.modularmachines.common.modules.Module;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import de.nedelosk.modularmachines.common.network.packets.PacketModule;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ModuleEngine extends Module implements IModuleEngine {

	protected final double kineticModifier;
	protected final int maxKineticEnergy;
	protected final int materialPerWork;
	protected static final float engineKineticModifier = Config.engineKineticOutput;

	public static final PropertyBool WORKING = new PropertyBool("isWorking", false);

	@SideOnly(Side.CLIENT)
	public static final PropertyFloat PROGRESS = new PropertyFloat("progress", 0);

	public ModuleEngine(String name, int complexity, double kineticModifier, int maxKineticEnergy, int materialPerTick) {
		super(name, complexity);
		this.kineticModifier = kineticModifier;
		this.maxKineticEnergy = maxKineticEnergy;
		this.materialPerWork = materialPerTick;
	}

	@Override
	public List<IModuleContentHandler> createHandlers(IModuleState state) {
		List<IModuleContentHandler> handlers = super.createHandlers(state);
		handlers.add(new ModuleKineticHandler(state, maxKineticEnergy, 100));
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
				kineticHandler.increaseKineticEnergy(kineticModifier * engineKineticModifier);
				needUpdate = true;
			}
		}else if(isWorking){
			if(kineticHandler.getKineticEnergyStored() > 0){
				kineticHandler.reduceKineticEnergy(kineticModifier * engineKineticModifier);
			}else{
				state.set(WORKING, false);
			}
			needUpdate = true;
		}

		if(needUpdate){
			PacketHandler.INSTANCE.sendToAll(new PacketModule(modular.getHandler(), state));
		}
	}

	protected abstract boolean canWork(IModuleState state);

	protected abstract boolean removeMaterial(IModuleState state);

	@SideOnly(Side.CLIENT)
	@Override
	public void updateClient(IModuleState state, int tickCount) {
		if (isWorking(state)) {
			addProgress(state, 0.025F);
			if (getProgress(state) > 1) {
				setProgress(state, 0);
			}
		}else{
			if(getProgress(state) > 0){
				if(getProgress(state) < 1){
					addProgress(state, 0.025F);
				}else{
					setProgress(state, 0);
				}
			}
		}
	}

	@Override
	public IModuleState createState(IModular modular, IModuleContainer container) {
		return super.createState(modular, container).register(WORKING);
	}

	@Override
	protected IModuleState createClientState(IModular modular, IModuleContainer container) {
		return super.createClientState(modular, container).register(PROGRESS);
	}

	@Override
	public EnumPosition getPosition(IModuleContainer container) {
		return EnumPosition.RIGHT;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public float getProgress(IModuleState state) {
		return state.get(PROGRESS);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void setProgress(IModuleState state, float progress) {
		state.set(PROGRESS, progress);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addProgress(IModuleState state, float progress) {
		state.set(PROGRESS, state.get(PROGRESS) + progress);
	}

	@Override
	public boolean isWorking(IModuleState state) {
		return state.get(WORKING);
	}

	protected void setIsWorking(IModuleState state, boolean isWorking) {
		state.set(WORKING, isWorking);
	}

	@Override
	public EnumModuleSize getSize() {
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
		return ModelHandler.getModelLocation(container, "windows",  getSize());
	}

}