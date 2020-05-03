db.core.Trail.drop();
db.core.Poi.drop();

// Add sample Poi
db.core.Poi.insertOne(
    { "position": { "name": "string", "description": "string", "tags": [ "string" ], "coords": { "altitude": 0.0, "values": [ 0.0, 0.0 ] }, "postCode": "string" }, "name": "string", "description": "string", "tags": [ "string" ], "otherNames": [ "string" ], "geo": {}, "resourcesLinks": [ "string" ], "types": [ "string" ], "trailReferences": [ { "trailCode": "string", "postcode": "string" } ], "postCode": "string" }
);

// Add sample Trail
db.core.Trail.insertOne(
    { "name" : "string", "code" : "string", "startPos" : { "name" : "string", "description" : "string", "tags" : [ "string" ], "location" : { "type": "Point", "coordinates": [0, 0, 0] }, "alt" : 0, "postCode" : "string" }, "finalPos" : { "name" : "string", "description" : "string", "tags" : [ "string" ], "location" : { "type": "Point", "coordinates": [0, 0, 0] }, "alt" : 0, "postCode" : "string" }, "postcodes" : [ "string" ], "description" : "string", "geoPoints" : [{"type": "Point", "coordinates": [0, 0, 0] }], "trackLength" : 0, "eta" : 0, "POIs" : [ { "name" : "string", "description" : "string", "externalLinks" : [ "string" ], "position" : { "name" : "string", "description" : "string", "tags" : [ "string" ], "location" : { "type": "Point", "coordinates": [0, 0, 0] }, "alt" : 0, "postCode" : "string" }, "trails" : [ { "trailCode" : "string", "postCode" : "string" } ], "type" : [ "string" ], "otherNames" : [ "string" ], "tags" : [ "string" ], "resources" : [ "string" ] } ], "wayPoints" : [ { "position" : { "name" : "string", "description" : "string", "tags" : [ "string" ], "location" : { "type": "Point", "coordinates": [0, 0, 0] }, "alt" : 0, "postCode" : "string" }, "connectingTrail" : { "trailCode" : "string", "postCode" : "string" } } ], "totalLength" : 0, "totalElevationChange" : 0, "totalElevationDown" : 0, "totalElevationUp" : 0, "classification" : "T", "country" : "italy" }
);

db.core.Trail.createIndexes( [
    { "geoPoints.coordinates" : "2dsphere"},
    { "startPos.location" : "2dsphere" },
    { "finalPos.location" : "2dsphere" },
    { "country" : 1, "startPos.postCode": 1, "finalPos.postCode": 1 ,"code": 1 },
    { "code" : 1,  "postCodes": 1 }]
);

db.core.Poi.createIndexes([ { "position.location" : "2dsphere" }, { "types": 1, "postCode": 1, "trailCodes": 1 } ]);