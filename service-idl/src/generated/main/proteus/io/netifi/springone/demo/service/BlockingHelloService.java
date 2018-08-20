package io.netifi.springone.demo.service;

/**
 */
@javax.annotation.Generated(
    value = "by Proteus proto compiler (version 0.8.9)",
    comments = "Source: io/netifi/springone/demo/service/protobuf/service.proto")
public interface BlockingHelloService {
  String SERVICE_ID = "io.netifi.springone.demo.service.HelloService";
  String METHOD_SAY_HELLO = "SayHello";

  /**
   * <pre>
   * Returns a Hello World Message
   * </pre>
   */
  io.netifi.springone.demo.service.HelloResponse sayHello(io.netifi.springone.demo.service.HelloRequest message, io.netty.buffer.ByteBuf metadata);
}
