package de.nedelosk.forestmods.client.render.tile;

import java.util.ArrayList;

import com.google.common.collect.Lists;

import de.nedelosk.forestmods.common.blocks.tile.TileModular;
import de.nedelosk.forestmods.common.modular.Modular;
import de.nedelosk.forestmods.library.modular.IModular;
import de.nedelosk.forestmods.library.modular.renderer.IRenderState;
import de.nedelosk.forestmods.library.modular.renderer.ISimpleRenderer;
import de.nedelosk.forestmods.library.modules.IModule;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileModularMachineRenderer extends TileEntitySpecialRenderer {

	public ArrayList<RenderEntry> entrys = Lists.newArrayList();

	@Override
	public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float p_147500_8_) {
		if (entity instanceof TileModular) {
			TileModular machineTile = (TileModular) entity;
			IRenderState state = new RenderState(null, x, y, z, null, machineTile.modular);
			if (machineTile.modular != null) {
				ISimpleRenderer renderer = machineTile.modular.getRenderer(state);
				if (renderer != null) {
					renderer.render(state);
				}
			}
		}
	}

	public void renderItem(ItemStack stack) {
		NBTTagCompound tagCompound = stack.getTagCompound();
		if (!stack.hasTagCompound()) {
			return;
		}
		IModular machine;
		if (getEntry(stack) == null) {
			machine = addEntry(new Modular(tagCompound, null), stack).modular;
		} else {
			machine = getEntry(stack).modular;
		}
		IRenderState state = new RenderState(stack, 0, 0, 0, null, machine);
		if (machine != null) {
			ISimpleRenderer renderer = machine.getRenderer(state);
			if (renderer != null) {
				renderer.render(state);
			}
		}
	}

	public RenderEntry getEntry(ItemStack stack) {
		for(RenderEntry entry : entrys) {
			if (entry.equals(stack)) {
				return entry;
			}
		}
		return null;
	}

	public RenderEntry addEntry(IModular modular, ItemStack stack) {
		RenderEntry entry = new RenderEntry(modular, stack);
		entrys.add(entry);
		return entry;
	}

	public static class RenderState implements IRenderState {

		ItemStack itemStack;
		double x;
		double y;
		double z;
		IModule module;
		IModular modular;

		public RenderState(ItemStack itemStack, double x, double y, double z, IModule module, IModular modular) {
			this.itemStack = itemStack;
			this.x = x;
			this.y = y;
			this.z = z;
			this.module = module;
			this.modular = modular;
		}

		@Override
		public ItemStack getItem() {
			return itemStack;
		}

		@Override
		public double getX() {
			return x;
		}

		@Override
		public double getY() {
			return y;
		}

		@Override
		public double getZ() {
			return z;
		}
		
		@Override
		public IModule getCurrentModule() {
			return module;
		}
		
		@Override
		public void setCurrentModule(IModule module) {
			this.module = module;
		}

		@Override
		public IModular getModular() {
			return modular;
		}
	}

	public static class RenderEntry {

		private IModular modular;
		private ItemStack itemStack;

		public RenderEntry(IModular modular, ItemStack itemStack) {
			this.modular = modular;
			this.itemStack = itemStack;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof RenderEntry) {
				RenderEntry entry = (RenderEntry) obj;
				if (entry.itemStack == null) {
					return false;
				}
				if (itemStack.getItem() == entry.itemStack.getItem() && itemStack.getItemDamage() == entry.itemStack.getItemDamage()
						&& (itemStack.hasTagCompound() && entry.itemStack.hasTagCompound()
								&& itemStack.getTagCompound().equals(entry.itemStack.getTagCompound())
								|| !itemStack.hasTagCompound() && !entry.itemStack.hasTagCompound())) {
					return true;
				}
			} else if (obj instanceof ItemStack) {
				ItemStack entry = (ItemStack) obj;
				if (itemStack.getItem() == entry.getItem() && itemStack.getItemDamage() == entry.getItemDamage()
						&& (itemStack.hasTagCompound() && entry.hasTagCompound() && itemStack.getTagCompound().equals(entry.getTagCompound())
								|| !itemStack.hasTagCompound() && !entry.hasTagCompound())) {
					return true;
				}
			}
			return false;
		}
	}
}
