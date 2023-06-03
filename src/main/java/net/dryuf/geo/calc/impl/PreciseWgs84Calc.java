package net.dryuf.geo.calc.impl;

import jakarta.inject.Singleton;
import net.dryuf.geo.calc.Wgs84Calc;
import net.dryuf.geo.model.GeoLocation;
import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.locationtech.jts.geom.CoordinateXY;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.opengis.referencing.operation.TransformException;


@Singleton
public class PreciseWgs84Calc implements Wgs84Calc
{
	public static final PreciseWgs84Calc INSTANCE = new PreciseWgs84Calc();

	private static final DefaultGeographicCRS WGS84_CRS = DefaultGeographicCRS.WGS84;


	@Override
	public double distanceLl(GeoLocation a, GeoLocation b)
	{
		GeometryFactory geomFactory = new GeometryFactory(new PrecisionModel(PrecisionModel.FIXED), 4326);

		Point aPoint = geomFactory.createPoint(new CoordinateXY(a.getLon(), a.getLat()));
		Point bPoint = geomFactory.createPoint(new CoordinateXY(b.getLon(), b.getLat()));
		try {
			return JTS.orthodromicDistance(aPoint.getCoordinate(), bPoint.getCoordinate(), WGS84_CRS);
		}
		catch (TransformException e) {
			throw new RuntimeException(e);
		}
	}
}
