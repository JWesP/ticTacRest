package tictacrest.core;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Message {
	private long id;

    @Length(max = 3)
    private String content;

    public Message() {
        // Jackson deserialization
    }

    public Message(long id, String content) {
        this.id = id;
        this.content = content;
    }

    @JsonProperty
    public long getId() {
        return id;
    }

    @JsonProperty
    public String getContent() {
        return content;
    }

}
