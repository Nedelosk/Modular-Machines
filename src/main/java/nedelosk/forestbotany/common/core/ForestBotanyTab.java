package nedelosk.forestbotany.common.core;

import nedelosk.forestbotany.common.genetics.PlantManager;
import nedelosk.forestbotany.common.genetics.templates.crop.Crop;
import nedelosk.forestbotany.common.genetics.templates.crop.CropDefinition;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ForestBotanyTab extends CreativeTabs {

	public static ForestBotanyTab instance;
	
	public ForestBotanyTab() {
		super("ForestBotany");
	}

	@Override
	public Item getTabIconItem() {
		return null;
	}
	
	@Override
	public ItemStack getIconItemStack() {
		return PlantManager.cropManager.getMemberStack(new Crop(PlantManager.cropManager.templateAsGenome(CropDefinition.Wheat.getTemplate())), 0);
	}
	
	@Override
	public int func_151243_f() {
		return super.func_151243_f();
	}

}
