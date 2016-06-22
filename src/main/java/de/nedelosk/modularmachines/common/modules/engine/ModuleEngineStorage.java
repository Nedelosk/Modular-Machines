package de.nedelosk.modularmachines.common.modules.engine;

import java.util.ArrayList;
import java.util.List;

import de.nedelosk.modularmachines.api.modular.EnumLogicType;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModularLogic;
import de.nedelosk.modularmachines.api.modular.IModularLogicType;
import de.nedelosk.modularmachines.api.modular.ModularHelper;
import de.nedelosk.modularmachines.api.modules.IModuleModelHandler;
import de.nedelosk.modularmachines.api.modules.engine.IModuleEngine;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.tool.IModuleTool;
import net.minecraft.nbt.NBTTagCompound;

public class ModuleEngineStorage implements IModularLogic {
	
	protected IModular modular;
	protected List<EngineLayer> layers;
	
	public ModuleEngineStorage(IModular modular) {
		this.modular = modular;
		this.layers = new ArrayList<>();
		int layerIndex = 0;
		for(IModuleState<IModuleTool> tool : modular.getModules(IModuleTool.class)){
			if(tool != null){
				EngineLayer layer = new EngineLayer(layerIndex);
				List<IModuleState<IModuleEngine>> engines = ModularHelper.getEnginesForTool(tool);
				for(IModuleState<IModuleEngine> engine : engines){
					if(engine != null){
						layer.addEngine(engine);
					}
				}
				if(!layers.isEmpty()){
					layers.add(layer);
				}
				layerIndex++;
			} 
		}
	}
	
	public List<EngineLayer> getEngineLayers() {
		return layers;
	}

	@Override
	public IModularLogicType getType() {
		return EnumLogicType.ENGINE_STORAGE;
	}

	@Override
	public IModuleModelHandler getModelHandler() {
		return new ModelEngineHandler();
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
	}

	@Override
	public IModular getModular() {
		return modular;
	}
	
	public class EngineLayer{
		protected int index;
		protected IModuleState<IModuleTool> tool;
		protected List<IModuleState<IModuleEngine>> engines;
		
		public EngineLayer(int index) {
			this.index = index;
			this.engines = new ArrayList<>();
		}
		
		public void setToolFromLayer(IModuleState<IModuleTool> tool){
			this.tool = tool;
		}
		
		public void addEngine(IModuleState<IModuleEngine> engine){
			engines.add(engine);
		}
	}

}
