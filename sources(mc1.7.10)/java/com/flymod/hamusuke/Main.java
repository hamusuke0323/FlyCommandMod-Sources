package com.flymod.hamusuke;

import com.flymod.hamusuke.command.CommandFlying;
import com.flymod.hamusuke.items.AllowFlyStick;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

@Mod(modid = Main.MODID, name = Main.MODNAME, version = Main.VERSION)
public class Main {
	public static final String MODID = "flycommand";
	public static final String MODNAME = "FlyCommandMod";
	public static final String VERSION = "1.0";
	public static final String COMMON = "com.flymod.hamusuke.proxy.CommonProxy";
	public static final String CLIENT = "com.flymod.hamusuke.proxy.ClientProxy";

	public static Item allowflystick;

	@EventHandler
	public static void preInit(FMLPreInitializationEvent event) {
		allowflystick = new AllowFlyStick();
		GameRegistry.registerItem(allowflystick, "AllowFlyingStick");
	}

	@EventHandler
	public static void init(FMLInitializationEvent event) {
		GameRegistry.addRecipe(new ItemStack(allowflystick),
				"ZXZ",
				"YXY",
				" X ",
				'X', Items.stick,
				'Y', Items.blaze_rod,
				'Z', Items.feather
		);
	}

	@EventHandler
	public static void serverInit(FMLServerStartingEvent event) {
		event.registerServerCommand(new CommandFlying());
	}
}
