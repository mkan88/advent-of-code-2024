(ns advent-of-code.day-07
  (:require
   [clojure.java.io :refer [resource]]
   [clojure.string :as str]
   [advent-of-code.utils :as utils]
   [clojure.math.combinatorics :as comb]))

(defn- load-equations [input]
  (->> input
       str/split-lines
       (map utils/parse-out-longs)
       ;; #() doesn't work for some reason
       (map (fn [l] [(first l) (rest l)]))))

(def ^:private equations (load-equations (slurp (resource "day-07.txt"))))

(first equations)

(defn- solve [acc tgt [f & r :as xs] ops]
  (cond
    (and (empty? xs) (= acc tgt)) [true]
    (and (empty? xs) (not= acc tgt)) [false]
    :else (mapcat #(solve (% acc f) tgt r ops) ops)))

(defn- valid? [tgt [f & r] ops]
  (some true? (solve f tgt r ops)))

;; (first (valid? (first (first equations)) (second (first equations)) [+ *]))

(defn part-1
  "Day 07 Part 1"
  [input]
  (->> (load-equations input)
       (filter (fn [[target xs]] (valid? target xs [+ *])))
       (map first)
       (reduce +)))

(defn- || [a b]
  (parse-long (str a b)))

(defn part-2
  "Day 07 Part 2"
  [input]
  (->> (load-equations input)
       (filter (fn [[target xs]] (valid? target xs [+ * ||])))
       (map first)
       (reduce +)))

