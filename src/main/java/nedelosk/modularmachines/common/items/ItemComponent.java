package nedelosk.modularmachines.common.items;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.forestcore.library.modules.manager.IItemManager;
import nedelosk.modularmachines.common.core.TabModularMachines;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemComponent extends Item {

	public ArrayList<List> metas = Lists.newArrayList();
	public String componentName;

	public ItemComponent(String name) {
		this.setUnlocalizedName("component." + name);
		this.setCreativeTab(TabModularMachines.components);
		this.componentName = name;
	}

	public static ItemComponent addMetaData(IItemManager item, int color, String name, String... oreDict) {
		((ItemComponent) item.item()).metas.add(Lists.newArrayList(color, name, oreDict));
		return (ItemComponent) item.item();
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "component." + componentName + "." + stack.getItemDamage();
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < metas.size(); i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
		itemIcon = iconRegister.registerIcon("modularmachines:components/" + componentName);
	}

	@Override
	public int getColorFromItemStack(ItemStack stack, int renderpass) {
		if (metas.size() > stack.getItemDamage() && metas.get(stack.getItemDamage()) != null)
			return (int) metas.get(stack.getItemDamage()).get(0);
		return super.getColorFromItemStack(stack, renderpass);
	}

}
