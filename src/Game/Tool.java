package Game;

public class Tool extends Item {
	private boolean usedTool;

	public Tool() {
		super("tool");
		usedTool = false;
	}

	@Override
	public void use() {
		if (Game.getCurrentRoom().getName().equals("bedroom")) {
			if (usedTool) {
				Game.print("You already opened the attic door!");
			} else {
				Game.getCurrentRoom().getExit(4).setLock(false);
				usedTool = true;
				Game.print("Using the tool, you are able to unlock the attic. A ladder falls down allowing you to climb up to it!");
			}
		} else
			Game.print("You don't see any way to use the tool here.");
	}
}
