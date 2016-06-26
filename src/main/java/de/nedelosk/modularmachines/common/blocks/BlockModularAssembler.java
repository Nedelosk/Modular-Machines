package de.nedelosk.modularmachines.common.blocks;

import de.nedelosk.modularmachines.client.statemapper.BlankStateMapper;
import de.nedelosk.modularmachines.common.blocks.tile.TileModularAssembler;
import de.nedelosk.modularmachines.common.core.ModularMachines;
import de.nedelosk.modularmachines.common.core.TabModularMachines;
import forestry.api.core.IStateMapperRegister;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.BlockStateMapper;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockModularAssembler extends BlockContainerForest implements IStateMapperRegister{

	public BlockModularAssembler() {
		super(Material.IRON);
		setSoundType(SoundType.METAL);
		setCreativeTab(TabModularMachines.tabModules);
		setHardness(2.0F);
		setHarvestLevel("pickaxe", 1);
		setUnlocalizedName("modular_assembler");
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileModularAssembler();
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(world.isRemote){
			return false;
		}
		player.openGui(ModularMachines.instance, 0, player.worldObj, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}
	
	@Override
	public void registerStateMapper() {
		ModularMachines.proxy.registerStateMapper(this, new BlankStateMapper());
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.INVISIBLE;
	}
}
