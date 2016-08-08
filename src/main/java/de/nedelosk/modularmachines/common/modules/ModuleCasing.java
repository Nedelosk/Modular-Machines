package de.nedelosk.modularmachines.common.modules;

import java.util.ArrayList;
import java.util.List;

import de.nedelosk.modularmachines.api.energy.IHeatSource;
import de.nedelosk.modularmachines.api.modules.IModelInitHandler;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.IModuleCasing;
import de.nedelosk.modularmachines.api.modules.handlers.BlockModificator;
import de.nedelosk.modularmachines.api.modules.handlers.IModuleContentHandler;
import de.nedelosk.modularmachines.api.modules.handlers.energy.ModuleHeatBuffer;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.models.IModelHandler;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storaged.EnumModuleSize;
import de.nedelosk.modularmachines.api.modules.storaged.EnumPosition;
import de.nedelosk.modularmachines.api.modules.storaged.drives.heaters.IModuleHeater;
import de.nedelosk.modularmachines.client.modules.ModelHandlerCasing;
import de.nedelosk.modularmachines.common.items.ItemModule;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import de.nedelosk.modularmachines.common.network.packets.PacketModule;
import de.nedelosk.modularmachines.common.utils.Translator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModuleCasing extends Module implements IModuleCasing {

	private final int allowedComplexity;
	private final int maxHeat;
	private final float resistance;
	private final float hardness;
	private final int harvestLevel;
	private final String harvestTool;

	public ModuleCasing(int complexity, int allowedComplexity, int maxHeat, float resistance, float hardness, String harvestTool, int harvestLevel) {
		super("casing", complexity);
		this.maxHeat = maxHeat;
		this.allowedComplexity = allowedComplexity;
		this.resistance = resistance;
		this.hardness = hardness;
		this.harvestTool = harvestTool;
		this.harvestLevel = harvestLevel;
	}

	@Override
	public List<IModuleContentHandler> createHandlers(IModuleState state) {
		List<IModuleContentHandler> handlers = super.createHandlers(state);
		handlers.add(new BlockModificator(state, maxHeat, resistance, hardness, harvestTool, harvestLevel));
		return handlers;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModelHandler createModelHandler(IModuleState state) {
		return new ModelHandlerCasing(state.getContainer());
	}

	@SideOnly(Side.CLIENT)
	@Override
	public List<IModelInitHandler> getInitModelHandlers(IModuleContainer container) {
		List<IModelInitHandler> handlers = new ArrayList<>();
		handlers.add(new ModelHandlerCasing(container));
		return handlers;
	}

	@Override
	public void addTooltip(List<String> tooltip, IModuleContainer container) {
		if(!(container.getItemStack().getItem() instanceof ItemModule)){
			tooltip.add(Translator.translateToLocal("mm.module.tooltip.name") + container.getDisplayName());
		}
		tooltip.add(Translator.translateToLocal("mm.module.tooltip.complexity") + complexity);
		tooltip.add(Translator.translateToLocal("mm.module.tooltip.position.can.use") + Translator.translateToLocal("module.storage." + EnumPosition.INTERNAL.getName() + ".name"));
	}

	@Override
	public EnumPosition getPosition(IModuleContainer container) {
		return null;
	}

	@Override
	public EnumModuleSize getSize() {
		return EnumModuleSize.LARGE;
	}

	@Override
	public int getAllowedComplexity(IModuleState state) {
		return allowedComplexity;
	}

	@Override
	public EnumPosition getCurrentPosition(IModuleState state) {
		return EnumPosition.INTERNAL;
	}

	@Override
	public boolean canUseFor(EnumPosition position, IModuleContainer container) {
		return position == EnumPosition.INTERNAL;
	}
}