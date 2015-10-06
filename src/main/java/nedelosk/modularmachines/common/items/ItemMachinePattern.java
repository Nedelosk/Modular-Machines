package nedelosk.modularmachines.common.items;

import java.util.List;

import nedelosk.modularmachines.common.core.tabs.TabModularMachinesModules;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemMachinePattern extends Item {
	
	public String[] components;
	public int[] cost;
	public IIcon[] icons;
	public String type;
	
    public ItemMachinePattern(String type, String[] patterns, int[] cost) {
        setUnlocalizedName("patternMachine" + type);
        this.setCreativeTab(TabModularMachinesModules.instance);
        this.components = patterns;
        this.cost = cost;
        this.type = type;
    }
    
    @Override
    public void registerIcons(IIconRegister IIconRegister) {
    	icons = new IIcon[components.length];
    	for(int i = 0;i < components.length;i++)
    		icons[i] = IIconRegister.registerIcon("modularmachines:patterns/" + type + "_" + components[i]);
    }
    
    @Override
    public void getSubItems(Item item, CreativeTabs p_150895_2_, List list) {
    	for(int i = 0;i < components.length;i++)
    		list.add(new ItemStack(item, 1, i));
    }
    
    @Override
    public IIcon getIconFromDamage(int meta) {
    	return icons[meta];
    }
    
    @Override
    public String getUnlocalizedName(ItemStack stack) {
    	return getUnlocalizedName() + stack.getItemDamage();
    }

    public int getPatternCost(ItemStack pattern) {
    	if(pattern == null)
    		return 2;
        return cost[pattern.getItemDamage()];
    }
}
