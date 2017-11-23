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

public class ItemModuleMeta extends Item implements IItemModelRegister {
	
	public String[] names;
	public String uln;
	
	public ItemModuleMeta(String uln, String[] names) {
		setUnlocalizedName(uln);
		setHasSubtypes(true);
		setCreativeTab(TabModularMachines.tabModularMachines);
		this.names = names;
		this.uln = uln;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerItemModels(Item item, ModelManager manager) {
		for (int i = 0; i < names.length; ++i) {
			manager.registerItemModel(item, i, uln + "/" + names[i]);
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
	public String getUnlocalizedName(ItemStack itemstack) {
		return Registry.setUnlocalizedItemName(uln + "_" + names[itemstack.getItemDamage()]);
	}
}
