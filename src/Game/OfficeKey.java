package Game;

public class OfficeKey extends Item {
	private boolean usedOfficeKey;

	public OfficeKey() {
		super("officekey");
		usedOfficeKey = false;
	}

	@Override
	public void use() {
		if (Game.getCurrentRoom().getName().equals("livingroom")) {
			if (usedOfficeKey) {
				Game.print("You already opened the office door!");
			} else {
				Game.getCurrentRoom().getExit(1).setLock(false);
				usedOfficeKey = true;
				Game.print("Using the office key, you are able to unlock the office (who would've thought).");
			}
		} else
			Game.print("You don't see any way to use the tool here.");
	}
}
