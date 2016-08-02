package de.nedelosk.modularmachines.common.items;

import java.util.List;
import java.util.Locale;

import de.nedelosk.modularmachines.api.Translator;
import de.nedelosk.modularmachines.api.material.IMaterial;
import de.nedelosk.modularmachines.api.material.IMetalMaterial;
import de.nedelosk.modularmachines.api.material.MaterialList;
import de.nedelosk.modularmachines.common.core.TabModularMachines;
import de.nedelosk.modularmachines.common.utils.IColoredItem;
import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemComponent extends Item implements IColoredItem, IItemModelRegister {

	public MaterialList<IMetalMaterial> materials;
	public String componentName;

	public ItemComponent(String name, IMetalMaterial... materials) {
		this.setUnlocalizedName("component." + name);
		this.setCreativeTab(TabModularMachines.tabModularMachines);
		this.setHasSubtypes(true);
		this.componentName = name;
		this.materials = new MaterialList<IMetalMaterial>(materials);
	}

	@Override
	public void registerModel(Item item, IModelManager manager) {
		for(IMetalMaterial material : materials){
			manager.registerItemModel(item, materials.getIndex(material), "components/" + componentName);
		}
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return materials.get(stack.getItemDamage()).getLocalizedName() + " " + Translator.translateToLocal("component." + componentName + ".name");
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "component." + componentName + "." + materials.get(stack.getItemDamage()).getName().toLowerCase(Locale.ENGLISH);
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for(IMaterial material : materials) {
			list.add(getStack((IMetalMaterial) material));
		}
	}

	public ItemStack getStack(IMetalMaterial material, int size){
		if(materials.getIndex(material) > -1){
			return new ItemStack(this, size, materials.getIndex(material));
		}
		return null;
	}

	public ItemStack getStack(IMetalMaterial material){
		return getStack(material, 1);
	}

	@Override
	public int getColorFromItemstack(ItemStack stack, int renderpass) {
		if (materials.get(stack.getItemDamage()) != null) {
			return materials.get(stack.getItemDamage()).getColor();
		}
		return 16777215;
	}
}
