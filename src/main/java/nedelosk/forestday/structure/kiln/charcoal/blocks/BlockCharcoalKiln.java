package nedelosk.forestday.structure.kiln.charcoal.blocks;

import java.util.List;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.forestday.Forestday;
import nedelosk.forestday.api.Tabs;
import nedelosk.forestday.machines.blocks.BlockMachieneBase;
import nedelosk.forestday.structure.blastfurnace.blocks.tile.TileBlastFurnaceController;
import nedelosk.forestday.structure.blastfurnace.blocks.tile.TileBlastFurnaceRegulator;
import nedelosk.forestday.structure.kiln.charcoal.blocks.tile.TileCharcoalKiln;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCharcoalKiln extends BlockMachieneBase {
	
	public BlockCharcoalKiln() {
		super(Material.wood);
		this.setStepSound(soundTypeWood);
		this.setHarvestLevel("axe", 1);
		this.setBlockName("machine.kiln.charcoal");
		this.setCreativeTab(Tabs.tabForestdayMultiBlocks);
		}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float par7, float par8, float par9) {
		FMLNetworkHandler.openGui(player, Forestday.instance, 0, world, x, y, z);
	  return true;
	}
    
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileCharcoalKiln();
	}
	
    
	@Override
	public int damageDropped(int metadata) {
		return metadata;
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

}
