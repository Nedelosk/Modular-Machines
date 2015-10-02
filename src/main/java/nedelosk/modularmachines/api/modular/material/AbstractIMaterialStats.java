package nedelosk.modularmachines.api.modular.material;

public abstract class AbstractIMaterialStats implements IMaterialStats{

	protected final String materialType;

	public AbstractIMaterialStats(String materialType) {
		this.materialType = materialType;
	}

	@Override
	public String getIdentifier() {
	    return materialType;
	}

}
