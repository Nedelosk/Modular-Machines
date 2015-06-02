package nedelosk.forestday.structure.airheater.blocks;

import java.util.List;

import nedelosk.forestday.Forestday;
import nedelosk.forestday.api.Tabs;
import nedelosk.forestday.machines.blocks.BlockMachieneBase;
import nedelosk.forestday.structure.airheater.blocks.tile.TileAirHeaterController;
import nedelosk.forestday.structure.airheater.blocks.tile.TileAirHeaterRegulator;
import nedelosk.forestday.structure.alloysmelter.blocks.tile.TileAlloySmelterController;
import nedelosk.forestday.structure.alloysmelter.blocks.tile.TileAlloySmelterRegulator;
import nedelosk.forestday.structure.base.blocks.tile.TileController;
import nedelosk.forestday.structure.base.blocks.tile.TileRegulatorHeat;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
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

public class BlockAirHeater extends BlockMachieneBase {

	public IIcon[] icons;
	
	public final int meta_controller = 0;
	public final int meta_regulator = 1;
	public final int brick = 2;
	
	
	public BlockAirHeater() {
		super(Material.gourd);
		this.setStepSound(soundTypeStone);
		this.setHarvestLevel("pickaxe", 1);
		this.setBlockName("machine.air.heater");
		this.setCreativeTab(Tabs.tabForestdayMultiBlocks);
		}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float par7, float par8, float par9) {
	
		if(world.getTileEntity(x, y,z) instanceof TileRegulatorHeat)
		{
		FMLNetworkHandler.openGui(player, Forestday.instance, 7, world, x, y, z);
		}
		if(world.getTileEntity(x, y,z) instanceof TileController)
		{
		FMLNetworkHandler.openGui(player, Forestday.instance, 5, world, x, y, z);
		}
		
	  return true;
	}
	
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon (int side, int meta)
    {
        switch (meta) {
		case meta_controller:
			switch (side) {
			case 0:
				return icons[brick];
			case 1:
				return icons[brick];
			default:
				return icons[meta_controller];
			}
		case meta_regulator:
			switch (side) {
			case 0:
				return icons[meta_regulator];
			case 1:
				return icons[meta_regulator];
			default:
				return icons[brick];
			}
		default:
			return icons[brick];
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons (IIconRegister iconRegister)
    {
    	icons = new IIcon[3];
    	//Brick
        icons[meta_controller] = iconRegister.registerIcon("forestday:controllers/air_heater_controller");
        icons[meta_regulator] = iconRegister.registerIcon("forestday:regulators/air_heater_regulator");
        icons[brick] = iconRegister.registerIcon("forestday:walls/ash_brick");
    }
    
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
			switch (meta) {
			case 0:
				return new TileAirHeaterController();
			case 1:
				return new TileAirHeaterRegulator();
			default:
				return null;
			}
	}
	
    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks (Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int i = 0; i < 2; i++)
            par3List.add(new ItemStack(par1, 1, i));
    }

}
