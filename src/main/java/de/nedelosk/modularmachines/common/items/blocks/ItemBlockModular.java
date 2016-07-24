package de.nedelosk.modularmachines.common.items.blocks;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import de.nedelosk.modularmachines.api.Translator;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.ModularManager;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandlerItem;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandlerTileEntity;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.common.blocks.tile.TileModular;
import de.nedelosk.modularmachines.common.modular.assembler.ModularAssembler;
import de.nedelosk.modularmachines.common.modular.handlers.ModularHandlerItem;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class ItemBlockModular extends ItemBlock {

	public ItemBlockModular(Block block) {
		super(block);
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		return new ModularHandlerItem(stack);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		if(GuiScreen.isShiftKeyDown()){
			IModularHandler<IModular, NBTTagCompound> handler = stack.getCapability(ModularManager.MODULAR_HANDLER_CAPABILITY, null);
			if(handler != null){
				if(stack.hasTagCompound()){
					handler.deserializeNBT(stack.getTagCompound());
					tooltip.add(TextFormatting.WHITE.toString() + TextFormatting.ITALIC + Translator.translateToLocal("mm.tooltip.modular.info"));
					IModular modular = handler.getModular();
					for(IModuleState state : modular.getModuleStates()){
						if(state != null){
							tooltip.add(ChatFormatting.GRAY + state.getContainer().getDisplayName());
						}
					}
				}
			}
		}else{
			tooltip.add(TextFormatting.WHITE.toString() + TextFormatting.ITALIC + Translator.translateToLocal("mm.tooltip.holdshift"));
		}
		super.addInformation(stack, playerIn, tooltip, advanced);
	}

	@Override
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
			IModularHandlerItem itemHandler = (IModularHandlerItem) stack.getCapability(ModularManager.MODULAR_HANDLER_CAPABILITY, null);
			if(itemHandler != null){
				itemHandler.deserializeNBT(stack.getTagCompound());
			}
			if(itemHandler != null && itemHandler.getModular() != null){
				TileModular machine = (TileModular) tile;
				IModularHandlerTileEntity tileHandler = (IModularHandlerTileEntity) machine.getCapability(ModularManager.MODULAR_HANDLER_CAPABILITY, null);
				int heading = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

				tileHandler.setOwner(player.getGameProfile());
				tileHandler.setFacing(getFacingForHeading(heading));

				if(itemHandler.isAssembled() && itemHandler.getModular() != null){
					tileHandler.setAssembled(true);
					tileHandler.setModular(itemHandler.getModular().copy(tileHandler));
				}else if(!itemHandler.isAssembled() && itemHandler.getAssembler() != null){
					tileHandler.setAssembled(false);
					tileHandler.setAssembler(itemHandler.getAssembler().copy(tileHandler));
				}else{
					tileHandler.setAssembled(false);
					tileHandler.setAssembler(new ModularAssembler(tileHandler, new ItemStack[26]));
				}
			}

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
