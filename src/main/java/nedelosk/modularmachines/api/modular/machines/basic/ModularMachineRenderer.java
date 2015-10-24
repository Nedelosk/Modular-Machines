package nedelosk.modularmachines.api.modular.machines.basic;

import java.util.Locale;
import java.util.Vector;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.modular.module.basic.IModule;
import nedelosk.modularmachines.api.modular.module.tool.producer.energy.IProducerBattery;
import nedelosk.modularmachines.api.modular.module.tool.producer.energy.IProducerEngine;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;

@SideOnly(Side.CLIENT)
public class ModularMachineRenderer {

	public static class EngineRenderer implements IModularRenderer{

		public ModelBase model = new ModelBase() {
		};
		
	    public ModelRenderer Base_Engine;
	    public ModelRenderer Disc_Engine;
	    public ModelRenderer Window_Engine;
		
		public final ModuleStack<IModule, IProducerEngine> stack;
		public ResourceLocation baseTexture;
		public ResourceLocation discTexture;
		public ResourceLocation windowTexture;
		
		public EngineRenderer(ModuleStack<IModule, IProducerEngine> stack) {
			this.stack = stack;
			
	        Base_Engine = new ModelRenderer(model, 0, 0);
	        Base_Engine.setRotationPoint(2.0F, 15.0F, -5.0F);
	        Base_Engine.addBox(0.0F, 0.0F, 0.0F, 3, 3, 10, 0.0F);
	        Disc_Engine = new ModelRenderer(model, 0, 0);
	        Disc_Engine.setRotationPoint(1.0F, 14.0F, -5.0F);
	        Disc_Engine.addBox(0.0F, 0.0F, 0.0F, 5, 5, 3, 0.0F);
	        Window_Engine = new ModelRenderer(model, 0, 0);
	        Window_Engine.setRotationPoint(7.0F, 10.5F, -6.0F);
	        Window_Engine.addBox(0.0F, 0.0F, 0.0F, 1, 12, 12, 0.0F);
			
	        baseTexture = loadTexture("normal", stack.getProducer().getMode().toLowerCase(Locale.ENGLISH), "engine/", "_base.png");
	        discTexture = loadTexture("iron", stack.getProducer().getModifier(stack), "engine/", ".png");
	        windowTexture = loadTexture("normal", stack.getProducer().getMode().toLowerCase(Locale.ENGLISH), "engine/", "_window.png");
		}
		
		@Override
		public void renderMachineItemStack(IModular machine, ItemStack itemStack) {
			Tessellator t = Tessellator.instance;
			TextureManager manager = Minecraft.getMinecraft().getTextureManager();
			GL11.glPushMatrix();
			GL11.glTranslated(0.5F, 1.5F, 0.5F);
			GL11.glRotated(180, 0F, 0F, 1F);
			GL11.glRotated(180, 0F, 1F, 0F);
			GL11.glPushMatrix();
			
			manager.bindTexture(baseTexture);
			Base_Engine.render(0.0625F);
			manager.bindTexture(windowTexture);
			Window_Engine.render(0.0625F);
			manager.bindTexture(discTexture);
			Disc_Engine.render(0.0625F);
			
			GL11.glPopMatrix();
			GL11.glPopMatrix();
		}

