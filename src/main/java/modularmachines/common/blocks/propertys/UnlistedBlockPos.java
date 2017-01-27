package modularmachines.common.blocks.propertys;

import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.property.IUnlistedProperty;

public final class UnlistedBlockPos implements IUnlistedProperty<BlockPos> {

	public static final UnlistedBlockPos POS = new UnlistedBlockPos();

	@Override
	public String getName() {
		return "pos";
	}

	@Override
	public boolean isValid(BlockPos value) {
		return true;
	}

	@Override
	public Class<BlockPos> getType() {
		return BlockPos.class;
	}

	@Override
	public String valueToString(BlockPos value) {
		return null;
	}
}