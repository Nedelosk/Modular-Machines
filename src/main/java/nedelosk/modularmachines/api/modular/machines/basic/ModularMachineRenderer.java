package nedelosk.modularmachines.api.modular.machines.basic;

import java.util.Locale;
import java.util.Vector;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.modular.module.basic.IModule;
import nedelosk.modularmachines.api.modular.module.tool.producer.energy.IProducerEngine;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class ModularMachineRenderer {

	public static class EngineRenderer implements IModularRenderer{

		public final ModuleStack<IModule, IProducerEngine> stack;
		public ResourceLocation texture;
		public ResourceLocation modeTexture;
		public int color = 0;
		
		public EngineRenderer(ModuleStack<IModule, IProducerEngine> stack) {
			this.stack = stack;
			modeTexture = new ResourceLocation("modularmachines", "textures/models/modules/engine/" + stack.getProducer().getMode().toLowerCase(Locale.ENGLISH) + "_engine.png");
			IReloadableResourceManager resourceManager = (SimpleReloadableResourceManager)Minecraft.getMinecraft().getResourceManager();
			try{
				resourceManager.getResource(new ResourceLocation("modularmachines", "textures/models/modules/engine/" + stack.getModule().getModifier(stack) + ".png")); 
			}catch(Exception e){
				texture = new ResourceLocation("modularmachines", "textures/models/modules/engine/iron.png");
			}
		}
		
		@Override
		public void renderMachineItemStack(IModular machine, ItemStack stack) {
			
		}

		@Override
		public void renderMachine(IModularTileEntity entity, double x, double y, double z) {
			Tessellator t = Tessellator.instance;
			TextureManager manager = Minecraft.getMinecraft().getTextureManager();
			GL11.glPushMatrix();
			GL11.glTranslated(x + 0.5D, y + 0.5D, z + 0.5D);
			GL11.glPushMatrix();
			if(entity.getFacing() == 2){
				GL11.glRotated(180, 0F, 1F, 0F);
			}else if(entity.getFacing() == 3){
			}else if(entity.getFacing() == 4){
				GL11.glRotated(270, 0F, 1F, 0F);
			}else if(entity.getFacing() == 5){
				GL11.glRotated(90, 0F, 1F, 0F);
			}
			
			manager.bindTexture(modeTexture);
			//Top
		    t.startDrawingQuads();
		    t.setNormal(0, 1, 0);
		    t.addVertexWithUV(-0.4375, 0.0625, -0.3125, 0.25, 0.25);
		    t.addVertexWithUV(-0.4375, 0.0625, 0.3125, 0.25, 1);
		    t.addVertexWithUV(-0.25, 0.0625, 0.3125, 0.5, 1);
		    t.addVertexWithUV(-0.25, 0.0625, -0.3125, 0.5, 0.25);
		    t.draw();
		    
		    //Down
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.4375, -0.125, -0.3125, 0.5, 0.25);
			t.addVertexWithUV(-0.25, -0.125, -0.3125, 0.75, 0.25);
			t.addVertexWithUV(-0.25, -0.125, 0.3125, 0.75, 1);
			t.addVertexWithUV(-0.4375, -0.125, 0.3125, 0.5, 1);
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.1875, 0.5, 0.3125, 0, 0);
			t.addVertexWithUV(0.1875, -0.5, 0.3125, 0, 1);
			t.addVertexWithUV(0.1875, -0.5, -0.3125, 1, 1);
			t.addVertexWithUV(0.1875, 0.5, -0.3125, 1, 0);
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.4375, 0.0625, -0.3125, 1, 0.25);
			t.addVertexWithUV(-0.4375, -0.125, -0.3125, 0.75, 0.25);
			t.addVertexWithUV(-0.4375, -0.125, 0.3125, 0.75, 1);
			t.addVertexWithUV(-0.4375, 0.0625, 0.3125, 1, 1);
			t.draw();
			
			GL11.glPopMatrix();
			GL11.glPopMatrix();
		}
		
	}
	
	public static class CasingRenderer implements IModularRenderer{

		public final ModuleStack stack;
		public ResourceLocation texture;
		public int color = 0;
		
		public CasingRenderer(ModuleStack stack) {
			this.stack = stack;
			IReloadableResourceManager resourceManager = (SimpleReloadableResourceManager)Minecraft.getMinecraft().getResourceManager();
			try{
				resourceManager.getResource(new ResourceLocation("modularmachines", "textures/models/modules/casing/" + stack.getModule().getModifier(stack) + ".png")); 
			}catch(Exception e){
				texture = new ResourceLocation("modularmachines", "textures/models/modules/casing/iron.png");
			}
		}
		
		@Override
		public void renderMachineItemStack(IModular machine, ItemStack stack) {
			
		}

		@Override
		public void renderMachine(IModularTileEntity entity, double x, double y, double z) {
			/*Tessellator t = Tessellator.instance;
			TextureManager manager = Minecraft.getMinecraft().getTextureManager();
			GL11.glPushMatrix();
			GL11.glTranslated(x + 0.5D, y + 0.5D, z + 0.5D);
			GL11.glPushMatrix();
			if(entity.getFacing() == 2){
				GL11.glRotated(180, 0F, 1F, 0F);
			}else if(entity.getFacing() == 3){
			}else if(entity.getFacing() == 4){
				GL11.glRotated(270, 0F, 1F, 0F);
			}else if(entity.getFacing() == 5){
				GL11.glRotated(90, 0F, 1F, 0F);
			}
			
			manager.bindTexture(texture);
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
			t.addVertexWithUV(0.1875, 0.5, 0.3125, 0.546875, 0.5);
			t.addVertexWithUV(0.1875, -0.5, 0.3125, 0.546875, 1);
			t.addVertexWithUV(0.1875, -0.5, -0.3125, 0.703125, 1);
			t.addVertexWithUV(0.1875, 0.5, -0.3125, 0.703125, 0.5);
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.5, 0.5, 0.3125, 0.75, 0);
			t.addVertexWithUV(0.5, -0.5, 0.3125, 0.75, 0.5);
			t.addVertexWithUV(0.1875, -0.5, 0.3125, 0.828125, 0.5);
			t.addVertexWithUV(0.1875, 0.5, 0.3125, 0.828125, 0);
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.1875, 0.5, -0.3125, 0.921875, 0);
			t.addVertexWithUV(0.1875, -0.5, -0.3125, 0.921875, 0.5);
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
			t.addVertexWithUV(-0.1875, 0.5, -0.3125, 0.046875, 0.5);
			t.addVertexWithUV(-0.1875, -0.5, -0.3125, 0.046875, 1);
			t.addVertexWithUV(-0.1875, -0.5, 0.3125, 0.203125, 1);
			t.addVertexWithUV(-0.1875, 0.5, 0.3125, 0.203125, 0.5);
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.1875, 0.5, 0.3125, 0.171875, 0);
			t.addVertexWithUV(-0.1875, -0.5, 0.3125, 0.171875, 0.5);
			t.addVertexWithUV(-0.5, -0.5, 0.3125, 0.25, 0.5);
			t.addVertexWithUV(-0.5, 0.5, 0.3125, 0.25, 0);
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.5, 0.5, -0.3125, 0, 0);
			t.addVertexWithUV(-0.5, -0.5, -0.3125, 0, 0.5);
			t.addVertexWithUV(-0.1875, -0.5, -0.3125, 0.078125, 0.5);
			t.addVertexWithUV(-0.1875, 0.5, -0.3125, 0.078125, 0);
			t.draw();
			
			//Front
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.5, 0.5, -0.5, 0, 0.5);
			t.addVertexWithUV(-0.5, -0.5, -0.5, 0, 1);
			t.addVertexWithUV(-0.5, -0.5, -0.3125, 0.046875, 1);
			t.addVertexWithUV(-0.5, 0.5, -0.3125, 0.046875, 0.5);
			t.draw();
			
			//Front
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.5, 0.5, 0.3125, 0.203125, 0.5);
			t.addVertexWithUV(-0.5, -0.5, 0.3125, 0.203125, 1);
			t.addVertexWithUV(-0.5, -0.5, 0.5, 0.25, 1);
			t.addVertexWithUV(-0.5, 0.5, 0.5, 0.25, 0.5);
			t.draw();
			
			GL11.glPopMatrix();
			GL11.glPopMatrix();*/
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
						if(moduleStack.getModule() != module){
							
						}else{
							IModularRenderer renderer = moduleStack.getProducer().getItemRenderer(machine, moduleStack, stack);
							if(renderer != null)
								renderer.renderMachineItemStack(machine, stack);
						}
					}
				}
			}
		}

		@Override
		public void renderMachine(IModularTileEntity entity, double x, double y, double z) {
			IModular machine = entity.getModular();
			for(Vector<ModuleStack> stacks : machine.getModules().values()){
				for(ModuleStack stack : stacks){
					if(stack != null && stack.getModule() != module && stack.getProducer() == null){
						IModularRenderer renderer = stack.getModule().getMachineRenderer(machine, stack, entity);
						if(renderer != null)
							renderer.renderMachine(entity, x, y, z);
					}
					else if(stack != null && stack.getModule() != module && stack.getProducer() != null){
						IModularRenderer renderer = stack.getProducer().getMachineRenderer(machine, stack, entity);
						if(renderer != null)
							renderer.renderMachine(entity, x, y, z);
					}
				}
			}
		}
		
	}
	
}
