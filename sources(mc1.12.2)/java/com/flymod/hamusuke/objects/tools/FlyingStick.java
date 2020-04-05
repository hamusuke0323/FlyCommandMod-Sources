package com.flymod.hamusuke.objects.tools;

import com.flymod.hamusuke.objects.items.ItemBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class FlyingStick extends ItemBase {

	public FlyingStick(String name) {
		super(name);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack item = playerIn.getHeldItem(handIn);
		if(!playerIn.capabilities.allowFlying) {
			playerIn.capabilities.allowFlying = true;
			item.damageItem(1, playerIn);
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, item);
		}
		if(playerIn.capabilities.allowFlying) {
			playerIn.capabilities.allowFlying = false;
			playerIn.capabilities.isFlying = false;
			item.damageItem(1, playerIn);
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, item);
		}
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, item);
	}
}
