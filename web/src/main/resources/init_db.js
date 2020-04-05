db.core.Trail.drop();

db.core.Trail.createIndexes( [{ "startPos.coordinates" : "2dsphere" }, { "finalPos.coordinates" : "2dsphere" }] );
db.core.Trail.createIndex( { "country" : 1, "startPos.postCode": 1, "finalPos.postCode": 1 ,"code": 1 } );
db.core.Trail.createIndex( { "code" : 1,  "postCodes": 1 } );