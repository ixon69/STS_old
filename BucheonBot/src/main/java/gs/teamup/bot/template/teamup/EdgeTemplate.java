package gs.teamup.bot.template.teamup;

import com.google.common.base.Strings;

import gs.teamup.bot.pojo.edge.ChatMessage;
import gs.teamup.bot.pojo.edge.ChatMessageList;
import gs.teamup.bot.pojo.edge.ReplyList;
import gs.teamup.bot.pojo.edge.Room;
import gs.teamup.bot.pojo.edge.bot.Feed;
import gs.teamup.bot.pojo.edge.bot.Reply;
import gs.teamup.bot.pojo.edge.bot.Say;
import gs.teamup.bot.template.BaseTemplate;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import java.util.List;

@CommonsLog
@Component
public class EdgeTemplate extends BaseTemplate {

    @Value("${teamup.edge}")
    String edgeUrl;


    public ChatMessage getMessage(int room, int msg) {

        String url = String.format("%s/messages/%s/1/2/%s", edgeUrl, room, msg-1);

        ParameterizedTypeReference<ChatMessageList> p = new ParameterizedTypeReference<ChatMessageList>() {
        };
        ChatMessageList m = get(url, p);

        if (m != null) {
            List<ChatMessage> chatMessageList = m.getChatMessageList();
            if (chatMessageList != null && chatMessageList.isEmpty() == false) {
                return chatMessageList.get(0);
            }
        }

        return null;
    }

    public String getMessageLong(int room, int msg) {

        String url = String.format("%s/message/%s/%s", edgeUrl, room, msg);

        ParameterizedTypeReference<String> p = new ParameterizedTypeReference<String>() {
        };
        String content = get(url, p);

        return content;

    }


    public void say(int room, String msg) {
        if (Strings.isNullOrEmpty(msg)) {
            return;
        }

        String url = String.format("%s/message/%s", edgeUrl, room);

        ParameterizedTypeReference<String> p = new ParameterizedTypeReference<String>() {
        };

        post(url, Say.create(msg), p);
    }


    public Feed getFeed(int feedNo) {
//        String url = String.format("%s/feed/markup/%s", edgeUrl, feed);
        String url = String.format("%s/feed/%s", edgeUrl, feedNo);

        ParameterizedTypeReference<Feed> p = new ParameterizedTypeReference<Feed>() {
        };

        Feed feed = get(url, p);
        
        return feed;
    }

    public String getFeedContent(int feedNo) {
    	Feed feed = getFeed(feedNo);
      
    	if(feed == null) return null;
      
    	// Feed가 HTML로 저장되어있을 경우 markup 데이터 다시 가져오기
    	// HTML이 아닌경우 404 에러 발생
    	if(feed.getType() == 2 || feed.getContent() == null) {
    		String url2 = String.format("%s/feed/markup/%s", edgeUrl, feedNo);
    		ParameterizedTypeReference<String> p2 = new ParameterizedTypeReference<String>() {
    		};
          
    		String content = get(url2, p2);
          
    		feed.setContent(content);
    	}
      
    	return feed.getContent();
    }
    
    public String createFeed(int feedGroup, int feedtype, String content) {
    	String url = String.format("%s/feed/%s/%s", edgeUrl, feedGroup, feedtype);
    	
    	ParameterizedTypeReference<String> p = new ParameterizedTypeReference<String>() {
        };
        
        return  post(url, Feed.create(content), p);
    }
    
    public void reply(int feed, String msg) {
        String url = String.format("%s/feed/reply/%s", edgeUrl, feed);

        ParameterizedTypeReference<String> p = new ParameterizedTypeReference<String>() {
        };

        post(url, Reply.create(msg), p);
    }

    public Reply getReply(int feed, int reply) {
        String url = String.format("%s/feed/replies/%s/1/2/%s", edgeUrl, feed, reply-1);

        ParameterizedTypeReference<ReplyList> p = new ParameterizedTypeReference<ReplyList>() {
        };

        ReplyList replyList = get(url, p);
        
        return replyList.getReplyList().get(0);
    }
    
}
