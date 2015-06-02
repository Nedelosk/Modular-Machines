package nedelosk.forestbotany.common.genetics.allele;

import java.awt.Color;

import nedelosk.forestbotany.api.genetics.allele.IAllelePlantCrop;
import nedelosk.forestbotany.common.genetics.templates.crop.CropDefinition;
import nedelosk.forestbotany.common.genetics.templates.crop.CropDefinition.SeedFile;
import net.minecraft.item.ItemStack;

public class AllelePlantCrop extends AllelePlant implements IAllelePlantCrop {

	private final int growthStages;
	private final SeedFile seedType;
	private boolean isSpecial = false;
	public ItemStack seed;
	
	private int[] color = new int[8];
	
	public AllelePlantCrop(String uid, boolean isDominant, int minClimate, int maxClimate, int minHumidity, int maxHumidity, int growthStages, SeedFile seedType, CropDefinition subType,int definitionID, Color... color) {
		super("crop." + uid, isDominant, minClimate, maxClimate, minHumidity, maxHumidity);
		this.growthStages = growthStages;
		this.seedType = seedType;
		this.definitionID = definitionID;
		for(int i = 0;i < color.length;i++)
		{
		this.color[i] = color[i].getRGB();
		}
	}
	
	@Override
	public int getGrowthStages() {
		return growthStages;
	}
	
	public SeedFile getSeedFile() {
		return seedType;
	}
	
	public boolean isSpecial() {
		return isSpecial;
	}
	
	public AllelePlantCrop setIsSpecial() {
		this.isSpecial = true;
		return this;
	}
	
	@Override
	public SeedFile getSeedType() {
		return seedType;
	}
	
	public void setColor(int... color) {
		this.color = color;
	}
	
	public int[] getColor() {
		return color;
	}
	
	public void setSeed(ItemStack stack)
	{
		seed = stack;
	}

	@Override
	public int getColorFromRenderPass(int renderPass) {
		return color[renderPass];
	}

}
