package nedelosk.forestcore.api.modules;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import nedelosk.forestcore.api.modules.basic.IObjectManager;

public abstract class AModuleManager implements IModuleManager{

	public ArrayList<IModule> modules = Lists.newArrayList();
	
	@Override
	public <O> void register(IObjectManager<O> manager, O object, Object... objects) {
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
				modules.preInit();
			}
		}
	}

	@Override
	public void init() {
		for (IModule modules : modules) {
			if (modules.isActive()) {
				modules.init();
			}
		}
	}

	@Override
	public void postInit() {
		for (IModule modules : modules) {
			if (modules.isActive()) {
				modules.postInit();
			}
		}
	}

}
