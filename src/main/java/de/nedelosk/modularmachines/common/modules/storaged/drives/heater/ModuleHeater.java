package de.nedelosk.modularmachines.common.modules.storaged.drives.heater;

import java.util.ArrayList;
import java.util.List;

import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modules.IModelInitHandler;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.items.IModuleColored;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.models.IModelHandler;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storaged.EnumModuleSize;
import de.nedelosk.modularmachines.api.modules.storaged.EnumPosition;
import de.nedelosk.modularmachines.api.modules.storaged.EnumWallType;
import de.nedelosk.modularmachines.api.modules.storaged.drives.heaters.IModuleHeater;
import de.nedelosk.modularmachines.client.modules.ModelHandler;
import de.nedelosk.modularmachines.client.modules.ModelHandlerStatus;
import de.nedelosk.modularmachines.common.modules.Module;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import de.nedelosk.modularmachines.common.network.packets.PacketSyncModule;
import de.nedelosk.modularmachines.common.network.packets.PacketSyncHeatBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ModuleHeater extends Module implements IModuleHeater, IModuleColored {

	protected final int maxHeat;
	protected final int heatModifier;
	protected final EnumModuleSize size;

	public ModuleHeater(String name, int complexity, int maxHeat, int heatModifier, EnumModuleSize size) {
		super(name, complexity);
		this.maxHeat = maxHeat;
		this.heatModifier = heatModifier;
		this.size = size;
	}

	@Override
	public int getMaxHeat() {
		return maxHeat;
	}

	@Override
	public EnumModuleSize getSize() {
		return size;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public EnumWallType getWallType(IModuleState state) {
		return EnumWallType.NONE;
	}

	@Override
	public void updateServer(IModuleState state, int tickCount) {
		IModular modular = state.getModular();
		if(state.getModular().updateOnInterval(20)){
			boolean needUpdate = false;
			if (canAddHeat(state)) {
				modular.getHeatSource().increaseHeat(heatModifier);
				afterAddHeat(state);
				needUpdate = true;
			} else {
				needUpdate = updateFuel(state);
			}
			if(needUpdate){
				PacketHandler.INSTANCE.sendToAll(new PacketSyncModule(modular.getHandler(), state));
				PacketHandler.INSTANCE.sendToAll(new PacketSyncHeatBuffer(modular.getHandler()));
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void updateClient(IModuleState<IModule> state, int tickCount) {
	}

	@Override
	public EnumPosition getPosition(IModuleContainer container) {
		return EnumPosition.RIGHT;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModelHandler createModelHandler(IModuleState state) {
		ResourceLocation[] locs = new ResourceLocation[]{
				ModelHandler.getModelLocation(state.getContainer(), "heaters", true),
				ModelHandler.getModelLocation(state.getContainer(), "heaters", false)
		};
		return new ModelHandlerStatus("heaters", state.getContainer(), locs);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public List<IModelInitHandler> getInitModelHandlers(IModuleContainer container) {
		List handlers = new ArrayList<>();
		ResourceLocation[] locs = new ResourceLocation[]{
				ModelHandler.getModelLocation(container, "heaters", true),
				ModelHandler.getModelLocation(container, "heaters", false)
		};
		handlers.add(new ModelHandlerStatus("heaters", container, locs));
		return handlers;
	}

	protected abstract boolean canAddHeat(IModuleState state);

	protected abstract boolean updateFuel(IModuleState state);

	protected abstract void afterAddHeat(IModuleState state);
}