package modularmachines.common.utils;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;

import modularmachines.api.modules.IModuleContainer;
import modularmachines.api.modules.Module;
import modularmachines.api.modules.logic.IModuleGuiLogic;
import modularmachines.api.modules.pages.ModuleComponent;
import modularmachines.common.modules.logic.ModuleGuiLogic;

public class GuiLogicCache extends WorldSavedData {
	
	public final Map<BlockPos, CacheValue> logicCache = new HashMap<>();
	
	public GuiLogicCache(String name) {
		super(name);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		logicCache.clear();
		NBTTagList logics = nbt.getTagList("Logics", 10);
		for (int i = 0; i < logics.tagCount(); i++) {
			NBTTagCompound tagCompound = logics.getCompoundTagAt(i);
			BlockPos pos = NBTUtil.getPosFromTag(tagCompound);
			logicCache.put(pos, new CacheValue(tagCompound.getInteger("Module"), tagCompound.getInteger("Page")));
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		NBTTagList logics = new NBTTagList();
		for (Entry<BlockPos, CacheValue> entry : logicCache.entrySet()) {
			CacheValue logic = entry.getValue();
			NBTTagCompound tagCompound = NBTUtil.createPosTag(entry.getKey());
			tagCompound.setInteger("Module", logic.getModuleIndex());
			tagCompound.setInteger("Page", logic.getPageIndex());
		}
		compound.setTag("Logics", logics);
		return compound;
	}
	
	public void remove(BlockPos pos) {
		logicCache.remove(pos);
	}
	
	@Nullable
	public IModuleGuiLogic getLogic(World world, BlockPos pos) {
		CacheValue cacheValue = logicCache.get(pos);
		if (cacheValue == null || cacheValue.logic == null) {
			IModuleContainer provider = ModuleUtil.getContainer(pos, world);
			if (provider == null) {
				return null;
			}
			if (cacheValue == null) {
				logicCache.put(pos, cacheValue = new CacheValue(provider));
			} else {
				cacheValue.setProvider(provider);
			}
		}
		return cacheValue.logic;
	}
	
	protected static class CacheValue {
		@Nullable
		public IModuleGuiLogic logic;
		public int moduleIndex = -1;
		public int pageIndex = -1;
		
		public CacheValue(int moduleIndex, int pageIndex) {
			this.moduleIndex = moduleIndex;
			this.pageIndex = pageIndex;
		}
		
		public CacheValue(IModuleContainer provider) {
			this.logic = new ModuleGuiLogic(provider, moduleIndex, pageIndex);
		}
		
		public void setProvider(IModuleContainer provider) {
			this.logic = new ModuleGuiLogic(provider, moduleIndex, pageIndex);
		}
		
		public int getModuleIndex() {
			if (logic != null) {
				Module module = logic.getCurrentModule();
				if (module != null) {
					return module.getIndex();
				}
			}
			return moduleIndex;
		}
		
		public int getPageIndex() {
			if (logic != null) {
				ModuleComponent page = logic.getCurrentComponent();
				if (page != null) {
					return page.getIndex();
				}
			}
			return pageIndex;
		}
	}
	
}
