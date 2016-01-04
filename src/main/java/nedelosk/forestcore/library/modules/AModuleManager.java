package nedelosk.forestcore.library.modules;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import nedelosk.forestcore.library.modules.manager.IBlockManager;
import nedelosk.forestcore.library.modules.manager.IItemManager;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public abstract class AModuleManager implements IModuleManager {

	public ArrayList<IModule> modules = Lists.newArrayList();
	private static ArrayList<String> loadedNodules = Lists.newArrayList();

	@Override
	public void register(IBlockManager manager, Block object, Class<? extends ItemBlock> item, Object... objects) {
		manager.register(object, item, objects);
		for (IModule modules : modules) {
			if (modules.isActive()) {
				modules.onRegisterObject(manager);
			}
		}
	}

	@Override
	public void register(IItemManager manager, Item object, Object... objects) {
		manager.register(object, objects);
		for (IModule modules : modules) {
			if (modules.isActive()) {
				modules.onRegisterObject(manager);
			}
		}
	}

	@Override
	public List<IModule> getModules() {
		return modules;
	}

	@Override
	public IModule registerModule(IModule module) {
		modules.add(module);
		return module;
	}

	@Override
	public void preInit() {
		for (IModule modules : modules) {
			if (modules.isActive()) {
				modules.preInit(this);
				if (!loadedNodules.contains(modules.getName()))
					loadedNodules.add(modules.getName());
			}
		}
	}

	@Override
	public void init() {
		for (IModule modules : modules) {
			if (modules.isActive()) {
				modules.init(this);
			}
		}
	}

	@Override
	public void postInit() {
		for (IModule modules : modules) {
			if (modules.isActive()) {
				modules.postInit(this);
			}
		}
	}

	public static boolean isModuleLoaded(String moduleName) {
		return loadedNodules.contains(moduleName);
	}

}
