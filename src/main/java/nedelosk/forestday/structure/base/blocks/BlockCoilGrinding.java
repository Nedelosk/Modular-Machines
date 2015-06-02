package nedelosk.forestday.structure.base.blocks;

import java.util.List;

import nedelosk.forestday.Forestday;
import nedelosk.forestday.api.Tabs;
import nedelosk.forestday.structure.base.blocks.tile.TileCoilGrinding;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCoilGrinding extends BlockContainer {

	public BlockCoilGrinding() {
		super(Material.iron);
		this.setStepSound(soundTypeMetal);
		this.setCreativeTab(Tabs.tabForestdayMultiBlocks);
		this.setBlockName("structure.coil.grinding");
		this.setHardness(0.5F);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float par7, float par8, float par9) {
		
		FMLNetworkHandler.openGui(player, Forestday.instance, 0, world, x, y, z);
		
	  return true;
	}
	
	private IIcon[] icons;
	public static int[] coilMax = new int[] { 50, 100, 250, 500, 1000, 1500, 2000, 3000, 4000};
	public static String[] coilName = new String[] { "Wood", "Stone", "Iron", "Steel", "Dark_Steel", "Enderium"};
	public static int[] coilSpeed = new int[] { 10, 25, 50, 100, 250, 500, };
	public static int[] coilWeight = new int[] { 20, 40, 70, 55, 78, 45};
	public static int[] coilMaxWeight = new int[] { 80, 140, 260, 320, 400, 650};
	public static int[] coilMaxSharpness = new int[] { 150, 350, 500, 650, 790, 2000};

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
			return new TileCoilGrinding(coilMax[meta], coilName[meta], coilSpeed[meta]);
	}
	
    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks (Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int i = 0; i < coilName.length; i++)
            par3List.add(new ItemStack(par1, 1, i));
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
