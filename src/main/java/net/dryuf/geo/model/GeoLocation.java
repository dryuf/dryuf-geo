package net.dryuf.geo.model;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;


/**
 * Geographical location based on longitude and latitude.
 */
@Getter
@Builder(builderClassName = "Builder")
@JsonDeserialize(builder = GeoLocation.Builder.class)
public class GeoLocation
{
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

	@JsonPOJOBuilder(withPrefix = "")
	public static class Builder
	{
		public GeoLocation build()
		{
			return new GeoLocation(this);
		}
	}
}
