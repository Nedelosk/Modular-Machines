package modularmachines.common.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.client.model.ModelManager;
import modularmachines.common.core.Registry;
import modularmachines.common.core.Tabs;
import modularmachines.common.utils.content.IItemModelRegister;

public class ItemModules extends Item implements IItemModelRegister {
	
	public ItemModules() {
		setUnlocalizedName("modules");
		setHasSubtypes(true);
		setCreativeTab(Tabs.tabModularMachines);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerItemModels(ModelManager manager) {
		for (ModuleItems item : ModuleItems.values()) {
			manager.registerItemModel(this, item.ordinal(), "modules/" + item.getName());
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list) {
		if (isInCreativeTab(tab)) {
			for (ModuleItems item : ModuleItems.values()) {
				list.add(item.get());
			}
		}
	}
	
	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return Registry.getItemName("modules_" + ModuleItems.getItem(itemstack.getItemDamage()).getName());
	}
}
