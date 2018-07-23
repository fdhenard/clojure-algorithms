(ns code-practice-clojure.utils)

(defn pp [x] (-> x clojure.pprint/pprint with-out-str))
