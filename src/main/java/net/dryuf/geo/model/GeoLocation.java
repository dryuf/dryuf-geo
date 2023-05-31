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

	@JsonPOJOBuilder(withPrefix = "")
	public static class Builder
	{
	}
}
