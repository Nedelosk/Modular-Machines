package de.nedelosk.modularmachines.common.modules.heater;

import de.nedelosk.modularmachines.api.modules.EnumModuleSize;
import de.nedelosk.modularmachines.api.modules.EnumWallType;
import de.nedelosk.modularmachines.api.modules.IModuleColored;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.IModuleModelHandler;
import de.nedelosk.modularmachines.api.modules.IModuleState;
import de.nedelosk.modularmachines.api.modules.heater.IModuleHeater;
import de.nedelosk.modularmachines.client.modules.ModelHandlerInit;
import de.nedelosk.modularmachines.common.modules.ModuleStoraged;
import net.minecraft.util.ResourceLocation;
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

	@SideOnly(Side.CLIENT)
	@Override
	public IModuleModelHandler getInitModelHandler(IModuleContainer container) {
		ResourceLocation[] locations = new ResourceLocation[2];
		locations[0] = new ResourceLocation("modularmachines:module/heaters/" + container.getMaterial().getName() + "_" + size.getName() + "_off");
		locations[1] = new ResourceLocation("modularmachines:module/heaters/" + container.getMaterial().getName() + "_" + size.getName() + "_on");
		return new ModelHandlerInit(locations);
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