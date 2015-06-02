package nedelosk.forestday.structure.base.blocks;

import java.util.List;

import nedelosk.forestday.api.Tabs;
import nedelosk.forestday.structure.base.blocks.tile.TileCoilHeat;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCoilHeat extends BlockContainer {

	public BlockCoilHeat() {
		super(Material.iron);
		this.setStepSound(soundTypeMetal);
		this.setCreativeTab(Tabs.tabForestdayMultiBlocks);
		this.setBlockName("structure.coil.heat");
		this.setHardness(0.5F);
	}
	
	private IIcon[] icons;
	public static int[] coilHeat = new int[] { 100, 200, 500, 1000, 2500, 5000, 55, 10, 45, 75, 90 };

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		switch (meta) {
		case 0:
			return new TileCoilHeat(150, coilHeat[meta], "Red");
		case 1:
			return new TileCoilHeat(300, coilHeat[meta], "Blue");
		case 2:
			return new TileCoilHeat(750, coilHeat[meta], "DarkBlue");
		case 3:
			return new TileCoilHeat(1500, coilHeat[meta], "Yellow");
		case 4:
			return new TileCoilHeat(5000, coilHeat[meta], "Brown");
		case 5:
			return new TileCoilHeat(10000, coilHeat[meta], "Green");
		case 6:
			return new TileCoilHeat(120, coilHeat[meta], "Copper");
		default:
			return null;
		}
	}
	
    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks (Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int i = 0; i < 7; i++)
            par3List.add(new ItemStack(par1, 1, i));
    }
    
	@Override
	public int damageDropped(int metadata) {
		return metadata;
	}
    
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon (int side, int meta)
    {
        switch (meta) {
		case 0:
			switch (side) {
			case 0:
				return icons[0];
			case 1:
				return icons[0];
			default:
				return icons[6];
			}
		case 1:
			switch (side) {
			case 0:
				return icons[1];
			case 1:
				return icons[1];
			default:
				return icons[7];
			}
		case 2:
			switch (side) {
			case 0:
				return icons[2];
			case 1:
				return icons[2];
			default:
				return icons[8];
			}
		case 3:
			switch (side) {
			case 0:
				return icons[3];
			case 1:
				return icons[3];
			default:
				return icons[9];
			}
		case 4:
			switch (side) {
			case 0:
				return icons[4];
			case 1:
				return icons[4];
			default:
				return icons[10];
			}
		case 5:
			switch (side) {
			case 0:
				return icons[5];
			case 1:
				return icons[5];
			default:
				return icons[11];
			}
		case 6:
			switch (side) {
			case 0:
				return icons[12];
			case 1:
				return icons[12];
			default:
				return icons[13];
			}
		default:
			return icons[0];
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons (IIconRegister iconRegister)
    {
    	icons = new IIcon[14];
        
        //Coil Top
        icons[0] = iconRegister.registerIcon("forestday:coils/heat_coil_red_alloy_top");
        icons[1] = iconRegister.registerIcon("forestday:coils/heat_coil_blue_alloy_top");
        icons[2] = iconRegister.registerIcon("forestday:coils/heat_coil_dark_blue_alloy_top");
        icons[3] = iconRegister.registerIcon("forestday:coils/heat_coil_yellow_alloy_top");
        icons[4] = iconRegister.registerIcon("forestday:coils/heat_coil_brown_alloy_top");
        icons[5] = iconRegister.registerIcon("forestday:coils/heat_coil_green_alloy_top");
        icons[12] = iconRegister.registerIcon("forestday:coils/heat_coil_copper_top");
        
    	//Coil
        icons[6] = iconRegister.registerIcon("forestday:coils/heat_coil_red_alloy");
        icons[7] = iconRegister.registerIcon("forestday:coils/heat_coil_blue_alloy");
        icons[8] = iconRegister.registerIcon("forestday:coils/heat_coil_dark_blue_alloy");
        icons[9] = iconRegister.registerIcon("forestday:coils/heat_coil_yellow_alloy");
        icons[10] = iconRegister.registerIcon("forestday:coils/heat_coil_brown_alloy");
        icons[11] = iconRegister.registerIcon("forestday:coils/heat_coil_green_alloy");
        icons[13] = iconRegister.registerIcon("forestday:coils/heat_coil_copper");
    }

}
