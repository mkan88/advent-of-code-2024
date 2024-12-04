(ns advent-of-code.utils
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

(defn parse-out-longs
  "Parse out all numbers in `line` that are integers (longs)"
  [line]
  (map parse-long (re-seq #"[-+]?\d+" line)))

(defn partition-at [pred coll]
  (loop [c coll
         curr-c []
         res []]
    (if (empty? c)
      (conj res curr-c)
      (if (pred (first c))
        (recur (rest c) [(first c)] (conj res curr-c))
        (recur (rest c) (conj curr-c (first c)) res)))))
