package gs.teamup.bot.pojo.edge.bot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Feed {
    @JsonProperty("team")
    int team;

    @JsonProperty("feedgroup")
    int  feedgroup;

    @JsonProperty("groupname")
    String groupname;

    @JsonProperty("user")
    int user;

    @JsonProperty("type")
    int type;

    @JsonProperty("push")
    int  push;

    @JsonProperty("watch")
    int  watch;

    @JsonProperty("len")
    int len;

    @JsonProperty("content")
    String content;
    
    @JsonProperty("removable")
    int removable;

    @JsonProperty("tagfeeds")
    String tagfeeds;

    @JsonProperty("tagusers")
    String tagusers;

    @JsonProperty("liked")
    int liked;

    @JsonProperty("replycount")
    int replycount;

    @JsonProperty("likecount")
    int likecount;

    @JsonProperty("filecount")
    int filecount;

    @JsonProperty("mediacount")
    int mediacount;

    @JsonProperty("readcount")
    int readcount;

    @JsonProperty("created")
    String created;

    public static Feed create(String content){
        Feed c = new Feed();
        c.setContent(content);
        return c;
    }

}
