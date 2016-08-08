package de.nedelosk.modularmachines.common.events;

import de.nedelosk.modularmachines.api.ModularMachinesApi;
import de.nedelosk.modularmachines.api.modules.ModuleEvents;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.items.ModuleProvider;
import de.nedelosk.modularmachines.client.model.ModelModular;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.IRegistry;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EventHandler {

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void tooltipEvent(ItemTooltipEvent event) {
		IModuleContainer container = ModularMachinesApi.getContainerFromItem(event.getItemStack());
		if (container != null) {
			container.addTooltip(event.getToolTip());
		}
	}


	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onModuleModelInit(ModuleEvents.ModuleModelInitEvent event) {
		IModuleContainer conatiner = event.getContainer();
		ResourceLocation windowLocation = conatiner.getModule().getWindowLocation(conatiner);
		if(windowLocation != null){
			ModelLoaderRegistry.getModelOrMissing(windowLocation);
		}
	}

	@SubscribeEvent
	public void onInitCapabilities(AttachCapabilitiesEvent.Item event) {
		event.addCapability(new ResourceLocation("modularmachines:modules"), new ModuleProvider());
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onTextureStitch(TextureStitchEvent event) {
		event.getMap().registerSprite(new ResourceLocation("modularmachines:gui/container"));
		event.getMap().registerSprite(new ResourceLocation("modularmachines:gui/liquid"));
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onBakeModel(ModelBakeEvent event) {
		IRegistry<ModelResourceLocation, IBakedModel> registry = event.getModelRegistry();
		registry.putObject(new ModelResourceLocation("modularmachines:modular"), new ModelModular());
		registry.putObject(new ModelResourceLocation("modularmachines:modular", "inventory"), new ModelModular());
	}
}
