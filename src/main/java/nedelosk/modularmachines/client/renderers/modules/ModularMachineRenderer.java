package nedelosk.modularmachines.client.renderers.modules;

import java.io.IOException;
import java.util.Vector;

import org.lwjgl.opengl.GL11;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.machines.basic.IModularRenderer;
import nedelosk.modularmachines.api.modular.machines.basic.IModularTileEntity;
import nedelosk.modularmachines.api.modular.module.basic.IModule;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.nedeloskcore.utils.RenderUtils;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

public class ModularMachineRenderer {

	public static class CasingRenderer implements IModularRenderer{

		public final ModuleStack stack;
		public final ResourceLocation texture;
		public int color = 0;
		
		public CasingRenderer(ModuleStack stack) {
			this.stack = stack;
			if(stack.getModule().getModifier() != null)
				texture = new ResourceLocation("modularmachines", "textures/models/modules/casing/" + stack.getModule().getModifier() + ".png");
			else
				texture = new ResourceLocation("modularmachines", "textures/models/modules/casing/iron.png");
			 IReloadableResourceManager resourceManager = (SimpleReloadableResourceManager)Minecraft.getMinecraft().getResourceManager();
			 try{
				 if(resourceManager.getResource(new ResourceLocation("modularmachines", "textures/models/modules/casing/" + stack.getModule().getModifier() + ".png")) == null)
					 if(stack.getModule().getMaterial() != null)
						 color = stack.getModule().getMaterial().primaryColor;
			 }catch(Exception e){
				 
			 }
		}
		
		@Override
		public void renderMachineItemStack(IModular machine, ItemStack stack) {
			
		}

		@Override
		public void renderMachine(IModularTileEntity entity, double x, double y, double z) {
			IIcon casingIcon = Block.getBlockFromItem(stack.getItem().getItem()).getIcon(0, stack.getItem().getItemDamage());
			Tessellator t = Tessellator.instance;
			GL11.glPushMatrix();
			GL11.glTranslated(x + 0.5D, y + 0.5D, z + 0.5D);
			GL11.glPushMatrix();
			
			RenderUtils.bindTexture(texture);
			if(color != 0){
				float f1 = (color >> 16 & 255) / 255.0F;
				float f2 = (color >> 8 & 255) / 255.0F;
				float f3 = (color & 255) / 255.0F;
		        GL11.glColor4f(f1, f2, f3, 1.0F);
			}
			
			//Down
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.5, -0.5, -0.5, 0.5, 0);
			t.addVertexWithUV(0.5, -0.5, -0.5, 0.75, 0);
			t.addVertexWithUV(0.5, -0.5, 0.5, 0.75, 0.5);
			t.addVertexWithUV(-0.5, -0.5, 0.5, 0.5, 0.5);
			t.draw();
			
			//Top
		    t.startDrawingQuads();
		    t.setNormal(0, 1, 0);
		    t.addVertexWithUV(-0.5, 0.5, -0.5, 0.25, 0);
		    t.addVertexWithUV(-0.5, 0.5, 0.5, 0.25, 0.5);
		    t.addVertexWithUV(0.5, 0.5, 0.5, 0.5, 0.5);
		    t.addVertexWithUV(0.5, 0.5, -0.5, 0.5, 0);
		    t.draw();
		    
			//Front
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.5, 0.5, 0.5, 0.25, 0.5);
			t.addVertexWithUV(-0.5, -0.5, 0.5, 0.25, 1);
			t.addVertexWithUV(0.5, -0.5, 0.5, 0.5, 1);
			t.addVertexWithUV(0.5, 0.5, 0.5, 0.5, 0.5);
			t.draw();
			
