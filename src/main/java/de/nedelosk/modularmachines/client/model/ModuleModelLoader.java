package de.nedelosk.modularmachines.client.model;

import java.util.List;

import de.nedelosk.modularmachines.api.modules.IModelInitHandler;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.ModuleEvents;
import de.nedelosk.modularmachines.common.core.ModularMachines;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModuleModelLoader {

	@SubscribeEvent
	public void onModelBake(ModelBakeEvent event){
		for(IModuleContainer container : ModularMachines.iModuleContainerRegistry){
			if(container != null && container.getModule() != null){
				List<IModelInitHandler> handlers = container.getModule().getInitModelHandlers(container);
				if(handlers != null && !handlers.isEmpty()){
					for(IModelInitHandler handle : handlers){
						if(handle != null){
							handle.initModels(container);
						}
					}
				}
				MinecraftForge.EVENT_BUS.post(new ModuleEvents.ModuleModelInitEvent(container));
			}
		}
	}
}
