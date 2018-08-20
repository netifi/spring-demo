package io.netifi.springone.demo.service;

@javax.annotation.Generated(
    value = "by Proteus proto compiler (version 0.8.9)",
    comments = "Source: io/netifi/springone/demo/service/protobuf/service.proto")
@io.netifi.proteus.annotations.internal.ProteusGenerated(
    type = io.netifi.proteus.annotations.internal.ProteusResourceType.CLIENT,
    idlClass = HelloService.class)
public final class HelloServiceClient implements HelloService {
  private final io.rsocket.RSocket rSocket;
  private final java.util.function.Function<? super org.reactivestreams.Publisher<io.netifi.springone.demo.service.HelloResponse>, ? extends org.reactivestreams.Publisher<io.netifi.springone.demo.service.HelloResponse>> sayHello;
  private final java.util.function.Function<java.util.Map<String, String>, java.util.function.Function<? super org.reactivestreams.Publisher<io.netifi.springone.demo.service.HelloResponse>, ? extends org.reactivestreams.Publisher<io.netifi.springone.demo.service.HelloResponse>>> sayHelloTrace;

  public HelloServiceClient(io.rsocket.RSocket rSocket) {
    this.rSocket = rSocket;
    this.sayHello = java.util.function.Function.identity();
    this.sayHelloTrace = io.netifi.proteus.tracing.ProteusTracing.trace();
  }

  public HelloServiceClient(io.rsocket.RSocket rSocket, io.micrometer.core.instrument.MeterRegistry registry) {
    this.rSocket = rSocket;
    this.sayHello = io.netifi.proteus.metrics.ProteusMetrics.timed(registry, "proteus.client", "service", HelloService.SERVICE, "method", HelloService.METHOD_SAY_HELLO);
    this.sayHelloTrace = io.netifi.proteus.tracing.ProteusTracing.trace();
  }


  public HelloServiceClient(io.rsocket.RSocket rSocket, io.opentracing.Tracer tracer) {
    this.rSocket = rSocket;
    this.sayHello = java.util.function.Function.identity();
    this.sayHelloTrace = io.netifi.proteus.tracing.ProteusTracing.trace(tracer, HelloService.METHOD_SAY_HELLO, io.netifi.proteus.tracing.Tag.of("proteus.service", HelloService.SERVICE), io.netifi.proteus.tracing.Tag.of("proteus.type", "client"), io.netifi.proteus.tracing.Tag.of("proteus.version", "0.8.9"));
  }


  public HelloServiceClient(io.rsocket.RSocket rSocket, io.micrometer.core.instrument.MeterRegistry registry, io.opentracing.Tracer tracer) {
    this.rSocket = rSocket;
    this.sayHello = io.netifi.proteus.metrics.ProteusMetrics.timed(registry, "proteus.client", "service", HelloService.SERVICE, "method", HelloService.METHOD_SAY_HELLO);
    this.sayHelloTrace = io.netifi.proteus.tracing.ProteusTracing.trace(tracer, HelloService.METHOD_SAY_HELLO, io.netifi.proteus.tracing.Tag.of("proteus.service", HelloService.SERVICE), io.netifi.proteus.tracing.Tag.of("proteus.type", "client"), io.netifi.proteus.tracing.Tag.of("proteus.version", "0.8.9"));
  }

  @io.netifi.proteus.annotations.internal.ProteusGeneratedMethod(returnTypeClass = io.netifi.springone.demo.service.HelloResponse.class)
  public reactor.core.publisher.Mono<io.netifi.springone.demo.service.HelloResponse> sayHello(io.netifi.springone.demo.service.HelloRequest message) {
    return sayHello(message, io.netty.buffer.Unpooled.EMPTY_BUFFER);
  }

  @java.lang.Override
  @io.netifi.proteus.annotations.internal.ProteusGeneratedMethod(returnTypeClass = io.netifi.springone.demo.service.HelloResponse.class)
  public reactor.core.publisher.Mono<io.netifi.springone.demo.service.HelloResponse> sayHello(io.netifi.springone.demo.service.HelloRequest message, io.netty.buffer.ByteBuf metadata) {
  java.util.Map<String, String> map = new java.util.HashMap<>();
    return reactor.core.publisher.Mono.defer(new java.util.function.Supplier<reactor.core.publisher.Mono<io.rsocket.Payload>>() {
      @java.lang.Override
      public reactor.core.publisher.Mono<io.rsocket.Payload> get() {
        final io.netty.buffer.ByteBuf tracingMetadata = io.netifi.proteus.tracing.ProteusTracing.mapToByteBuf(io.netty.buffer.ByteBufAllocator.DEFAULT, map);
        final io.netty.buffer.ByteBuf metadataBuf = io.netifi.proteus.frames.ProteusMetadata.encode(io.netty.buffer.ByteBufAllocator.DEFAULT, HelloService.SERVICE, HelloService.METHOD_SAY_HELLO, tracingMetadata, metadata);
        io.netty.buffer.ByteBuf data = serialize(message);
        tracingMetadata.release();
        return rSocket.requestResponse(io.rsocket.util.ByteBufPayload.create(data, metadataBuf));
      }
    }).map(deserializer(io.netifi.springone.demo.service.HelloResponse.parser())).transform(sayHello).transform(sayHelloTrace.apply(map));
  }

  private static io.netty.buffer.ByteBuf serialize(final com.google.protobuf.MessageLite message) {
    int length = message.getSerializedSize();
    io.netty.buffer.ByteBuf byteBuf = io.netty.buffer.ByteBufAllocator.DEFAULT.buffer(length);
    try {
      message.writeTo(com.google.protobuf.CodedOutputStream.newInstance(byteBuf.internalNioBuffer(0, length)));
      byteBuf.writerIndex(length);
      return byteBuf;
    } catch (Throwable t) {
      byteBuf.release();
      throw new RuntimeException(t);
    }
  }

  private static <T> java.util.function.Function<io.rsocket.Payload, T> deserializer(final com.google.protobuf.Parser<T> parser) {
    return new java.util.function.Function<io.rsocket.Payload, T>() {
      @java.lang.Override
      public T apply(io.rsocket.Payload payload) {
        try {
          com.google.protobuf.CodedInputStream is = com.google.protobuf.CodedInputStream.newInstance(payload.getData());
          return parser.parseFrom(is);
        } catch (Throwable t) {
          throw new RuntimeException(t);
        } finally {
          payload.release();
        }
      }
    };
  }
}
