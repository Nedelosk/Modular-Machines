package de.nedelosk.modularmachines.common.items.blocks;

import de.nedelosk.modularmachines.api.modular.ModularManager;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandlerTileEntity;
import de.nedelosk.modularmachines.common.blocks.tile.TileModular;
import de.nedelosk.modularmachines.common.core.Registry;
import de.nedelosk.modularmachines.common.modular.ModularAssembler;
import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBlockChassis extends Item implements IItemModelRegister {

	public Block block;

	public ItemBlockChassis(Block block) {
		this.block = block;
		setUnlocalizedName("modular.chassis");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public CreativeTabs getCreativeTab(){
		return this.block.getCreativeTabToDisplayOn();
	}

	@Override
	public void registerModel(Item item, IModelManager manager) {
		manager.registerItemModel(item, 0, "modular_chassis");
	}

	/**
	 * Called when a Block is right-clicked with this Item
	 */
	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		IBlockState iblockstate = worldIn.getBlockState(pos);
		Block block = iblockstate.getBlock();

		if (!block.isReplaceable(worldIn, pos))
		{
			pos = pos.offset(facing);
		}

		if (stack.stackSize != 0 && playerIn.canPlayerEdit(pos, facing, stack) && worldIn.canBlockBePlaced(this.block, pos, false, facing, (Entity)null, stack))
		{
			int i = this.getMetadata(stack.getMetadata());
			IBlockState iblockstate1 = this.block.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, i, playerIn);

			if (placeBlockAt(stack, playerIn, worldIn, pos, facing, hitX, hitY, hitZ, iblockstate1))
			{
				SoundType soundtype = this.block.getSoundType();
				worldIn.playSound(playerIn, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
				--stack.stackSize;
			}

			return EnumActionResult.SUCCESS;
		}
		else
		{
			return EnumActionResult.FAIL;
		}
	}

	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState){
		if (!world.setBlockState(pos, newState, 3)) {
			return false;
		}

		IBlockState state = world.getBlockState(pos);
		if (state.getBlock() == this.block){
			TileEntity tile = world.getTileEntity(pos);
			if (!(tile instanceof TileModular)) {
				world.setBlockToAir(pos);
				return false;
			}
			TileModular machine = (TileModular) tile;
			IModularHandlerTileEntity  tileHandler = (IModularHandlerTileEntity) machine.getCapability(ModularManager.MODULAR_HANDLER_CAPABILITY, null);
			int heading = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

			tileHandler.setAssembled(false);
			tileHandler.setAssembler(new ModularAssembler(tileHandler));
			tileHandler.setOwner(player.getGameProfile());
			tileHandler.setFacing(getFacingForHeading(heading));

			ItemBlock.setTileEntityNBT(world, player, pos, stack);
		}
		this.block.onBlockPlacedBy(world, pos, state, player, stack);

		return true;
	}

	private EnumFacing getFacingForHeading(int heading) {
		switch (heading) {
			case 0:
				return EnumFacing.NORTH;
			case 1:
				return EnumFacing.EAST;
			case 2:
				return EnumFacing.SOUTH;
			case 3:
			default:
				return EnumFacing.WEST;
		}
	}

	@Override
	public int getMetadata(int i) {
		return i;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return Registry.setUnlocalizedItemName(super.getUnlocalizedName(stack).replace("item.", ""));
	}
}
