package net.dryuf.geo.examples.spatialrestjpa.spatialdb.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Data;
import net.dryuf.geo.jpa.GeoLocationJpaType;
import net.dryuf.geo.model.GeoLocation;
import org.hibernate.annotations.Type;


@Entity
@Table(name = "Target" , indexes = @Index(name = "idx_Target_location", columnList = "location", unique = false))
@Data
public class TargetDb
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Version
	private Long version;

	private String name;

	@Type(GeoLocationJpaType.class)
	@Column(columnDefinition = "geometry(POINT, 4326)")
	private GeoLocation location;
}
