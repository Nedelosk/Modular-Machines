package de.nedelosk.modularmachines.common.blocks;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockOre extends BlockForest {

	private String[] ore = new String[] { "Copper", "Tin", "Silver", "Lead", "Nickel", "Ruby", "Columbite", "Aluminum" };

	public BlockOre() {
		super(Material.GROUND, CreativeTabs.BUILDING_BLOCKS);
		setHardness(2.0f);
		setResistance(3.0F);
		setUnlocalizedName("ores");
		setSoundType(SoundType.STONE);
		setHarvestLevel("pickaxe", 1);
	}
	
	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
		drops.add(new ItemStack(this, 1, getMetaFromState(state)));
		return drops;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List<ItemStack> list) {
		for(int i = 0; i < ore.length; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}
}
