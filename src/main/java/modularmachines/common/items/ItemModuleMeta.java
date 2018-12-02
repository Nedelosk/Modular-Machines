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

public class ItemModuleMeta extends Item implements IItemModelRegister {
	
	private String[] names;
	private String uln;
	
	public ItemModuleMeta(String uln, String[] names) {
		setTranslationKey(uln);
		setHasSubtypes(true);
		setCreativeTab(Tabs.tabModularMachines);
		this.names = names;
		this.uln = uln;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerItemModels(ModelManager manager) {
		for (int i = 0; i < names.length; ++i) {
			manager.registerItemModel(this, i, uln + "/" + names[i]);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list) {
		if (isInCreativeTab(tab)) {
			for (int i = 0; i < names.length; i++) {
				list.add(new ItemStack(this, 1, i));
			}
		}
	}
	
	@Override
	public String getTranslationKey(ItemStack itemstack) {
		int meta = itemstack.getItemDamage();
		if (meta >= names.length || meta < 0) {
			return "UNKNOWN";
		}
		return Registry.getItemName(uln + "_" + names[meta]);
	}
}
