package nedelosk.forestbotany.common.genetics.allele;

import nedelosk.forestbotany.api.genetics.allele.IAlleleFloat;
import nedelosk.forestbotany.api.genetics.allele.IAlleleInteger;

public class AlleleFloat extends Allele implements IAlleleFloat {

	private final float value;

	public AlleleFloat(String uid, float value) {
		this(uid, value, false);
	}

	public AlleleFloat(String uid, float value, boolean isDominant) {
		super(uid, isDominant);
		this.value = value;
	}

	@Override
	public float getValue() {
		return value;
	}

}
