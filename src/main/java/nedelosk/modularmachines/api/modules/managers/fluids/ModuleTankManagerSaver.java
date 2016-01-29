package nedelosk.modularmachines.api.modules.managers.fluids;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modules.fluids.TankData;
import nedelosk.modularmachines.api.modules.managers.ModuleManagerSaver;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class ModuleTankManagerSaver extends ModuleManagerSaver implements IModuleTankManagerSaver {

	protected final TankData[] datas;

	public ModuleTankManagerSaver(int tanksLots) {
		super();
		this.datas = new TankData[tanksLots];
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		super.readFromNBT(nbt, modular, stack);
		NBTTagList list = new NBTTagList();
		for ( int i = 0; i < datas.length; i++ ) {
			if (datas[i] != null) {
				NBTTagCompound nbtTag = new NBTTagCompound();
				datas[i].writeToNBT(nbtTag);
				nbtTag.setShort("Position", (short) i);
				list.appendTag(nbtTag);
			}
		}
		nbt.setTag("Datas", list);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		super.writeToNBT(nbt, modular, stack);
		NBTTagList list = nbt.getTagList("Datas", 10);
		int[] tankCapacitys = nbt.getIntArray("TankCapacitys");
		for ( int i = 0; i < list.tagCount(); i++ ) {
			NBTTagCompound nbtTag = list.getCompoundTagAt(i);
			short position = nbtTag.getShort("Position");
			datas[position] = new TankData();
			datas[position].readFromNBT(nbtTag);
		}
	}

	@Override
	public TankData getData(int id) {
		return datas[id];
	}

	@Override
	public void setData(int id, TankData data) {
		datas[id] = data;
	}

	@Override
	public TankData[] getDatas() {
		return datas;
	}
}
