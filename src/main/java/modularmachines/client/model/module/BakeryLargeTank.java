package modularmachines.client.model.module;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;

import net.minecraftforge.fluids.FluidStack;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.IModule;
import modularmachines.api.modules.components.handlers.IFluidHandlerComponent;
import modularmachines.api.modules.model.IModelInfo;
import modularmachines.api.modules.model.IModelList;
import modularmachines.client.model.AABBModelBaker;

@SideOnly(Side.CLIENT)
public class BakeryLargeTank extends BakeryBase {
	private static final AxisAlignedBB FLUID_BOUNDING_BOX = new AxisAlignedBB(3.0F / 16.0F, 2.0F / 16.0F, 11.0F / 16F, 13.0F / 16.0F, 13.0F / 16.0F, 15.99F / 16.0F);
	
	public BakeryLargeTank(ResourceLocation... modelLocation) {
		super(modelLocation);
	}
	
	@Override
	public void bakeModel(IModule module, IModelInfo modelInfo, IModelList modelList) {
		BlockRenderLayer layer = modelInfo.getLayer();
		if (layer != BlockRenderLayer.TRANSLUCENT) {
			super.bakeModel(module, modelInfo, modelList);
		} else {
			IFluidHandlerComponent component = module.getComponent(IFluidHandlerComponent.class);
			if (component == null) {
				return;
			}
			IFluidHandlerComponent.ITank tank = component.getTank(0);
			if (tank == null) {
				return;
			}
			FluidStack fluidStack = tank.getFluid();
			if (fluidStack == null || fluidStack.amount <= 0) {
				return;
			}
			modelList.add(bakeFluidModel(fluidStack, tank.getCapacity()));
		}
	}
	
	private IBakedModel bakeFluidModel(FluidStack fluidStack, double capacity) {
		AABBModelBaker baker = new AABBModelBaker();
		double amount = fluidStack.amount;
		double percent = amount / capacity;
		double height = FLUID_BOUNDING_BOX.maxY - FLUID_BOUNDING_BOX.minY;
		height *= percent;
		baker.setModelBounds(new AxisAlignedBB(FLUID_BOUNDING_BOX.minX, FLUID_BOUNDING_BOX.minY, FLUID_BOUNDING_BOX.minZ, FLUID_BOUNDING_BOX.maxX, FLUID_BOUNDING_BOX.minY + height, FLUID_BOUNDING_BOX.maxZ));
		TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(fluidStack.getFluid().getStill().toString());
		baker.addModel(sprite, 0);
		return baker.bakeModel(true);
	}
	
	@Override
	public boolean canRenderInLayer(IModule module, BlockRenderLayer layer) {
		return layer == BlockRenderLayer.TRANSLUCENT || layer == BlockRenderLayer.CUTOUT;
	}
}
