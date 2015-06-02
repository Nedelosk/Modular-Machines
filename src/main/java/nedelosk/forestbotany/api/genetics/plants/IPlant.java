package nedelosk.forestbotany.api.genetics.plants;

import java.util.List;

import nedelosk.forestbotany.api.botany.IInfuser;
import nedelosk.forestbotany.api.botany.IInfuserChamber;
import nedelosk.forestbotany.api.genetics.IGenome;
import nedelosk.forestbotany.api.genetics.allele.IAlleleGender;
import nedelosk.nedeloskcore.api.INBTTagable;
import net.minecraft.item.ItemStack;

public interface IPlant extends INBTTagable {

	IGenome getGenome();
	
	void setGender(IAlleleGender gender);
	
	void addTooltip(List<String> list, ItemStack stack);
	
	IPlant mutatePlant(IInfuser infuser);
}
