package nedelosk.forestbotany.api.genetics.allele;

public interface IAllelePlant extends IAllele {

	public int getTemperatureMax();

	public int getTemperatureMin();

	boolean hasEffect();
	
	int getMinHumidity();
	
	int getMaxHumidity();
	
	int getDefinitionID();
	
}
