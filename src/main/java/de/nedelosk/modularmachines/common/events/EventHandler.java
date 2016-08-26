package de.nedelosk.modularmachines.common.events;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import de.nedelosk.modularmachines.api.ModularMachinesApi;
import de.nedelosk.modularmachines.api.modules.ModuleEvents;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.items.ModuleProvider;
import de.nedelosk.modularmachines.client.model.ModelModular;
import de.nedelosk.modularmachines.common.utils.Translator;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.util.text.TextFormatting;
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
			if(Keyboard.isKeyDown(Keyboard.KEY_M)){
				List<String> moduleTooltip = new ArrayList<>();
				event.getToolTip().add(TextFormatting.DARK_GREEN + "" + TextFormatting.ITALIC + Translator.translateToLocal("mm.tooltip.moduleInfo"));
				container.addTooltip(moduleTooltip, event.getItemStack());
				for(String s : moduleTooltip){
					event.getToolTip().add(TextFormatting.DARK_GREEN + s);
				}
			}else{
				event.getToolTip().add(TextFormatting.DARK_GREEN + Translator.translateToLocal("mm.tooltip.hold.moduleInfo"));
			}
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
