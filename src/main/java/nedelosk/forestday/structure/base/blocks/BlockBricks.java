package nedelosk.forestday.structure.base.blocks;

import java.util.List;

import nedelosk.forestday.api.Tabs;
import nedelosk.forestday.structure.base.blocks.tile.TileStructureBrick;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockBricks extends BlockContainer {

	public BlockBricks() {
		super(Material.ground);
		this.setStepSound(soundTypeStone);
		this.setCreativeTab(Tabs.tabForestdayMultiBlocks);
		this.setBlockName("structure.wall");
		this.setHardness(0.5F);
	}
	
	public static IIcon[] icons = new IIcon[6];

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		switch (meta) {
		case 0:
			return new TileStructureBrick(75, "Loam");
		case 1:
			return new TileStructureBrick(125, "Ash");
		case 2:
			return new TileStructureBrick(250, "Hardened");
		case 3:
			return new TileStructureBrick(500, "Steel");
		case 4:
			return new TileStructureBrick(1000, "Obsidian");
		case 5:
			return new TileStructureBrick(2000, "Enderium");
		default:
			return null;
		}
	}
	
	@Override
	public int damageDropped(int metadata) {
		return metadata;
	}
	
	@Override
	public void registerBlockIcons(IIconRegister IIconRegister) 
	{
		icons[0] = IIconRegister.registerIcon("forestday:walls/loam_brick");
		icons[1] = IIconRegister.registerIcon("forestday:walls/ash_brick");
		icons[2] = IIconRegister.registerIcon("forestday:walls/hardened_brick");
		icons[3] = IIconRegister.registerIcon("forestday:walls/stell_brick");
		icons[4] = IIconRegister.registerIcon("forestday:walls/obsidian_brick");
		icons[5] = IIconRegister.registerIcon("forestday:walls/enderium_brick");
	}
	
	@Override
	public IIcon getIcon(int side, int meta) 
	{
		switch (meta) {
		case 0:
			return icons[0];
		case 1:
			return icons[1];
		case 2:
			return icons[2];
		case 3:
			return icons[3];
		case 4:
			return icons[4];
		case 5:
			return icons[5];
		default:
			return icons[0];
		}
	}
	
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) 
	{
		for(int i = 0;i < 6;i++)
		{
			list.add(new ItemStack(item, 1, i));
		}
	}
	
	public IIcon[] getIcons()
	{
		return this.icons;
	}

}
