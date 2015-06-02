package nedelosk.forestbotany.api.genetics;

import java.util.ArrayList;

import nedelosk.forestbotany.api.genetics.allele.IAllelePlant;
import nedelosk.forestbotany.common.genetics.templates.crop.CropDefinition.SeedFile;
import nedelosk.forestbotany.common.soil.SoilType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public interface IPlantDifinition {
	
	IAllelePlant getPlant();
	
	void setSoil(SoilType soil);
	
	void setFluid(FluidStack fluid);
	
	void setSeed(ItemStack stack);
	
	IGenome getGenome();
	
	int getGrowthStages();
	
	FluidStack getFluid();
	
	SoilType getSoil();
	
	SeedFile getFile();
	
	void addProduct(ItemStack product);
	
	ArrayList<ItemStack> getProducts();
	
	int getFruits();
	
}
