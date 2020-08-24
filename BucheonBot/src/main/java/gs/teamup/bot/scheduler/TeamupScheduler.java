package gs.teamup.bot.scheduler;

import gs.teamup.bot.service.TeamupService;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by thisno on 2016-04-12.
 */

@CommonsLog
@Component
public class TeamupScheduler {
    @Autowired
	TeamupService teamupService;
    
    //0 0 0/2 1/1 * ? *
    //1시간 마다 0 0 0/1 * * *
    @Scheduled(cron="0 0 0/1 * * *")
    //계속 1분마다
    
   // @Scheduled(cron="* * * * * MON-FRI")
    public void doUseCount() {
    	
    		//doEvent();
    	
    }

    private void doEvent() {
    	  teamupService.writeFeed(true);
    	 teamupService.writePACSFeed(true);
    	  
    }
}