			//Back
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.5, 0.5, -0.5, 0.75, 0.5);
			t.addVertexWithUV(0.5, -0.5, -0.5, 0.75, 1);
			t.addVertexWithUV(-0.5, -0.5, -0.5, 1, 1);
			t.addVertexWithUV(-0.5, 0.5, -0.5, 1, 0.5);
			t.draw();
		    
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.25, 0.5, 0.3125, 0.546875, 0.5);
			t.addVertexWithUV(0.25, -0.5, 0.3125, 0.546875, 1);
			t.addVertexWithUV(0.25, -0.5, -0.3125, 0.703125, 1);
			t.addVertexWithUV(0.25, 0.5, -0.3125, 0.703125, 0.5);
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.5, 0.5, 0.3125, 0.75, 0);
			t.addVertexWithUV(0.5, -0.5, 0.3125, 0.75, 0.5);
			t.addVertexWithUV(0.25, -0.5, 0.3125, 0.8125, 0.5);
			t.addVertexWithUV(0.25, 0.5, 0.3125, 0.8125, 0);
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.25, 0.5, -0.3125, 0.9375, 0);
			t.addVertexWithUV(0.25, -0.5, -0.3125, 0.9375, 0.5);
			t.addVertexWithUV(0.5, -0.5, -0.3125, 1, 0.5);
			t.addVertexWithUV(0.5, 0.5, -0.3125, 1, 0);
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.5, 0.5, 0.5, 0.5, 0.5);
			t.addVertexWithUV(0.5, -0.5, 0.5, 00.5, 1);
			t.addVertexWithUV(0.5, -0.5, 0.3125, 0.546875, 1);
			t.addVertexWithUV(0.5, 0.5, 0.3125, 0.546875, 0.5);
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.5, 0.5, -0.3125, 0.703125, 0.5);
			t.addVertexWithUV(0.5, -0.5, -0.3125, 0.703125, 1);
			t.addVertexWithUV(0.5, -0.5, -0.5, 0.75, 1);
			t.addVertexWithUV(0.5, 0.5, -0.5, 0.75, 0.5);
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.25, 0.5, -0.3125, 0.046875, 0.5);
			t.addVertexWithUV(-0.25, -0.5, -0.3125, 0.046875, 1);
			t.addVertexWithUV(-0.25, -0.5, 0.3125, 0.203125, 1);
			t.addVertexWithUV(-0.25, 0.5, 0.3125, 0.203125, 0.5);
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.25, 0.5, 0.3125, 0.1875, 0);
			t.addVertexWithUV(-0.25, -0.5, 0.3125, 0.1875, 0.5);
			t.addVertexWithUV(-0.5, -0.5, 0.3125, 0.25, 0.5);
			t.addVertexWithUV(-0.5, 0.5, 0.3125, 0.25, 0);
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.5, 0.5, -0.3125, 0, 0);
			t.addVertexWithUV(-0.5, -0.5, -0.3125, 0, 0.5);
			t.addVertexWithUV(-0.25, -0.5, -0.3125, 0.0625, 0.5);
			t.addVertexWithUV(-0.25, 0.5, -0.3125, 0.0625, 0);
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.5, 0.5, -0.5, 0, 0.5);
			t.addVertexWithUV(-0.5, -0.5, -0.5, 0, 1);
			t.addVertexWithUV(-0.5, -0.5, -0.3125, 0.046875, 1);
			t.addVertexWithUV(-0.5, 0.5, -0.3125, 0.046875, 0.5);
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.5, 0.5, 0.3125, 0.203125, 0.5);
			t.addVertexWithUV(-0.5, -0.5, 0.3125, 0.203125, 1);
			t.addVertexWithUV(-0.5, -0.5, 0.5, 0.25, 1);
			t.addVertexWithUV(-0.5, 0.5, 0.5, 0.25, 0.5);
			t.draw();
			
			GL11.glPopMatrix();
			GL11.glPopMatrix();
		}
		
	}
	
	public static class AlloySmelterRenderer implements IModularRenderer{

		public IModule module;
		
		public AlloySmelterRenderer(IModule module) {
			this.module = module;
		}
		
		@Override
		public void renderMachineItemStack(IModular machine, ItemStack stack) {
			for(Vector<ModuleStack> stacks : machine.getModules().values()){
				for(ModuleStack moduleStack : stacks){
					if(moduleStack != null){
						IModularRenderer renderer = moduleStack.getModule().getItemRenderer(machine, moduleStack, stack);
						if(renderer != null)
							renderer.renderMachineItemStack(machine, stack);
					}
				}
			}
		}

		@Override
		public void renderMachine(IModularTileEntity entity, double x, double y, double z) {
			IModular machine = entity.getModular();
			for(Vector<ModuleStack> stacks : machine.getModules().values()){
				for(ModuleStack stack : stacks){
					if(stack != null && stack.getModule() != module){
						IModularRenderer renderer = stack.getModule().getMachineRenderer(machine, stack, entity);
						if(renderer != null)
							renderer.renderMachine(entity, x, y, z);;
					}
				}
			}
		}
		
	}
	
}
