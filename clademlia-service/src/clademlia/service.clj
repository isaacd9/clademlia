(ns clademlia.service
  (:import [clademlia.thrift Clademlia java.net NetworkInterface])
  (:require [finagle-clojure.futures :as f]
            [finagle-clojure.thrift :as thrift])
  (:gen-class))

(def ip
  (.getHostAddress
    (java.net.InetAddress/getLocalHost)))

(def distance bit-xor)

(defn updateNode
  [nodeId ip port]
  (do
    (println "Updating")
    (println nodeId)
    (println ip)
    (println port)
  )
)

(defn ping
  [msg nid port]
  (let [rpcId (.getRpcId msg)
        otherNodeId (.getNodeId msg)
        otherIp (.getIp msg)
        otherPort (.getPort msg)]
        (do
          (updateNode otherNodeId otherIp otherPort)
          (f/value (PingMessage. rpcId nid ip))
        )
    )
)

(defn make-service
  [nid port]
  (thrift/service Clademlia
    (ping [msg nid port])
    ))

(defn -main
  [ip port nid & args]
  (do
    (f/await
      (thrift/serve
        (format ":%s" port)
        (make-service nid port)
        )
      )
    (println
      (format "Now serving on %s" port)
    )
  )
)
