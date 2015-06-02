package nedelosk.forestbotany.common.genetics.allele;

import nedelosk.forestbotany.api.genetics.allele.IAllelePlantTree;

public class AllelePlantTree extends AllelePlant implements IAllelePlantTree {

	public AllelePlantTree(String uid, boolean isDominant, int minClimate, int maxClimate, int minHumidity, int maxHumidity) {
		super("tree." + uid, isDominant, minClimate, maxClimate, minHumidity, maxHumidity);
	}

}
