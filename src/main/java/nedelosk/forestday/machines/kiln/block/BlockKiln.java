package nedelosk.forestday.machines.kiln.block;

import nedelosk.forestday.api.Tabs;
import nedelosk.forestday.common.machines.brick.kiln.TileKiln;
import nedelosk.forestday.machines.blocks.BlockMachieneBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockKiln extends BlockMachieneBase{
	
	public BlockKiln() {
		super(Material.ground);
		this.setStepSound(soundTypeMetal);
		this.setCreativeTab(Tabs.tabForestdayBlocks);
		this.setBlockName("machine.kiln");
		this.setHardness(3);
		this.setHarvestLevel("pickaxe", 1);
	}
	
	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int meta) {
		return new TileKiln();
	}
	
	@Override
	public int getRenderType()
	{
		return -1;
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
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register)
	{
		blockIcon = register.registerIcon("minecraft:dirt");
	}

}
