package de.nedelosk.modularmachines.common.modules.heater;

import de.nedelosk.modularmachines.api.modules.EnumModuleSize;
import de.nedelosk.modularmachines.api.modules.EnumWallType;
import de.nedelosk.modularmachines.api.modules.IModuleColored;
import de.nedelosk.modularmachines.api.modules.IModuleState;
import de.nedelosk.modularmachines.api.modules.heater.IModuleHeater;
import de.nedelosk.modularmachines.common.modules.ModuleStoraged;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ModuleHeater extends ModuleStoraged implements IModuleHeater, IModuleColored {

	protected final int maxHeat;
	protected final EnumModuleSize size;

	public ModuleHeater(String name, int complexity, int maxHeat, EnumModuleSize size) {
		super(name, complexity);
		this.maxHeat = maxHeat;
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
}