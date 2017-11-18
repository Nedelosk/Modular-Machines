package modularmachines.api.modules;

public enum EnumModulePositions implements IModulePosition{
	CASING,
	FRONT,
	BACK((float) (Math.PI)),
	RIGHT((float) (Math.PI / 2)),
	LEFT(-(float) (Math.PI / 2));
	
	private final float rotation;
	
	EnumModulePositions() {
		this(0.0F);
	}
	
	EnumModulePositions(float rotation) {
		this.rotation = rotation;
	}
	
	@Override
	public float getRotationAngle() {
		return rotation;
	}
}
