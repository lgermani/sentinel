package utils;

import com.thoughtworks.gauge.datastore.DataStore;
import com.thoughtworks.gauge.datastore.DataStoreFactory;


public class DataStoreHelper {

    public static DataStore getSpecDataStore(){
        return DataStoreFactory.getSpecDataStore();
    }

    public static DataStore getSuiteDataStore(){
        return DataStoreFactory.getSpecDataStore();
    }

    public static DataStore getScenarioDataStore(){
        return DataStoreFactory.getScenarioDataStore();
    }
}
