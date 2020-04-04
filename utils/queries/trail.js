// Create geoSpatial Index
db.collection.createIndex( { "startPos.coord" : "2dsphere" } )

// Lookup close trails
db.core.Trail.find(
   {
     "startPos.coord":
       {
           $near: { coordinates: [44.486066 , 11.301413 ] },
            $minDistance: 2,
            $maxDistance: 5000
          }
       }
   }
)


// Lookup trails
db.core.Trail.find({
    country: "",
    postCode: "",
    $or : [
        { "finalPos.postCode" : { $in : [ "40033", "4132" ] } },
        { "startPos.postCode" : { $in : [ "40033", "4132" ] } }
            ]
})