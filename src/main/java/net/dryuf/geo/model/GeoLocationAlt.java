package net.dryuf.geo.model;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;


/**
 * Geographical location based on longitude, latitude and altitude.
 */
@Getter
@Builder(builderClassName = "Builder")
@JsonDeserialize(builder = GeoLocationAlt.Builder.class)
public class GeoLocationAlt extends GeoLocation
{
	private final double alt;

	public static GeoLocationAlt of(GeoLocation l, double alt)
	{
		return GeoLocationAlt.builder()
			.lon(l.getLon())
			.lat(l.getLat())
			.alt(alt)
			.build();
	}

	public static GeoLocationAlt ofAlt0(GeoLocation l)
	{
		return of(l, 0);
	}

	public GeoLocation toLl()
	{
		return GeoLocation.builder()
			.lon(getLon())
			.lat(getLat())
			.build();
	}

	@JsonPOJOBuilder(withPrefix = "")
	public static class Builder extends GeoLocation.Builder
	{
		@Override
		public GeoLocationAlt.Builder lon(double lon)
		{
			super.lon(lon);
			return this;
		}

		@Override
		public GeoLocationAlt.Builder lat(double lat)
		{
			super.lat(lat);
			return this;
		}
	}
}
