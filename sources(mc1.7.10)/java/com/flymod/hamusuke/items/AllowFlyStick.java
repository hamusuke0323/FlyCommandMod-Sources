package com.flymod.hamusuke.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class AllowFlyStick extends Item {
	public AllowFlyStick() {
		setUnlocalizedName("allowflyingstick");
		setCreativeTab(CreativeTabs.tabTools);
		setTextureName("flycommand:flying_stick");
	}

	@Override
	public ItemStack onItemRightClick(ItemStack item, World worldIn, EntityPlayer playerIn) {
		if(!playerIn.capabilities.allowFlying) {
			playerIn.capabilities.allowFlying = true;
			item.damageItem(1, playerIn);
			return item;
		}

		if(playerIn.capabilities.allowFlying) {
			playerIn.capabilities.allowFlying = false;
			playerIn.capabilities.isFlying = false;
			item.damageItem(1, playerIn);
			return item;
		}
		return item;
	}
}
