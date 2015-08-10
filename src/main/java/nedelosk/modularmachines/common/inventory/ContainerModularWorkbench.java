package nedelosk.modularmachines.common.inventory;

import nedelosk.modularmachines.common.blocks.tile.TileModularWorkbench;
import nedelosk.modularmachines.common.core.manager.ModularRecipeManager;
import nedelosk.nedeloskcore.common.inventory.ContainerBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;

public class ContainerModularWorkbench
  extends ContainerBase
{
  private TileModularWorkbench workbench;
  private InventoryPlayer ip;
  
  public ContainerModularWorkbench(TileModularWorkbench tile, InventoryPlayer par1InventoryPlayer)
  {
	  super(tile, par1InventoryPlayer);
    this.workbench = tile;
    this.workbench.container = this;
    this.ip = par1InventoryPlayer;
    onCraftMatrixChanged(this.workbench);
  }
  
  @Override
public void onCraftMatrixChanged(IInventory par1IInventory)
  {
    InventoryCrafting ic = new InventoryCrafting(new ContainerDummy(), 5, 5);
    for (int a = 0; a < 25; a++) {
      ic.setInventorySlotContents(a, this.workbench.getStackInSlot(a));
    }
    this.workbench.setInventorySlotContentsSoftly(25, null);
    if ((this.workbench.getStackInSlot(25) == null))
    {
       this.workbench.setInventorySlotContentsSoftly(25, ModularRecipeManager.findMatchingModularRecipe(this.workbench, this.ip.player));
    }
  }
  
  @Override
public void onContainerClosed(EntityPlayer par1EntityPlayer)
  {
    super.onContainerClosed(par1EntityPlayer);
    if (!this.workbench.getWorldObj().isRemote) {
      this.workbench.container = null;
    }
  }
  
  @Override
public boolean canInteractWith(EntityPlayer par1EntityPlayer)
  {
    return this.workbench.getWorldObj().getTileEntity(this.workbench.xCoord, this.workbench.yCoord, this.workbench.zCoord) == this.workbench;
  }
  
  @Override
public ItemStack slotClick(int par1, int par2, int par3, EntityPlayer par4EntityPlayer)
  {
    if (par3 == 4)
    {
      par2 = 1;
      return super.slotClick(par1, par2, par3, par4EntityPlayer);
    }
    if (((par1 == 0) || (par1 == 1)) && (par2 > 0)) {
      par2 = 0;
    }
    return super.slotClick(par1, par2, par3, par4EntityPlayer);
  }
  
  @Override
public boolean func_94530_a(ItemStack par1ItemStack, Slot par2Slot)
  {
    return (par2Slot.inventory != this.workbench) && (super.func_94530_a(par1ItemStack, par2Slot));
  }

  @Override
  protected void addSlots(InventoryPlayer inventory) {
      this.addSlotToContainer(new SlotCrafting(inventory.player, inventoryBase, inventoryBase, 25, 140, 44));

      for (int l = 0; l < 5; ++l)
      {
          for (int i1 = 0; i1 < 5; ++i1)
          {
              this.addSlotToContainer(new Slot(inventoryBase, i1 + l * 5, 8 + i1 * 18, 8 + l * 18));
          }
      }
	
  }
  
  @Override
	protected void addInventory(InventoryPlayer inventory) {
        for (int i1 = 0; i1 < 3; i1++) {
            for (int l1 = 0; l1 < 9; l1++) {
                addSlotToContainer(new Slot(inventory, l1 + i1 * 9 + 9, 8 + l1 * 18, 109 + i1 * 18));
            }
        }

        for (int j1 = 0; j1 < 9; j1++) {
            addSlotToContainer(new Slot(inventory, j1, 8 + j1 * 18, 167));
        }
	}
}
