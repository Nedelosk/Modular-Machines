package de.nedelosk.modularmachines.common.modules;

import java.util.ArrayList;
import java.util.List;

import de.nedelosk.modularmachines.api.Translator;
import de.nedelosk.modularmachines.api.energy.IHeatSource;
import de.nedelosk.modularmachines.api.modules.IModelInitHandler;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.IModuleCasing;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.handlers.IModuleContentHandler;
import de.nedelosk.modularmachines.api.modules.handlers.energy.ModuleHeatBuffer;
import de.nedelosk.modularmachines.api.modules.models.IModelHandler;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storaged.EnumModuleSize;
import de.nedelosk.modularmachines.api.modules.storaged.EnumPosition;
import de.nedelosk.modularmachines.api.modules.storaged.drives.heaters.IModuleHeater;
import de.nedelosk.modularmachines.client.modules.ModelHandlerCasing;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import de.nedelosk.modularmachines.common.network.packets.PacketModule;
import net.minecraft.util.ResourceLocation;
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
	public int getMaxHeat() {
		return maxHeat;
	}

	@Override
	public float getResistance(IModuleState state) {
		return resistance;
	}

	@Override
	public float getHardness(IModuleState state) {
		return hardness;
	}

	@Override
	public int getHarvestLevel(IModuleState state) {
		return harvestLevel;
	}

	@Override
	public String getHarvestTool(IModuleState state) {
		return harvestTool;
	}

	@Override
	public List<IModuleContentHandler> createContentHandlers(IModuleState state) {
		List<IModuleContentHandler> handlers = super.createContentHandlers(state);
		handlers.add(new ModuleHeatBuffer(state, maxHeat, 15F));
		return handlers;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModelHandler createModelHandler(IModuleState state) {
		return new ModelHandlerCasing(new ResourceLocation("modularmachines:module/casings/" + state.getContainer().getMaterial().getName()),
				new ResourceLocation("modularmachines:module/casings/" + state.getContainer().getMaterial().getName() + "_side_left"),
				new ResourceLocation("modularmachines:module/casings/" + state.getContainer().getMaterial().getName() + "_side_right"));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public List<IModelInitHandler> getInitModelHandlers(IModuleContainer container) {
		List<IModelInitHandler> handlers = new ArrayList<>();
		handlers.add(new ModelHandlerCasing(new ResourceLocation("modularmachines:module/casings/" + container.getMaterial().getName()),
				new ResourceLocation("modularmachines:module/casings/" + container.getMaterial().getName() + "_side_left"),
				new ResourceLocation("modularmachines:module/casings/" + container.getMaterial().getName() + "_side_right")));
		return handlers;
	}

	@Override
	public void updateServer(IModuleState<IModule> state, int tickCount) {
		if(state.getModular().updateOnInterval(10)){
			boolean oneHeaterWork = false;
			List<IModuleState<IModuleHeater>> heaters = state.getModular().getModules(IModuleHeater.class);
			for(IModuleState<IModuleHeater> heater : heaters){
				if(heater.getModule().isWorking(heater)){
					oneHeaterWork = true;
				}
			}
			IHeatSource source = getHeatSource(state);
			if(!oneHeaterWork){
				source.reduceHeat(2);
				PacketHandler.INSTANCE.sendToAll(new PacketModule(state.getModular().getHandler(), state));
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void updateClient(IModuleState<IModule> state, int tickCount) {
	}

	@Override
	public IHeatSource getHeatSource(IModuleState state) {
		return (IHeatSource) state.getContentHandler(ModuleHeatBuffer.class);
	}

	@Override
	public void addTooltip(List<String> tooltip, IModuleContainer container) {
		tooltip.add(Translator.translateToLocal("mm.module.tooltip.complexity") + complexity);
		tooltip.add(Translator.translateToLocal("mm.module.tooltip.position.can.use") + Translator.translateToLocal("module.storage." + getPosition(container).getName() + ".name"));
	}

	@Override
	public EnumPosition getPosition(IModuleContainer container) {
		return EnumPosition.INTERNAL;
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
