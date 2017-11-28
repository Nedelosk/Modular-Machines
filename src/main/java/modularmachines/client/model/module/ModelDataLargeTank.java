package modularmachines.client.model.module;

import javax.annotation.Nullable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;

import net.minecraftforge.fluids.FluidStack;

import modularmachines.api.modules.IModule;
import modularmachines.api.modules.components.handlers.IFluidHandlerComponent;
import modularmachines.api.modules.data.IModuleData;
import modularmachines.api.modules.model.DefaultProperty;
import modularmachines.api.modules.model.IModelList;
import modularmachines.api.modules.model.IModuleModelState;
import modularmachines.client.model.AABBModelBaker;
import modularmachines.common.core.Constants;

public class ModelDataLargeTank extends ModelData {
	private static final AxisAlignedBB FLUID_BOUNDING_BOX = new AxisAlignedBB(3.0F / 16.0F, 2.0F / 16.0F, 11.0F / 16F, 13.0F / 16.0F, 13.0F / 16.0F, 15.99F / 16.0F);
	
	public static void addModelData(IModuleData data) {
		ModelDataLargeTank model = new ModelDataLargeTank();
		model.add(DefaultProperty.FIRST, new ResourceLocation(Constants.MOD_ID, "module/tank/large_storage"));
		model.add(DefaultProperty.SECOND, new ResourceLocation(Constants.MOD_ID, "module/tank/window"));
		data.setModel(model);
	}
	
	@Override
	public IModuleModelState createState(IModule module) {
		return super.createState(module);
	}
	
	@Override
	public void addModel(IModelList modelList, IModule module, IModuleModelState modelState, @Nullable BlockRenderLayer layer) {
		if (layer == BlockRenderLayer.TRANSLUCENT) {
			IFluidHandlerComponent component = module.getComponent(IFluidHandlerComponent.class);
			if (component != null) {
				IFluidHandlerComponent.ITank tank = component.getTank(0);
				if (tank != null) {
					FluidStack fluidStack = tank.getFluid();
					if (fluidStack != null && fluidStack.amount > 0) {
						AABBModelBaker baker = new AABBModelBaker();
						double amount = fluidStack.amount;
						double capacity = tank.getCapacity();
						double percent = amount / capacity;
						double height = FLUID_BOUNDING_BOX.maxY - FLUID_BOUNDING_BOX.minY;
						height *= percent;
						baker.setModelBounds(new AxisAlignedBB(FLUID_BOUNDING_BOX.minX, FLUID_BOUNDING_BOX.minY, FLUID_BOUNDING_BOX.minZ, FLUID_BOUNDING_BOX.maxX, FLUID_BOUNDING_BOX.minY + height, FLUID_BOUNDING_BOX.maxZ));
						TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(fluidStack.getFluid().getStill().toString());
						baker.addModel(sprite, 0);
						modelList.add(baker.bakeModel(true));
					}
				}
			}
		} else {
			super.addModel(modelList, module, modelState, layer);
		}
	}
	
	@Override
	public boolean canRenderInLayer(IModule module, BlockRenderLayer layer) {
		return layer == BlockRenderLayer.TRANSLUCENT || layer == BlockRenderLayer.CUTOUT;
	}
	
	@Override
	public boolean cacheModel() {
		return false;
	}
}
