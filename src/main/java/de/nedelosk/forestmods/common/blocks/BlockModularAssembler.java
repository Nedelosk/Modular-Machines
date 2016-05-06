package de.nedelosk.forestmods.common.blocks;

import buildcraft.api.tools.IToolWrench;
import de.nedelosk.forestmods.common.blocks.tile.TileModular;
import de.nedelosk.forestmods.common.blocks.tile.TileModularAssembler;
import de.nedelosk.forestmods.common.core.ForestMods;
import de.nedelosk.forestmods.common.core.TabModularMachines;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class BlockModularAssembler extends BlockContainerForest {

	public BlockModularAssembler() {
		super(Material.iron);
		setStepSound(soundTypeMetal);
		setCreativeTab(TabModularMachines.tabModules);
		setHardness(2.0F);
		setHarvestLevel("pickaxe", 1);
		setBlockName("modular_assembler");
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileModularAssembler();
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float par7, float par8, float par9) {
		if(world.isRemote){
			return false;
		}
		player.openGui(ForestMods.instance, 0, player.worldObj, x, y, z);
		return true;
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
}
