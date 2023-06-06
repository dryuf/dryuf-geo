package net.dryuf.geo.jpa;

import com.google.common.base.Preconditions;
import net.dryuf.geo.model.GeoLocation;
import org.geolatte.geom.G2D;
import org.geolatte.geom.Position;
import org.geolatte.geom.PositionFactory;
import org.geolatte.geom.Positions;
import org.geolatte.geom.crs.CoordinateReferenceSystems;
import org.geolatte.geom.jts.JTS;
import org.hibernate.boot.model.TypeContributions;
import org.hibernate.boot.model.TypeContributor;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.SqlTypes;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractJavaType;
import org.hibernate.type.descriptor.jdbc.JdbcType;
import org.hibernate.type.descriptor.jdbc.JdbcTypeIndicators;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKBReader;
import org.locationtech.jts.io.WKBWriter;


public class GeoLocationTypeContributor implements TypeContributor
{
	public static final PositionFactory<G2D> G2D_POSITION_FACTORY = Positions.getFactoryFor(G2D.class);

	@Override
	public void contribute(TypeContributions typeContributions, ServiceRegistry serviceRegistry)
	{
		typeContributions.contributeJavaType(new GeoLocationJavaType());
	}

	public static class GeoLocationJavaType extends AbstractJavaType<GeoLocation>
	{

		protected GeoLocationJavaType()
		{
			super(GeoLocation.class);
		}

		@Override
		public JdbcType getRecommendedJdbcType(JdbcTypeIndicators indicators) {
			return indicators.getJdbcType(SqlTypes.GEOMETRY);
		}


		@SuppressWarnings("unchecked")
		@Override
		public <X> X unwrap(GeoLocation value, Class<X> type, WrapperOptions options)
		{
			if (value == null) {
				return null;
			}
			else if (org.geolatte.geom.Geometry.class.isAssignableFrom(type)) {
				return (X) new org.geolatte.geom.Point<>(
					G2D_POSITION_FACTORY.mkPosition(value.getLon(), value.getLat()),
					CoordinateReferenceSystems.WGS84
					);
			}
			else if (CharSequence.class.isAssignableFrom(type)) {
				return (X) toString(value);
			}
			else if (Geometry.class.isAssignableFrom(type)) {
				return (X) GeoLocation.toJtsPoint(value);
			}
			else {
				throw unknownUnwrap(type);
			}
		}

		@Override
		public <X> GeoLocation wrap(X value, WrapperOptions options)
		{
			if (value == null) {
				return null;
			}
			else if (value instanceof CharSequence string) {
				return fromString(string);
			}
			else if (value instanceof org.geolatte.geom.Point<?> point) {
				Preconditions.checkArgument(point.getSRID() == 4326, "Expected SRID 4326 but got: srid=%s",	point.getSRID());
				final Position coordinate = point.getPosition();
				Preconditions.checkArgument(coordinate.getCoordinateDimension() == 2, "Expected dimensions 2 but got: dimensions=%s", point.getDimension());
				return GeoLocation.ofLonLat(coordinate.getCoordinate(0), coordinate.getCoordinate(1));
			}
			else if (value instanceof Point point) {
				return GeoLocation.fromJtsPoint(point);
			}
			else {
				throw unknownWrap(value.getClass());
			}
		}

		@Override
		public String toString(GeoLocation value)
		{
			WKBWriter wkbWriter = new WKBWriter(2, true);
			Point point = GeoLocation.toJtsPoint(value);
			return WKBWriter.toHex(wkbWriter.write(point));
		}

		@Override
		public GeoLocation fromString(CharSequence string)
		{
			try {
				WKBReader wkbReader = new WKBReader();
				Geometry point = wkbReader.read(WKBReader.hexToBytes(string.toString()));
				if (point != null && !(point instanceof Point)) {
					throw new ParseException("Expected Point but got: " + point.getClass().getName());
				}
				return GeoLocation.fromJtsPoint((Point) point);
			}
			catch (ParseException e) {
				throw new RuntimeException(e);
			}
		}
	}

}
