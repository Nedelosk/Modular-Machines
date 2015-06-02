package nedelosk.forestbotany.common.items;

import java.util.List;

import nedelosk.forestbotany.api.genetics.IPlantDifinition;
import nedelosk.forestbotany.api.genetics.allele.IAlleleGender;
import nedelosk.forestbotany.api.genetics.allele.IAllelePlant;
import nedelosk.forestbotany.api.genetics.plants.IPlant;
import nedelosk.forestbotany.common.core.ForestBotanyTab;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public abstract class ItemPlant extends Item {

	public ItemPlant(String plantName) {
		this.setCreativeTab(ForestBotanyTab.instance);
		this.setUnlocalizedName("fb.plant." + plantName);
		hasSubtypes = true;
	}
	
	public abstract IAllelePlant getSpecies(ItemStack itemStack);
	public abstract IAlleleGender getGender(ItemStack itemStack);
	public abstract IPlant getPlant(ItemStack itemStack);
	public abstract IPlantDifinition getDifinition(ItemStack stack);
	
	@Override
	public boolean isDamageable() {
		return false;
	}

	@Override
	public boolean isRepairable() {
		return false;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean p_77624_4_) {
		IPlant plant = getPlant(stack);
		plant.addTooltip(list, stack);
	}

	@Override
	public boolean getShareTag() {
		return true;
	}
	
	public abstract IIcon getPlantIcon(ItemStack stack, int renderpass);
	
}
