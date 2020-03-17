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

