(ns clojure-algorithms.binary-search-tree
  (:require [clojure.spec :as s]))


(s/def ::key string?)
(s/def ::value string?)
(s/def ::n int?)
(s/def ::node (s/keys :req [::key ::value ::n]
                      :opt [::left ::right]))
(s/def ::left ::node)
(s/def ::right ::node)

(defn test-it []
  (let [test-data {::key "hi"
                   ::value "1"
                   ::n 0
                   ::left {::key "what"
                           ::value "nothing"
                           ::n 0}
                   ::right {::key "hello"
                            ::value "goodbye"
                            ::n 0}
                   }
        is-valid(s/valid? ::node test-data)]
    (when (not is-valid)
      (s/explain ::node test-data))))

