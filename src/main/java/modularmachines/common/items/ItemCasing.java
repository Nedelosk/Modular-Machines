package modularmachines.common.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.client.model.ModelManager;
import modularmachines.common.core.Registry;
import modularmachines.common.core.Tabs;
import modularmachines.common.modules.ModuleRegistry;
import modularmachines.common.utils.content.IItemModelRegister;

public class ItemCasing extends Item implements IItemModelRegister {
	
	private String[] casings = new String[]{"bronze", "iron", "steel", "magmarium"};
	
	public ItemCasing() {
		setUnlocalizedName("casing");
		setHasSubtypes(true);
		setCreativeTab(Tabs.tabModularMachines);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerItemModels(ModelManager manager) {
		for (int i = 0; i < casings.length; i++) {
			manager.registerItemModel(this, i, "casing/" + casings[i]);
		}
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return Registry.getItemName(getUnlocalizedName().replace("item.", "") + "_" + casings[stack.getItemDamage()]);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems) {
		if (isInCreativeTab(tab)) {
			for (int i = 0; i < casings.length; i++) {
				subItems.add(new ItemStack(this, 1, i));
			}
		}
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		return ModuleRegistry.INSTANCE.placeModule(worldIn, pos, player, hand, facing) ? EnumActionResult.SUCCESS : EnumActionResult.FAIL;
	}
}
