package de.nedelosk.modularmachines.common.modules;

import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.renderer.IRenderState;
import de.nedelosk.modularmachines.api.modular.renderer.ISimpleRenderer;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.IModuleModelHandler;
import de.nedelosk.modularmachines.api.modules.casing.IModuleCasing;
import de.nedelosk.modularmachines.api.modules.handlers.IModulePage;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.state.PropertyInteger;
import de.nedelosk.modularmachines.client.modules.ModelHandlerCasing;
import de.nedelosk.modularmachines.client.render.modules.CasingRenderer;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
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
	private final int controllers;

	public ModuleCasing(int maxHeat, float resistance, float hardness, int controllers, String harvestTool, int harvestLevel) {
		super();
		this.maxHeat = maxHeat;
		this.resistance = resistance;
		this.hardness = hardness;
		this.controllers = controllers;
		this.harvestTool = harvestTool;
		this.harvestLevel = harvestLevel;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public ISimpleRenderer getRenderer(IRenderState state) {
		return new CasingRenderer(state.getModuleState().getContainer());
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
	public IModuleModelHandler getModelHandler(IModuleState state) {
		return new ModelHandlerCasing(new ResourceLocation("modularmachines:modul/casings/casing"), new ResourceLocation("modularmachines:modul/casings/right_storages/brick"), new ResourceLocation("modularmachines:modul/casings/left_storages/brick"));
	}

	@Override
	public IModulePage[] createPages(IModuleState state) {
		return null;
	}

	@Override
	public int getHeat(IModuleState state) {
		return (int) state.get(HEAT);
	}

	@Override
	public void addHeat(IModuleState state, int heat) {
		state.add(HEAT, (int)state.get(HEAT) + heat);
	}

	@Override
	public void setHeat(IModuleState state, int heat) {
		state.add(HEAT, heat);
	}

	@Override
	public int getControllers(IModuleState state) {
		return controllers;
	}

	@Override
	public boolean canAssembleCasing(IModuleState state) {
		return true;
	}
}
