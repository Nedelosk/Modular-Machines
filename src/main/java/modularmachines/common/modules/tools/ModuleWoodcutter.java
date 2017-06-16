package modularmachines.common.modules.tools;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import modularmachines.api.ILocatable;
import modularmachines.api.modules.IModuleStorage;
import modularmachines.api.modules.Module;

public class ModuleWoodcutter extends Module implements ITickable {

	private BlockPos currentPos;
	
	public ModuleWoodcutter(IModuleStorage storage) {
		super(storage);
		currentPos = BlockPos.ORIGIN;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setTag("Pos", NBTUtil.createPosTag(currentPos));
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		currentPos = NBTUtil.getPosFromTag(compound.getCompoundTag("Pos"));
	}

	@Override
	public void update() {
		ILocatable locatable = logic.getLocatable();
		BlockPos pos = locatable.getCoordinates();
		World world = locatable.getWorldObj();
		if(currentPos == BlockPos.ORIGIN){
			//currentPos = pos.add(x, y, z)
		}
	}

}
