package modularmachines.common.blocks.propertys;

import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.property.IUnlistedProperty;

public final class UnlistedBlockAccess implements IUnlistedProperty<IBlockAccess> {

	public static final UnlistedBlockAccess BLOCKACCESS = new UnlistedBlockAccess();

	@Override
	public String getName() {
		return "blockaccess";
	}

	@Override
	public boolean isValid(IBlockAccess value) {
		return true;
	}

	@Override
	public Class<IBlockAccess> getType() {
		return IBlockAccess.class;
	}

	@Override
	public String valueToString(IBlockAccess value) {
		return null;
	}
}