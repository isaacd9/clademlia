(ns clademlia.service
  (:import [clademlia.thrift Clademlia])
  (:require [finagle-clojure.futures :as f]
            [finagle-clojure.thrift :as thrift])
  (:gen-class))

(defn make-service
  []
  (thrift/service Clademlia
    ;; TODO implement service methods
    ))

(defn -main
  [& args]
  (f/await (thrift/serve ":9999" (make-service))))
