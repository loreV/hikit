package org.ltrails.common.data;

import org.bson.Document;
import org.jetbrains.annotations.NotNull;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public class PoiDAO {

    @NotNull
    public List<Trail> executeQueryAndGetResult(@NotNull Document doc) {
        throw new NotImplementedException();
    }

    @NotNull
    public List<Trail> getPosByPositionAndTypes(double longitude,
                                                double latitude,
                                                int meters,
                                                @NotNull List<String> types) {
        throw new NotImplementedException();
    }
}
