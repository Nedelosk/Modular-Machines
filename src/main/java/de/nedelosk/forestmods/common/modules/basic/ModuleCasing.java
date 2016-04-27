package de.nedelosk.forestmods.common.modules.basic;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestmods.client.render.modules.CasingRenderer;
import de.nedelosk.forestmods.common.modules.Module;
import de.nedelosk.forestmods.library.modular.IModular;
import de.nedelosk.forestmods.library.modular.renderer.IRenderState;
import de.nedelosk.forestmods.library.modular.renderer.ISimpleRenderer;
import de.nedelosk.forestmods.library.modules.IModuleContainer;
import de.nedelosk.forestmods.library.modules.ModuleManager;
import de.nedelosk.forestmods.library.modules.casing.IModuleCasing;
import de.nedelosk.forestmods.library.modules.handlers.IModulePage;
import net.minecraft.nbt.NBTTagCompound;

public class ModuleCasing extends Module implements IModuleCasing {

	private int heat;
	private final int maxHeat;
	private final int resistance;
	private final int hardness;

	public ModuleCasing(IModular modular, IModuleContainer container, int maxHeat, int resistance, int hardness) {
		super(modular, container);
		this.maxHeat = maxHeat;
		this.resistance = resistance;
		this.hardness = hardness;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public ISimpleRenderer getRenderer(IRenderState state) {
		return new CasingRenderer(container);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt, IModular modular) {
		super.writeToNBT(nbt, modular);

		nbt.setInteger("Heat", heat);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular) {
		super.readFromNBT(nbt, modular);

		heat = nbt.getInteger("Heat");
	}

	@Override
	protected String[] getDisabledHandlers() {
		return new String[] { ModuleManager.guiType, ModuleManager.inventoryType, ModuleManager.tankType };
	}

	@Override
	public int getMaxHeat() {
		return maxHeat;
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

	@Override
	public int getHeat() {
		return heat;
	}

	@Override
	public void addHeat(int heat) {
		this.heat += heat;
	}

	@Override
	public void setHeat(int heat) {
		this.heat = heat;
	}
}
