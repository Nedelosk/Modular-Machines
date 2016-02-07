package de.nedelosk.forestmods.common.modular;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestmods.api.client.IModularRenderer;
import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modular.tile.IModularTileEntity;
import de.nedelosk.forestmods.client.render.modules.ModuleRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ModularMachine extends ModularDefault {

	public ModularMachine() {
		super();
	}

	public ModularMachine(NBTTagCompound nbt, IModularTileEntity machine) {
		super(nbt, machine);
	}

	@Override
	public String getName() {
		return "modular.machine";
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModularRenderer getItemRenderer(IModular modular, ItemStack stack) {
		return new ModuleRenderer.ModularRenderer();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModularRenderer getMachineRenderer(IModular modular, IModularTileEntity tile) {
		return new ModuleRenderer.ModularRenderer();
	}
}