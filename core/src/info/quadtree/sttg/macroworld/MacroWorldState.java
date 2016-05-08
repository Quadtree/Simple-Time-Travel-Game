package info.quadtree.sttg.macroworld;

import java.util.List;

/**
 * The current state of the world, in macro terms. Typically multiple world
 * states will be kept around, for rewinding
 *
 * @author quadtree
 *
 */
public class MacroWorldState {
	public static final long TICKS_PER_DAY = 864000;
	public static final long TICKS_PER_YEAR = 315360000;

	/**
	 * This particular world's current tick. Ticks are 1/10 of a second long
	 *
	 * Years are 365 days long (there are no leap years). This means there are
	 * 315,360,000 ticks per year.
	 */
	long currentTick;

	List<Unit> units;
}
