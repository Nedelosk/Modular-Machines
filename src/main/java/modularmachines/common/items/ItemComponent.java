package modularmachines.common.items;

import java.util.Locale;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import modularmachines.client.model.ModelManager;
import modularmachines.common.core.TabModularMachines;
import modularmachines.common.materials.EnumMaterial;
import modularmachines.common.materials.MaterialList;
import modularmachines.common.utils.Translator;
import modularmachines.common.utils.content.IColoredItem;
import modularmachines.common.utils.content.IItemModelRegister;

public class ItemComponent extends Item implements IColoredItem, IItemModelRegister {

	public MaterialList materials;
	public String componentName;

	public ItemComponent(String name, EnumMaterial... materials) {
		this.setUnlocalizedName("component." + name);
		this.setCreativeTab(TabModularMachines.tabModularMachines);
		this.setHasSubtypes(true);
		this.componentName = name;
		this.materials = new MaterialList(materials);
	}

	@Override
	public void registerItemModels(Item item, ModelManager manager) {
		for (EnumMaterial material : materials) {
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
	public void getSubItems(Item item, CreativeTabs tab, NonNullList<ItemStack> list) {
		for (EnumMaterial material : materials) {
			list.add(getStack(material));
		}
	}

	public ItemStack getStack(EnumMaterial material, int size) {
		if (materials.getIndex(material) > -1) {
			return new ItemStack(this, size, materials.getIndex(material));
		}
		return null;
	}

	public ItemStack getStack(EnumMaterial material) {
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
