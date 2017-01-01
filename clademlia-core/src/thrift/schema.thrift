namespace java clademlia.thrift

struct PingMessage {
  1: required binary rpcId;
  2: required binary nodeId;
  3: optional string ip;
  4: optional i16 port;
}

struct StoreRequest {
  1: required binary nodeId;
  2: required binary rpcId;
  3: required string key;
  4: required binary value;
}

struct StoreResponse {
  1: required binary nodeId;
  2: required binary rpcId;
  3: required bool result;
}

struct FindRequest {
  1: required string key;
}

struct FindResponse {
  1: required string ip;
  2: required i16 port;
  3: required binary nodeId;
  4: optional binary value;
}

typedef list<FindResponse> FindResponseList

service Clademlia {
  PingMessage ping(1: PingMessage msg)
  StoreResponse store(1: StoreRequest req)
  FindResponseList findNode(1: FindRequest req)
  FindResponseList findValue(1: FindRequest req)
}
