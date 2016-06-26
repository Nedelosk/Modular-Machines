package de.nedelosk.modularmachines.common.items;

import java.util.List;

import de.nedelosk.forestmods.library.Tabs;
import de.nedelosk.modularmachines.common.core.Registry;
import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemToolParts extends ItemForest implements IItemModelRegister {

	public String[] parts = new String[] { "file_handle", "file_head_stone", "file_head_iron", "file_head_diamond", "knife_handle", "knife_head", "cutter_head",
	"cutter_handle" };

	public ItemToolParts() {
		super(null, Tabs.tabForestMods);
		setHasSubtypes(true);
		setUnlocalizedName("tool.parts");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel(Item item, IModelManager manager) {
		for(int i = 0; i < parts.length; ++i) {
			manager.registerItemModel(item, i, "toolparts/" + parts[i]);
		}
	}

	@Override
	public void getSubItems(Item id, CreativeTabs tab, List list) {
		for(int i = 0; i < parts.length; i++) {
			list.add(new ItemStack(id, 1, i));
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return Registry.setUnlocalizedItemName("parts." + itemstack.getItemDamage());
	}
}
