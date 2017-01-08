namespace java clademlia.thrift

struct PingMessage {
  1: required binary rpcId;
  2: required string nodeId;
  3: optional string ip;
  4: optional i16 port;
}

struct StoreRequest {
  1: required string rpcId;
  2: required string nodeId;
  3: required string key;
  4: required string value;
}

struct StoreResponse {
  1: required string rpcId;
  2: required string nodeId;
  3: required bool result;
}

struct FindRequest {
  1: required string rpcId;
  2: required string key;
}

struct FindResponse {
  1: required string rpcId;
  2: required string ip;
  3: required i16 port;
  4: required string nodeId;
  5: optional string value;
}

typedef list<FindResponse> FindResponseList

service Clademlia {
  PingMessage ping(1: PingMessage msg)
  StoreResponse store(1: StoreRequest req)
  FindResponseList findNode(1: FindRequest req)
  FindResponseList findValue(1: FindRequest req)
}