		@Override
		public void renderMachine(IModularTileEntity entity, double x, double y, double z) {
			Tessellator t = Tessellator.instance;
			TextureManager manager = Minecraft.getMinecraft().getTextureManager();
			GL11.glPushMatrix();
			GL11.glTranslated((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
			GL11.glRotated(180, 0F, 0F, 1F);
			GL11.glPushMatrix();
			if(entity.getFacing() == 2){
			}else if(entity.getFacing() == 3){
				GL11.glRotated(180, 0F, 1F, 0F);
			}else if(entity.getFacing() == 4){
				GL11.glRotated(270, 0F, 1F, 0F);
			}else if(entity.getFacing() == 5){
				GL11.glRotated(90, 0F, 1F, 0F);
			}
			
			float step;
			float progress = stack.getProducer().getProgress();

			if (progress > 0.5) {
				step = 5.99F - (progress - 0.5F) * 2F * 5.99F;
			} else {
				step = progress * 2F * 5.99F;
			}

			float tfactor = step / 16;
			ForgeDirection direction = ForgeDirection.values()[entity.getFacing() - 2];
			manager.bindTexture(discTexture);
			GL11.glTranslatef(direction.offsetX * tfactor, direction.offsetY * tfactor, direction.offsetZ * tfactor);
			Disc_Engine.render(0.0625F);
			GL11.glTranslatef(-direction.offsetX * tfactor, -direction.offsetY * tfactor, -direction.offsetZ * tfactor);
			
			manager.bindTexture(baseTexture);
			Base_Engine.render(0.0625F);
			manager.bindTexture(windowTexture);
			Window_Engine.render(0.0625F);
			
			GL11.glPopMatrix();
			GL11.glPopMatrix();
		}
		
	}
	
	public static class BatteryRenderer implements IModularRenderer{

		public ModelBase model = new ModelBase() {
		};
		
	    public ModelRenderer Battery_Base;
	    public ModelRenderer Battery_Top;
	    public ModelRenderer Battery_Down;
	    public ModelRenderer Battery_Right;
	    public ModelRenderer Battery_Left;
		
		public final ModuleStack<IModule, IProducerBattery> stack;
		public ResourceLocation baseTexture;
		public ResourceLocation topTexture;
		public ResourceLocation downTexture;
		public ResourceLocation rightTexture;
		public ResourceLocation leftTexture;
		
		public BatteryRenderer(ModuleStack<IModule, IProducerBattery> stack, IModular modular) {
			this.stack = stack;
			
	        this.Battery_Left = new ModelRenderer(model, 0, 0);
	        this.Battery_Left.setRotationPoint(-8.0F, 12.5F, -6.0F);
	        this.Battery_Left.addBox(0.0F, 0.0F, 0.0F, 1, 8, 2, 0.0F);
	        this.Battery_Right = new ModelRenderer(model, 0, 0);
	        this.Battery_Right.setRotationPoint(-8.0F, 12.5F, 4.0F);
	        this.Battery_Right.addBox(0.0F, 0.0F, 0.0F, 1, 8, 2, 0.0F);
	        this.Battery_Down = new ModelRenderer(model, 0, 0);
	        this.Battery_Down.setRotationPoint(-8.0F, 20.5F, -6.0F);
	        this.Battery_Down.addBox(0.0F, 0.0F, 0.0F, 1, 2, 12, 0.0F);
	        this.Battery_Top = new ModelRenderer(model, 0, 0);
	        this.Battery_Top.setRotationPoint(-8.0F, 10.5F, -6.0F);
	        this.Battery_Top.addBox(0.0F, 0.0F, 0.0F, 1, 2, 12, 0.0F);
	        this.Battery_Base = new ModelRenderer(model, 0, 0);
	        this.Battery_Base.setRotationPoint(-7.5F, 12.5F, -4.0F);
	        this.Battery_Base.addBox(0.0F, 0.0F, 0.0F, 1, 8, 8, 0.0F);
			
	        int energy = (modular.getManager().getEnergyHandler().getEnergyStored(ForgeDirection.EAST) / (modular.getManager().getEnergyHandler().getMaxEnergyStored(ForgeDirection.EAST) / 8));
	        baseTexture = loadTexture("iron", stack.getProducer().getModifier(stack).toLowerCase(Locale.ENGLISH), "battery/", "_base_" + energy + ".png");
			topTexture = loadTexture("iron", stack.getProducer().getModifier(stack).toLowerCase(Locale.ENGLISH), "battery/", "_top.png");
			downTexture = loadTexture("iron", stack.getProducer().getModifier(stack).toLowerCase(Locale.ENGLISH), "battery/", "_down.png");
			leftTexture = loadTexture("iron", stack.getProducer().getModifier(stack).toLowerCase(Locale.ENGLISH), "battery/", "_left.png");
			rightTexture = loadTexture("iron", stack.getProducer().getModifier(stack).toLowerCase(Locale.ENGLISH), "battery/", "_right.png");
		}
		
		@Override
		public void renderMachineItemStack(IModular machine, ItemStack stack) {
			Tessellator t = Tessellator.instance;
			TextureManager manager = Minecraft.getMinecraft().getTextureManager();
			GL11.glPushMatrix();
			GL11.glTranslated(0.5F, 1.5F, 0.5F);
			GL11.glRotated(180, 0F, 0F, 1F);
			GL11.glRotated(180, 0F, 1F, 0F);
			GL11.glPushMatrix();
			
			manager.bindTexture(baseTexture);
			Battery_Base.render(0.0625F);
			manager.bindTexture(topTexture);
			Battery_Top.render(0.0625F);
			manager.bindTexture(downTexture);
			Battery_Down.render(0.0625F);
			manager.bindTexture(leftTexture);
			Battery_Left.render(0.0625F);
			manager.bindTexture(rightTexture);
			Battery_Right.render(0.0625F);
			
			GL11.glPopMatrix();
			GL11.glPopMatrix();
		}

		@Override
		public void renderMachine(IModularTileEntity entity, double x, double y, double z) {
			Tessellator t = Tessellator.instance;
			TextureManager manager = Minecraft.getMinecraft().getTextureManager();
			GL11.glPushMatrix();
			GL11.glTranslated((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
			GL11.glRotated(180, 0F, 0F, 1F);
			GL11.glPushMatrix();
			if(entity.getFacing() == 2){
			}else if(entity.getFacing() == 3){
				GL11.glRotated(180, 0F, 1F, 0F);
			}else if(entity.getFacing() == 4){
				GL11.glRotated(270, 0F, 1F, 0F);
			}else if(entity.getFacing() == 5){
				GL11.glRotated(90, 0F, 1F, 0F);
			}
			
			manager.bindTexture(baseTexture);
			Battery_Base.render(0.0625F);
			manager.bindTexture(topTexture);
			Battery_Top.render(0.0625F);
			manager.bindTexture(downTexture);
			Battery_Down.render(0.0625F);
			manager.bindTexture(leftTexture);
			Battery_Left.render(0.0625F);
			manager.bindTexture(rightTexture);
			Battery_Right.render(0.0625F);
			
			GL11.glPopMatrix();
			GL11.glPopMatrix();
		}
		
	}
	
	public static class CasingRenderer implements IModularRenderer{

		public final ModuleStack stack;
		
		public ModelBase model = new ModelBase() {
		};
		
	    public ModelRenderer Base_Casing;
	    public ModelRenderer Front_Casing;
	    public ModelRenderer Back_Casing;
	    public ModelRenderer Top_Right_Casing;
	    public ModelRenderer Down_Right_Casing;
	    
		public ResourceLocation baseTexture;
		public ResourceLocation frontTexture;
		public ResourceLocation backTexture;
		public ResourceLocation topTexture;
		public ResourceLocation downTexture;
		
		public CasingRenderer(ModuleStack stack) {
			this.stack = stack;
			
	        this.Top_Right_Casing = new ModelRenderer(model, 0, 0);
	        this.Top_Right_Casing.setRotationPoint(1.0F, 9.0F, -5.0F);
	        this.Top_Right_Casing.addBox(0.0F, 0.0F, 0.0F, 6, 2, 10, 0.0F);
	        this.Front_Casing = new ModelRenderer(model, 0, 0);
	        this.Front_Casing.setRotationPoint(-7.0F, 9.0F, -7.0F);
	        this.Front_Casing.addBox(0.0F, 0.0F, 0.0F, 14, 15, 2, 0.0F);
	        this.Back_Casing = new ModelRenderer(model, 0, 0);
	        this.Back_Casing.setRotationPoint(-7.0F, 9.0F, 5.0F);
	        this.Back_Casing.addBox(0.0F, 0.0F, 0.0F, 14, 15, 2, 0.0F);
	        this.Base_Casing = new ModelRenderer(model, 0, 0);
	        this.Base_Casing.setRotationPoint(-7.0F, 9.0F, -5.0F);
	        this.Base_Casing.addBox(0.0F, 0.0F, 0.0F, 8, 15, 10, 0.0F);
	        this.Down_Right_Casing = new ModelRenderer(model, 0, 0);
	        this.Down_Right_Casing.setRotationPoint(1.0F, 22.0F, -5.0F);
	        this.Down_Right_Casing.addBox(0.0F, 0.0F, 0.0F, 6, 2, 10, 0.0F);
			
	        baseTexture = loadTexture("iron", stack.getModule().getTypeModifier(stack).toLowerCase(Locale.ENGLISH), "casing/", "_base.png");
	        frontTexture =  loadTexture("iron", stack.getModule().getTypeModifier(stack).toLowerCase(Locale.ENGLISH), "casing/", "_front.png");
	        backTexture = loadTexture("iron", stack.getModule().getTypeModifier(stack).toLowerCase(Locale.ENGLISH), "casing/", "_back.png");
	        topTexture = loadTexture("iron", stack.getModule().getTypeModifier(stack).toLowerCase(Locale.ENGLISH), "casing/", "_top.png");
	        downTexture = loadTexture("iron", stack.getModule().getTypeModifier(stack).toLowerCase(Locale.ENGLISH), "casing/", "_down.png");
		}
		
		@Override
		public void renderMachineItemStack(IModular machine, ItemStack stack) {
			Tessellator t = Tessellator.instance;
			TextureManager manager = Minecraft.getMinecraft().getTextureManager();
			GL11.glPushMatrix();
			GL11.glTranslated(0.5F, 1.5F, 0.5F);
			GL11.glRotated(180, 0F, 0F, 1F);
			GL11.glRotated(180, 0F, 1F, 0F);
			GL11.glPushMatrix();
			
			manager.bindTexture(baseTexture);
			Base_Casing.render(0.0625F);
			manager.bindTexture(frontTexture);
			Front_Casing.render(0.0625F);
			manager.bindTexture(backTexture);
			Back_Casing.render(0.0625F);
			manager.bindTexture(topTexture);
			Top_Right_Casing.render(0.0625F);
			manager.bindTexture(downTexture);
			Down_Right_Casing.render(0.0625F);
			
			GL11.glPopMatrix();
			GL11.glPopMatrix();
		}

		@Override
		public void renderMachine(IModularTileEntity entity, double x, double y, double z) {
			Tessellator t = Tessellator.instance;
			TextureManager manager = Minecraft.getMinecraft().getTextureManager();
			GL11.glPushMatrix();
			GL11.glTranslated((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
			GL11.glRotated(180, 0F, 0F, 1F);
			GL11.glPushMatrix();
			if(entity.getFacing() == 2){
			}else if(entity.getFacing() == 3){
				GL11.glRotated(180, 0F, 1F, 0F);
			}else if(entity.getFacing() == 4){
				GL11.glRotated(270, 0F, 1F, 0F);
			}else if(entity.getFacing() == 5){
				GL11.glRotated(90, 0F, 1F, 0F);
			}
			
			manager.bindTexture(baseTexture);
			Base_Casing.render(0.0625F);
			manager.bindTexture(frontTexture);
			Front_Casing.render(0.0625F);
			manager.bindTexture(backTexture);
			Back_Casing.render(0.0625F);
			manager.bindTexture(topTexture);
			Top_Right_Casing.render(0.0625F);
			manager.bindTexture(downTexture);
			Down_Right_Casing.render(0.0625F);
			
			GL11.glPopMatrix();
			GL11.glPopMatrix();
		}
		
	}
	
	public static class MachineRenderer implements IModularRenderer{

		public IModule module;
		
		public MachineRenderer(IModule module) {
			this.module = module;
		}
		
		@Override
		public void renderMachineItemStack(IModular machine, ItemStack itemStack) {
			for(Vector<ModuleStack> stacks : machine.getModules().values()){
				for(ModuleStack moduleStack : stacks){
					if(moduleStack != null){
						if(moduleStack.getModule() != module && moduleStack.getProducer() == null){
							IModularRenderer renderer = moduleStack.getModule().getItemRenderer(machine, moduleStack, itemStack);
							if(renderer != null)
								renderer.renderMachineItemStack(machine, itemStack);
						}else if(moduleStack.getModule() != module && moduleStack.getProducer() != null){
							IModularRenderer renderer = moduleStack.getProducer().getItemRenderer(machine, moduleStack, itemStack);
							if(renderer != null)
								renderer.renderMachineItemStack(machine, itemStack);
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
	
	public static ResourceLocation loadTexture(String defaultName, String name, String befor, String after){
		try{
			SimpleReloadableResourceManager manager = (SimpleReloadableResourceManager) Minecraft.getMinecraft().getResourceManager();
			if(manager.getResource(new ResourceLocation("modularmachines", "textures/models/modules/" + befor + name + after)) != null) {
				return new ResourceLocation("modularmachines", "textures/models/modules/" + befor + name + after);
			}
		}catch(Exception e){
			return new ResourceLocation("modularmachines", "textures/models/modules/" + befor + defaultName + after);
		}
		return null;
	}
	
}
