package nedelosk.modularmachines.common.items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import nedelosk.modularmachines.common.core.tabs.TabModularMachinesModules;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.crafting.PatternBuilder;
import tconstruct.library.util.IPattern;

public class ItemMachinePattern extends Item implements IPattern {
	
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
    public IIcon getIcon(ItemStack stack, int pass) {
    	return icons[stack.getItemDamage()];
    }
    
    @Override
    public String getUnlocalizedName(ItemStack stack) {
    	return getUnlocalizedName() + stack.getItemDamage();
    }

    @Override
    public int getPatternCost(ItemStack pattern) {
        return cost[pattern.getItemDamage()];
    }

    @Override
    public ItemStack getPatternOutput(ItemStack pattern, ItemStack input, PatternBuilder.MaterialSet set) {
        return TConstructRegistry.getPartMapping(this, pattern.getItemDamage(), set.materialID);
    }
}
