(ns clademlia.service
  (:import [java.net NetworkInterface InetAddress])
  (:import [clademlia.thrift Clademlia PingMessage StoreResponse])
  (:require [finagle-clojure.futures :as f]
            [finagle-clojure.thrift :as thrift])
  (:require [clojure.java.io :as io])
  (:gen-class))

(def data
  (atom #{}))

(def ip
  (.getHostAddress
    (InetAddress/getLocalHost)))

(defn file-exists
  "Check if file exists"
  [fname]
    (.exists (io/as-file fname)))

(def nid
  (let [fname ".nodeid"]
  (if (file-exists fname)
      (slurp fname)
      (let [nid (byte-array 20 (repeatedly #(rand-int 10000)))
            fnid (format "%h" nid)]
        (spit fname fnid)
        fnid)
      )))

(defn parse-nid
  [nid]
  (BigInteger. nid 16))

(def distance bit-xor)

(defn updateNode
  [nodeId ip port]
  (do
    (println "Updating")
    (println (parse-nid nodeId))
    (println ip)
    (println port)
  )
)

(defn ping
  [msg port]
  (let [rpcId (.getRpcId msg)
        otherNodeId (.getNodeId msg)
        otherIp (.getIp msg)
        otherPort (.getPort msg)]
        (do
          (updateNode otherNodeId otherIp otherPort)
          (let
            [res (PingMessage. rpcId nid)]
            (.setIp res ip)
            (.setPort res port)
            (f/value res)
          ))))

(defn store
  [msg]
  (let [rpcId (.getRpcId msg)
        nodeId (.getNodeId msg)
        k (.getKey msg)
        v (.getValue msg)]
    (do (swap! data assoc k v)
        (println (format "Assigning %s to %s" k v))
        (f/value (StoreResponse. rpcId nodeId true)))))

(defn make-service
  [port]
  (thrift/service Clademlia
    (ping [msg port])
    (store [msg])
    ))

(defn -main
  [port & args]
  (-> (thrift/serve (format ":%s" port) (make-service (Integer. port)))
      (f/map [res] (println (format "Now serving on %s" port)))))
