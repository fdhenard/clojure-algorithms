(ns clojure-algorithms.utils)

(defn pp [x] (-> x clojure.pprint/pprint with-out-str))
