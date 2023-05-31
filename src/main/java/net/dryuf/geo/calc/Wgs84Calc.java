package net.dryuf.geo.calc;

import jakarta.inject.Qualifier;
import net.dryuf.geo.model.GeoLocation;
import net.dryuf.geo.model.GeoLocationAlt;


/**
 * WGS-84 calculator.
 */
public interface Wgs84Calc
{
	/**
	 * Injects precise calculator.
	 */
	@Qualifier
	public @interface Precise
	{
	}

	/**
	 * Injects fast calculator.
	 */
	@Qualifier
	public @interface Fast
	{
	}

	double[] llaToXyz(GeoLocationAlt location);

	GeoLocationAlt xyzToLla(double[] xyz);

	double distanceLl(GeoLocation a, GeoLocation b);
}
