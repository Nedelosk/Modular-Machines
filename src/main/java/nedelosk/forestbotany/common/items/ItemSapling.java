package nedelosk.forestbotany.common.items;

import java.util.List;

import nedelosk.forestbotany.api.genetics.IPlantDifinition;
import nedelosk.forestbotany.api.genetics.allele.IAlleleGender;
import nedelosk.forestbotany.api.genetics.allele.IAllelePlant;
import nedelosk.forestbotany.api.genetics.allele.IAllelePlantTree;
import nedelosk.forestbotany.api.genetics.plants.IPlant;
import nedelosk.forestbotany.common.genetics.PlantManager;
import nedelosk.forestbotany.common.genetics.allele.Allele;
import nedelosk.forestbotany.common.genetics.allele.AlleleGender;
import nedelosk.forestbotany.common.genetics.templates.tree.Tree;
import nedelosk.forestbotany.common.genetics.templates.tree.TreeGenome;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

public class ItemSapling extends ItemPlant {

	public ItemSapling() {
		super("tree");
	}
	
	@Override
	public IAllelePlantTree getSpecies(ItemStack itemStack) {
		return TreeGenome.getSpecies(itemStack);
	}
	
	@Override
	public IAlleleGender getGender(ItemStack itemStack) {
		return TreeGenome.getGender(itemStack);
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack itemstack) {
		if (!itemstack.hasTagCompound()) {
			return "Unknown";
		}
		IAllelePlant species = getSpecies(itemstack);
		IAlleleGender gender = getGender(itemstack);

		String customTreeKey = "plants.trees." + species.getUID().replace("fb.plant.tree.", "") + ".species" ;
		String customGenderKey = gender.getUID().replace("fb.", "");
		if (StatCollector.canTranslate(customTreeKey) && StatCollector.canTranslate(customGenderKey)) {
			return StatCollector.translateToLocal(customTreeKey) + " " + StatCollector.translateToLocal("plants.trees.sapling") + " " + StatCollector.translateToLocal(customGenderKey);
		}
		return customTreeKey + "." + customGenderKey;
	}
	
	@Override
	public void getSubItems(Item item, CreativeTabs tabs, List list) {
		for (IPlant plant : PlantManager.treeManager.getTemplates()){
			//list.add(PlantManager.treeManager.getMemberStack(plant));
		}
	}

	@Override
	public IPlant getPlant(ItemStack itemStack) {
		return new Tree(itemStack.getTagCompound());
	}

	@Override
	public IIcon getPlantIcon(ItemStack stack, int renderpass) {
		return null;
	}

	@Override
	public IPlantDifinition getDifinition(ItemStack stack) {
		return null;
	}

}
