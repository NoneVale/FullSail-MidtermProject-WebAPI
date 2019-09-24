package me.nathancole.api.post.registry;

import com.mongodb.client.MongoDatabase;
import me.nathancole.api.datasection.AbstractMongoRegistry;
import me.nathancole.api.datasection.DataSection;
import me.nathancole.api.post.PostModel;

public class MPostRegistry extends AbstractMongoRegistry<PostModel> implements PostRegistry {

    public MPostRegistry(String p_Collection, MongoDatabase m_Database) {
        super(m_Database.getCollection(p_Collection), -1);
    }

    @Override
    public PostModel fromDataSection(String key, DataSection section) {
        return null;
    }
}
