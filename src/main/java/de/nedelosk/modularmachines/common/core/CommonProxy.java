package de.nedelosk.modularmachines.common.core;

import java.util.Collections;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class CommonProxy implements IGuiHandler {

	public void preInit(){
	}

	public void init(){
	}

	public List<String> addModuleInfo(ItemStack itemStack){
		return Collections.emptyList();
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
		switch (ID) {
			case 0:
				if (tile != null && tile instanceof de.nedelosk.modularmachines.api.gui.IGuiHandler) {
					return ((de.nedelosk.modularmachines.api.gui.IGuiHandler) tile).createContainer(player.inventory);
				}
			default:
				return null;
		}
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
		if ((world instanceof WorldClient)) {
			switch (ID) {
				case 0:
					if (tile instanceof de.nedelosk.modularmachines.api.gui.IGuiHandler) {
						return ((de.nedelosk.modularmachines.api.gui.IGuiHandler) tile).createGui(player.inventory);
					}
				default:
					return null;
			}
		}
		return null;
	}

	public void registerStateMapper(Block block, IStateMapper mapper) {
	}

	public void registerFluidStateMapper(Block block, Fluid fluid) {
	}

	public void registerBlock(Block block){

	}

	public void registerItem(Item item){

	}
}
