package me.nathancole.api.post;

import com.google.common.collect.Lists;
import me.nathancole.api.Main;
import me.nathancole.api.datasection.DataSection;
import me.nathancole.api.datasection.Model;
import me.nathancole.api.post.comment.CommentModel;
import me.nathancole.api.post.comment.registry.CommentRegistry;
import me.nathancole.api.post.comment.registry.MCommentRegistry;
import me.nathancole.api.user.UserModel;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PostModel implements Model {

    private String m_Body;
    private String m_Key;

    private List<CommentModel> m_Comments;
    private List<UserModel> m_Likes;

    private long m_PostTime;

    private UserModel m_Author;

    public PostModel(UUID p_Uuid) {
        this.m_Body = "";
        this.m_Key = p_Uuid.toString();

        this.m_Comments = Lists.newArrayList();
        this.m_Likes = Lists.newArrayList();

        this.m_PostTime = 0L;

        this.m_Author = null;
    }

    public PostModel(String p_Key, DataSection p_Data) {
        this.m_Body = p_Data.getString("body");
        this.m_Key = p_Key;

        // Load the Comment Registry Database for this post.  A comment database name is the id of the post it is under.
        CommentRegistry commentRegistry = new MCommentRegistry(this.m_Key, Main.getCommentDatabase());
        commentRegistry.loadAllFromDb();
        Main.getCommentRegistryMap().put(this.m_Key, commentRegistry);
        this.m_Comments = Lists.newArrayList();
        p_Data.getStringList("comments").forEach(id -> this.m_Comments.add(commentRegistry.getComment(UUID.fromString(id))));

        this.m_Likes = Lists.newArrayList();
        p_Data.getStringList("likes").forEach(id -> this.m_Likes.add(Main.getUserById(id)));

        this.m_PostTime = p_Data.getLong("post-time");

        this.m_Author = Main.getUserById(p_Data.getString("author"));
    }

    @Override
    public String getKey() {
        return this.m_Key;
    }

    @Override
    public Map<String, Object> serialize() {
        return null;
    }
}