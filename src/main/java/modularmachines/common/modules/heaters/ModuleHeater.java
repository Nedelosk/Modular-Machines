package modularmachines.common.modules.heaters;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modular.IModular;
import modularmachines.api.modular.handlers.IModularHandler;
import modularmachines.api.modular.handlers.IModularHandlerTileEntity;
import modularmachines.api.modules.EnumWallType;
import modularmachines.api.modules.IModule;
import modularmachines.api.modules.IModulePage;
import modularmachines.api.modules.IModuleProperties;
import modularmachines.api.modules.containers.IModuleColoredItem;
import modularmachines.api.modules.containers.IModuleContainer;
import modularmachines.api.modules.containers.IModuleItemContainer;
import modularmachines.api.modules.controller.IModuleController;
import modularmachines.api.modules.controller.ModuleControlled;
import modularmachines.api.modules.heaters.IModuleHeater;
import modularmachines.api.modules.models.IModelHandler;
import modularmachines.api.modules.models.ModelHandlerStatus;
import modularmachines.api.modules.models.ModuleModelLoader;
import modularmachines.api.modules.position.EnumModulePositions;
import modularmachines.api.modules.position.IModulePositioned;
import modularmachines.api.modules.position.IModulePostion;
import modularmachines.api.modules.properties.IModuleHeaterProperties;
import modularmachines.api.modules.state.IModuleState;
import modularmachines.common.modules.pages.ControllerPage;
import modularmachines.common.network.PacketHandler;
import modularmachines.common.network.packets.PacketSyncHeatBuffer;
import modularmachines.common.network.packets.PacketSyncModule;

public abstract class ModuleHeater extends ModuleControlled implements IModuleHeater, IModuleColoredItem, IModulePositioned {

	public ModuleHeater(String name) {
		super("heater." + name);
	}

	@Override
	public List<IModuleState> getUsedModules(IModuleState state) {
		return Collections.emptyList();
	}

	@Override
	protected IModulePage getControllerPage(IModuleState state) {
		return new ControllerPage(state);
	}

	@Override
	public double getMaxHeat(IModuleState state) {
		IModuleProperties properties = state.getContainer().getProperties();
		if (properties instanceof IModuleHeaterProperties) {
			return ((IModuleHeaterProperties) properties).getMaxHeat(state);
		}
		return 0;
	}

	@Override
	public int getHeatModifier(IModuleState state) {
		IModuleProperties properties = state.getContainer().getProperties();
		if (properties instanceof IModuleHeaterProperties) {
			return ((IModuleHeaterProperties) properties).getHeatModifier(state);
		}
		return 0;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public EnumWallType getWallType(IModuleState state) {
		return EnumWallType.NONE;
	}

	@Override
	public void updateServer(IModuleState state, int tickCount) {
		IModular modular = state.getModular();
		IModuleState<IModuleController> controller = modular.getModule(IModuleController.class);
		if (state.getModular().updateOnInterval(20) && (controller == null || controller.getModule() == null || controller.getModule().canWork(controller, state))) {
			boolean needUpdate = false;
			if (canAddHeat(state)) {
				modular.getHeatSource().increaseHeat(getMaxHeat(state), getHeatModifier(state));
				afterAddHeat(state);
				needUpdate = true;
			} else {
				needUpdate = updateFuel(state);
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
			PacketHandler.sendToNetwork(new PacketSyncHeatBuffer(handler), ((IModularHandlerTileEntity) handler).getPos(), (WorldServer) handler.getWorld());
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void updateClient(IModuleState<IModule> state, int tickCount) {
	}

	@Override
	public IModulePostion[] getValidPositions(IModuleContainer container) {
		return new IModulePostion[] { EnumModulePositions.SIDE };
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModelHandler createModelHandler(IModuleState state) {
		IModuleItemContainer container = state.getContainer().getItemContainer();
		ResourceLocation[] locations = new ResourceLocation[2];
		locations[0] = ModuleModelLoader.getModelLocation(getRegistryName().getResourceDomain(), container.getMaterial().getName(), "heaters", container.getSize(), true);
		locations[1] = ModuleModelLoader.getModelLocation(getRegistryName().getResourceDomain(), container.getMaterial().getName(), "heaters", container.getSize(), false);
		return new ModelHandlerStatus(locations);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Map<ResourceLocation, ResourceLocation> getModelLocations(IModuleItemContainer container) {
		Map<ResourceLocation, ResourceLocation> locations = new HashMap<>();
		locations.put(ModuleModelLoader.getModelLocation(getRegistryName().getResourceDomain(), container.getMaterial().getName(), "heaters", container.getSize(), true),
				ModuleModelLoader.getModelLocation(getRegistryName().getResourceDomain(), "default", "heaters", container.getSize(), true));
		locations.put(ModuleModelLoader.getModelLocation(getRegistryName().getResourceDomain(), container.getMaterial().getName(), "heaters", container.getSize(), false),
				ModuleModelLoader.getModelLocation(getRegistryName().getResourceDomain(), "default", "heaters", container.getSize(), false));
		return locations;
	}

	protected abstract boolean canAddHeat(IModuleState state);

	protected abstract boolean updateFuel(IModuleState state);

	protected abstract void afterAddHeat(IModuleState state);
}