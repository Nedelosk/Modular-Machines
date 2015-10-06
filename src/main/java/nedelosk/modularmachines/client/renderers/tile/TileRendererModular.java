package nedelosk.modularmachines.client.renderers.tile;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.common.blocks.tile.TileModular;
import nedelosk.modularmachines.common.modular.utils.MachineBuilder;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileRendererModular extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float p_147500_8_) {
		if(entity instanceof TileModular){
			TileModular machineTile = (TileModular) entity;
			if(machineTile.machine != null && machineTile.machine.getMachineRenderer(machineTile.machine, machineTile) != null)
				machineTile.machine.getMachineRenderer(machineTile.machine, machineTile).renderMachine(machineTile, x, y, z);
		}
	}
	
	public void renderTileEntityItem(ItemStack stack){
		NBTTagCompound tagCompound = stack.getTagCompound();
		IModular machine = MachineBuilder.createMachine(tagCompound.getString("MachineName"), tagCompound.getTag("Machine"));
		if(machine != null && machine.getItemRenderer(machine, stack) != null)
			machine.getItemRenderer(machine, stack).renderMachineItemStack(stack);
	}

}
