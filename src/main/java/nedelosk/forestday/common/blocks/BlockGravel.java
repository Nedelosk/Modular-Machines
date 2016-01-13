package nedelosk.forestday.common.blocks;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.forestcore.library.blocks.BlockForest;
import nedelosk.forestday.api.Tabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class BlockGravel extends BlockForest {

	public String[] textures = new String[] { "loam", "ore_gravel" };
	public IIcon[] icon;

	public BlockGravel() {
		super(Material.ground, Tabs.tabForestday);
		this.setHardness(1.0f);
		this.setStepSound(Block.soundTypeGravel);
		this.setHarvestLevel("shovel", 1, 0);
		this.setBlockName("gravel");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return icon[meta];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.icon = new IIcon[textures.length];
		for ( int i = 0; i < this.icon.length; ++i ) {
			this.icon[i] = iconRegister.registerIcon("forestday:" + textures[i]);
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		for ( int i = 0; i < 1; i++ ) {
			par3List.add(new ItemStack(par1, 1, i));
		}
	}
}
