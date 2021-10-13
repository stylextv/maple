package de.stylextv.maple.pathing;

import de.stylextv.maple.pathing.calc.goal.Goal;

public class PathingCommand {
	
	public static final PathingCommand DEFER = new PathingCommand(PathingCommandType.DEFER);
	
	private PathingCommandType type;
	
	private Goal goal;
	
	public PathingCommand(PathingCommandType type) {
		this(type, null);
	}
	
	public PathingCommand(PathingCommandType type, Goal goal) {
		this.type = type;
		this.goal = goal;
	}
	
	public PathingCommandType getType() {
		return type;
	}
	
	public Goal getGoal() {
		return goal;
	}
	
}
