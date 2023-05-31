package net.dryuf.geo.calc;


/**
 * Wgs84 calculator based on integer numbers.  It is extremely imprecise, providing only guess, but fast to evaluate
 * without math functions and float operations.
 *
 * The longitude and latitude are represented in degrees multiplied by 10_000_000 .
 */
public class Wgs84IntegerCalc
{
	public static int METER_LAT_STEPS = 90;

	public static final int DEGREE_BASE = 10_000_000;

	public static int HALF_GLOBE_STEPS = 180 * DEGREE_BASE;

	public static int GLOBE_STEPS = HALF_GLOBE_STEPS * 2;

	public int distanceLl(int aLon, int aLat, int bLon, int bLat)
	{
		int diffVert = Math.abs(aLat-bLat) / METER_LAT_STEPS;
		int aLonAdjusted = aLon < bLon ? aLon + GLOBE_STEPS : aLon;
		int diffLon = aLonAdjusted - bLon;

		if (diffLon < 0 || diffLon >= HALF_GLOBE_STEPS) {
			// this seems misleading but the negative number is actually result of overflow:
			diffLon -= GLOBE_STEPS;
		}
		int diffHoriz;
		if (diffVert < 81 * DEGREE_BASE && diffLon >= DEGREE_BASE) {
			// long distance with low degree, can afford to lose precision on degree
			diffHoriz = diffLon/METER_LAT_STEPS/2 * ((100000000-intSquare(aLat/(METER_LAT_STEPS * 1000)))/500000)/100;
		}
		else {
			// longitude difference is below 1 degree or approximate cos(lat) <= 0.19
			diffHoriz = diffLon/METER_LAT_STEPS * ((100000000-intSquare(aLat/(METER_LAT_STEPS * 1000))+97656)/97656)/256;
		}

		if (diffHoriz == 0 || diffVert/diffHoriz > 5) {
			return diffVert;
		}
		else if (diffVert == 0 || diffHoriz/diffVert > 5) {
			return diffHoriz;
		}
		return (diffHoriz + diffVert)*5/8;
	}

	private int intSquare(int a)
	{
		return a*a;
	}
}
