// GENERATED CODE -- DO NOT EDIT!

'use strict';
var rsocket_rpc_frames = require('rsocket-rpc-frames');
var rsocket_rpc_core = require('rsocket-rpc-core');
var rsocket_rpc_tracing = require('rsocket-rpc-tracing');
var rsocket_rpc_metrics = require('rsocket-rpc-metrics').Metrics;
var rsocket_flowable = require('rsocket-flowable');
var service_pb = require('./service_pb.js');

var RecordsServiceClient = function () {
  function RecordsServiceClient(rs, tracer, meterRegistry) {
    this._rs = rs;
    this._tracer = tracer;
    this.recordsTrace = rsocket_rpc_tracing.trace(tracer, "RecordsService", {"rsocket.rpc.service": "io.rsocket.springone.demo.RecordsService"}, {"method": "records"}, {"rsocket.rpc.role": "client"});
    this.recordsMetrics = rsocket_rpc_metrics.timed(meterRegistry, "RecordsService", {"service": "io.rsocket.springone.demo.RecordsService"}, {"method": "records"}, {"role": "client"});
  }
  RecordsServiceClient.prototype.records = function records(message, metadata) {
    const map = {};
    return this.recordsMetrics(
      this.recordsTrace(map)(new rsocket_flowable.Flowable(subscriber => {
        var dataBuf = Buffer.from(message.serializeBinary());
        var tracingMetadata = rsocket_rpc_tracing.mapToBuffer(map);
        var metadataBuf = rsocket_rpc_frames.encodeMetadata('io.rsocket.springone.demo.RecordsService', 'records', tracingMetadata, metadata || Buffer.alloc(0));
          this._rs.requestStream({
            data: dataBuf,
            metadata: metadataBuf
          }).map(function (payload) {
            //TODO: resolve either 'https://github.com/rsocket/rsocket-js/issues/19' or 'https://github.com/google/protobuf/issues/1319'
            var binary = !payload.data || payload.data.constructor === Buffer || payload.data.constructor === Uint8Array ? payload.data : new Uint8Array(payload.data);
            return service_pb.Record.deserializeBinary(binary);
          }).subscribe(subscriber);
        })
      )
    );
  };
  return RecordsServiceClient;
}();

exports.RecordsServiceClient = RecordsServiceClient;

var TournamentServiceClient = function () {
  function TournamentServiceClient(rs, tracer, meterRegistry) {
    this._rs = rs;
    this._tracer = tracer;
    this.tournamentTrace = rsocket_rpc_tracing.trace(tracer, "TournamentService", {"rsocket.rpc.service": "io.rsocket.springone.demo.TournamentService"}, {"method": "tournament"}, {"rsocket.rpc.role": "client"});
    this.tournamentMetrics = rsocket_rpc_metrics.timed(meterRegistry, "TournamentService", {"service": "io.rsocket.springone.demo.TournamentService"}, {"method": "tournament"}, {"role": "client"});
  }
  TournamentServiceClient.prototype.tournament = function tournament(message, metadata) {
    const map = {};
    return this.tournamentMetrics(
      this.tournamentTrace(map)(new rsocket_flowable.Flowable(subscriber => {
        var dataBuf = Buffer.from(message.serializeBinary());
        var tracingMetadata = rsocket_rpc_tracing.mapToBuffer(map);
        var metadataBuf = rsocket_rpc_frames.encodeMetadata('io.rsocket.springone.demo.TournamentService', 'tournament', tracingMetadata, metadata || Buffer.alloc(0));
          this._rs.requestStream({
            data: dataBuf,
            metadata: metadataBuf
          }).map(function (payload) {
            //TODO: resolve either 'https://github.com/rsocket/rsocket-js/issues/19' or 'https://github.com/google/protobuf/issues/1319'
            var binary = !payload.data || payload.data.constructor === Buffer || payload.data.constructor === Uint8Array ? payload.data : new Uint8Array(payload.data);
            return service_pb.RoundResult.deserializeBinary(binary);
          }).subscribe(subscriber);
        })
      )
    );
  };
  return TournamentServiceClient;
}();

exports.TournamentServiceClient = TournamentServiceClient;

var RankingServiceClient = function () {
  function RankingServiceClient(rs, tracer, meterRegistry) {
    this._rs = rs;
    this._tracer = tracer;
    this.rankTrace = rsocket_rpc_tracing.trace(tracer, "RankingService", {"rsocket.rpc.service": "io.rsocket.springone.demo.RankingService"}, {"method": "rank"}, {"rsocket.rpc.role": "client"});
    this.rankMetrics = rsocket_rpc_metrics.timed(meterRegistry, "RankingService", {"service": "io.rsocket.springone.demo.RankingService"}, {"method": "rank"}, {"role": "client"});
  }
  RankingServiceClient.prototype.rank = function rank(messages, metadata) {
    const map = {};
    return this.rankMetrics(
      this.rankTrace(map)(new rsocket_flowable.Flowable(subscriber => {
        var dataBuf;
        var tracingMetadata = rsocket_rpc_tracing.mapToBuffer(map);
        var metadataBuf ;
          this._rs.requestChannel(messages.map(function (message) {
            dataBuf = Buffer.from(message.serializeBinary());
            metadataBuf = rsocket_rpc_frames.encodeMetadata('io.rsocket.springone.demo.RankingService', 'rank', tracingMetadata, metadata || Buffer.alloc(0));
            return {
              data: dataBuf,
              metadata: metadataBuf
            };
          })).map(function (payload) {
            //TODO: resolve either 'https://github.com/rsocket/rsocket-js/issues/19' or 'https://github.com/google/protobuf/issues/1319'
            var binary = !payload.data || payload.data.constructor === Buffer || payload.data.constructor === Uint8Array ? payload.data : new Uint8Array(payload.data);
            return service_pb.Record.deserializeBinary(binary);
          }).subscribe(subscriber);
        })
      )
    );
  };
  return RankingServiceClient;
}();

