package de.nedelosk.modularmachines.common.items;

import java.util.List;

import de.nedelosk.modularmachines.common.core.Registry;
import de.nedelosk.modularmachines.common.core.TabModularMachines;
import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemCasing extends Item implements IItemModelRegister {

	private String[] casings = new String[]{"bronze", "iron", "steel", "magmarium"};

	public ItemCasing() {
		setUnlocalizedName("casing");
		setHasSubtypes(true);
		setCreativeTab(TabModularMachines.tabModules);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel(Item item, IModelManager manager) {
		for(int i = 0;i < casings.length;i++){
			manager.registerItemModel(item, i, "casing/" + casings[i]);
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return Registry.setUnlocalizedItemName(getUnlocalizedName().replace("item.", "") + "." + casings[stack.getItemDamage()]);
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tab, List<ItemStack> subItems) {
		for(int i = 0; i < casings.length; i++) {
			subItems.add(new ItemStack(item, 1, i));
		}
	}

}
