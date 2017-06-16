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
import modularmachines.common.materials.EnumMaterial;
import modularmachines.common.materials.MaterialList;
import modularmachines.common.utils.Translator;
import modularmachines.common.utils.content.IColoredItem;
import modularmachines.common.utils.content.IItemModelRegister;

public class ItemMetal extends Item implements IItemModelRegister, IColoredItem {

	private final MaterialList[] materials;
	private final String uln;

	public ItemMetal(String uln, MaterialList... materials) {
		setCreativeTab(TabModularMachines.tabModularMachines);
		setUnlocalizedName(uln);
		setHasSubtypes(true);
		this.materials = materials;
		this.uln = uln;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerItemModels(Item item, ModelManager manager) {
		int listIndex = 0;
		for (MaterialList list : materials) {
			for (EnumMaterial material : list.getMaterials()) {
				manager.registerItemModel(item, listIndex * 10 + list.getIndex(material), "components/" + uln);
			}
			listIndex++;
		}
	}

	@Override
	public int getColorFromItemstack(ItemStack stack, int tintIndex) {
		return getColor(stack.getItemDamage());
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tab, NonNullList<ItemStack> subItems) {
		for (MaterialList list : materials) {
			for (EnumMaterial material : list.getMaterials()) {
				subItems.add(getStack(material));
			}
		}
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return getMaterial(stack.getItemDamage()).getLocalizedName() + " " + Translator.translateToLocal("component." + uln + ".name");
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return Registry.setUnlocalizedItemName(uln + getName(itemstack.getItemDamage()));
	}

	public ItemStack getStack(EnumMaterial material) {
		return getStack(material, 1);
	}

	public ItemStack getStack(EnumMaterial material, int size) {
		int listIndex = 0;
		for (MaterialList list : materials) {
			int index = list.getIndex(material);
			if (index > -1) {
				return new ItemStack(this, size, listIndex * 10 + index);
			}
			listIndex++;
		}
		return null;
	}

	public ItemStack getStack(String oreDict) {
		return getStack(oreDict, 1);
	}

	public ItemStack getStack(String oreDict, int size) {
		int listIndex = 0;
		for (MaterialList list : materials) {
			int index = list.getIndex(list.getFromOre(oreDict));
			if (index > -1) {
				return new ItemStack(this, size, listIndex * 10 + index);
			}
			listIndex++;
		}
		return null;
	}

	private EnumMaterial getMaterial(int index) {
		int listIndex = 0;
		while (index > 9) {
			listIndex++;
			index -= 10;
		}
		return materials[listIndex].get(index);
	}

	private String getName(int index) {
		return getMaterial(index).getName();
	}

	private int getColor(int index) {
		int listIndex = 0;
		while (index > 9) {
			listIndex++;
			index -= 10;
		}
		return materials[listIndex].getColor(index);
	}
}
