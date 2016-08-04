package blog.app.models;

import com.github.slugify.Slugify;

import javax.persistence.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "Post")
@Table(name = "posts")
public class Post implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    @OneToOne
    private User user;

    private String title;

    @Column(length = 4000)
    private String content;

    private String background;

    private Date createdAt;

    public Post() {
        this.createdAt = new Date();
    }

    public Post(String title, String content) {
        this();
        this.title = title;
        this.content = content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getTitleSlug() {
        Slugify slugify = null;
        try {
            slugify = new Slugify();
            return slugify.slugify(getTitle());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }
}
