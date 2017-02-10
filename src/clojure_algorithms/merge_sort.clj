(ns clojure-algorithms.merge-sort)


(defn merge
  ([left right] (merge left right []))
  ([left right accum]
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
          (recur (rest left) (rest right) (into accum sorted_))))))




