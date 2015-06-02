package nedelosk.forestday.structure.base.blocks;

import java.util.List;

import nedelosk.forestday.Forestday;
import nedelosk.forestday.api.Tabs;
import nedelosk.forestday.common.core.Defaults;
import nedelosk.forestday.structure.base.blocks.tile.TileBusFluid;
import nedelosk.forestday.structure.base.blocks.tile.TileBusItem;
import nedelosk.forestday.structure.base.blocks.tile.TileBus.Mode;
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

public class BlockBusMetalFluid extends BlockContainer {

	public BlockBusMetalFluid() {
		super(Material.iron);
		this.setCreativeTab(Tabs.tabForestdayMultiBlocks);
		this.setBlockName("structure.bus.metal.fluid");
		this.setHardness(0.5F);
	}
	
	public static IIcon iconBus;
	private IIcon[] icons = BlockMetal.icons;

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
			return new TileBusFluid(125, "Steel", icons[0]);
		case 1:
			return new TileBusFluid(250, "LightSteel", icons[1]);
		case 2:
			return new TileBusFluid(500, "DarkSteel", icons[2]);
		case 3:
			return new TileBusFluid(1000, "Obsidian", icons[3]);
		case 4:
			return new TileBusFluid(2000, "Enderium", icons[4]);
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
		return Defaults.BUSRENDERER_METAL_ID;
	}
	
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) 
	{
		for(int i = 0;i < 5;i++)
		{
			list.add(new ItemStack(item, 1, i));
		}
	}

}
