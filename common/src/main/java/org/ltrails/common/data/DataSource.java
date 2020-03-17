package org.ltrails.common.data;


import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public interface DataSource {
    MongoClient getClient();

    MongoDatabase getDB();

    String getDBName();
}
