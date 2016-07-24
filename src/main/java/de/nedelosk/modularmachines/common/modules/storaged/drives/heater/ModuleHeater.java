package de.nedelosk.modularmachines.common.modules.storaged.drives.heater;

import de.nedelosk.modularmachines.api.energy.IHeatSource;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.ModularUtils;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.IModuleCasing;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.items.IModuleColored;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storaged.EnumModuleSize;
import de.nedelosk.modularmachines.api.modules.storaged.EnumPosition;
import de.nedelosk.modularmachines.api.modules.storaged.EnumWallType;
import de.nedelosk.modularmachines.api.modules.storaged.drives.heaters.IModuleHeater;
import de.nedelosk.modularmachines.common.modules.Module;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import de.nedelosk.modularmachines.common.network.packets.PacketModule;
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
		IModuleState<IModuleCasing> casingState = ModularUtils.getCasing(modular);
		IHeatSource heatBuffer = casingState.getModule().getHeatSource(casingState);
		if(state.getModular().updateOnInterval(20)){
			boolean needUpdate = false;
			if (canAddHeat(state)) {
				heatBuffer.increaseHeat(heatModifier);
				afterAddHeat(state);
				needUpdate = true;
			} else {
				needUpdate = updateFuel(state);
			}
			if(needUpdate){
				PacketHandler.INSTANCE.sendToAll(new PacketModule(modular.getHandler(), state));
				PacketHandler.INSTANCE.sendToAll(new PacketModule(modular.getHandler(), casingState));
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

	protected abstract boolean canAddHeat(IModuleState state);

	protected abstract boolean updateFuel(IModuleState state);

	protected abstract void afterAddHeat(IModuleState state);
}