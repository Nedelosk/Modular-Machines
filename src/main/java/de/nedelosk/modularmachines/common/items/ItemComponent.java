package de.nedelosk.modularmachines.common.items;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import de.nedelosk.modularmachines.api.Translator;
import de.nedelosk.modularmachines.api.material.IMetalMaterial;
import de.nedelosk.modularmachines.common.core.TabModularMachines;
import de.nedelosk.modularmachines.common.utils.IColoredItem;
import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemComponent extends Item implements IColoredItem, IItemModelRegister {

	public ArrayList<IMetalMaterial> materials = Lists.newArrayList();
	public String componentName;

	public ItemComponent(String name, IMetalMaterial... materials) {
		this.setUnlocalizedName("component." + name);
		this.setCreativeTab(TabModularMachines.tabModularMachines);
		this.componentName = name;
		for(IMetalMaterial material : materials){
			this.materials.add(material);
		}
	}

	@Override
	public void registerModel(Item item, IModelManager manager) {
		for(int i = 0; i < materials.size(); i++){
			manager.registerItemModel(item, i, "components/" + componentName);
		}
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return materials.get(stack.getItemDamage()).getLocalizedName() + " " + Translator.translateToLocal("component." + componentName + ".name");
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "component." + componentName + "." + materials.get(stack.getItemDamage()).getName();
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for(int i = 0; i < materials.size(); i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	public int getColorFromItemstack(ItemStack stack, int renderpass) {
		if (materials.size() > stack.getItemDamage() && materials.get(stack.getItemDamage()) != null) {
			return materials.get(stack.getItemDamage()).getColor();
		}
		return 16777215;
	}
}
