package de.nedelosk.modularmachines.common.modules.storaged.drives.turbines;

import java.util.ArrayList;
import java.util.List;

import de.nedelosk.modularmachines.api.energy.IKineticSource;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modules.IModelInitHandler;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.handlers.IModuleContentHandler;
import de.nedelosk.modularmachines.api.modules.handlers.energy.ModuleKineticHandler;
import de.nedelosk.modularmachines.api.modules.models.IModelHandler;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storaged.EnumModuleSize;
import de.nedelosk.modularmachines.api.modules.storaged.EnumPosition;
import de.nedelosk.modularmachines.api.modules.storaged.EnumWallType;
import de.nedelosk.modularmachines.api.modules.storaged.drives.IModuleTurbine;
import de.nedelosk.modularmachines.api.property.PropertyBool;
import de.nedelosk.modularmachines.client.modules.ModelHandlerDefault;
import de.nedelosk.modularmachines.common.config.Config;
import de.nedelosk.modularmachines.common.modules.Module;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import de.nedelosk.modularmachines.common.network.packets.PacketModule;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ModuleTurbine extends Module implements IModuleTurbine{

	protected final EnumModuleSize size;
	protected final double kineticModifier;
	protected final int maxKineticEnergy;
	protected final int steamPerWork;

	public static final PropertyBool WORKING = new PropertyBool("isWorking", false);
	protected static final float turgineKineticModifier = Config.turbineKineticOutput;

	public ModuleTurbine(String name, int complexity, EnumModuleSize size, double kineticModifier, int maxKineticEnergy, int steamPerWork) {
		super(name, complexity);

		this.size = size;
		this.kineticModifier = kineticModifier;
		this.maxKineticEnergy = maxKineticEnergy;
		this.steamPerWork = steamPerWork;
	}

	@Override
	public EnumPosition getPosition(IModuleContainer container) {
		return EnumPosition.RIGHT;
	}

	@Override
	public EnumModuleSize getSize() {
		return size;
	}

	@Override
	public EnumWallType getWallType(IModuleState state) {
		return EnumWallType.WINDOW;
	}

	@Override
	public List<IModuleContentHandler> createContentHandlers(IModuleState state) {
		List<IModuleContentHandler> handlers = super.createContentHandlers(state);
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
				kineticHandler.increaseKineticEnergy(kineticModifier * turgineKineticModifier);
				needUpdate = true;
			}
		}else if(isWorking){
			if(kineticHandler.getKineticEnergyStored() > 0){
				kineticHandler.reduceKineticEnergy(kineticModifier * turgineKineticModifier);
			}else{
				state.set(WORKING, false);
			}
			needUpdate = true;
		}

		if(needUpdate){
			PacketHandler.INSTANCE.sendToAll(new PacketModule(modular.getHandler(), state));
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
		return new ModelHandlerDefault(new ResourceLocation("modularmachines:module/turbines/" + state.getContainer().getMaterial().getName()));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public List<IModelInitHandler> getInitModelHandlers(IModuleContainer container) {
		List<IModelInitHandler> handlers = new ArrayList<>();
		handlers.add(new ModelHandlerDefault(new ResourceLocation("modularmachines:module/turbines/" + container.getMaterial().getName())));
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
