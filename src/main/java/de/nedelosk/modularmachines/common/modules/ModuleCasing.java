package de.nedelosk.modularmachines.common.modules;

import java.util.ArrayList;
import java.util.List;

import de.nedelosk.modularmachines.api.modules.IModelInitHandler;
import de.nedelosk.modularmachines.api.modules.IModuleCasing;
import de.nedelosk.modularmachines.api.modules.handlers.BlockModificator;
import de.nedelosk.modularmachines.api.modules.handlers.IModuleContentHandler;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.models.IModelHandler;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storaged.EnumModuleSize;
import de.nedelosk.modularmachines.api.modules.storaged.EnumStoragePosition;
import de.nedelosk.modularmachines.client.modules.ModelHandlerCasing;
import de.nedelosk.modularmachines.common.utils.Translator;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModuleCasing extends Module implements IModuleCasing {

	private final int complexity;
	private final int allowedComplexity;
	private final int maxHeat;
	private final float resistance;
	private final float hardness;
	private final int harvestLevel;
	private final String harvestTool;

	public ModuleCasing(int complexity, int allowedComplexity, int maxHeat, float resistance, float hardness, String harvestTool, int harvestLevel) {
		super("casing");
		this.complexity = complexity;
		this.maxHeat = maxHeat;
		this.allowedComplexity = allowedComplexity;
		this.resistance = resistance;
		this.hardness = hardness;
		this.harvestTool = harvestTool;
		this.harvestLevel = harvestLevel;
	}

	@Override
	public int getComplexity(IModuleContainer container) {
		return complexity;
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
	protected boolean showSize(IModuleContainer container) {
		return false;
	}

	@Override
	public void addTooltip(List<String> tooltip, ItemStack stack, IModuleContainer container) {
		tooltip.add(Translator.translateToLocal("mm.module.tooltip.storage.position") + EnumStoragePosition.INTERNAL.getLocName());
		super.addTooltip(tooltip, stack, container);
	}

	@Override
	public EnumStoragePosition getPosition(IModuleContainer container) {
		return null;
	}

	@Override
	public EnumModuleSize getSize(IModuleContainer container) {
		return EnumModuleSize.LARGE;
	}

	@Override
	public int getAllowedComplexity(IModuleContainer container) {
		return allowedComplexity;
	}

	@Override
	public EnumStoragePosition getCurrentPosition(IModuleState state) {
		return EnumStoragePosition.INTERNAL;
	}

	@Override
	public boolean canUseFor(EnumStoragePosition position, IModuleContainer container) {
		return position == EnumStoragePosition.INTERNAL;
	}
}