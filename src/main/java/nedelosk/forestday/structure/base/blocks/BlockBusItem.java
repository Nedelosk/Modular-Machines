package nedelosk.forestday.structure.base.blocks;

import java.util.List;

import nedelosk.forestday.Forestday;
import nedelosk.forestday.api.Tabs;
import nedelosk.forestday.common.core.Defaults;
import nedelosk.forestday.structure.base.blocks.tile.TileBus.Mode;
import nedelosk.forestday.structure.base.blocks.tile.TileBusItem;
import net.minecraft.block.BlockContainer;
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

public class BlockBusItem extends BlockContainer {

	public BlockBusItem() {
		super(Material.iron);
		this.setCreativeTab(Tabs.tabForestdayMultiBlocks);
		this.setBlockName("structure.bus");
		this.setHardness(1.0F);
	}
	
	public static IIcon iconBus;
	private IIcon[] icons = BlockBricks.icons;

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float par7, float par8, float par9) {
		
		FMLNetworkHandler.openGui(player, Forestday.instance, 4, world, x, y, z);
		
	  return true;
	}
	
	@Override
	public int damageDropped(int metadata) {
		return metadata;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		switch (meta) {
		case 0:
			return new TileBusItem(75,"Loam", icons[0]);
		case 1:
			return new TileBusItem(125, "Ash", icons[1]);
		case 2:
			return new TileBusItem(250, "Hardened", icons[2]);
		case 3:
			return new TileBusItem(500, "Steel", icons[3]);
		case 4:
			return new TileBusItem(1000, "Obsidian", icons[4]);
		case 5:
			return new TileBusItem(2000, "Enderium", icons[5]);
		default:
			return null;
		}
	}
	
	@Override
	public void registerBlockIcons(IIconRegister IIconRegister) {
		this.iconBus = IIconRegister.registerIcon("forestday:icons/bus");
	}
	
	@Override
	public int getRenderType() {
		return Defaults.BUSRENDERER_ID;
	}
	
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) 
	{
		for(int i = 0;i < 6;i++)
		{
			list.add(new ItemStack(item, 1, i));
		}
	}

}
