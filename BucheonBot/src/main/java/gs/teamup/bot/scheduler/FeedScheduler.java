package gs.teamup.bot.scheduler;

import com.google.common.base.Strings;
import gs.teamup.bot.component.EventQueue;
import gs.teamup.bot.controller.ChatController;
import gs.teamup.bot.pojo.edge.ChatMessage;
import gs.teamup.bot.pojo.edge.bot.Reply;
import gs.teamup.bot.pojo.event.TeamupEventChat;
import gs.teamup.bot.pojo.event.TeamupEventFeed;
import gs.teamup.bot.template.teamup.EdgeTemplate;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by thisno on 2016-04-12.
 */

@CommonsLog
@Component
public class FeedScheduler {

    @Autowired
    @Qualifier("feedEventQueue")
    private EventQueue<TeamupEventFeed> feedEventQueue;

    @Autowired
    private EdgeTemplate edgeTemplate;

    @Scheduled(fixedDelay = 1000)
    public void feed() {
        TeamupEventFeed teamupEventFeed = feedEventQueue.poll();
        if (teamupEventFeed == null) {
            return;
        }

        log.debug("feed pop : " + teamupEventFeed.getType() + " : " + teamupEventFeed.getFeedgroup() + " : " + teamupEventFeed.getFeed());
        
        String t = teamupEventFeed.getType();
        switch(t) {
        case "feed.changefeed" : 
        case "feed.feed" : {
        	String content = edgeTemplate.getFeedContent(teamupEventFeed.getFeed());
			
        	log.debug("feed content : " + content);
        	
            break;
        }
        case "feed.changereply" : 
        case "feed.reply" : {
        	Reply reply = edgeTemplate.getReply(teamupEventFeed.getFeed(), teamupEventFeed.getReply());
        	
        	log.debug("reply content : " + reply.getContent());
        	
        	break;
        }
        }
//        if ("feed.feed".equals(t) || "feed.changefeed".equals(t)) {
//        	String content = edgeTemplate.getFeedContent(teamupEventFeed.getFeed());
//        			
//        	log.debug("feed content : " + content);
//        			
//        } else if ("feed.reply".equals(t) || "feed.changereply".equals(t)) {
////            edgeTemplate.reply(teamupEventFeed.getFeed(), "테스트 답글입니다.");
//        	Reply reply = edgeTemplate.getReply(teamupEventFeed.getFeed());
//        	
//        	log.debug("reply content : " + reply.getContent());
//        	
//        }

    }

}
