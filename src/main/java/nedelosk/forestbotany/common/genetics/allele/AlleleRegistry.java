package nedelosk.forestbotany.common.genetics.allele;

import java.util.LinkedHashMap;
import java.util.Map;

import nedelosk.forestbotany.api.genetics.allele.IAllele;
import nedelosk.forestbotany.api.genetics.allele.IAlleleGender;
import nedelosk.forestbotany.api.genetics.allele.IAllelePlant;
import nedelosk.forestbotany.api.genetics.allele.IAlleleRegistry;
import net.minecraftforge.common.config.Configuration;

public class AlleleRegistry implements IAlleleRegistry {

	private final LinkedHashMap<String, IAllele> alleleMap = new LinkedHashMap<String, IAllele>(2048);
	private static Configuration config;
	
	@Override
	public Map<String, IAllele> getRegisteredAlleles() {
		return alleleMap;
	}

	@Override
	public void registerAllele(IAllele allele) {
		if(allele instanceof IAllelePlant || allele instanceof IAlleleGender)
			alleleMap.put(allele.getUID(), allele);
		else if(config.get("alleles", "Allele " + allele.getUID().replace("fb.", ""), true).getBoolean())
		{
		alleleMap.put(allele.getUID(), allele);
		}
	}

	@Override
	public IAllele getAllele(String uid) {
		return alleleMap.get(uid);
	}
	
	public static void setConfig(Configuration config)
	{
		AlleleRegistry.config = config;
	}

}
