package nedelosk.modularmachines.common.core.registry;

import nedelosk.modularmachines.api.ModularMachinesApi;
import nedelosk.modularmachines.api.techtree.TechPointTypes;
import nedelosk.modularmachines.api.techtree.TechTreeCategories;
import nedelosk.modularmachines.api.techtree.TechTreeEntry;
import nedelosk.modularmachines.api.techtree.TechTreePage;
import nedelosk.modularmachines.common.core.MMItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class TechTreeRegistry {

	public static void preInit()
	{
		TechTreeCategories.registerCategory("BASIC", new ResourceLocation("modularmachines", "textures/items/modules/moduleEnergyManager_0.png"), new ResourceLocation("modularmachines", "textures/gui/gui_techtreeback.png"));
		TechTreeCategories.registerCategory("ENERGY", new ResourceLocation("modularmachines", "textures/items/modules/moduleEnergyManager_0.png"), new ResourceLocation("modularmachines", "textures/gui/gui_techtreeback.png"));
		TechTreeCategories.registerCategory("MODULE", new ResourceLocation("modularmachines", "textures/items/modules/moduleEnergyManager_0.png"), new ResourceLocation("modularmachines", "textures/gui/gui_techtreeback.png"));
		registerBasicStuff();
		registerEnergyStuff();
		registerModuleStuff();
	}
	
	public static ItemStack getItemStackFromData(String name, int tier)
	{
		ItemStack stack = new ItemStack(MMItems.Module_Items.item());
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setString("Name", name);
		nbt.setInteger("Tier", tier);
		stack.setTagCompound(nbt);
		return stack;
	}
	
	public static void registerBasicStuff()
	{
		
		new TechTreeEntry("MODULE.BASE", "BASIC", 1, TechPointTypes.VERY_EASY, 0, 0, new ItemStack(MMItems.Module_Items.item())).setPages(new TechTreePage("mm.techtree_page.BASIC.MODULE.0")).setParents("PRODUCER").registerTechTreeEntry();
		new TechTreeEntry("MODULE.IMPROVED", "BASIC", 1, TechPointTypes.VERY_EASY, -1, 0, new ItemStack(MMItems.Module_Items.item(), 1, 1)).setParents("MODULE.BASE").setPages(new TechTreePage("mm.techtree_page.MODULE.IMPROVED.0")).registerTechTreeEntry();
		new TechTreeEntry("MODULE.ADVANCED", "BASIC", 1, TechPointTypes.VERY_EASY, -2, 0, new ItemStack(MMItems.Module_Items.item(), 1, 2)).setParents("MODULE.IMPROVED").setPages(new TechTreePage("mm.techtree_page.MODULE.ADVANCED.0")).registerTechTreeEntry();
		
		//Furnace Module
		new TechTreeEntry("MODULE.FURNACE.BASE", "BASIC", 1, TechPointTypes.VERY_EASY, 2, 1, getItemStackFromData("moduleProducerFurnace", 0)).setPages(new TechTreePage("mm.techtree_page.MODULE.FURNACE.BASE.0")).setParents("MODULE.BASE").registerTechTreeEntry();
		new TechTreeEntry("MODULE.FURNACE.IMPROVED", "BASIC", 1, TechPointTypes.VERY_EASY, 2, 2, getItemStackFromData("moduleProducerFurnace", 1)).setPages(new TechTreePage("mm.techtree_page.MODULE.FURNACE.IMPROVED.0")).setParents("MODULE.FURNACE.BASE", "MODULE.IMPROVED").registerTechTreeEntry();
		new TechTreeEntry("MODULE.FURNACE.ADVANCED", "BASIC", 1, TechPointTypes.VERY_EASY, 2, 3, getItemStackFromData("moduleProducerFurnace", 2)).setPages(new TechTreePage("mm.techtree_page.MODULE.FURNACE.ADVANCED.0")).setParents("MODULE.FURNACE.IMPROVED", "MODULE.ADVANCED").registerTechTreeEntry();
		
		//Alloy Smelter Module
		new TechTreeEntry("MODULE.ALLOYSMELTER.BASE", "BASIC", 1, TechPointTypes.VERY_EASY, 4, 1, getItemStackFromData("moduleProducerAlloySmelter", 0)).setPages(new TechTreePage("mm.techtree_page.MODULE.ALLOYSMELTER.BASE.0")).setParents("MODULE.FURNACE.BASE").registerTechTreeEntry();
		new TechTreeEntry("MODULE.ALLOYSMELTER.IMPROVED", "BASIC", 1, TechPointTypes.VERY_EASY, 4, 2, getItemStackFromData("moduleProducerAlloySmelter", 1)).setPages(new TechTreePage("mm.techtree_page.MODULE.ALLOYSMELTER.IMPROVED.0")).setParents("MODULE.ALLOYSMELTER.BASE", "MODULE.FURNACE.IMPROVED").registerTechTreeEntry();
		new TechTreeEntry("MODULE.ALLOYSMELTER.ADVANCED", "BASIC", 1, TechPointTypes.VERY_EASY, 4, 3, getItemStackFromData("moduleProducerAlloySmelter", 2)).setPages(new TechTreePage("mm.techtree_page.MODULE.ALLOYSMELTER.ADVANCED.0")).setParents("MODULE.ALLOYSMELTER.IMPROVED", "MODULE.FURNACE.ADVANCED").registerTechTreeEntry();
		
		//Sag Mill Module
		new TechTreeEntry("MODULE.SAGMILL.BASE", "BASIC", 1, TechPointTypes.VERY_EASY, 2, -1, getItemStackFromData("moduleProducerSawMill", 0)).setPages(new TechTreePage("mm.techtree_page.MODULE.SAGMILL.BASE.0")).setParents("MODULE.BASE").registerTechTreeEntry();
		new TechTreeEntry("MODULE.SAGMILL.IMPROVED", "BASIC", 1, TechPointTypes.VERY_EASY, 2, -2, getItemStackFromData("moduleProducerSawMill", 1)).setPages(new TechTreePage("mm.techtree_page.MODULE.SAGMILL.IMPROVED.0")).setParents("MODULE.SAGMILL.BASE", "MODULE.IMPROVED").registerTechTreeEntry();
		new TechTreeEntry("MODULE.SAGMILL.ADVANCED", "BASIC", 1, TechPointTypes.VERY_EASY, 2, -3, getItemStackFromData("moduleProducerAlloySmelter", 2)).setPages(new TechTreePage("mm.techtree_page.MODULE.SAGMILL.ADVANCED.0")).setParents("MODULE.SAGMILL.IMPROVED", "MODULE.ADVANCED").registerTechTreeEntry();
		
		//Pulverizer Module
		new TechTreeEntry("MODULE.PULVERIZER.BASE", "BASIC", 1, TechPointTypes.VERY_EASY, 4, -1, getItemStackFromData("moduleProducerPulverizer", 0)).setPages(new TechTreePage("mm.techtree_page.MODULE.MODULE.PULVERIZER.BASE.0")).setParents("MODULE.SAGMILL.BASE").registerTechTreeEntry();
		new TechTreeEntry("MODULE.PULVERIZER.IMPROVED", "BASIC", 1, TechPointTypes.VERY_EASY, 4, -2, getItemStackFromData("moduleProducerPulverizer", 1)).setPages(new TechTreePage("mm.techtree_page.MODULE.PULVERIZER.IMPROVED.0")).setParents("MODULE.PULVERIZER.BASE", "MODULE.SAGMILL.IMPROVED").registerTechTreeEntry();
		new TechTreeEntry("MODULE.PULVERIZER.ADVANCED", "BASIC", 1, TechPointTypes.VERY_EASY, 4, -3, getItemStackFromData("moduleProducerPulverizer", 2)).setPages(new TechTreePage("mm.techtree_page.MODULE.PULVERIZER.ADVANCED.0")).setParents("MODULE.PULVERIZER.IMPROVED", "MODULE.SAGMILL.ADVANCED").registerTechTreeEntry();
		
		//Centrifuge Module
		new TechTreeEntry("MODULE.CENTRIFUGE.BASE", "BASIC", 1, TechPointTypes.VERY_EASY, 6, 0, getItemStackFromData("moduleProducerCentrifuge", 0)).setPages(new TechTreePage("mm.techtree_page.MODULE.CENTRIFUGE.BASE.0")).setParents("MODULE.PULVERIZER.BASE", "MODULE.ALLOYSMELTER.BASE").registerTechTreeEntry();
		new TechTreeEntry("MODULE.CENTRIFUGE.IMPROVED", "BASIC", 1, TechPointTypes.VERY_EASY, 7, 0, getItemStackFromData("moduleProducerCentrifuge", 1)).setPages(new TechTreePage("mm.techtree_page.MODULE.CENTRIFUGE.IMPROVED.0")).setParents("MODULE.CENTRIFUGE.BASE", "MODULE.ALLOYSMELTER.IMPROVED", "MODULE.PULVERIZER.IMPROVED").registerTechTreeEntry();
		new TechTreeEntry("MODULE.CENTRIFUGE.ADVANCED", "BASIC", 1, TechPointTypes.VERY_EASY, 8, 0, getItemStackFromData("moduleProducerCentrifuge", 2)).setPages(new TechTreePage("mm.techtree_page.MODULE.PULVERIZER.ADVANCED.0")).setParents("MODULE.CENTRIFUGE.IMPROVED", "MODULE.PULVERIZER.ADVANCED", "MODULE.ALLOYSMELTER.ADVANCED").registerTechTreeEntry();
	}
	
	public static void registerEnergyStuff()
	{
		new TechTreeEntry("CAPACITOR.BASE", "ENERGY", 15, TechPointTypes.EASY, 0, 0, new ItemStack(MMItems.Module_Item_Capacitor.item(), 1, 0)).setParents("ENGINE.BASE", "BATTERY").setPages(new TechTreePage("mm.techtree_page.CAPACITOR.BASE.0")).registerTechTreeEntry();
		new TechTreeEntry("CAPACITOR.IMPROVED", "ENERGY", 7, TechPointTypes.NORMAL, 0, 1, new ItemStack(MMItems.Module_Item_Capacitor.item(), 1, 1)).setParents("CAPACITOR.BASE").setPages(new TechTreePage("mm.techtree_page.CAPACITOR.IMPROVED.0")).registerTechTreeEntry();
		new TechTreeEntry("CAPACITOR.ADVANCED", "ENERGY", 13, TechPointTypes.HARD, 0, 2, new ItemStack(MMItems.Module_Item_Capacitor.item(), 1, 3)).setParents("CAPACITOR.IMPROVED").setPages(new TechTreePage("mm.techtree_page.CAPACITOR.ADVANCED.0")).registerTechTreeEntry();
		
		new TechTreeEntry("ENGINE.BASE", "ENERGY", 15, TechPointTypes.EASY, 2, 0, getItemStackFromData("moduleEngineNormal", 0)).setParents("BATTERY").setPages(new TechTreePage("mm.techtree_page.ENGINE.BASE.0")).registerTechTreeEntry();
		new TechTreeEntry("ENGINE.IMPROVED", "ENERGY", 7, TechPointTypes.NORMAL, 2, 1, getItemStackFromData("moduleEngineNormal", 1)).setParents("ENGINE.BASE").setPages(new TechTreePage("mm.techtree_page.ENGINE.IMPROVED.0")).registerTechTreeEntry();
		new TechTreeEntry("ENGINE.ADVANCED", "ENERGY", 13, TechPointTypes.HARD, 2, 2, getItemStackFromData("moduleEngineNormal", 2)).setParents("ENGINE.ADVANCED").setPages(new TechTreePage("mm.techtree_page.ENGINE.ADVANCED.0")).registerTechTreeEntry();
	}
	
	public static void registerModuleStuff()
	{
		new TechTreeEntry("CASING", "MODULE", 15, TechPointTypes.EASY, 0, 0, ModularMachinesApi.bookmark.get("Basic").get(0)).setPages(new TechTreePage("mm.techtree_page.CASING.0")).registerTechTreeEntry();
		
		//Energy
		new TechTreeEntry("BATTERY", "MODULE", 15, TechPointTypes.EASY, 0, 2, new ItemStack(MMItems.Module_Item_Capacitor.item(), 1, 0)).setParents("CASING").setPages(new TechTreePage("mm.techtree_page.BATTERY.0")).registerTechTreeEntry();
		new TechTreeEntry("ENERGYACCEPTOR", "MODULE", 15, TechPointTypes.EASY, 0, 4, new ItemStack(MMItems.Module_Item_Capacitor.item(), 1, 0)).setParents("BATTERY").setPages(new TechTreePage("mm.techtree_page.ENERGYACCEPTOR.0")).registerTechTreeEntry();
		new TechTreeEntry("ENGINE", "MODULE", 15, TechPointTypes.EASY, 2, 2, new ItemStack(MMItems.Module_Item_Capacitor.item(), 1, 0)).setParents("BATTERY").setPages(new TechTreePage("mm.techtree_page.ENGINE.0")).registerTechTreeEntry();
		new TechTreeEntry("ENERGYMANAGER", "MODULE", 15, TechPointTypes.EASY, -2, 2, new ItemStack(MMItems.Module_Item_Capacitor.item(), 1, 0)).setParents("BATTERY").setPages(new TechTreePage("mm.techtree_page.ENERGYMANAGER.0")).registerTechTreeEntry();
		new TechTreeEntry("CAPACITOR", "MODULE", 15, TechPointTypes.EASY, -2, 4, new ItemStack(MMItems.Module_Item_Capacitor.item(), 1, 0)).setParents("ENERGYMANAGER").setPages(new TechTreePage("mm.techtree_page.CAPACITOR.0")).registerTechTreeEntry();
		
		//Tool
		new TechTreeEntry("TOOL", "MODULE", 15, TechPointTypes.EASY, 0, -2, new ItemStack(Items.iron_axe)).setParents("CASING").setPages(new TechTreePage("mm.techtree_page.TOOL.0")).registerTechTreeEntry();
		new TechTreeEntry("PRODUCER", "MODULE", 15, TechPointTypes.EASY, 0, -4, new ItemStack(Items.iron_axe)).setParents("TOOL").setPages(new TechTreePage("mm.techtree_page.PRODUCER.0")).registerTechTreeEntry();
		new TechTreeEntry("GENERATOR", "MODULE", 15, TechPointTypes.EASY, 2, -2, new ItemStack(Blocks.furnace)).setParents("TOOL").setPages(new TechTreePage("mm.techtree_page.GENERATOR.0")).registerTechTreeEntry();
		
		//Fluid
		new TechTreeEntry("TANKMANAGER", "MODULE", 15, TechPointTypes.EASY, 2, 0, new ItemStack(Items.iron_axe)).setParents("CASING").setPages(new TechTreePage("mm.techtree_page.TANKMANAGER.0")).registerTechTreeEntry();
		new TechTreeEntry("TANK", "MODULE", 15, TechPointTypes.EASY, 4, 0, new ItemStack(Items.iron_axe)).setParents("TANKMANAGER").setPages(new TechTreePage("mm.techtree_page.TANK.0")).registerTechTreeEntry();
    	
    	//Storage
		new TechTreeEntry("STORAGEMANAGER", "MODULE", 15, TechPointTypes.EASY, -2, 0, new ItemStack(Items.iron_axe)).setParents("CASING").setPages(new TechTreePage("mm.techtree_page.STORAGEMANAGER.0")).registerTechTreeEntry();
		new TechTreeEntry("STORAGE", "MODULE", 15, TechPointTypes.EASY, -4, 0, new ItemStack(Blocks.chest)).setParents("STORAGEMANAGER").setPages(new TechTreePage("mm.techtree_page.STORAGE.0")).registerTechTreeEntry();
	}
	
}
