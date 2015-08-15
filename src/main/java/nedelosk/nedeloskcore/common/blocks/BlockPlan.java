package nedelosk.nedeloskcore.common.blocks;

import java.util.ArrayList;
import nedelosk.nedeloskcore.common.blocks.tile.TilePlan;
import nedelosk.nedeloskcore.common.core.NedeloskCore;
import nedelosk.nedeloskcore.common.core.registry.NCItems;
import nedelosk.nedeloskcore.common.core.registry.NRegistry;
import nedelosk.nedeloskcore.utils.ItemUtils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockPlan extends BlockContainerForest {

	public TilePlan plan;
	
	public BlockPlan() {
		super(Material.wood);
		setBlockName(NRegistry.setUnlocalizedBlockName("plan", "nc"));
		setHardness(2.0F);
		setStepSound(soundTypeWood);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TilePlan();
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
		if(world.getTileEntity(x, y, z) != null && world.getTileEntity(x, y, z) instanceof TilePlan)
		{
			player.openGui(NedeloskCore.instance, 0, world, x, y, z);
			return true;
		}
		return false;
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block p_149749_5_, int p_149749_6_) {
		plan = (TilePlan) world.getTileEntity(x, y, z);
		if(!world.isRemote)
		{
			ItemUtils.dropItems(world, x, y, z);
		}
		super.breakBlock(world, x, y, z, p_149749_5_, p_149749_6_);
	}
	
	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		ArrayList<ItemStack> list = new ArrayList<ItemStack>();
		list.add(new ItemStack(NCItems.Plan.item(), 1, 0));
		list.get(0).setTagCompound(plan.plan);
		return list;
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
