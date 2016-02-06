package de.nedelosk.forestmods.common.blocks;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestcore.blocks.BlockForest;
import de.nedelosk.forestmods.common.core.ItemManager;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockOre extends BlockForest {

	private String[] ore = new String[] { "Copper", "Tin", "Silver", "Lead", "Nickel", "Ruby", "Columbite", "Aluminum" };
	public IIcon[] oreIcon;

	public BlockOre() {
		super(Material.ground, CreativeTabs.tabBlock);
		this.setHardness(2.0f);
		this.setResistance(3.0F);
		this.setBlockName("ores");
		this.setStepSound(Block.soundTypeStone);
		this.setHarvestLevel("pickaxe", 1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return oreIcon[meta];
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
		if (metadata == 5) {
			int fortmod = world.rand.nextInt(fortune + 2) - 1;
			if (fortmod < 0) {
				fortmod = 0;
			}
			int amount = (2 + world.rand.nextInt(5)) * (fortmod + 1);
			if (amount > 0) {
				drops.add(new ItemStack(ItemManager.itemGems, amount, 0));
			}
		} else {
			drops.add(new ItemStack(this, 1, metadata));
		}
		return drops;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.oreIcon = new IIcon[ore.length];
		for ( int i = 0; i < this.oreIcon.length; ++i ) {
			this.oreIcon[i] = iconRegister.registerIcon("forestmods:ore" + ore[i]);
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		for ( int i = 0; i < ore.length; i++ ) {
			par3List.add(new ItemStack(par1, 1, i));
		}
	}
}
