package nedelosk.modularmachines.common.core.manager;

import nedelosk.modularmachines.api.materials.Material;
import nedelosk.modularmachines.api.materials.MaterialType;
import nedelosk.modularmachines.common.core.MMRegistry;
import nedelosk.modularmachines.common.modular.utils.MaterialManager;
import nedelosk.nedeloskcore.common.core.registry.NCItemManager;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictionaryManager {

	public static void init()
	{
		OreDictionary.registerOre("dustCoal", new ItemStack(MMItemManager.Dusts.item(), 1, 0));
		OreDictionary.registerOre("dustObsidian", new ItemStack(MMItemManager.Dusts.item(), 1, 1));
		OreDictionary.registerOre("dustIron", new ItemStack(MMItemManager.Dusts.item(), 1, 2));
		OreDictionary.registerOre("dustGold", new ItemStack(MMItemManager.Dusts.item(), 1, 3));
		OreDictionary.registerOre("dustDiamond", new ItemStack(MMItemManager.Dusts.item(), 1, 4));
		OreDictionary.registerOre("dustCopper", new ItemStack(MMItemManager.Dusts.item(), 1, 5));
		OreDictionary.registerOre("dustTin", new ItemStack(MMItemManager.Dusts.item(), 1, 6));
		OreDictionary.registerOre("dustSilver", new ItemStack(MMItemManager.Dusts.item(), 1, 7));
		OreDictionary.registerOre("dustLead", new ItemStack(MMItemManager.Dusts.item(), 1, 8));
		OreDictionary.registerOre("dustNickel", new ItemStack(MMItemManager.Dusts.item(), 1, 9));
		OreDictionary.registerOre("dustBronze", new ItemStack(MMItemManager.Dusts.item(), 1, 10));
		OreDictionary.registerOre("dustInvar", new ItemStack(MMItemManager.Dusts.item(), 1, 11));
		OreDictionary.registerOre("dustRuby", new ItemStack(MMItemManager.Dusts.item(), 1, 12));
		OreDictionary.registerOre("gearWood", new ItemStack(NCItemManager.Gears_Wood.item(), 1, 1));
		OreDictionary.registerOre("ingotBronze", new ItemStack(MMItemManager.Alloy_Ingots.item(), 1, 0));
		OreDictionary.registerOre("ingotInvar", new ItemStack(MMItemManager.Alloy_Ingots.item(), 1, 1));
		OreDictionary.registerOre("nuggetBronze", new ItemStack(MMItemManager.Alloy_Nuggets.item(), 1, 0));
		OreDictionary.registerOre("nuggetInvar", new ItemStack(MMItemManager.Alloy_Nuggets.item(), 1, 1));
		OreDictionary.registerOre("oreColumbite", new ItemStack(MMBlockManager.Ore_Others.item(), 1, 0));
		OreDictionary.registerOre("oreAluminium", new ItemStack(MMBlockManager.Ore_Others.item(), 1, 1));
		OreDictionary.registerOre("oreAluminum", new ItemStack(MMBlockManager.Ore_Others.item(), 1, 1));
		OreDictionary.registerOre("ingotNiobium", new ItemStack(MMItemManager.Ingots_Others.item(), 1, 0));
		OreDictionary.registerOre("nuggetNiobium", new ItemStack(MMItemManager.Nuggets_Others.item(), 1, 0));
		OreDictionary.registerOre("ingotTantalum", new ItemStack(MMItemManager.Ingots_Others.item(), 1, 1));
		OreDictionary.registerOre("nuggetTantalum", new ItemStack(MMItemManager.Nuggets_Others.item(), 1, 1));
		OreDictionary.registerOre("ingotAluminium", new ItemStack(MMItemManager.Ingots_Others.item(), 1, 2));
		OreDictionary.registerOre("nuggetAluminium", new ItemStack(MMItemManager.Nuggets_Others.item(), 1, 2));
		OreDictionary.registerOre("ingotAluminum", new ItemStack(MMItemManager.Ingots_Others.item(), 1, 2));
		OreDictionary.registerOre("nuggetAluminum", new ItemStack(MMItemManager.Nuggets_Others.item(), 1, 2));
		OreDictionary.registerOre("dustColumbite", new ItemStack(MMItemManager.Dusts_Others.item(), 1, 0));
		OreDictionary.registerOre("dustNiobium", new ItemStack(MMItemManager.Dusts_Others.item(), 1, 1));
		OreDictionary.registerOre("dustTantalum", new ItemStack(MMItemManager.Dusts_Others.item(), 1, 2));
		OreDictionary.registerOre("dustAluminium", new ItemStack(MMItemManager.Dusts_Others.item(), 1, 3));
		OreDictionary.registerOre("dustAlumium", new ItemStack(MMItemManager.Dusts_Others.item(), 1, 3));
		OreDictionary.registerOre("blockObsidian", Blocks.obsidian);
		
        for(int i = 0;i < MMRegistry.materials.size();i++) {
            ItemStack stack = new ItemStack(MMItemManager.Component_Plates.item(), 1, i);
            Material mat = MMRegistry.materials.get(i);
            if(mat.type == MaterialType.METAL || mat.type == MaterialType.METAL_Custom || mat == MMRegistry.Plastic){
            	MaterialManager.setMaterial(stack, mat);
            	if (MaterialManager.getMaterial(stack) != null){
            		OreDictionary.registerOre("plate" + mat.getOreDict() , stack);
            	}
            }
        }
        
        for(int i = 0;i < MMRegistry.materials.size();i++) {
            ItemStack stack = new ItemStack(MMItemManager.Component_Rods.item(), 1, i);
            Material mat = MMRegistry.materials.get(i);
            if(mat.type == MaterialType.METAL || mat.type == MaterialType.METAL_Custom){
            	MaterialManager.setMaterial(stack, mat);
            	if (MaterialManager.getMaterial(stack) != null){
            		OreDictionary.registerOre("rod" + mat.getOreDict() , stack);
            	}
            }
        }
        
        for(int i = 0;i < MMRegistry.materials.size();i++) {
            ItemStack stack = new ItemStack(MMItemManager.Component_Screws.item(), 1);
            Material mat = MMRegistry.materials.get(i);
            if(mat.type == MaterialType.METAL || mat.type == MaterialType.METAL_Custom){
            	MaterialManager.setMaterial(stack, mat);
            	if (MaterialManager.getMaterial(stack) != null){
            		OreDictionary.registerOre("screw" + mat.getOreDict() , stack);
            	}
            }
        }
        
        for(int i = 0;i < MMRegistry.materials.size();i++) {
            ItemStack stack = new ItemStack(MMItemManager.Component_Gears.item(), 1, i);
            Material mat = MMRegistry.materials.get(i);
            if(mat.type == MaterialType.METAL || mat.type == MaterialType.METAL_Custom){
            	MaterialManager.setMaterial(stack, mat);
            	if (MaterialManager.getMaterial(stack) != null){
            		OreDictionary.registerOre("gear" + mat.getOreDict(), stack);
            	}
            }
        }
        
        for(int i = 0;i < MMRegistry.materials.size();i++) {
            ItemStack stack = new ItemStack(MMItemManager.Component_Saw_Blades.item(), 1, i);
            Material mat = MMRegistry.materials.get(i);
            if(mat.type == MaterialType.METAL || mat.type == MaterialType.METAL_Custom){
            	MaterialManager.setMaterial(stack, mat);
            	if (MaterialManager.getMaterial(stack) != null){
            		OreDictionary.registerOre("sawBlade" + mat.getOreDict(), stack);
            	}
            }
        }
		
        for(int i = 0;i < MMRegistry.materials.size();i++) {
            ItemStack stack = new ItemStack(MMBlockManager.Metal_Blocks.block(), 1, i);
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
