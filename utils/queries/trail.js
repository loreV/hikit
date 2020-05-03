// Create geoSpatial Index
db.collection.createIndex( { "startPos.coord" : "2dsphere" } )

// Lookup close trails
db.core.Trail.find(
   {
     "startPos.location":
       {
           $near: { coordinates: [44.486066 , 11.301413 ] },
            $minDistance: 2,
            $maxDistance: 5000
          }
   }
)

// Lookup with calculation
db.core.Trail.aggregate([
    {
        $geoNear: {
            near: { "type": "Point", coordinates: [0, 5] },
            distanceField: "distanceToIt",
            key: "geoPoints.coordinates",
            includeLocs: "closestLocation",
            maxDistance: 500,
            spherical: true,
            uniqueDocs: true
        }
    }
]);

// Lookup trails
db.core.Trail.find({
    country: "",
    postCode: "",
    $or : [
        { "finalPos.postCode" : { $in : [ "40033", "4132" ] } },
        { "startPos.postCode" : { $in : [ "40033", "4132" ] } }
            ]
})