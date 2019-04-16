package io.rsocket.springone.demo.client;

import java.time.Duration;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import io.netifi.proteus.spring.core.annotation.Group;
import io.rsocket.springone.demo.RecordsRequest;
import io.rsocket.springone.demo.RoundResult;
import io.rsocket.springone.demo.TournamentServiceClient;
import org.HdrHistogram.Recorder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

import org.springframework.stereotype.Service;

@Service
public class TournamentRunner {
    private static final Logger logger = LogManager.getLogger(TournamentRunner.class);

    @Group("springone.demo.tournament")
    private TournamentServiceClient tournamentService;

    public void run(String... args) throws Exception {
        RecordsRequest request = RecordsRequest.newBuilder().setMaxResults(Integer.MAX_VALUE).build();

        Recorder recorder = new Recorder(3600000000000L, 3);
        Flux.interval(Duration.ofSeconds(10))
            .doOnNext(
                aLong -> {
                    System.out.println("---- TOURNAMENT HISTO ----");
                    recorder
                        .getIntervalHistogram()
                        .outputPercentileDistribution(System.out, 5, 1000.0, false);
                    System.out.println("---- TOURNAMENT HISTO ----");
                })
            .subscribe();

        Flux.range(0, Runtime.getRuntime().availableProcessors())
            .subscribe(__ ->
        tournamentService
            .tournament(request)
            .repeat()
            .subscribe(new BaseSubscriber<RoundResult>() {

                long lastNanoTime;
                @Override
                protected void hookOnSubscribe(Subscription subscription) {
                    lastNanoTime = System.nanoTime();
                    subscription.request(1);
                }

                @Override
                protected void hookOnNext(RoundResult record) {
                    long diff = System.nanoTime() - lastNanoTime;
                    recorder.recordValue(diff);

//                    if (record.getRound() == 1){
//                        logger.info(
//                                "\n=----------------------------------------------------------="+
//                                "\n< @_@ > SUPER WINNER < @_@ >  ===> "+record.getWinner().getSuperName()+
//                                "\n=----------------------------------------------------------=");
//                    }
//                    else {
//                        try {
//                            logger.info(JsonFormat.printer().print(record));
//                        }
//                        catch (InvalidProtocolBufferException e) { }
//                    }

                    lastNanoTime = System.nanoTime();
                    request(1);
                }
            }));

        Thread.currentThread().join();
    }
}
