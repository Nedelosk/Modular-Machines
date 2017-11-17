package modularmachines.common.items.blocks;

import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import net.minecraftforge.common.capabilities.ICapabilityProvider;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.Module;
import modularmachines.api.modules.ModuleRegistry;
import modularmachines.api.modules.logic.IModuleLogic;
import modularmachines.client.model.ModelManager;
import modularmachines.common.blocks.tile.TileEntityMachine;
import modularmachines.common.utils.Translator;
import modularmachines.common.utils.WorldUtil;
import modularmachines.common.utils.content.IItemModelRegister;

public class ItemBlockMachine extends ItemBlock implements IItemModelRegister {

	public ItemBlockMachine(Block block) {
		super(block);
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		return new ItemMachineCapabilityProvider(stack);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (GuiScreen.isShiftKeyDown()) {
			IModuleLogic moduleLogic = stack.getCapability(ModuleRegistry.MODULE_LOGIC, null);
			if (stack.hasTagCompound()) {
				moduleLogic.readFromNBT(stack.getTagCompound());
			}
			tooltip.add(TextFormatting.WHITE.toString() + TextFormatting.ITALIC + Translator.translateToLocal("mm.tooltip.modular.info"));
			for (Module module : moduleLogic.getModules()) {
				if (module != null) {
					ItemStack itemStack = module.getParentItem();
					tooltip.add(TextFormatting.GRAY + module.getData().getDisplayName() + " - " + itemStack.getDisplayName());
				}
			}
		} else {
			tooltip.add(TextFormatting.WHITE.toString() + TextFormatting.ITALIC + Translator.translateToLocal("mm.tooltip.holdshift"));
		}
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
	
	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState) {
		if (!world.setBlockState(pos, newState, 3)) {
			return false;
		}
		IBlockState state = world.getBlockState(pos);
		if (state.getBlock() == this.block) {
			TileEntityMachine tile = WorldUtil.getTile(world, pos, TileEntityMachine.class);
			if (tile == null) {
				world.setBlockToAir(pos);
				return false;
			}
			IModuleLogic itemLogic = stack.getCapability(ModuleRegistry.MODULE_LOGIC, null);
			if (itemLogic != null) {
				if(stack.hasTagCompound()) {
					itemLogic.readFromNBT(stack.getTagCompound());
				}
				TileEntityMachine machine = (TileEntityMachine) tile;
				IModuleLogic machineLogic = machine.getLogic();
				int heading = MathHelper.floor(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
				machine.setOwner(player.getGameProfile());
				machine.setFacing(getFacingForHeading(heading));
				NBTTagCompound compound = itemLogic.writeToNBT(new NBTTagCompound());
				machineLogic.readFromNBT(compound);
				/*if (itemHandler.isAssembled() && itemHandler.getModular() != null) {
					tileHandler.setModular(itemHandler.getModular().copy(tileHandler));
				} else if (!itemHandler.isAssembled() && itemHandler.getAssembler() != null) {
					tileHandler.setAssembler(itemHandler.getAssembler().copy(tileHandler));
				} else {
					tileHandler.setAssembler(new ModularAssembler(tileHandler));
				}*/
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
	@SideOnly(Side.CLIENT)
	public void registerItemModels(Item item, ModelManager manager) {
		manager.registerItemModel(item, 0, "machine");
	}
	
	@Override
	public int getMetadata(int i) {
		return i;
	}
}
