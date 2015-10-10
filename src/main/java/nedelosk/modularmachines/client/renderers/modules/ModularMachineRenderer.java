package nedelosk.modularmachines.client.renderers.modules;

import java.util.Vector;

import org.lwjgl.opengl.GL11;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.machines.basic.IModularRenderer;
import nedelosk.modularmachines.api.modular.machines.basic.IModularTileEntity;
import nedelosk.modularmachines.api.modular.module.basic.IModule;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.nedeloskcore.utils.RenderUtils;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ModularMachineRenderer {

	public static class CasingRenderer implements IModularRenderer{

		public ModuleStack stack;
		
		public CasingRenderer(ModuleStack stack) {
			this.stack = stack;
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
			RenderUtils.bindBlockTexture();
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.5, 0.5, -0.5, casingIcon.getMaxU(), casingIcon.getMaxV());
			t.addVertexWithUV(0.5, -0.5, -0.5, casingIcon.getMaxU(), casingIcon.getMinV());
			t.addVertexWithUV(-0.5, -0.5, -0.5, casingIcon.getMinU(), casingIcon.getMinV());
			t.addVertexWithUV(-0.5, 0.5, -0.5, casingIcon.getMinU(), casingIcon.getMaxV());
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.5, -0.5, -0.5, casingIcon.getMaxU(), casingIcon.getMaxV());
			t.addVertexWithUV(0.5, -0.5, -0.5, casingIcon.getMaxU(), casingIcon.getMinV());
			t.addVertexWithUV(0.5, -0.5, 0.5, casingIcon.getMinU(), casingIcon.getMinV());
			t.addVertexWithUV(-0.5, -0.5, 0.5, casingIcon.getMinU(), casingIcon.getMaxV());
			t.draw();
			
		    t.startDrawingQuads();
		    t.setNormal(0, 1, 0);
		    t.addVertexWithUV(-0.5, 0.5, -0.5, casingIcon.getMaxU(), casingIcon.getMaxV());
		    t.addVertexWithUV(-0.5, 0.5, 0.5, casingIcon.getMaxU(), casingIcon.getMinV());
		    t.addVertexWithUV(0.5, 0.5, 0.5, casingIcon.getMinU(), casingIcon.getMinV());
		    t.addVertexWithUV(0.5, 0.5, -0.5, casingIcon.getMinU(), casingIcon.getMaxV());
		    t.draw();
			
			/*t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.25, 0.5, 0.3125, casingIcon.getMaxU(), casingIcon.getMaxV());
			t.addVertexWithUV(0.25, -0.5, 0.3125, casingIcon.getMaxU(), casingIcon.getMinV());
			t.addVertexWithUV(0.25, -0.5, -0.3125, casingIcon.getMinU(), casingIcon.getMinV());
			t.addVertexWithUV(0.25, 0.5, -0.3125, casingIcon.getMinU(), casingIcon.getMaxV());
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.5, 0.5, 0.5, casingIcon.getMaxU(), casingIcon.getMaxV());
			t.addVertexWithUV(0.5, -0.5, 0.5, casingIcon.getMaxU(), casingIcon.getMinV());
			t.addVertexWithUV(0.5, -0.5, 0.3125, casingIcon.getMinU(), casingIcon.getMinV());
			t.addVertexWithUV(0.5, 0.5, 0.3125, casingIcon.getMinU(), casingIcon.getMaxV());
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.5, 0.5, -0.3125, casingIcon.getMaxU(), casingIcon.getMaxV());
			t.addVertexWithUV(0.5, -0.5, -0.3125, casingIcon.getMaxU(), casingIcon.getMinV());
			t.addVertexWithUV(0.5, -0.5, -0.5, casingIcon.getMinU(), casingIcon.getMinV());
			t.addVertexWithUV(0.5, 0.5, -0.5, casingIcon.getMinU(), casingIcon.getMaxV());
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.5, 0.5, 0.3125, casingIcon.getMaxU(), casingIcon.getMaxV());
			t.addVertexWithUV(0.5, -0.5, 0.3125, casingIcon.getMaxU(), casingIcon.getMinV());
			t.addVertexWithUV(0.25, -0.5, 0.3125, casingIcon.getMinU(), casingIcon.getMinV());
			t.addVertexWithUV(0.25, 0.5, 0.3125, casingIcon.getMinU(), casingIcon.getMaxV());
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.25, 0.5, -0.3125, casingIcon.getMaxU(), casingIcon.getMaxV());
			t.addVertexWithUV(0.25, -0.5, -0.3125, casingIcon.getMaxU(), casingIcon.getMinV());
			t.addVertexWithUV(0.5, -0.5, -0.3125, casingIcon.getMinU(), casingIcon.getMinV());
			t.addVertexWithUV(0.5, 0.5, -0.3125, casingIcon.getMinU(), casingIcon.getMaxV());
			t.draw();*/
			
			/*t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.25, 0.5, -0.3125, casingIcon.getMaxU(), casingIcon.getMaxV());
			t.addVertexWithUV(-0.25, -0.5, -0.3125, casingIcon.getMaxU(), casingIcon.getMinV());
			t.addVertexWithUV(-0.25, -0.5, 0.3125, casingIcon.getMinU(), casingIcon.getMinV());
			t.addVertexWithUV(-0.25, 0.5, 0.3125, casingIcon.getMinU(), casingIcon.getMaxV());
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.5, 0.5, -0.5, casingIcon.getMaxU(), casingIcon.getMaxV());
			t.addVertexWithUV(-0.5, -0.5, -0.5, casingIcon.getMaxU(), casingIcon.getMinV());
			t.addVertexWithUV(-0.5, -0.5, -0.3125, casingIcon.getMinU(), casingIcon.getMinV());
			t.addVertexWithUV(-0.5, 0.5, -0.3125, casingIcon.getMinU(), casingIcon.getMaxV());
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.5, 0.5, 0.3125, casingIcon.getMaxU(), casingIcon.getMaxV());
			t.addVertexWithUV(-0.5, -0.5, 0.3125, casingIcon.getMaxU(), casingIcon.getMinV());
			t.addVertexWithUV(-0.5, -0.5, 0.5, casingIcon.getMinU(), casingIcon.getMinV());
			t.addVertexWithUV(-0.5, 0.5, 0.5, casingIcon.getMinU(), casingIcon.getMaxV());
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.25, 0.5, 0.3125, casingIcon.getMaxU(), casingIcon.getMaxV());
			t.addVertexWithUV(-0.25, -0.5, 0.3125, casingIcon.getMaxU(), casingIcon.getMinV());
			t.addVertexWithUV(-0.5, -0.5, 0.3125, casingIcon.getMinU(), casingIcon.getMinV());
			t.addVertexWithUV(-0.5, 0.5, 0.3125, casingIcon.getMinU(), casingIcon.getMaxV());
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.5, 0.5, -0.3125, casingIcon.getMaxU(), casingIcon.getMaxV());
			t.addVertexWithUV(-0.5, -0.5, -0.3125, casingIcon.getMaxU(), casingIcon.getMinV());
			t.addVertexWithUV(-0.25, -0.5, -0.3125, casingIcon.getMinU(), casingIcon.getMinV());
			t.addVertexWithUV(-0.25, 0.5, -0.3125, casingIcon.getMinU(), casingIcon.getMaxV());
			t.draw();*/
			
			//Back
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.5, 0.5, 0.5, casingIcon.getMaxU(), casingIcon.getMaxV());
			t.addVertexWithUV(-0.5, -0.5, 0.5, casingIcon.getMaxU(), casingIcon.getMinV());
			t.addVertexWithUV(0.5, -0.5, 0.5, casingIcon.getMinU(), casingIcon.getMinV());
			t.addVertexWithUV(0.5, 0.5, 0.5, casingIcon.getMinU(), casingIcon.getMaxV());
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
