package net.dryuf.geo.examples.spatialrestjpa.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import net.dryuf.geo.examples.spatialrestjpa.spatialdb.model.TargetDb;
import lombok.Builder;
import lombok.Value;
import net.dryuf.geo.model.GeoLocation;


@Value
@Builder(builderClassName = "Builder")
@JsonDeserialize(builder = Target.Builder.class)
public class Target
{
	private Long id;

	private Long version;

	private String name;

	private GeoLocation location;

	public static Target fromDb(TargetDb db)
	{
		if (db == null) {
			return null;
		}
		return Target.builder()
			.id(db.getId())
			.version(db.getVersion())
			.name(db.getName())
			.location(db.getLocation())
			.build();
	}

	public TargetDb toDb()
	{
		TargetDb db = new TargetDb();
		db.setId(getId());
		db.setVersion(getVersion());
		db.setName(getName());
		db.setLocation(getLocation());
		return db;
	}

	@JsonPOJOBuilder(withPrefix = "")
	public static final class Builder
	{
	}
}
