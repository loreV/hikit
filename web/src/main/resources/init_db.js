db.core.Trail.drop();
db.core.Poi.drop();

// Add sample Poi
db.core.Poi.insertOne(
    {"name":"string","description":"string","position":{"name":"string","description":"string","tags":["string"],"coordinates":{"latitude":0,"longitude":0},"alt":0,"postCode":"string"},"trails":[{"trailCode":"string","postCode":"string"}],"type":["string"],"otherNames":["string"],"tags":["string"],"resourcesLinks":["string"],"types":["string"],"postCode":"string","geo":{}}
);

// Add sample Trail
db.core.Trail.insertOne(
    {"name":"string","code":"string","startPos":{"name":"string","description":"string","tags":["string"],"coordinates":{"latitude":0,"longitude":0},"alt":0,"postCode":"string"},"finalPos":{"name":"string","description":"string","tags":["string"],"coordinates":{"latitude":0,"longitude":0},"alt":0,"postCode":"string"},"postcodes":["string"],"description":"string","geo":{},"trackLength":0,"eta":0,"POIs":[{"name":"string","description":"string","externalLinks":["string"],"position":{"name":"string","description":"string","tags":["string"],"coordinates":{"latitude":0,"longitude":0},"alt":0,"postCode":"string"},"trails":[{"trailCode":"string","postCode":"string"}],"type":["string"],"otherNames":["string"],"tags":["string"],"resources":["string"]}],"wayPoints":[{"position":{"name":"string","description":"string","tags":["string"],"coordinates":{"latitude":0,"longitude":0},"alt":0,"postCode":"string"},"connectingTrail":{"trailCode":"string","postCode":"string"}}],"totalLength":0,"totalElevationChange":0,"totalElevationDown":0,"totalElevationUp":0,"classification":"T", "country": "italy"}
);

db.core.Trail.createIndexes( [
    { "startPos.coordinates" : "2dsphere" },
    { "finalPos.coordinates" : "2dsphere" },
    { "country" : 1, "startPos.postCode": 1, "finalPos.postCode": 1 ,"code": 1 },
    { "code" : 1,  "postCodes": 1 }]
);
db.core.Poi.createIndexes([ { "position.coordinates" : "2dsphere" }, { "types": 1, "postCode": 1, "trailCodes": 1 } ]);