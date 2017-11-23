package modularmachines.common.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.world.World;

import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.IModule;
import modularmachines.api.modules.components.IBoundingBoxComponent;
import modularmachines.api.modules.container.IModuleContainer;
import modularmachines.client.model.ModelManager;
import modularmachines.common.ModularMachines;
import modularmachines.common.modules.ModuleCapabilities;
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
		//registry.putObject(new ModelResourceLocation("modularmachines:modular"), new ModelModular());
		//registry.putObject(new ModelResourceLocation("modularmachines:machine", "inventory"), new ModelModular());
		//registry.putObject(new ModelResourceLocation("modularmachines:module_storage"), new ModuleStorageModelBaked());
		ModelManager.getInstance().onBakeModels(event);
	}
	
	@SubscribeEvent
	public void onDrawHighlight(DrawBlockHighlightEvent event) {
		World world = Minecraft.getMinecraft().world;
		RayTraceResult posHit = event.getTarget();
		BlockPos pos = posHit.getBlockPos();
		EnumFacing facing = posHit.sideHit;
		EntityPlayer player = event.getPlayer();
		if (posHit.typeOfHit == RayTraceResult.Type.BLOCK) {
			TileEntity tileEntity = WorldUtil.getTile(world, pos, TileEntity.class);
			if (tileEntity != null && tileEntity.hasCapability(ModuleCapabilities.MODULE_CONTAINER, facing.getOpposite())) {
				IModuleContainer container = tileEntity.getCapability(ModuleCapabilities.MODULE_CONTAINER, facing.getOpposite());
				float partialTicks = event.getPartialTicks();
				if (container != null) {
					GlStateManager.enableBlend();
					GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
					GlStateManager.glLineWidth(2.0F);
					GlStateManager.disableTexture2D();
					GlStateManager.depthMask(false);
					
					if (!world.getWorldBorder().contains(pos)) {
						for (IModule module : container.getModules()) {
							for (IBoundingBoxComponent component : module.getInterfaces(IBoundingBoxComponent.class)) {
								if (world.getWorldBorder().contains(pos)) {
									double d3 = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double) partialTicks;
									double d4 = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double) partialTicks;
									double d5 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double) partialTicks;
									float red = 0.0F;
									if (module.getIndex() == posHit.subHit) {
										red = 0.8F;
									}
									RenderGlobal.drawSelectionBoundingBox(component.getCollisionBox().grow(0.0020000000949949026D).offset(pos).offset(-d3, -d4, -d5), red, 0.0F, 0.0F, 0.4F);
								}
							}
						}
					}
					
					GlStateManager.depthMask(true);
					GlStateManager.enableTexture2D();
					GlStateManager.disableBlend();
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onPostRenderOverlay(RenderGameOverlayEvent.Post event) {
		ScaledResolution resolution = event.getResolution();
		int width = resolution.getScaledWidth();
		int height = resolution.getScaledHeight();
		Minecraft mc = Minecraft.getMinecraft();
		World world = mc.world;
		RayTraceResult posHit = mc.objectMouseOver;
		if (posHit != null && posHit.typeOfHit == RayTraceResult.Type.BLOCK) {
			BlockPos pos = posHit.getBlockPos();
			EnumFacing facing = posHit.sideHit;
			TileEntity tileEntity = WorldUtil.getTile(world, pos, TileEntity.class);
			if (tileEntity != null && tileEntity.hasCapability(ModuleCapabilities.MODULE_CONTAINER, facing.getOpposite())) {
				IModuleContainer container = tileEntity.getCapability(ModuleCapabilities.MODULE_CONTAINER, facing.getOpposite());
				if(container != null){
					FontRenderer fontRenderer = mc.fontRenderer;
					/*IStoragePosition position = EnumStoragePosition.getPositionFromFacing(facing, container.getLocatable().getFacing());
					if(position != EnumStoragePosition.NONE) {
						IStorage storage = container.getStorage(position);
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
					}*/
				}
			}
		}
	}
}
