package com.flymod.hamusuke.init;

import java.util.ArrayList;
import java.util.List;

import com.flymod.hamusuke.objects.tools.FlyingStick;

import net.minecraft.item.Item;

public class ItemInit {
	public static final List<Item> ITEMS = new ArrayList<Item>();

	public static final Item FLYINGSTICK = new FlyingStick("flying_stick");
}
