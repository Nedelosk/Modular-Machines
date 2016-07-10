package de.nedelosk.modularmachines.common.events;

import com.mojang.realmsclient.gui.ChatFormatting;

import de.nedelosk.modularmachines.api.Translator;
import de.nedelosk.modularmachines.api.material.IMaterial;
import de.nedelosk.modularmachines.api.modular.ModularManager;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.IModuleStoraged;
import de.nedelosk.modularmachines.api.modules.ModuleEvents;
import de.nedelosk.modularmachines.client.model.ModelModularMachine;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.IRegistry;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
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
			IModule module = container.getModule();
			IMaterial material = container.getMaterial();
			event.getToolTip().add(Translator.translateToLocal("mm.module.tooltip.material") + material.getLocalizedName());
			event.getToolTip().add(Translator.translateToLocal("mm.module.tooltip.name") + container.getDisplayName());
			container.addTooltip(event.getToolTip());
		}
		ItemStack stack = event.getItemStack();
		event.getToolTip().add(ChatFormatting.YELLOW.toString() + stack.getItem().getRegistryName() + ":" + stack.getItemDamage());
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onModuleModelInit(ModuleEvents.ModuleModelInitEvent event) {
		IModuleContainer conatiner = event.getContainer();
		if(conatiner.getModule() instanceof IModuleStoraged){
			ResourceLocation windowLocation = ((IModuleStoraged)conatiner.getModule()).getWindowLocation(conatiner);
			if(windowLocation != null){
				ModelLoaderRegistry.getModelOrMissing(windowLocation);
			}
		}
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
		registry.putObject(new ModelResourceLocation("modularmachines:modular"), new ModelModularMachine());
		registry.putObject(new ModelResourceLocation("modularmachines:modular", "inventory"), new ModelModularMachine());
	}
}
