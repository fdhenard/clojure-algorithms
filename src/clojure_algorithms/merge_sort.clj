(ns clojure-algorithms.merge-sort
  )


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
    (println "prewalk trying: " (with-out-str (clojure.pprint/pprint x)))
    (if (sequential? x)
     (let [_count (count x)]
       (if (<= _count 1)
         x
         (let [mid (/ _count 2)]
           (split-at mid x))))
     x)))

(defn prewalk-split-test []
  (w/prewalk prewalk-split [1 2 3 4 5]))

(defn merge-it-2 [left right accum]
  (do
    (println "merge it 2: " (pp {:left left :right right :accum accum}))
    (let [left-one (first left)
         right-one (first right)]
     (if (and (nil? left-one) (nil? right-one))
       accum
       (cond
         (nil? left-one) (recur [] (rest right) (conj accum right-one))
         (nil? right-one) (recur (rest left) [] (conj accum left-one))
         :else
         (if (<= left-one right-one)
           (recur (rest left) right (conj accum left-one))
           (recur left (rest right) (conj accum right-one))))))))

(defn postwalk-merge [x]
  (do
    (println "postwalk merge: " (-> x clojure.pprint/pprint with-out-str))
    (if (and (sequential? x) (sequential? (first x)))
      (merge-it-2 (first x) (first (rest x)) [])
      x)))

(defn whole-test []
  (->> [5 2 1 2 4 2 3 3 3]
       (w/prewalk prewalk-split)
       (#(do
          (println "after prewalk: " (-> % clojure.pprint/pprint with-out-str))
          %))
       (w/postwalk postwalk-merge)))
