package com.example.sweater.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="message")
public class MessageAndAuthor {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @NotBlank(message="please fill the message")
    @Length(max=2048, message = "message too long")
    private String text;
    private String tag;
    @ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "user_id")
    private UserAndDetails author;
    @Transient
    private String filename;

    public MessageAndAuthor(String text, String tag, UserAndDetails user) {
        this.text = text;
        this.tag = tag;
        this.author = user;
    }

    public String getAuthorName(){
        return author != null ? author.getUsername() : "<none>";
    }

    public MessageAndAuthor() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public UserAndDetails getAuthor() {
        return author;
    }

    public void setAuthor(UserAndDetails author) {
        this.author = author;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
    public String getFilename() {
        return filename;
    }
}
