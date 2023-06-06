package net.dryuf.geo.examples.spatialrestjpa.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import net.dryuf.geo.examples.spatialrestjpa.spatialdb.model.StoreDb;
import lombok.Builder;
import lombok.Value;
import net.dryuf.geo.model.GeoLocation;


@Value
@Builder(builderClassName = "Builder")
@JsonDeserialize(builder = Store.Builder.class)
public class Store
{
	private Long id;

	private Long version;

	private String name;

	private GeoLocation location;

	public static Store fromDb(StoreDb db)
	{
		if (db == null) {
			return null;
		}
		return Store.builder()
			.id(db.getId())
			.version(db.getVersion())
			.name(db.getName())
			.location(db.getLocation())
			.build();
	}

	public StoreDb toDb()
	{
		StoreDb db = new StoreDb();
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
