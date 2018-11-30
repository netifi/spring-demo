package io.rsocket.springone.demo.client;

import io.netifi.proteus.spring.core.annotation.Group;
import io.rsocket.springone.demo.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class RecordsRunner {
    private static final Logger logger = LogManager.getLogger(RecordsRunner.class);

    @Group("springone.demo.records")
    private RecordsServiceClient recordsService;

    public void run(String... args) throws Exception {
			RecordsRequest request =
				RecordsRequest.newBuilder()
						.setMaxResults(1000)
						.build();

			Iterable<Record> result = recordsService.records(request).toIterable();

	    for (Record record: result) {
				System.out.println(record);
			}

			// Exit
			System.exit(0);
    }
}
