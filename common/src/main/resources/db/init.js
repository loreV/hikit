use ltrails;



db.core.Trail.createIndex( { "startPos.coord" : "2dsphere" } )
db.core.Trail.createIndex( { "finalPos.coord" : "2dsphere" } )