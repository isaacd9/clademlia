(ns clademlia.client
  (:import [clademlia.thrift Clademlia])
  (:require [finagle-clojure.futures :as f]
            [finagle-clojure.thrift :as thrift]))

(defn make-client
  [address]
  (thrift/client address Clademlia))
