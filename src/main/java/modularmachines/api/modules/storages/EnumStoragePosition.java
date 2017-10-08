package modularmachines.api.modules.storages;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.translation.I18n;

import modularmachines.api.modules.EnumModuleSizes;

import scala.actors.threadpool.Arrays;

public enum EnumStoragePosition implements IMachineStoragePosition {
	CASING(EnumModuleSizes.LARGEST),
	LEFT(EnumModuleSizes.LARGE, -(float) (Math.PI / 2)),
	RIGHT(EnumModuleSizes.LARGE, (float) (Math.PI / 2)),
	TOP(EnumModuleSizes.LARGE),
	BACK(EnumModuleSizes.LARGE, (float) (Math.PI)),
	NONE(EnumModuleSizes.NONE);

	public static final EnumStoragePosition[] VALUES = values();
	float rotation;
	EnumModuleSizes size;
	
	private EnumStoragePosition(EnumModuleSizes size) {
		this(size, 0F);
	}

	private EnumStoragePosition(EnumModuleSizes size, float rotation) {
		this.size = size;
		this.rotation = rotation;
	}
	
	@Override
	public EnumModuleSizes getSize() {
		return size;
	}

	@Override
	public float getRotation() {
		return rotation;
	}
	
	@Override
	public String getDisplayName() {
		return I18n.translateToLocal("module.storage." + getName() + ".name");
	}

	@Override
	public String getName() {
		return name().toLowerCase(Locale.ENGLISH);
	}

	@Override
	public int compareTo(IStoragePosition position) {
		if (position instanceof EnumStoragePosition) {
			if (((EnumStoragePosition) position).ordinal() > ordinal()) {
				return -1;
			}
			return 1;
		}
		return 0;
	}
	
	public static List<IStoragePosition> getValidPositions(){
		List<IStoragePosition> positions = new ArrayList<>();
		for(EnumStoragePosition position : VALUES){
			if(position != NONE){
				positions.add(position);
			}
		}
		return positions;
	}
	
	public static EnumStoragePosition getPositionFromFacing(EnumFacing facing, EnumFacing logicFacing){
		if(facing == logicFacing){
			return CASING;
		}else if(facing == logicFacing.getOpposite()){
			return BACK;
		}else if(facing == EnumFacing.UP){
			return TOP;
		}else if(logicFacing.rotateY() == facing){
			return RIGHT;
		}else if(logicFacing.rotateY().getOpposite() == facing){
			return LEFT;
		}
		return NONE;
	}
	
	public static List<IStoragePosition> getPositions(){
		return Arrays.asList(VALUES);
	}

	@Override
	public String toString() {
		return getDisplayName();
	}
}