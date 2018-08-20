package io.netifi.springone.demo.service;

@javax.annotation.Generated(
    value = "by Proteus proto compiler (version 0.8.9)",
    comments = "Source: io/netifi/springone/demo/service/protobuf/service.proto")
@io.netifi.proteus.annotations.internal.ProteusGenerated(
    type = io.netifi.proteus.annotations.internal.ProteusResourceType.CLIENT,
    idlClass = BlockingHelloService.class)
public final class BlockingHelloServiceClient implements BlockingHelloService {
  private final io.netifi.springone.demo.service.HelloServiceClient delegate;

  public BlockingHelloServiceClient(io.rsocket.RSocket rSocket) {
    this.delegate = new io.netifi.springone.demo.service.HelloServiceClient(rSocket);
  }

  public BlockingHelloServiceClient(io.rsocket.RSocket rSocket, io.micrometer.core.instrument.MeterRegistry registry) {
    this.delegate = new io.netifi.springone.demo.service.HelloServiceClient(rSocket, registry);
  }

  @io.netifi.proteus.annotations.internal.ProteusGeneratedMethod(returnTypeClass = io.netifi.springone.demo.service.HelloResponse.class)
  public io.netifi.springone.demo.service.HelloResponse sayHello(io.netifi.springone.demo.service.HelloRequest message) {
    return sayHello(message, io.netty.buffer.Unpooled.EMPTY_BUFFER);
  }

  @java.lang.Override
  @io.netifi.proteus.annotations.internal.ProteusGeneratedMethod(returnTypeClass = io.netifi.springone.demo.service.HelloResponse.class)
  public io.netifi.springone.demo.service.HelloResponse sayHello(io.netifi.springone.demo.service.HelloRequest message, io.netty.buffer.ByteBuf metadata) {
    return delegate.sayHello(message, metadata).block();
  }

}

