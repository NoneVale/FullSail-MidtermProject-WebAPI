package me.nathancole.api.post.comment;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import me.nathancole.api.Main;
import me.nathancole.api.datasection.DataSection;
import me.nathancole.api.datasection.Model;
import me.nathancole.api.user.UserModel;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CommentModel implements Model {

    private String m_Body;
    private String m_Key;
    private String m_PostId;

    private List<UserModel> m_Likes;

    private long m_PostTime;

    private UserModel m_Author;

    public CommentModel(UUID p_Uuid, UserModel p_Author) {
        this.m_Body = "";
        this.m_Key = p_Uuid.toString();
        this.m_PostId = "";

        this.m_Likes = Lists.newArrayList();

        this.m_PostTime = 0L;

        this.m_Author = p_Author;
    }

    public CommentModel(String p_Key, DataSection p_Data) {
        this.m_Body = p_Data.getString("body");
        this.m_Key = p_Key;
        this.m_PostId = p_Data.getString("post-id");

        this.m_Likes = Lists.newArrayList();
        p_Data.getStringList("likes").forEach(id -> this.m_Likes.add(Main.getUserById(id)));

        this.m_PostTime = p_Data.getLong("post-time");

        this.m_Author = Main.getUserById(p_Data.getString("author"));
    }

    public String getBody() {
        return m_Body;
    }

    public void setBody(String p_Body) {
        this.m_Body = p_Body;
        Main.getCommentRegistry(getPostId()).register(this);
    }

    public String getPostId() {
        return m_PostId;
    }

    public void setPostId(String p_PostId) {
        m_PostId = p_PostId;
        Main.getCommentRegistry(getPostId()).register(this);
    }

    public ImmutableList<UserModel> getLikes() {
        return ImmutableList.copyOf(m_Likes);
    }

    public void addLike(UserModel p_UserModel) {
        m_Likes.add(p_UserModel);
        Main.getCommentRegistry(getPostId()).register(this);
    }

    public void removeLike(UserModel p_UserModel) {
        m_Likes.remove(p_UserModel);
        Main.getCommentRegistry(getPostId()).register(this);
    }

    public long getPostTime() {
        return m_PostTime;
    }

    public void setPostTime(long p_PostTime) {
        m_PostTime = p_PostTime;
        Main.getCommentRegistry(getPostId()).register(this);
    }

    public UserModel getAuthor() {
        return m_Author;
    }

    public void setAuthor(UserModel p_Author) {
        m_Author = p_Author;
        Main.getCommentRegistry(getPostId()).register(this);
    }

    @Override
    public String getKey() {
        return this.m_Key;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("body", m_Body);

        List<String> likeIds = Lists.newArrayList();
        m_Likes.forEach(user -> likeIds.add(user.getKey()));
        map.put("likes", likeIds);

        map.put("post-time", m_PostTime);

        map.put("author", m_Author.getKey());
        return map;
    }
}