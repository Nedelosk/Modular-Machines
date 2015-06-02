package nedelosk.forestbotany.common.genetics.allele;

import nedelosk.forestbotany.api.genetics.allele.IAlleleGender;

public class AlleleGender extends Allele implements IAlleleGender {

	public AlleleGender(String uid, boolean isDominant) {
		super( "gender." + uid, isDominant);
	}

}
