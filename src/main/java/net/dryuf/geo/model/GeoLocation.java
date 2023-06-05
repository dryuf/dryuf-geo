package net.dryuf.geo.model;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.common.base.Preconditions;
import lombok.Builder;
import lombok.Getter;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;


/**
 * Geographical location based on longitude and latitude.
 *
 * The type is immutable, serializable via Jackson and storable via Hibernate ORM.  The database definition should be
 * <code>geometry(point, 4326)</code> .
 */
@Getter
@Builder(builderClassName = "Builder")
@JsonDeserialize(builder = GeoLocation.Builder.class)
public class GeoLocation
{
	public static final GeometryFactory GEO_FACTORY = new GeometryFactory(new PrecisionModel(), 4326);

	private final double lon;

	private final double lat;

	public GeoLocation(Builder builder)
	{
		this.lon = builder.lon;
		this.lat = builder.lat;
	}

	public static GeoLocation ofLonLat(double lon, double lat)
	{
		return GeoLocation.builder()
			.lon(lon)
			.lat(lat)
			.build();
	}

	public static Point toJtsPoint(GeoLocation location)
	{
		if (location == null) {
			return null;
		}
		return GEO_FACTORY.createPoint(new Coordinate(location.getLon(), location.getLat()));
	}

	public static GeoLocation fromJtsPoint(Point point)
	{
		if (point == null) {
			return null;
		}

		Preconditions.checkArgument(!point.isEmpty());
		Preconditions.checkArgument(Double.isNaN(point.getCoordinate().getZ()));

		return GeoLocation.builder()
			.lon(point.getX())
			.lat(point.getY())
			.build();
	}


	@JsonPOJOBuilder(withPrefix = "")
	public static class Builder
	{
		public GeoLocation build()
		{
			return new GeoLocation(this);
		}
	}
}
