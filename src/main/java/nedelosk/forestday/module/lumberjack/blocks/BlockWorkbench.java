package nedelosk.forestday.module.lumberjack.blocks;

import java.util.List;

import nedelosk.forestday.api.Tabs;
import nedelosk.forestday.common.blocks.BlockForestday;
import nedelosk.forestday.common.machines.wood.workbench.TileWorkbench;
import nedelosk.forestday.machines.blocks.BlockMachieneBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockWorkbench extends BlockMachieneBase {
	
	public BlockWorkbench() {
		super(Material.wood, "workbench");
		this.setStepSound(soundTypeWood);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileWorkbench();
	}
	
	@Override
	public void registerBlockIcons(IIconRegister IIconRegister) {
		this.blockIcon = IIconRegister.registerIcon("minecraft:planks_oak");
	}
	
	@Override
	public boolean canSilkHarvest(World world, EntityPlayer player, int x, int y, int z, int metadata) {
	  return false;
	}
	
	@Override
	public int getRenderType()
	{
		return -1;
	}
	
	@Override
	public void getSubBlocks(Item item, CreativeTabs arg1, List list) {
		list.add(new ItemStack(item, 1, 0));
		list.add(new ItemStack(item, 1, 1));
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}


}
