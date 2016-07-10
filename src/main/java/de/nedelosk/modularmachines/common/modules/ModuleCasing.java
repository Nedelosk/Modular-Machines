package de.nedelosk.modularmachines.common.modules;

import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modules.IModuleCasing;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.IModuleModelHandler;
import de.nedelosk.modularmachines.api.modules.IModuleState;
import de.nedelosk.modularmachines.api.property.PropertyInteger;
import de.nedelosk.modularmachines.client.modules.ModelHandlerDefault;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModuleCasing extends Module implements IModuleCasing {

	public static final PropertyInteger HEAT = new PropertyInteger("heat", 0);
	private final int maxHeat;
	private final float resistance;
	private final float hardness;
	private final int harvestLevel;
	private final String harvestTool;

	public ModuleCasing(int complexity, int maxHeat, float resistance, float hardness, String harvestTool, int harvestLevel) {
		super("casing", complexity);
		this.maxHeat = maxHeat;
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
	public IModuleState createState(IModular modular, IModuleContainer container) {
		return super.createState(modular, container).register(HEAT);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModuleModelHandler getInitModelHandler(IModuleContainer container) {
		return new ModelHandlerDefault(new ResourceLocation("modularmachines:module/casings/" + container.getMaterial().getName()));
	}

	@Override
	public int getHeat(IModuleState state) {
		return state.get(HEAT);
	}

	@Override
	public void addHeat(IModuleState state, int heat) {
		state.set(HEAT, state.get(HEAT) + heat);
	}

	@Override
	public void setHeat(IModuleState state, int heat) {
		state.set(HEAT, heat);
	}
}
