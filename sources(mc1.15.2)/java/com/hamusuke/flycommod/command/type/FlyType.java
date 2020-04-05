package com.hamusuke.flycommod.command.type;

public enum FlyType {
	NOT_SET(-1, ""),
	ALLOW(0, "allow"),
	NOALLOW(1, "noallow"),
	NOGRAVITY(2, "nogravity"),
	GRAVITY(3, "gravity"),
	SETFLYSPEED(4, "setflyspeed"),
	SETWALKSPEED(5, "setwalkspeed"),
	ALLOWEDIT(6, "allowedit"),
	DISABLEDAMAGE(5, "disabledamage"),
	ISFLYING(7, "isflying"),
	GETFLYSPEED(8, "getflyspeed"),
	GETWALKSPEED(9, "getwalkspeed");

	private final int id;
	private final String name;

	private FlyType(int flyTypeId, String flyTypeName) {
	   this.id = flyTypeId;
	   this.name = flyTypeName;
	}

	public int getID() {
	   return this.id;
	}

	public String getName() {
	   return this.name;
	}
}
