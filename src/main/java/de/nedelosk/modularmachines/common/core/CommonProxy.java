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

public class CommonProxy {

	public void preInit(){
	}

	public void init(){
	}

	public List<String> addModuleInfo(ItemStack itemStack){
		return Collections.emptyList();
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
