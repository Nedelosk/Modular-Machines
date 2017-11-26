package modularmachines.api;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

public interface IScrewdriver {
	
	@Nullable
	EnumFacing getSelectedFacing(ItemStack itemStack);
	
	void setSelectedFacing(ItemStack itemStack, @Nullable EnumFacing facing);
}
