package com.hamusuke.flycommod.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class ItemFlyingStick extends Item {

	public ItemFlyingStick() {
		super(new Properties().group(ItemGroup.TOOLS));
		this.setRegistryName("flying_stick");
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack item = playerIn.getHeldItem(handIn);
		if(!playerIn.abilities.allowFlying) {
			playerIn.abilities.allowFlying = true;
			return new ActionResult<ItemStack>(ActionResultType.SUCCESS, item);
		}
		if(playerIn.abilities.allowFlying) {
			playerIn.abilities.allowFlying = false;
			playerIn.abilities.isFlying = false;
			return new ActionResult<ItemStack>(ActionResultType.SUCCESS, item);
		}
		return new ActionResult<ItemStack>(ActionResultType.FAIL, item);
	}

}
