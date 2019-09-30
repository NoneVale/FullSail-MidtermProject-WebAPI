package me.nathancole.api.post.comment.registry;

import com.mongodb.client.MongoDatabase;
import me.nathancole.api.datasection.AbstractMongoRegistry;
import me.nathancole.api.post.comment.CommentModel;

import java.util.Map;

public class MCommentRegistry extends AbstractMongoRegistry<CommentModel> implements CommentRegistry {
    public MCommentRegistry(String p_Collection, MongoDatabase m_Database) {
        super(m_Database.getCollection(p_Collection), -1);
    }


    @Override
    public Map<String, CommentModel> getRegisteredData() {
        return m_RegisteredData.asMap();
    }
}
