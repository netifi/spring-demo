package io.rsocket.springone.demo.client;

import io.netifi.proteus.spring.core.annotation.Broadcast;
import io.rsocket.springone.demo.RankingServiceClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BroadcastRunner implements CommandLineRunner {
    private static final Logger logger = LogManager.getLogger(BroadcastRunner.class);

    @Broadcast("springone.demo.ranking")
    private RankingServiceClient rankingService;

    @Override
    public void run(String... args) throws Exception {
    	/*RankingRequest request =
			    RankingRequest.newBuilder()
			                  .addRecords(Record.newBuilder()
			                                    .setId(0)
			                                    .setName("Ryland Degnan")
			                                    .setDescription("Co-founder and CTO, Netifi")
			                                    .setThumbnail("https://attendease-event-content.s3.us-west-2.amazonaws.com/events/521abb61-6216-487e-a721-db53fa7003ac/upload/content/f5060af654de642b167c.jpg"))
			                  .addRecords(Record.newBuilder()
			                                    .setId(1)
			                                    .setName("Stephane Maldini")
			                                    .setDescription("Reactive Engineering Cook, Pivotal")
			                                    .setThumbnail("https://attendease-event-content.s3.us-west-2.amazonaws.com/events/521abb61-6216-487e-a721-db53fa7003ac/upload/content/aa207287b449bdd02dee.jpg"))
			                  .build();

	    RankingResponse result = rankingService.rank(request)
	                                           .block();

	    System.out.println(result);

        // Exit
        System.exit(0);*/
    }
}