exports.RankingServiceClient = RankingServiceClient;

var RecordsServiceServer = function () {
  function RecordsServiceServer(service, tracer, meterRegistry) {
    this._service = service;
    this._tracer = tracer;
    this.recordsTrace = rsocket_rpc_tracing.traceAsChild(tracer, "RecordsService", {"rsocket.rpc.service": "io.rsocket.springone.demo.RecordsService"}, {"method": "records"}, {"rsocket.rpc.role": "server"});
    this.recordsMetrics = rsocket_rpc_metrics.timed(meterRegistry, "RecordsService", {"service": "io.rsocket.springone.demo.RecordsService"}, {"method": "records"}, {"role": "server"});
    this._channelSwitch = (payload, restOfMessages) => {
      if (payload.metadata == null) {
        return rsocket_flowable.Flowable.error(new Error('metadata is empty'));
      }
      var method = rsocket_rpc_frames.getMethod(payload.metadata);
      var spanContext = rsocket_rpc_tracing.deserializeTraceData(this._tracer, payload.metadata);
      let deserializedMessages;
      switch(method){
        default:
          return rsocket_flowable.Flowable.error(new Error('unknown method'));
      }
    };
  }
  RecordsServiceServer.prototype.fireAndForget = function fireAndForget(payload) {
    throw new Error('fireAndForget() is not implemented');
  };
  RecordsServiceServer.prototype.requestResponse = function requestResponse(payload) {
    return rsocket_flowable.Single.error(new Error('requestResponse() is not implemented'));
  };
  RecordsServiceServer.prototype.requestStream = function requestStream(payload) {
    try {
      if (payload.metadata == null) {
        return rsocket_flowable.Flowable.error(new Error('metadata is empty'));
      }
      var method = rsocket_rpc_frames.getMethod(payload.metadata);
      var spanContext = rsocket_rpc_tracing.deserializeTraceData(this._tracer, payload.metadata);
      switch (method) {
        case 'records':
          return this.recordsMetrics(
            this.recordsTrace(spanContext)(new rsocket_flowable.Flowable(subscriber => {
              var binary = !payload.data || payload.data.constructor === Buffer || payload.data.constructor === Uint8Array ? payload.data : new Uint8Array(payload.data);
              return this._service
                .records(service_pb.RecordsRequest.deserializeBinary(binary), payload.metadata)
                .map(function (message) {
                  return {
                    data: Buffer.from(message.serializeBinary()),
                    metadata: Buffer.alloc(0)
                  }
                }).subscribe(subscriber);
              }
            )
          )
        );
        default:
          return rsocket_flowable.Flowable.error(new Error('unknown method'));
      }
    } catch (error) {
      return rsocket_flowable.Flowable.error(error);
    }
  };
  RecordsServiceServer.prototype.requestChannel = function requestChannel(payloads) {
    return new rsocket_flowable.Flowable(s => payloads.subscribe(s)).lift(s =>
      new rsocket_rpc_core.SwitchTransformOperator(s, (payload, flowable) => this._channelSwitch(payload, flowable)),
    );
  };
  RecordsServiceServer.prototype.metadataPush = function metadataPush(payload) {
    return rsocket_flowable.Single.error(new Error('metadataPush() is not implemented'));
  };
  return RecordsServiceServer;
}();

exports.RecordsServiceServer = RecordsServiceServer;

