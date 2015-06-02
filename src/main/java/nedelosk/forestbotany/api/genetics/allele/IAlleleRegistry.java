package nedelosk.forestbotany.api.genetics.allele;

import java.util.Map;

public interface IAlleleRegistry {

	Map<String, IAllele> getRegisteredAlleles();

	void registerAllele(IAllele allele);

	IAllele getAllele(String uid);

}
