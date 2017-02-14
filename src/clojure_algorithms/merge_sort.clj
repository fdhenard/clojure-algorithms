(ns clojure-algorithms.merge-sort
  )


(defn pp [x] (-> x clojure.pprint/pprint with-out-str))

(defn merge-it
  ([left right] (merge-it left right []))
  ([left right accum]
   (do
     (println "merging: " {:left left :right right :accum accum})
     (let [left-one (first left)
           right-one (first right)
           sorted_ (if (some nil? [left-one right-one])
                     (filter #(not (nil? %)) [left-one right-one])
                     (if (<= left-one right-one)
                       [left-one right-one]
                       [right-one left-one]))
           ]
       (if (and (nil? left-one) (nil? right-one))
         accum
         (recur (rest left) (rest right) (into accum sorted_)))))))


;; (defn splitter [coll accum]
;;   (let [_count (count coll)
;;         mid (/ _count 2)]
;;     (if (<= _count 2)
;;       accum
;;       (let [[left right] (split-at mid coll)]
;;         [(recur left-u) (recur right)]))))

;; (defn splitter [doing for-later done]
;;   (let [_count (count doing)]
;;     (if (<= _count 2)
;;       (conj done doing)
;;       (let [mid (/ _count 2)
;;             [left right] (split-at mid doing)]
;;         (recur left right)))))

;; (defn splitter [coll]
;;   (let [mid (-> coll count (/ 2))]
;;     (reduce #(split-at mid coll) [] coll)))




;; (defn merge-sort [coll]
;;   (let [mid (/ (count coll) 2)
;;         [left-unsorted right-unsorted] (split-at mid coll)]
;;     (if (<= (count left-unsorted) 1)
;;       (merge left-unsorted right-unsorted)
;;       (let [left-sorted (recur left-unsorted)
;;             right-sorted (recur right-unsorted)]
;;         (merge left-sorted right-sorted)))))

;; (defn merge-sort [remaining sorted]
;;   (let [_count (count coll)
;;         mid (-> _count (/ 2))
;;         the-split (split-at mid coll)]
;;     (if (<= _count 2)
      
;;       (recur (first the-split) ))))

(defn walk-printer [text x]
  (do
    (println text (with-out-str (clojure.pprint/pprint x)))
    x))

(defn walk-inner-xform [x]
  (cond
    (number? x) x
    (sequential? x) (let [_count (count x)]
               (if (<= _count 2)
                 x
                 (let [mid (/ _count 2)]
                   (split-at mid x))))))


(defn prewalk-try [x]
  (do
    (println "prewalk trying: " (with-out-str (clojure.pprint/pprint x)))
    (if (sequential? x)
     (let [_count (count x)]
       (if (<= _count 1)
         x
         (let [mid (/ _count 2)]
           (split-at mid x))))
     x)))

(defn prewalk-try-test []
  (w/prewalk prewalk-try [1 2 3 4 5]))

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
  (->> [5 1 4 2 3]
       (w/prewalk prewalk-try)
       (#(do
          (println "after prewalk: " (-> % clojure.pprint/pprint with-out-str))
          %))
       (w/postwalk postwalk-merge)))
