(ns advent-of-code.day-03
  (:require
   [clojure.java.io :refer [resource]]
   [clojure.string :as str]
   [advent-of-code.utils :as utils]))

(defn- load-mul-strs [input]
  (->> input
       (str/split-lines)))

(def ^:private mul-strs (load-mul-strs (slurp (resource "day-03.txt"))))
(def ^:private mul-re #"mul\((\d{1,3}),(\d{1,3})\)")

(defn- mul-re-seq-res [[expr g1 g2]]
  (let [a (Integer/parseInt g1)
        b (Integer/parseInt g2)]
    (* a b)))

(re-seq mul-re (first mul-strs))
(mul-re-seq-res (first (re-seq mul-re (first mul-strs))))

(defn part-1
  "Day 03 Part 1"
  [input]
  (->> (load-mul-strs input)
       (map #(re-seq mul-re %))
       (apply concat)
       (map mul-re-seq-res)
       (reduce +)))

(def ^:private mul-re2 #"do\(\)|don't\(\)|mul\((\d{1,3}),(\d{1,3})\)")

(defn- is-do-don't? [[expr g1 g2]]
  (or (= expr "do()") (= expr "don't()")))

(defn- partition-at [pred coll]
  (loop [c coll
         curr-c []
         res []]
    (if (empty? c)
      (conj res curr-c)
      (if (pred (first c))
        (recur (rest c) [(first c)] (conj res curr-c))
        (recur (rest c) (conj curr-c (first c)) res)))))

(defn part-2
  "Day 03 Part 2"
  [input]
  (->> (load-mul-strs input)
       (map #(re-seq mul-re2 %))
       (apply concat)
       (partition-at is-do-don't?)
       (filter #(not= (first (first %)) "don't()"))
       (apply concat)
       (filter #(not= (first %) "do()"))
       (map mul-re-seq-res)
       (reduce +)))

