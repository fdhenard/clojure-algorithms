(ns clojure-algorithms.merge-sort
  (:require [clojure.walk :as w]))


(defn pp [x] (-> x clojure.pprint/pprint with-out-str))

(defn walk-printer [text x]
  (do
    (println text (with-out-str (clojure.pprint/pprint x)))
    x))

;; (defn walk-inner-xform [x]
;;   (cond
;;     (number? x) x
;;     (sequential? x) (let [_count (count x)]
;;                (if (<= _count 2)
;;                  x
;;                  (let [mid (/ _count 2)]
;;                    (split-at mid x))))))


(defn prewalk-split [x]
  (do
    (println "prewalk splitting: " (with-out-str (clojure.pprint/pprint x)))
    (if (sequential? x)
     (let [_count (count x)]
       (if (<= _count 1)
         x
         (let [mid (/ _count 2)]
           (split-at mid x))))
     x)))

(defn prewalk-split-test []
  (w/prewalk prewalk-split [1 2 3 4 5]))

(defn merge-it
  ([left right] (merge-it left right []))
  ([left right accum]
   (do
     (println "merge it: " (pp {:left left :right right :accum accum}))
     (let [left-one (first left)
           right-one (first right)]
       (if (and (nil? left-one) (nil? right-one))
         accum
         (cond
           (nil? left-one) (recur [] (rest right) (conj accum right-one))
           (nil? right-one) (recur (rest left) [] (conj accum left-one))
           (<= left-one right-one) (recur (rest left) right (conj accum left-one))
           :else (recur left (rest right) (conj accum right-one))))))))

(defn postwalk-merge [x]
  (do
    (println "postwalk merge: " (-> x clojure.pprint/pprint with-out-str))
    (if (and (sequential? x) (sequential? (first x)))
      (let [one-of-two (get x 0)
            two-of-two (get x 1)]
        (merge-it one-of-two two-of-two))
      x)))

(defn whole-test []
  (->> [5 2 1 2 4 2 3 3 3]
       (w/prewalk prewalk-split)
       (#(do
          (println "after prewalk: " (-> % clojure.pprint/pprint with-out-str))
          %))
       (w/postwalk postwalk-merge)))


(defn attempt-2 [todo remaining done]
  (do
    (println "attempt 2: " (pp {:todo todo :remaining remaining :done done}))
    (cond
      (and (not (sequential? (first done))) (empty? todo) (empty? remaining))
      done
      (sequential? (first done))
      (recur todo remaining (merge-it (get done 0) (get done 1)))
      (empty? todo)
      (recur (first remaining) (rest remaining) done)
      (not (empty? todo))
      (if (= (count todo) 1)
        (recur [] remaining [todo done])
        (let [mid (/ (count todo) 2)
              [left right] (split-at mid todo)]
          (recur left (cons right remaining) done))))))



(defn attempt-test []
  (attempt-2 [5 2 1 2 4 2 3 3 3] nil nil))
