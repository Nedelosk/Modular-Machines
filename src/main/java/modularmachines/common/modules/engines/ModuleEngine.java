package modularmachines.common.modules.engines;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.energy.IKineticSource;
import modularmachines.api.modular.IModular;
import modularmachines.api.modular.handlers.IModularHandler;
import modularmachines.api.modular.handlers.IModularHandlerTileEntity;
import modularmachines.api.modules.EnumWallType;
import modularmachines.api.modules.IModuleEngine;
import modularmachines.api.modules.IModulePage;
import modularmachines.api.modules.IModuleProperties;
import modularmachines.api.modules.containers.IModuleContainer;
import modularmachines.api.modules.containers.IModuleItemContainer;
import modularmachines.api.modules.containers.IModuleProvider;
import modularmachines.api.modules.controller.IModuleController;
import modularmachines.api.modules.controller.ModuleControlled;
import modularmachines.api.modules.handlers.IModuleContentHandler;
import modularmachines.api.modules.handlers.energy.ModuleKineticHandler;
import modularmachines.api.modules.models.IModelHandler;
import modularmachines.api.modules.models.ModelHandlerDefault;
import modularmachines.api.modules.models.ModuleModelLoader;
import modularmachines.api.modules.position.EnumModulePositions;
import modularmachines.api.modules.position.IModulePositioned;
import modularmachines.api.modules.position.IModulePostion;
import modularmachines.api.modules.properties.IModuleKineticProperties;
import modularmachines.api.modules.state.IModuleState;
import modularmachines.api.property.PropertyBool;
import modularmachines.common.modules.pages.ControllerPage;
import modularmachines.common.network.PacketHandler;
import modularmachines.common.network.packets.PacketSyncModule;

public abstract class ModuleEngine extends ModuleControlled implements IModuleEngine, IModulePositioned {

	public static final PropertyBool WORKING = new PropertyBool("isWorking", false);

	public ModuleEngine(String name) {
		super("engine." + name);
	}

	@Override
	protected IModulePage getControllerPage(IModuleState state) {
		return new ControllerPage(state);
	}

	@Override
	public int getMaxKineticEnergy(IModuleState state) {
		IModuleProperties properties = state.getContainer().getProperties();
		if (properties instanceof IModuleKineticProperties) {
			return ((IModuleKineticProperties) properties).getMaxKineticEnergy(state);
		}
		return 0;
	}

	@Override
	public int getMaterialPerWork(IModuleState state) {
		IModuleProperties properties = state.getContainer().getProperties();
		if (properties instanceof IModuleKineticProperties) {
			return ((IModuleKineticProperties) properties).getMaterialPerWork(state);
		}
		return 0;
	}

	@Override
	public double getKineticModifier(IModuleState state) {
		IModuleProperties properties = state.getContainer().getProperties();
		if (properties instanceof IModuleKineticProperties) {
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
		return state.getContentHandler(ModuleKineticHandler.class);
	}

	@Override
	public void updateServer(IModuleState state, int tickCount) {
		IModular modular = state.getModular();
		IModuleState<IModuleController> controller = modular.getModule(IModuleController.class);
		if (state.getModular().updateOnInterval(2) && (controller == null || controller.getModule() == null || controller.getModule().canWork(controller, state))) {
			boolean isWorking = state.get(WORKING);
			ModuleKineticHandler kineticHandler = state.getContentHandler(ModuleKineticHandler.class);
			boolean needUpdate = false;
			if (canWork(state)) {
				if (removeMaterial(state)) {
					if (!isWorking) {
						state.set(WORKING, true);
					}
					kineticHandler.increaseKineticEnergy(getKineticModifier(state) * 2);
					needUpdate = true;
				}
			} else if (isWorking) {
				if (kineticHandler.getStored() > 0) {
					kineticHandler.reduceKineticEnergy(getKineticModifier(state) * 2);
				} else {
					state.set(WORKING, false);
				}
				needUpdate = true;
			}
			if (needUpdate) {
				sendModuleUpdate(state);
			}
		}
	}

	@Override
	public void sendModuleUpdate(IModuleState state) {
		IModularHandler handler = state.getModular().getHandler();
		if (handler instanceof IModularHandlerTileEntity) {
			PacketHandler.sendToNetwork(new PacketSyncModule(state), ((IModularHandlerTileEntity) handler).getPos(), (WorldServer) handler.getWorld());
		}
	}

	protected abstract boolean canWork(IModuleState state);

	protected abstract boolean removeMaterial(IModuleState state);

	@SideOnly(Side.CLIENT)
	@Override
	public void updateClient(IModuleState state, int tickCount) {
	}

	@Override
	public IModuleState createState(IModuleProvider provider, IModuleContainer container) {
		return super.createState(provider, container).register(WORKING);
	}

	@Override
	public IModulePostion[] getValidPositions(IModuleContainer container) {
		return new IModulePostion[] { EnumModulePositions.SIDE };
	}

	@Override
	public boolean isWorking(IModuleState state) {
		return state.get(WORKING);
	}

	protected void setIsWorking(IModuleState state, boolean isWorking) {
		state.set(WORKING, isWorking);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModelHandler createModelHandler(IModuleState state) {
		IModuleItemContainer container = state.getContainer().getItemContainer();
		return new ModelHandlerDefault(ModuleModelLoader.getModelLocation(getRegistryName().getResourceDomain(), container.getMaterial().getName(), "engines", container.getSize()));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Map<ResourceLocation, ResourceLocation> getModelLocations(IModuleItemContainer container) {
		Map<ResourceLocation, ResourceLocation> locations = new HashMap<>();
		locations.put(ModuleModelLoader.getModelLocation(getRegistryName().getResourceDomain(), container.getMaterial().getName(), "engines", container.getSize()),
				ModuleModelLoader.getModelLocation(getRegistryName().getResourceDomain(), "default", "engines", container.getSize()));
		return locations;
	}

	@Override
	public EnumWallType getWallType(IModuleState state) {
		return EnumWallType.WINDOW;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public ResourceLocation getWindowLocation(IModuleItemContainer container) {
		return ModuleModelLoader.getModelLocation(getRegistryName().getResourceDomain(), container.getMaterial().getName(), "windows", container.getSize());
	}
}