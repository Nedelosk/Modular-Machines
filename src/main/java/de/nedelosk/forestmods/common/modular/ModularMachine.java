package de.nedelosk.forestmods.common.modular;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestmods.api.modular.renderer.IRenderState;
import de.nedelosk.forestmods.api.modular.renderer.ISimpleRenderer;
import de.nedelosk.forestmods.client.render.modules.ModularRenderer;
import de.nedelosk.forestmods.common.blocks.tile.TileModularMachine;
import net.minecraft.nbt.NBTTagCompound;

public class ModularMachine extends Modular {

	public ModularMachine() {
		super();
	}

	public ModularMachine(NBTTagCompound nbt, TileModularMachine machine) {
		super(nbt, machine);
	}

	public ModularMachine(NBTTagCompound nbt) {
		super(nbt);
	}

	@Override
	public String getName() {
		return "modular.machine";
	}

	@SideOnly(Side.CLIENT)
	@Override
	public ISimpleRenderer getRenderer(IRenderState state) {
		return new ModularRenderer();
	}
}