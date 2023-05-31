package net.dryuf.geo.calc.impl;

import jakarta.inject.Singleton;
import net.dryuf.geo.calc.Wgs84Calc;
import net.dryuf.geo.model.GeoLocation;
import net.dryuf.geo.model.GeoLocationAlt;
import org.geolatte.geom.C3D;
import org.geolatte.geom.GeometryOperations;
import org.geolatte.geom.Point;
import org.geolatte.geom.crs.CrsRegistry;
import org.geolatte.geom.crs.GeocentricCartesianCoordinateReferenceSystem;
import org.locationtech.proj4j.CRSFactory;
import org.locationtech.proj4j.CoordinateReferenceSystem;
import org.locationtech.proj4j.ProjCoordinate;


@Singleton
public class PreciseWgs84Calc implements Wgs84Calc
{
	public static final PreciseWgs84Calc INSTANCE = new PreciseWgs84Calc();

	private static final CRSFactory CRS_FACTORY = new CRSFactory();

	private static final CoordinateReferenceSystem WGS84_CRS = CRS_FACTORY.createFromName("EPSG:4326");

	public static final CoordinateReferenceSystem XYZ_CRS = CRS_FACTORY.createFromName("EPSG:4978");

	public static final GeocentricCartesianCoordinateReferenceSystem XYZ_CRS_GL =
		(GeocentricCartesianCoordinateReferenceSystem) CrsRegistry.getCoordinateReferenceSystemForEPSG(4978, null);


	@Override
	public double[] llaToXyz(GeoLocationAlt location)
	{
		ProjCoordinate coordinate = new ProjCoordinate(location.getLon(), location.getLat(), location.getAlt());

		ProjCoordinate xyzCoordinate = new ProjCoordinate();
		XYZ_CRS.getProjection().project(coordinate, xyzCoordinate);

		return new double[]{ xyzCoordinate.x, xyzCoordinate.y, xyzCoordinate.z };
	}

	@Override
	public GeoLocationAlt xyzToLla(double[] xyz)
	{
		ProjCoordinate xyzCoordinate = new ProjCoordinate(xyz[0], xyz[1], xyz[2]);

		ProjCoordinate coordinate = new ProjCoordinate();
		WGS84_CRS.getProjection().inverseProject(xyzCoordinate, coordinate);

		return GeoLocationAlt.builder()
			.lon(coordinate.x)
			.lat(coordinate.y)
			.alt(coordinate.z)
			.build();
	}

	@Override
	public double distanceLl(GeoLocation a, GeoLocation b)
	{
		Point<C3D> point1 = createC3D(llaToXyz(GeoLocationAlt.ofAlt0(a)));
		Point<C3D> point2 = createC3D(llaToXyz(GeoLocationAlt.ofAlt0(b)));

		return GeometryOperations.projectedGeometryOperations().distance(point1, point2);
	}

	private Point<C3D> createC3D(double[] coordinates)
	{
		return new Point<C3D>(new C3D(coordinates[0], coordinates[1], coordinates[2]), XYZ_CRS_GL);
	}
}
