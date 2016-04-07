package de.nedelosk.forestmods.common.modules.basic;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestmods.api.modular.renderer.IRenderState;
import de.nedelosk.forestmods.api.modular.renderer.ISimpleRenderer;
import de.nedelosk.forestmods.api.modules.casing.IModuleCasing;
import de.nedelosk.forestmods.api.modules.handlers.IModulePage;
import de.nedelosk.forestmods.api.utils.ModuleManager;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.client.render.modules.CasingRenderer;
import de.nedelosk.forestmods.common.modules.Module;

public class ModuleCasing extends Module implements IModuleCasing {

	private final int heat;
	private final int resistance;
	private final int hardness;

	public ModuleCasing(String name, int heat, int resistance, int hardness) {
		super(name);
		this.heat = heat;
		this.resistance = resistance;
		this.hardness = hardness;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public ISimpleRenderer getRenderer(ModuleStack moduleStack, IRenderState state) {
		return new CasingRenderer(moduleStack);
	}

	@Override
	protected String[] getDisabledHandlers() {
		return new String[] { ModuleManager.guiType, ModuleManager.inventoryType, ModuleManager.tankType };
	}

	@Override
	public int getMaxHeat() {
		return heat;
	}

	@Override
	public int getResistance() {
		return resistance;
	}

	@Override
	public int getHardness() {
		return hardness;
	}

	@Override
	protected IModulePage[] createPages() {
		return null;
	}
}
