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

(defn part-2
  "Day 03 Part 2"
  [input]
  input)
