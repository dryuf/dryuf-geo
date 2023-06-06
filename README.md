# Dryuf Geo

Utilities for processing Geospatial data and storing them into database.

### Release

```
<dependency>
	<groupId>net.dryuf</groupId>
	<artifactId>dryuf-geo</artifactId>
	<version>1.0.1</version>
</dependency>
```

## Model

### GeoLocation and GeoLocationAlt classes

Simple wrappers for holding strictly longitude and latitude values, alternatively with altitude.


## ORM


The framework provides both Hibernate `TypeContributor` and `UserType` to transparently work with `GeoLocation` 
models.  Currently only PostgreSql is supported for `UserType`, the `TypeContributor` should support all that 
Geolatte supports .

`GeoLocation` classes are transparently stored into database using `TypeContributor` and can be used in `@Entity` 
objects as well as query parameters. 


## Examples

Examples can be found in [examples directory](examples/).

## License

The code is released under version 2.0 of the [Apache License][].

## Stay in Touch

Feel free to contact me at kvr000@gmail.com and https://github.com/kvr000/ and https://github.com/dryuf/ and https://www.linkedin.com/in/zbynek-vyskovsky/

[Apache License]: http://www.apache.org/licenses/LICENSE-2.0

<!--- vim: set tw=120: --->
