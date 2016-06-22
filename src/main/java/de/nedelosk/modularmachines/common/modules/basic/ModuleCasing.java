package de.nedelosk.modularmachines.common.modules.basic;

import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.assembler.IAssemblerSlot;
import de.nedelosk.modularmachines.api.modular.renderer.IRenderState;
import de.nedelosk.modularmachines.api.modular.renderer.ISimpleRenderer;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.ModuleManager;
import de.nedelosk.modularmachines.api.modules.casing.IModuleCasing;
import de.nedelosk.modularmachines.api.modules.handlers.IModulePage;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.state.PropertyInteger;
import de.nedelosk.modularmachines.client.render.modules.CasingRenderer;
import de.nedelosk.modularmachines.common.modules.Module;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModuleCasing extends Module implements IModuleCasing {

	public static final PropertyInteger HEAT = new PropertyInteger("heat");
	private final int maxHeat;
	private final int resistance;
	private final int hardness;
	private final int controllers;

	public ModuleCasing(int maxHeat, int resistance, int hardness, int controllers) {
		super();
		this.maxHeat = maxHeat;
		this.resistance = resistance;
		this.hardness = hardness;
		this.controllers = controllers;
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
	public int getResistance(IModuleState state) {
		return resistance;
	}

	@Override
	public int getHardness(IModuleState state) {
		return hardness;
	}
	
	@Override
	public IModuleState createState(IModular modular, IModuleContainer container) {
		return super.createState(modular, container).add(HEAT, 0);
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
