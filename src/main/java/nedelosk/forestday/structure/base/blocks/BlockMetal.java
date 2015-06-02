package nedelosk.forestday.structure.base.blocks;

import java.util.List;

import nedelosk.forestday.api.Tabs;
import nedelosk.forestday.structure.base.blocks.tile.TileStructureMetal;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockMetal extends BlockContainer {

	public BlockMetal() {
		super(Material.ground);
		this.setStepSound(soundTypeMetal);
		this.setCreativeTab(Tabs.tabForestdayMultiBlocks);
		this.setBlockName("structure.metal");
		this.setHardness(0.5F);
	}
	
	@Override
	public int damageDropped(int metadata) {
		return metadata;
	}
	
	public static IIcon[] icons = new IIcon[5];

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		switch (meta) {
		case 0:
			return new TileStructureMetal(125, "Steel");
		case 1:
			return new TileStructureMetal(250, "LightSteel");
		case 2:
			return new TileStructureMetal(500, "DarkSteel");
		case 3:
			return new TileStructureMetal(1000, "Obsidian");
		case 4:
			return new TileStructureMetal(2000, "Enderium");
		default:
			return null;
		}
	}
	
	@Override
	public void registerBlockIcons(IIconRegister IIconRegister) 
	{
		icons[0] = IIconRegister.registerIcon("forestday:walls/steel");
		icons[1] = IIconRegister.registerIcon("forestday:walls/light_steel");
		icons[2] = IIconRegister.registerIcon("forestday:walls/dark_steel");
		icons[3] = IIconRegister.registerIcon("forestday:walls/obsidian_steel");
		icons[4] = IIconRegister.registerIcon("forestday:walls/enderium_steel");
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
		default:
			return icons[0];
		}
	}
	
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) 
	{
		for(int i = 0;i < 5;i++)
		{
			list.add(new ItemStack(item, 1, i));
		}
	}
	
	public IIcon[] getIcons()
	{
		return this.icons;
	}

}