var TournamentServiceServer = function () {
  function TournamentServiceServer(service, tracer, meterRegistry) {
    this._service = service;
    this._tracer = tracer;
    this.tournamentTrace = rsocket_rpc_tracing.traceAsChild(tracer, "TournamentService", {"rsocket.rpc.service": "io.rsocket.springone.demo.TournamentService"}, {"method": "tournament"}, {"rsocket.rpc.role": "server"});
    this.tournamentMetrics = rsocket_rpc_metrics.timed(meterRegistry, "TournamentService", {"service": "io.rsocket.springone.demo.TournamentService"}, {"method": "tournament"}, {"role": "server"});
    this._channelSwitch = (payload, restOfMessages) => {
      if (payload.metadata == null) {
        return rsocket_flowable.Flowable.error(new Error('metadata is empty'));
      }
      var method = rsocket_rpc_frames.getMethod(payload.metadata);
      var spanContext = rsocket_rpc_tracing.deserializeTraceData(this._tracer, payload.metadata);
      let deserializedMessages;
      switch(method){
        default:
          return rsocket_flowable.Flowable.error(new Error('unknown method'));
      }
    };
  }
  TournamentServiceServer.prototype.fireAndForget = function fireAndForget(payload) {
    throw new Error('fireAndForget() is not implemented');
  };
  TournamentServiceServer.prototype.requestResponse = function requestResponse(payload) {
    return rsocket_flowable.Single.error(new Error('requestResponse() is not implemented'));
  };
  TournamentServiceServer.prototype.requestStream = function requestStream(payload) {
    try {
      if (payload.metadata == null) {
        return rsocket_flowable.Flowable.error(new Error('metadata is empty'));
      }
      var method = rsocket_rpc_frames.getMethod(payload.metadata);
      var spanContext = rsocket_rpc_tracing.deserializeTraceData(this._tracer, payload.metadata);
      switch (method) {
        case 'tournament':
          return this.tournamentMetrics(
            this.tournamentTrace(spanContext)(new rsocket_flowable.Flowable(subscriber => {
              var binary = !payload.data || payload.data.constructor === Buffer || payload.data.constructor === Uint8Array ? payload.data : new Uint8Array(payload.data);
              return this._service
                .tournament(service_pb.RecordsRequest.deserializeBinary(binary), payload.metadata)
                .map(function (message) {
                  return {
                    data: Buffer.from(message.serializeBinary()),
                    metadata: Buffer.alloc(0)
                  }
                }).subscribe(subscriber);
              }
            )
          )
        );
        default:
          return rsocket_flowable.Flowable.error(new Error('unknown method'));
      }
    } catch (error) {
      return rsocket_flowable.Flowable.error(error);
    }
  };
  TournamentServiceServer.prototype.requestChannel = function requestChannel(payloads) {
    return new rsocket_flowable.Flowable(s => payloads.subscribe(s)).lift(s =>
      new rsocket_rpc_core.SwitchTransformOperator(s, (payload, flowable) => this._channelSwitch(payload, flowable)),
    );
  };
  TournamentServiceServer.prototype.metadataPush = function metadataPush(payload) {
    return rsocket_flowable.Single.error(new Error('metadataPush() is not implemented'));
  };
  return TournamentServiceServer;
}();

exports.TournamentServiceServer = TournamentServiceServer;

var RankingServiceServer = function () {
  function RankingServiceServer(service, tracer, meterRegistry) {
    this._service = service;
    this._tracer = tracer;
    this.rankTrace = rsocket_rpc_tracing.traceAsChild(tracer, "RankingService", {"rsocket.rpc.service": "io.rsocket.springone.demo.RankingService"}, {"method": "rank"}, {"rsocket.rpc.role": "server"});
    this.rankMetrics = rsocket_rpc_metrics.timed(meterRegistry, "RankingService", {"service": "io.rsocket.springone.demo.RankingService"}, {"method": "rank"}, {"role": "server"});
    this._channelSwitch = (payload, restOfMessages) => {
      if (payload.metadata == null) {
        return rsocket_flowable.Flowable.error(new Error('metadata is empty'));
      }
      var method = rsocket_rpc_frames.getMethod(payload.metadata);
      var spanContext = rsocket_rpc_tracing.deserializeTraceData(this._tracer, payload.metadata);
      let deserializedMessages;
      switch(method){
        case 'rank':
          deserializedMessages = restOfMessages.map(payload => {
            var binary = !payload.data || payload.data.constructor === Buffer || payload.data.constructor === Uint8Array ? payload.data : new Uint8Array(payload.data);
            return service_pb.RankingRequest.deserializeBinary(binary);
          });
          return this.rankMetrics(
            this.rankTrace(spanContext)(
              this._service
                .rank(deserializedMessages, payload.metadata)
                .map(function (message) {
                  return {
                    data: Buffer.from(message.serializeBinary()),
                    metadata: Buffer.alloc(0)
                  }
                })
              )
            );
        default:
          return rsocket_flowable.Flowable.error(new Error('unknown method'));
      }
    };
  }
  RankingServiceServer.prototype.fireAndForget = function fireAndForget(payload) {
    throw new Error('fireAndForget() is not implemented');
  };
  RankingServiceServer.prototype.requestResponse = function requestResponse(payload) {
    return rsocket_flowable.Single.error(new Error('requestResponse() is not implemented'));
  };
  RankingServiceServer.prototype.requestStream = function requestStream(payload) {
    return rsocket_flowable.Flowable.error(new Error('requestStream() is not implemented'));
  };
  RankingServiceServer.prototype.requestChannel = function requestChannel(payloads) {
    return new rsocket_flowable.Flowable(s => payloads.subscribe(s)).lift(s =>
      new rsocket_rpc_core.SwitchTransformOperator(s, (payload, flowable) => this._channelSwitch(payload, flowable)),
    );
  };
  RankingServiceServer.prototype.metadataPush = function metadataPush(payload) {
    return rsocket_flowable.Single.error(new Error('metadataPush() is not implemented'));
  };
  return RankingServiceServer;
}();

exports.RankingServiceServer = RankingServiceServer;

