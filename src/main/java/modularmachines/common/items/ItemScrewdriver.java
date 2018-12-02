package modularmachines.common.items;

import javax.annotation.Nullable;
import java.util.List;

import org.apache.commons.lang3.text.WordUtils;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.IScrewdriver;
import modularmachines.api.modules.container.IModuleContainer;
import modularmachines.client.model.ModelManager;
import modularmachines.common.core.Registry;
import modularmachines.common.core.Tabs;
import modularmachines.common.modules.ModuleCapabilities;
import modularmachines.common.utils.Translator;
import modularmachines.common.utils.content.IItemModelRegister;

@Optional.InterfaceList({ /*@Interface(iface = "buildcraft.api.tools.IToolWrench", modid = "BuildCraftAPI|core"),*/
		@Optional.Interface(iface = "cofh.api.item.IToolHammer", modid = "cofhapi|item")})
public class ItemScrewdriver extends Item implements IItemModelRegister, IScrewdriver { //implements IToolWrench {
	
	public ItemScrewdriver() {
		setCreativeTab(Tabs.tabModularMachines);
		setTranslationKey("screwdriver");
		setHarvestLevel("wrench", 0);
		setMaxStackSize(1);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		Block block = worldIn.getBlockState(pos).getBlock();
		if (block.rotateBlock(worldIn, pos, facing)) {
			player.swingArm(hand);
			return EnumActionResult.SUCCESS;
		}
		return EnumActionResult.FAIL;
	}
	
	@Override
	public boolean doesSneakBypassUse(ItemStack stack, IBlockAccess world, BlockPos pos, EntityPlayer player) {
		EnumFacing facing = player.getHorizontalFacing().getOpposite();
		TileEntity tileEntity = world.getTileEntity(pos);
		if (tileEntity == null || !tileEntity.hasCapability(ModuleCapabilities.MODULE_CONTAINER, facing)) {
			return false;
		}
		IModuleContainer container = tileEntity.getCapability(ModuleCapabilities.MODULE_CONTAINER, facing);
		return container != null;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		if (!player.isSneaking()) {
			return super.onItemRightClick(world, player, hand);
		}
		ItemStack itemStack = player.getHeldItem(hand);
		EnumFacing facing = getSelectedFacing(itemStack);
		if (facing != null) {
			if (facing == EnumFacing.UP) {
				facing = null;
			} else {
				facing = EnumFacing.byIndex(facing.ordinal() + 1);
			}
		} else {
			facing = EnumFacing.NORTH;
		}
		setSelectedFacing(itemStack, facing);
		if (!world.isRemote) {
			String facingText = facing != null ? WordUtils.capitalize(facing.getName()) : "None";
			player.sendStatusMessage(new TextComponentTranslation("mm.message.screwdriver.facing", new TextComponentString(facingText)), true);
		}
		return ActionResult.newResult(EnumActionResult.SUCCESS, itemStack);
	}
	
	@Override
	public boolean isFull3D() {
		return true;
	}
	
	@Override
	public void registerItemModels(ModelManager manager) {
		manager.registerItemModel(this, 0);
	}
	
	@Nullable
	@Override
	public EnumFacing getSelectedFacing(ItemStack itemStack) {
		NBTTagCompound compound = itemStack.getTagCompound();
		if (compound == null || !compound.hasKey("Facing")) {
			return null;
		}
		byte facing = compound.getByte("Facing");
		return EnumFacing.byIndex(facing);
	}
	
	@Override
	public void setSelectedFacing(ItemStack itemStack, @Nullable EnumFacing facing) {
		NBTTagCompound compound = itemStack.getTagCompound();
		if (facing == null) {
			if (compound != null) {
				compound.removeTag("Facing");
				if (compound.isEmpty()) {
					itemStack.setTagCompound(null);
				}
			}
			return;
		}
		if (compound == null) {
			itemStack.setTagCompound(compound = new NBTTagCompound());
		}
		compound.setByte("Facing", (byte) facing.ordinal());
	}
	
	@Override
	public String getHighlightTip(ItemStack item, String displayName) {
		EnumFacing facing = getSelectedFacing(item);
		String facingText = facing != null ? WordUtils.capitalize(facing.getName()) : "";
		if (!facingText.isEmpty()) {
			facingText = " (" + facingText + ")";
		}
		return super.getHighlightTip(item, displayName) + facingText;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		EnumFacing facing = getSelectedFacing(stack);
		if (facing != null) {
			tooltip.add(Translator.translateToLocalFormatted("mm.tooltip.screwdriver.facing", WordUtils.capitalize(facing.getName())));
		}
	}
	
	@Override
	public String getTranslationKey(ItemStack stack) {
		return Registry.getItemName(getTranslationKey().replace("item.", ""));
	}
}
