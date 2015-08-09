package nedelosk.modularmachines.common.core.registry;

import nedelosk.modularmachines.api.techtree.TechPointTypes;
import nedelosk.modularmachines.api.techtree.TechTreeCategories;
import nedelosk.modularmachines.api.techtree.TechTreeEntry;
import nedelosk.modularmachines.api.techtree.TechTreePage;
import nedelosk.modularmachines.common.core.MMItems;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class TechTreeRegistry {

	public static void preInit()
	{
		TechTreeCategories.registerCategory("BASIC", new ResourceLocation("modularmachines", "textures/items/modules/moduleEnergyManager_0.png"), new ResourceLocation("modularmachines", "textures/gui/gui_techtreeback.png"));
		new TechTreeEntry("MODULE.BASE", "BASIC", 1, TechPointTypes.VERY_EASY, 0, 0, new ItemStack(MMItems.Module_Items.item())).setPages(new TechTreePage("mm.techtree_page.BASIC.MODULE.0"), new TechTreePage("mm.techtree_page.MODULE.BASE"), new TechTreePage("mm.techtree_page.BASIC.MODULE.2"), new TechTreePage("mm.techtree_page.BASIC.MODULE.3"), new TechTreePage("mm.techtree_page.BASIC.MODULE.4"), new TechTreePage(new ShapedOreRecipe(Blocks.furnace, "+++", "+ +", "+++", '+', Blocks.cobblestone) )).setAutoUnlock().registerTechTreeEntry();
		new TechTreeEntry("MODULE.IMPROVED", "BASIC", 1, TechPointTypes.VERY_EASY, 0, 1, new ItemStack(MMItems.Module_Items.item(), 1, 1)).setParents("MODULE.BASE").registerTechTreeEntry();
		new TechTreeEntry("MODULE.ADVANCED", "BASIC", 1, TechPointTypes.VERY_EASY, 0, 2, new ItemStack(MMItems.Module_Items.item(), 1, 2)).setParents("MODULE.IMPROVED").registerTechTreeEntry();
	}
	
}
