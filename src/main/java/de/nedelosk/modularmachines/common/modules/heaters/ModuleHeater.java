package de.nedelosk.modularmachines.common.modules.heaters;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandlerTileEntity;
import de.nedelosk.modularmachines.api.modules.EnumWallType;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.IModulePage;
import de.nedelosk.modularmachines.api.modules.IModuleProperties;
import de.nedelosk.modularmachines.api.modules.containers.IModuleColoredItem;
import de.nedelosk.modularmachines.api.modules.containers.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.containers.IModuleItemContainer;
import de.nedelosk.modularmachines.api.modules.controller.IModuleController;
import de.nedelosk.modularmachines.api.modules.controller.ModuleControlled;
import de.nedelosk.modularmachines.api.modules.heaters.IModuleHeater;
import de.nedelosk.modularmachines.api.modules.models.IModelHandler;
import de.nedelosk.modularmachines.api.modules.models.ModelHandlerStatus;
import de.nedelosk.modularmachines.api.modules.models.ModuleModelLoader;
import de.nedelosk.modularmachines.api.modules.position.EnumModulePositions;
import de.nedelosk.modularmachines.api.modules.position.IModulePositioned;
import de.nedelosk.modularmachines.api.modules.position.IModulePostion;
import de.nedelosk.modularmachines.api.modules.properties.IModuleHeaterProperties;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.common.modules.pages.ControllerPage;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import de.nedelosk.modularmachines.common.network.packets.PacketSyncHeatBuffer;
import de.nedelosk.modularmachines.common.network.packets.PacketSyncModule;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ModuleHeater extends ModuleControlled implements IModuleHeater, IModuleColoredItem, IModulePositioned {

	public ModuleHeater(String name) {
		super("heater." + name);
	}

	@Override
	public List<IModuleState> getUsedModules(IModuleState state){
		return Collections.emptyList();
	}

	@Override
	protected IModulePage getControllerPage(IModuleState state) {
		return new ControllerPage(state);
	}

	@Override
	public double getMaxHeat(IModuleState state) {
		IModuleProperties properties = state.getContainer().getProperties();
		if(properties instanceof IModuleHeaterProperties){
			return ((IModuleHeaterProperties) properties).getMaxHeat(state);
		}
		return 0;
	}

	@Override
	public int getHeatModifier(IModuleState state) {
		IModuleProperties properties = state.getContainer().getProperties();
		if(properties instanceof IModuleHeaterProperties){
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
		IModuleState<IModuleController> controller =  modular.getModule(IModuleController.class);
		if(state.getModular().updateOnInterval(20) && (controller == null || controller.getModule() == null || controller.getModule().canWork(controller, state))){
			boolean needUpdate = false;
			if (canAddHeat(state)) {
				modular.getHeatSource().increaseHeat(getMaxHeat(state), getHeatModifier(state));
				afterAddHeat(state);
				needUpdate = true;
			} else {
				needUpdate = updateFuel(state);
			}
			if(needUpdate){
				sendModuleUpdate(state);
			}
		}
	}

	@Override
	public void sendModuleUpdate(IModuleState state) {
		IModularHandler handler = state.getModular().getHandler();
		if(handler instanceof IModularHandlerTileEntity){
			PacketHandler.sendToNetwork(new PacketSyncModule(state), ((IModularHandlerTileEntity)handler).getPos(), (WorldServer) handler.getWorld());
			PacketHandler.sendToNetwork(new PacketSyncHeatBuffer(handler), ((IModularHandlerTileEntity)handler).getPos(), (WorldServer) handler.getWorld());
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void updateClient(IModuleState<IModule> state, int tickCount) {
	}

	@Override
	public IModulePostion[] getValidPositions(IModuleContainer container) {
		return new IModulePostion[]{EnumModulePositions.SIDE};
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
		locations.put(ModuleModelLoader.getModelLocation(getRegistryName().getResourceDomain(), container.getMaterial().getName(), "heaters", container.getSize(), true), ModuleModelLoader.getModelLocation(getRegistryName().getResourceDomain(), "default", "heaters", container.getSize(), true));
		locations.put(ModuleModelLoader.getModelLocation(getRegistryName().getResourceDomain(), container.getMaterial().getName(), "heaters", container.getSize(), false), ModuleModelLoader.getModelLocation(getRegistryName().getResourceDomain(), "default", "heaters", container.getSize(), false));
		return locations;
	}

	protected abstract boolean canAddHeat(IModuleState state);

	protected abstract boolean updateFuel(IModuleState state);

	protected abstract void afterAddHeat(IModuleState state);
}