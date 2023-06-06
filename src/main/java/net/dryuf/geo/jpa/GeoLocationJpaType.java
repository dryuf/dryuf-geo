package net.dryuf.geo.jpa;

import net.dryuf.geo.model.GeoLocation;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserTypeSupport;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKBReader;
import org.locationtech.jts.io.WKBWriter;
import org.postgresql.util.PGobject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;


public class GeoLocationJpaType extends UserTypeSupport<GeoLocation>
{
	public GeoLocationJpaType()
	{
		super(GeoLocation.class, Types.OTHER);
	}

	@Override
	public int getSqlType() {
		return Types.OTHER;
	}

	@Override
	public Class<GeoLocation> returnedClass() {
		return GeoLocation.class;
	}

	@Override
	public GeoLocation nullSafeGet(ResultSet rs, int position, SharedSessionContractImplementor session, Object owner) throws SQLException {
		PGobject value = (PGobject) rs.getObject(position);
		if (value == null) {
			return null;
		}
		Geometry geometry;
		try {
			final WKBReader wkbReader = new WKBReader();
			geometry = wkbReader.read(WKBReader.hexToBytes(value.getValue()));
			if (!(geometry instanceof Point)) {
				throw new ParseException("Expected Point, got: " + geometry.getGeometryType());
			}
		}
		catch (ParseException e) {
			throw new SQLException("Cannot convert PGObject to Geometry", e);
		}
		if (rs.wasNull()) {
			return null;
		}
		final Point point = (Point) geometry;
		double lon = point.getX();
		double lat = point.getY();
		return GeoLocation.ofLonLat(lon, lat);
	}

	@Override
	public void nullSafeSet(PreparedStatement st, GeoLocation value, int index, SharedSessionContractImplementor session) throws SQLException {
		if (value == null) {
			st.setNull(index, Types.OTHER);
		} else {
			WKBWriter wkbWriter = new WKBWriter();
			PGobject object = new PGobject();
			Point point = GeoLocation.toJtsPoint(value);
			object.setValue(WKBWriter.toHex(wkbWriter.write(point)));
			object.setType("geometry");
			st.setObject(index, object);
		}
	}
}
