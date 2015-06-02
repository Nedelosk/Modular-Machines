package nedelosk.forestbotany.common.genetics.allele;

import nedelosk.forestbotany.api.genetics.allele.IAlleleInteger;

public class AlleleInteger extends Allele implements IAlleleInteger {

	private final int value;

	public AlleleInteger(String uid, int value) {
		this(uid, value, false);
	}

	public AlleleInteger(String uid, int value, boolean isDominant) {
		super(uid, isDominant);
		this.value = value;
	}

	@Override
	public int getValue() {
		return value;
	}

}
