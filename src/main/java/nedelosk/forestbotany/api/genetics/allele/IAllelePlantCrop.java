package nedelosk.forestbotany.api.genetics.allele;

import nedelosk.forestbotany.common.genetics.templates.crop.CropDefinition.SeedFile;

public interface IAllelePlantCrop extends IAllelePlant {

	int getGrowthStages();
	
	int getColorFromRenderPass(int renderPass);
	
	public SeedFile getSeedType();
}
