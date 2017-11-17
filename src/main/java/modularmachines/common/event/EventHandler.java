package modularmachines.common.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.world.World;

import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.Module;
import modularmachines.api.modules.ModuleRegistry;
import modularmachines.api.modules.logic.IModuleLogic;
import modularmachines.api.modules.storages.EnumStoragePosition;
import modularmachines.api.modules.storages.IStorage;
import modularmachines.api.modules.storages.IStoragePosition;
import modularmachines.client.model.ModelManager;
import modularmachines.client.model.block.ModelModular;
import modularmachines.client.model.block.ModuleStorageModelBaked;
import modularmachines.client.model.module.ModelLoader;
import modularmachines.common.ModularMachines;
import modularmachines.common.utils.WorldUtil;

public class EventHandler {

	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority = EventPriority.LOW)
	public void tooltipEvent(ItemTooltipEvent event) {
		event.getToolTip().addAll(ModularMachines.proxy.addModuleInfo(event.getItemStack()));
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onTextureStitch(TextureStitchEvent.Pre event) {
		event.getMap().registerSprite(new ResourceLocation("modularmachines:gui/container"));
		event.getMap().registerSprite(new ResourceLocation("modularmachines:gui/liquid"));
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onBakeModel(ModelBakeEvent event) {
		IRegistry<ModelResourceLocation, IBakedModel> registry = event.getModelRegistry();
		registry.putObject(new ModelResourceLocation("modularmachines:modular"), new ModelModular());
		registry.putObject(new ModelResourceLocation("modularmachines:machine", "inventory"), new ModelModular());
		registry.putObject(new ModelResourceLocation("modularmachines:module_storage"), new ModuleStorageModelBaked());
		ModelLoader.loadModels();
		ModelManager.getInstance().onBakeModels(event);
	}
	
	@SubscribeEvent
	public void onPostRenderOverlay(RenderGameOverlayEvent.Post event) {
		ScaledResolution resolution = event.getResolution();
		int width = resolution.getScaledWidth();
		int height = resolution.getScaledHeight();
		Minecraft mc = Minecraft.getMinecraft();
		World world = mc.world;
		RayTraceResult posHit = mc.objectMouseOver;
		if(posHit != null && posHit.typeOfHit == RayTraceResult.Type.BLOCK && posHit.getBlockPos() != null){
			BlockPos pos = posHit.getBlockPos();
			EnumFacing facing = posHit.sideHit;
			TileEntity tileEntity = WorldUtil.getTile(world, pos, TileEntity.class);
			if(tileEntity != null && tileEntity.hasCapability(ModuleRegistry.MODULE_LOGIC, facing.getOpposite())){
				IModuleLogic logic = tileEntity.getCapability(ModuleRegistry.MODULE_LOGIC, facing.getOpposite());
				if(logic != null){
					FontRenderer fontRenderer = mc.fontRenderer;
					IStoragePosition position = EnumStoragePosition.getPositionFromFacing(facing, logic.getLocatable().getFacing());
					if(position != EnumStoragePosition.NONE) {
						IStorage storage = logic.getStorage(position);
						String text = "Storage: " + position.getDisplayName();
						int size = Math.max(fontRenderer.getStringWidth(text), 4+5*18) + 10;
						int y = height / 2;
						int x = width-size;
						fontRenderer.drawStringWithShadow(text, x, y, -1);
						y += fontRenderer.FONT_HEIGHT;
						x += 4;
						if (storage != null) {
							int xOffset = 0;
							int yOffset = 0;
							RenderItem renderItem = mc.getRenderItem();
							GlStateManager.pushMatrix();
							RenderHelper.enableGUIStandardItemLighting();
							GlStateManager.disableLighting();
							GlStateManager.enableRescaleNormal();
							GlStateManager.enableColorMaterial();
							GlStateManager.enableLighting();
							GlStateManager.enableDepth();
							renderItem.zLevel = 100.0F;
							for (Module module : storage.getModules().getModules()) {
								renderItem.renderItemAndEffectIntoGUI(module.getParentItem(), x + xOffset * 18, y + yOffset * 18);
								xOffset++;
								if (xOffset == 5) {
									xOffset = 0;
									yOffset++;
								}
							}
							renderItem.zLevel = 0.0F;
							GlStateManager.disableLighting();
							GlStateManager.popMatrix();
							GlStateManager.disableDepth();
							GlStateManager.enableLighting();
							RenderHelper.disableStandardItemLighting();
						}
					}
				}
			}
		}
	}
}
