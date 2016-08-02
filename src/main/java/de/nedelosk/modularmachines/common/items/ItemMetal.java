package de.nedelosk.modularmachines.common.items;

import java.util.List;

import de.nedelosk.modularmachines.api.material.IMaterial;
import de.nedelosk.modularmachines.api.material.MaterialList;
import de.nedelosk.modularmachines.common.core.Registry;
import de.nedelosk.modularmachines.common.core.TabModularMachines;
import de.nedelosk.modularmachines.common.utils.IColoredItem;
import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
	public void registerModel(Item item, IModelManager manager) {
		int listIndex=0;
		for(MaterialList list : materials){
			for(IMaterial material : list.getMaterials()){
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
	public void getSubItems(Item item, CreativeTabs tab, List subItems) {
		for(MaterialList list : materials){
			for(IMaterial material : list.getMaterials()){
				subItems.add(getStack(material));
			}
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return Registry.setUnlocalizedItemName(uln + getName(itemstack.getItemDamage()));
	}

	public ItemStack getStack(IMaterial material){
		return getStack(material, 1);
	}

	public ItemStack getStack(IMaterial material, int size){
		int listIndex=0;
		for(MaterialList list : materials){
			int index = list.getIndex(material);
			if(index > -1){
				return new ItemStack(this, size, listIndex * 10 + index);
			}
			listIndex++;
		}
		return null;
	}

	public ItemStack getStack(String oreDict){
		return getStack(oreDict, 1);
	}

	public ItemStack getStack(String oreDict, int size){
		int listIndex=0;
		for(MaterialList list : materials){
			int index = list.getIndex(list.getFromOre(oreDict));
			if(index > -1){
				return new ItemStack(this, size, listIndex * 10 + index);
			}
			listIndex++;
		}
		return null;
	}

	private String getName(int index) {
		int listIndex = 0;
		while (index > 9) {
			listIndex++;
			index -= 10;
		}
		return materials[listIndex].getName(index);
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
