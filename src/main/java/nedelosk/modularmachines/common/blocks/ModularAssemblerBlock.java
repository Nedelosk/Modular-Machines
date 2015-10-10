package nedelosk.modularmachines.common.blocks;

import java.util.List;
import nedelosk.modularmachines.common.ModularMachines;
import nedelosk.modularmachines.common.blocks.tile.TileModularAssembler;
import nedelosk.modularmachines.common.inventory.assembler.ContainerModularAssembler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ModularAssemblerBlock extends ModularBlock {

	public ModularAssemblerBlock() {
		super(Material.iron);
		setStepSound(soundTypeMetal);
		setHardness(2.0F);
		setHarvestLevel("pickaxe", 1);
		setBlockName("modular.assembler");
		setBlockTextureName("iron_block");
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public int getRenderType() {
		return -1;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileModularAssembler(meta + 1);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float par7, float par8, float par9) {
		if(!world.isRemote){
			player.openGui(ModularMachines.instance, 0, world, x, y, z);
			((ContainerModularAssembler) player.openContainer).syncOnOpen((EntityPlayerMP) player);
		}
		return true;
	}
	
	@Override
	public void getSubBlocks(Item item, CreativeTabs ptab, List list) {
		for(int i = 0;i < 6;i++)
			list.add(new ItemStack(item, 1, i));
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		/*if(world.getTileEntity(x, y, z) != null && world.getTileEntity(x, y, z) instanceof TileModularAssembler)
		{
			TileModularAssembler tile = (TileModularAssembler) world.getTileEntity(x, y, z);
			ItemUtils.dropItem(world, x, y, z, tile.slots);
		}*/
		super.breakBlock(world, x, y, z, block, meta);
	}

}
