package me.nathancole.api.user.registry;

import com.mongodb.client.MongoDatabase;
import me.nathancole.api.datasection.AbstractMongoRegistry;
import me.nathancole.api.user.UserModel;

import java.util.Map;

public class MUserRegistry extends AbstractMongoRegistry<UserModel> implements UserRegistry {

    public MUserRegistry(MongoDatabase database) {
        super(database.getCollection(m_Name), -1);
    }

    @Override
    public Map<String, UserModel> getRegisteredData() {
        return m_RegisteredData.asMap();
    }
}
