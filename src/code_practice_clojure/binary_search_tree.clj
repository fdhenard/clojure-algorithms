(ns code-practice-clojure.binary-search-tree
  (:require [clojure.spec :as s]
            [code-practice-clojure.utils :as utils]))


;; TODO as of 6/9/17:
;; - add function arities for clean calls
;; - add nicer test cases
;; - comment printlns
;; - add clojure.spec validations
;; - add other "methods" (functions)

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

(defn put [bst key val path accum]
  (do
    (println "put: " (utils/pp {:bst bst
                                :key key
                                :val val
                                :path path
                                :accum accum}))
    (let [assoc-in-or-res (fn [obj path res]
                            (if (empty? path)
                              res
                              (assoc-in accum path res)))]
     (cond
       (and (nil? bst) (nil? key))
       accum
       (and (nil? bst) (not (nil? key)))
       (assoc-in-or-res accum path {::key key ::val val})
       (< key (::key bst))
       (recur (::left bst) key val (conj path ::left) (assoc-in-or-res accum path (select-keys bst [::right ::key ::val])))
       (> key (::key bst))
       (recur (::right bst) key val (conj path ::right) (assoc-in-or-res accum path (select-keys bst [::left ::key ::val])))))))

;; (s/fdef put
;;         :args #(-> %
;;                    :key
;;                    ((complement nil?))))


(defn test-put []
  (put nil 1 "1" [] {}))

(def scen-2 {::key 3
             ::value "three"
             ::left {::key 1
                     ::value "one"}
             ::right {::key 5
                      ::value "five"}})

(defn test-put-2 []
  (println "result!!!!!!!!: " (utils/pp (put scen-2 4 "four" [] {}))))
