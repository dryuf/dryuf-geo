package net.dryuf.geo.calc.impl;


import net.dryuf.geo.model.GeoLocation;
import org.testng.Assert;
import org.testng.annotations.Test;


public class PreciseWgs84CalcTest
{
	@Test
	public void distanceLl_same_0()
	{
		double result = PreciseWgs84Calc.INSTANCE.distanceLl(
			GeoLocation.ofLonLat(1, 1),
			GeoLocation.ofLonLat(1, 1)
		);

		Assert.assertEquals(result, 0, 0.1);
	}

	@Test
	public void distanceLl_close_expected()
	{
		double result1 = PreciseWgs84Calc.INSTANCE.distanceLl(
			GeoLocation.ofLonLat(0, 0),
			GeoLocation.ofLonLat(0.1, 0.1)
		);

		Assert.assertEquals(result1, 15690, 1);

		double result2 = PreciseWgs84Calc.INSTANCE.distanceLl(
			GeoLocation.ofLonLat(0, 0),
			GeoLocation.ofLonLat(10, 10)
		);

		Assert.assertEquals(result2, 1565109, 1);

		double result3 = PreciseWgs84Calc.INSTANCE.distanceLl(
			GeoLocation.ofLonLat(0, 90),
			GeoLocation.ofLonLat(90, 90)
		);

		Assert.assertEquals(result3, 0, 1);

	}
}
