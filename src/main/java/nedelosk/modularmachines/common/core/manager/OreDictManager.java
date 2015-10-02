package nedelosk.modularmachines.common.core.manager;

import nedelosk.modularmachines.api.modular.material.Material;
import nedelosk.modularmachines.api.modular.material.MaterialType;
import nedelosk.modularmachines.common.core.MMBlocks;
import nedelosk.modularmachines.common.core.MMItems;
import nedelosk.modularmachines.common.core.MMRegistry;
import nedelosk.modularmachines.common.machines.utils.MaterialManager;
import nedelosk.nedeloskcore.common.core.registry.NCItems;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictManager {

	public static void init()
	{
		OreDictionary.registerOre("dustCoal", new ItemStack(MMItems.Dusts.item(), 1, 0));
		OreDictionary.registerOre("dustObsidian", new ItemStack(MMItems.Dusts.item(), 1, 1));
		OreDictionary.registerOre("dustIron", new ItemStack(MMItems.Dusts.item(), 1, 2));
		OreDictionary.registerOre("dustGold", new ItemStack(MMItems.Dusts.item(), 1, 3));
		OreDictionary.registerOre("dustDiamond", new ItemStack(MMItems.Dusts.item(), 1, 4));
		OreDictionary.registerOre("dustCopper", new ItemStack(MMItems.Dusts.item(), 1, 5));
		OreDictionary.registerOre("dustTin", new ItemStack(MMItems.Dusts.item(), 1, 6));
		OreDictionary.registerOre("dustSilver", new ItemStack(MMItems.Dusts.item(), 1, 7));
		OreDictionary.registerOre("dustLead", new ItemStack(MMItems.Dusts.item(), 1, 8));
		OreDictionary.registerOre("dustNickel", new ItemStack(MMItems.Dusts.item(), 1, 9));
		OreDictionary.registerOre("dustBronze", new ItemStack(MMItems.Dusts.item(), 1, 10));
		OreDictionary.registerOre("dustInvar", new ItemStack(MMItems.Dusts.item(), 1, 11));
		OreDictionary.registerOre("dustRuby", new ItemStack(MMItems.Dusts.item(), 1, 12));
		OreDictionary.registerOre("gearWood", new ItemStack(NCItems.Gears_Wood.item(), 1, 1));
		OreDictionary.registerOre("ingotBronze", new ItemStack(MMItems.Alloy_Ingots.item(), 1, 0));
		OreDictionary.registerOre("ingotInvar", new ItemStack(MMItems.Alloy_Ingots.item(), 1, 1));
		OreDictionary.registerOre("nuggetBronze", new ItemStack(MMItems.Alloy_Nuggets.item(), 1, 0));
		OreDictionary.registerOre("nuggetInvar", new ItemStack(MMItems.Alloy_Nuggets.item(), 1, 1));
		OreDictionary.registerOre("oreColumbite", new ItemStack(MMBlocks.Ore_Others.item(), 1, 0));
		OreDictionary.registerOre("oreAluminium", new ItemStack(MMBlocks.Ore_Others.item(), 1, 1));
		OreDictionary.registerOre("ingotNiobium", new ItemStack(MMItems.Ingots_Others.item(), 1, 0));
		OreDictionary.registerOre("nuggetNiobium", new ItemStack(MMItems.Nuggets_Others.item(), 1, 0));
		OreDictionary.registerOre("ingotTantalum", new ItemStack(MMItems.Ingots_Others.item(), 1, 1));
		OreDictionary.registerOre("nuggetTantalum", new ItemStack(MMItems.Nuggets_Others.item(), 1, 1));
		OreDictionary.registerOre("ingotAluminium", new ItemStack(MMItems.Ingots_Others.item(), 1, 2));
		OreDictionary.registerOre("nuggetAluminium", new ItemStack(MMItems.Nuggets_Others.item(), 1, 2));
		OreDictionary.registerOre("dustColumbite", new ItemStack(MMItems.Dusts_Others.item(), 1, 0));
		OreDictionary.registerOre("dustNiobium", new ItemStack(MMItems.Dusts_Others.item(), 1, 1));
		OreDictionary.registerOre("dustTantalum", new ItemStack(MMItems.Dusts_Others.item(), 1, 2));
		OreDictionary.registerOre("dustAluminium", new ItemStack(MMItems.Dusts_Others.item(), 1, 3));
		OreDictionary.registerOre("blockObsidian", Blocks.obsidian);
		
        for(int i = 0;i < MMRegistry.materials.size();i++) {
            ItemStack stack = new ItemStack(MMItems.Component_Plates.item(), 1, i);
            Material mat = MMRegistry.materials.get(i);
            if(mat.type == MaterialType.METAL || mat.type == MaterialType.METAL_Custom || mat == MMRegistry.Plastic){
            	MaterialManager.setMaterial(stack, mat);
            	if (MaterialManager.getMaterial(stack) != null){
            		OreDictionary.registerOre("plate" + mat.getOreDict() , stack);
            	}
            }
        }
        
        for(int i = 0;i < MMRegistry.materials.size();i++) {
            ItemStack stack = new ItemStack(MMItems.Component_Rods.item(), 1, i);
            Material mat = MMRegistry.materials.get(i);
            if(mat.type == MaterialType.METAL || mat.type == MaterialType.METAL_Custom){
            	MaterialManager.setMaterial(stack, mat);
            	if (MaterialManager.getMaterial(stack) != null){
            		OreDictionary.registerOre("rod" + mat.getOreDict() , stack);
            	}
            }
        }
        
        for(int i = 0;i < MMRegistry.materials.size();i++) {
            ItemStack stack = new ItemStack(MMItems.Component_Screws.item(), 1);
            Material mat = MMRegistry.materials.get(i);
            if(mat.type == MaterialType.METAL || mat.type == MaterialType.METAL_Custom){
            	MaterialManager.setMaterial(stack, mat);
            	if (MaterialManager.getMaterial(stack) != null){
            		OreDictionary.registerOre("screw" + mat.getOreDict() , stack);
            	}
            }
        }
        
        for(int i = 0;i < MMRegistry.materials.size();i++) {
            ItemStack stack = new ItemStack(MMItems.Component_Gears.item(), 1, i);
            Material mat = MMRegistry.materials.get(i);
            if(mat.type == MaterialType.METAL || mat.type == MaterialType.METAL_Custom){
            	MaterialManager.setMaterial(stack, mat);
            	if (MaterialManager.getMaterial(stack) != null){
            		OreDictionary.registerOre("gear" + mat.getOreDict(), stack);
            	}
            }
        }
		
        for(int i = 0;i < MMRegistry.materials.size();i++) {
            ItemStack stack = new ItemStack(MMBlocks.Metal_Blocks.block(), 1, i);
            Material mat = MMRegistry.materials.get(i);
            if(mat.type == MaterialType.METAL){
            	MaterialManager.setMaterial(stack, mat);
            	if (MaterialManager.getMaterial(stack) != null){
            		OreDictionary.registerOre("block" + mat.getOreDict() , stack);
            	}
            }
        }
	}
	
}
