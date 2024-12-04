(ns advent-of-code.day-02
  (:require
   [clojure.java.io :refer [resource]]
   [clojure.string :as str]
   [advent-of-code.utils :as utils]))

(defn load-reports [input]
  (->> input
       (str/split-lines)
       (map utils/parse-out-longs)))

;; (def ^:private reports (load-reports (slurp (resource "day-02.txt"))))

(defn- adjacent-differ-within-range? [report low high]
  (->> (partition 2 1 report)
       (map #(abs (- (first %) (last %))))
       (every? #(and (>= % low) (<= % high)))))

(defn- is-strictly-monotonic? [report]
  (or (apply > report)
      (apply < report)))

;; (adjacent-differ-within-range? '(1 2 3 3) 1 3)

(defn part-1
  "Day 02 Part 1"
  [input]
  (->> (load-reports input)
       (filter is-strictly-monotonic?)
       (filter #(adjacent-differ-within-range? % 1 3))
       count))

(defn- drop-nth [n col]
  (concat (take n col) (drop (inc n) col)))

(defn- dropped-versions [report]
  (map #(drop-nth % report) (range (count report))))

(defn- any-viable? [reports]
  (some #(and (is-strictly-monotonic? %) (adjacent-differ-within-range? % 1 3)) reports))

;; (map #(drop-nth % (first reports)) (range (count (first reports))))
;; (dropped-versions (first reports))
;; (any-viable? (dropped-versions (first reports)))

(defn part-2
  "Day 02 Part 2"
  [input]
  (->> (load-reports input)
       (map dropped-versions)
       (filter any-viable?)
       count))
