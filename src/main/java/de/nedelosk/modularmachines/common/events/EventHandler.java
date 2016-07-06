package de.nedelosk.modularmachines.common.events;

import de.nedelosk.modularmachines.api.modular.ModularManager;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.client.model.ModelModularMachine;
import de.nedelosk.modularmachines.common.utils.Translator;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.IRegistry;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EventHandler {

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void tooltipEvent(ItemTooltipEvent event) {
		IModuleContainer container = ModularManager.getContainerFromItem(event.getItemStack());
		if (container != null) {
			event.getToolTip().add(Translator.translateToLocal("mm.module.tooltip.type") + ": " + container.getMaterial().getLocalizedName());
			event.getToolTip().add(Translator.translateToLocal("mm.module.tooltip.tier") + ": " + container.getMaterial().getTier());
			event.getToolTip().add(Translator.translateToLocal("mm.module.tooltip.name") + ": "
					+ Translator.translateToLocal(container.getUnlocalizedName()));
			container.addTooltip(event.getToolTip());
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onBakeModel(TextureStitchEvent event) {
		event.getMap().registerSprite(new ResourceLocation("modularmachines:gui/container"));
		event.getMap().registerSprite(new ResourceLocation("modularmachines:gui/liquid"));
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onBakeModel(ModelBakeEvent event) {
		IRegistry<ModelResourceLocation, IBakedModel> registry = event.getModelRegistry();
		registry.putObject(new ModelResourceLocation("modularmachines:modular"), new ModelModularMachine());
		registry.putObject(new ModelResourceLocation("modularmachines:modular", "inventory"), new ModelModularMachine());
	}
}
