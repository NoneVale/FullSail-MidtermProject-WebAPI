package me.nathancole.api.post;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
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

    public PostModel(UUID p_Uuid, UserModel p_Author) {
        this.m_Body = "";
        this.m_Key = p_Uuid.toString();

        this.m_Comments = Lists.newArrayList();
        this.m_Likes = Lists.newArrayList();

        this.m_PostTime = 0L;

        this.m_Author = p_Author;
    }

    public PostModel(String p_Key, DataSection p_Data) {
        this.m_Author = Main.getUserById(p_Data.getString("author"));

        this.m_Body = p_Data.getString("body");
        this.m_Key = p_Key;

        // Load the Comment Registry Database for this post.  A comment database name is the id of the post it is under.
        CommentRegistry commentRegistry = new MCommentRegistry(this.m_Key, Main.getCommentDatabase());
        commentRegistry.loadAllFromDb();
        Main.getCommentRegistryMap().put(this.m_Key, commentRegistry);
        this.m_Comments = Lists.newArrayList();
        p_Data.getStringList("comments").forEach(id -> this.m_Comments.add(commentRegistry.getComment(UUID.fromString(id), this.m_Author)));

        this.m_Likes = Lists.newArrayList();
        p_Data.getStringList("likes").forEach(id -> this.m_Likes.add(Main.getUserById(id)));

        this.m_PostTime = p_Data.getLong("post-time");

    }

    public String getBody() {
        return m_Body;
    }

    public void setBody(String p_Body) {
        this.m_Body = p_Body;
        Main.getPostRegistry(getAuthor().getKey()).register(this);
    }

    public ImmutableList<CommentModel> getComments() {
        return ImmutableList.copyOf(m_Comments);
    }

    public void addComment(CommentModel p_Comment) {
        p_Comment.setPostId(getKey());
        m_Comments.add(p_Comment);
        Main.getPostRegistry(getAuthor().getKey()).register(this);
    }

    public void removeComment(CommentModel p_Comment) {
        m_Comments.remove(p_Comment);
        Main.getPostRegistry(getAuthor().getKey()).register(this);
    }

    public ImmutableList<UserModel> getLikes() {
        return ImmutableList.copyOf(m_Likes);
    }

    public void addLike(UserModel p_UserModel) {
        m_Likes.add(p_UserModel);
        Main.getPostRegistry(getAuthor().getKey()).register(this);
    }

    public void removeLike(UserModel p_UserModel) {
        m_Likes.remove(p_UserModel);
        Main.getPostRegistry(getAuthor().getKey()).register(this);
    }

    public long getPostTime() {
        return m_PostTime;
    }

    public void setPostTime(long p_PostTime) {
        m_PostTime = p_PostTime;
        Main.getPostRegistry(getAuthor().getKey()).register(this);
    }

    public UserModel getAuthor() {
        return m_Author;
    }

    public void setAuthor(UserModel p_Author) {
        m_Author = p_Author;
        Main.getPostRegistry(getAuthor().getKey()).register(this);
    }

    @Override
    public String getKey() {
        return this.m_Key;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("body", m_Body);

        List<String> commentIds = Lists.newArrayList();
        m_Comments.forEach(comment -> commentIds.add(comment.getKey()));
        map.put("comments", commentIds);
        List<String> likeIds = Lists.newArrayList();
        m_Likes.forEach(user -> likeIds.add(user.getKey()));
        map.put("likes", likeIds);

        map.put("post-time", m_PostTime);

        map.put("author", m_Author.getKey());
        return map;
    }
}