package modularmachines.api.modules.positions;

public enum EnumCasingPositions implements IModulePosition {
	CENTER,
	FRONT,
	TOP,
	UP,
	BACK((float) (Math.PI)),
	RIGHT((float) (Math.PI / 2)),
	LEFT(-(float) (Math.PI / 2));
	
	private final float rotation;
	
	EnumCasingPositions() {
		this(0.0F);
	}
	
	EnumCasingPositions(float rotation) {
		this.rotation = rotation;
	}
	
	@Override
	public float getRotationAngle() {
		return rotation;
	}
}
