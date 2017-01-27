package modularmachines.common.items.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import modularmachines.common.blocks.tile.TileEntityMachine;
import modularmachines.common.utils.TileUtil;

public class ItemBlockMachine extends ItemBlock {

	public ItemBlockMachine(Block block) {
		super(block);
	}

	/*@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		return new ModularHandlerItem(stack, ModularManager.DEFAULT_STORAGE_POSITIONS);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		if (GuiScreen.isShiftKeyDown()) {
			IModularHandler<NBTTagCompound, Object> handler = stack.getCapability(ModularManager.MODULAR_HANDLER_CAPABILITY, null);
			if (handler != null) {
				if (stack.hasTagCompound()) {
					handler.deserializeNBT(stack.getTagCompound());
					tooltip.add(TextFormatting.WHITE.toString() + TextFormatting.ITALIC + Translator.translateToLocal("mm.tooltip.modular.info"));
					IModular modular = handler.getModular();
					if (modular != null) {
						for (IModuleProvider provider : modular.getProviders()) {
							if (provider != null) {
								ItemStack itemStack = provider.getItemStack();
								String moduleName = "";
								for (IModuleState state : provider.getModuleStates()) {
									if (state != null) {
										moduleName += state.getContainer().getDisplayName() + " - ";
									}
								}
								tooltip.add(ChatFormatting.GRAY + moduleName + itemStack.getDisplayName());
							}
						}
					}
				}
			}
		} else {
			tooltip.add(TextFormatting.WHITE.toString() + TextFormatting.ITALIC + Translator.translateToLocal("mm.tooltip.holdshift"));
		}
		super.addInformation(stack, playerIn, tooltip, advanced);
	}*/

	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState) {
		if (!world.setBlockState(pos, newState, 3)) {
			return false;
		}
		IBlockState state = world.getBlockState(pos);
		if (state.getBlock() == this.block) {
			TileEntityMachine tile = TileUtil.getTile(world, pos, TileEntityMachine.class);
			if (tile == null) {
				world.setBlockToAir(pos);
				return false;
			}
			/*IModularHandlerItem itemHandler = (IModularHandlerItem) stack.getCapability(ModularManager.MODULAR_HANDLER_CAPABILITY, null);
			if (itemHandler != null) {
				itemHandler.deserializeNBT(stack.getTagCompound());
			}
			if (itemHandler != null && itemHandler.getModular() != null) {
				TileModular machine = (TileModular) tile;
				IModularHandlerTileEntity tileHandler = (IModularHandlerTileEntity) machine.getCapability(ModularManager.MODULAR_HANDLER_CAPABILITY, null);
				int heading = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
				tileHandler.setOwner(player.getGameProfile());
				tileHandler.setFacing(getFacingForHeading(heading));
				if (itemHandler.isAssembled() && itemHandler.getModular() != null) {
					tileHandler.setModular(itemHandler.getModular().copy(tileHandler));
				} else if (!itemHandler.isAssembled() && itemHandler.getAssembler() != null) {
					tileHandler.setAssembler(itemHandler.getAssembler().copy(tileHandler));
				} else {
					tileHandler.setAssembler(new ModularAssembler(tileHandler));
				}
			}*/
			setTileEntityNBT(world, player, pos, stack);
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
}
