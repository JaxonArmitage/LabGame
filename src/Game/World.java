package Game;

import java.util.Scanner;

public class World {

	public static void main(String[] args) {
	}

	public static Room buildWorld() {
		Room livingRoom = new Room("Living Room");
		Room office = new Room("Office");
		Room kitchen = new Room("Kitchen");
		Room bedroom = new Room("Bedroom");
		Room attic = new Room("Attic");
		Room garage = new Room("Garage");
		Item officekey = new OfficeKey();
		Item tool = new Tool();
		Item musicbox = new Item("musicbox");
		Item flashlight = new Item("flashlight");
		Item knife = new Item("knife");
		Item mysteriousorb = new Item("mysteriousorb");
		Item couch = new Item("couch");
		Item bed = new Item("bed");
		couch.setWeight("h");
		bed.setWeight("h");
		attic.addExit(bedroom, 5); // down
		bedroom.addExit(attic, 4); // up
		bedroom.addExit(livingRoom, 3); // south
		livingRoom.addExit(office, 1); // west
		livingRoom.addExit(bedroom, 2); // north
		livingRoom.addExit(kitchen, 0); // east
		office.addExit(livingRoom, 0);
		kitchen.addExit(livingRoom, 1);
		kitchen.addExit(garage, 3);
		garage.addExit(kitchen, 2);
		bedroom.addItem(officekey);
		bedroom.addItem(bed);
		livingRoom.addItem(tool);
		livingRoom.addItem(musicbox);
		livingRoom.addItem(flashlight);
		livingRoom.addItem(knife);
		livingRoom.addItem(couch);
		kitchen.addItem(mysteriousorb);
		office.setLock(true);
		attic.setLock(true);
		garage.setLock(true);
		return livingRoom;
	}
}
