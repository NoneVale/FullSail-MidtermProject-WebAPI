package me.nathancole.api.post.comment;

import com.google.common.collect.Lists;
import me.nathancole.api.Main;
import me.nathancole.api.datasection.DataSection;
import me.nathancole.api.datasection.Model;
import me.nathancole.api.post.comment.registry.CommentRegistry;
import me.nathancole.api.post.comment.registry.MCommentRegistry;
import me.nathancole.api.user.UserModel;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CommentModel implements Model {

    private String m_Body;
    private String m_Key;

    private List<UserModel> m_Likes;

    private long m_PostTime;

    private UserModel m_Author;

    public CommentModel(UUID uuid) {
        this.m_Body = "";
        this.m_Key = uuid.toString();

        this.m_Likes = Lists.newArrayList();

        this.m_PostTime = 0L;

        this.m_Author = null;
    }

    public CommentModel(String p_Key, DataSection p_Data) {
        this.m_Body = p_Data.getString("body");
        this.m_Key = p_Key;

        this.m_Likes = Lists.newArrayList();
        p_Data.getStringList("likes").forEach(id -> this.m_Likes.add(Main.getUserById(id)));

        this.m_PostTime = p_Data.getLong("post-time");

        this.m_Author = Main.getUserById(p_Data.getString("author"));
    }

    @Override
    public String getKey() {
        return null;
    }

    @Override
    public Map<String, Object> serialize() {
        return null;
    }
}