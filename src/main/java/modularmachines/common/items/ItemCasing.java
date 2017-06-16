package modularmachines.common.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.client.model.ModelManager;
import modularmachines.common.core.Registry;
import modularmachines.common.core.TabModularMachines;
import modularmachines.common.utils.content.IItemModelRegister;

public class ItemCasing extends Item implements IItemModelRegister {

	private String[] casings = new String[] { "wood", "bronze", "iron", "steel", "magmarium" };

	public ItemCasing() {
		setUnlocalizedName("casing");
		setHasSubtypes(true);
		setCreativeTab(TabModularMachines.tabModules);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerItemModels(Item item, ModelManager manager) {
		for (int i = 0; i < casings.length; i++) {
			manager.registerItemModel(item, i, "casing/" + casings[i]);
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return Registry.setUnlocalizedItemName(getUnlocalizedName().replace("item.", "") + "." + casings[stack.getItemDamage()]);
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tab, NonNullList<ItemStack> subItems) {
		for (int i = 0; i < casings.length; i++) {
			subItems.add(new ItemStack(item, 1, i));
		}
	}
}
