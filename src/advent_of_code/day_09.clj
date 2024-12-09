(ns advent-of-code.day-09
  (:require
   [clojure.java.io :refer [resource]]
   [clojure.string :as str]
   [advent-of-code.utils :as utils]
   [clojure.math.combinatorics :as comb]))

(defn- load-disk [input]
  (->> input
       seq
       (map #(Character/digit % 10))
       (partition 2)
       (map-indexed (fn [idx [dig emp]] (concat (repeat dig idx) (repeat emp \.))))
       flatten
       vec))

;; (def ^:private disk (load-disk (slurp (resource "day-09.txt"))))
;; (def ^:private disk (load-disk (slurp (resource "day-09-example.txt"))))

(defn- swap-pos [v i1 i2]
  (assoc v i2 (v i1) i1 (v i2)))

(defn- compact [disk]
  (loop [l 0
         r (dec (count disk))
         disk disk]
    (cond
      (= l r) disk
      (and (= (disk l) \.) (not= (disk r) \.)) (recur (inc l) (dec r) (swap-pos disk l r))
      (and (= (disk l) \.) (= (disk r) \.)) (recur l (dec r) disk)
      (not= (disk l) \.) (recur (inc l) r disk)
      ;; (and (= (disk l) \.) (not= (disk r) \.)) (recur (inc l) (dec r) (swap-pos disk l r))
    )))

(defn- checksum [disk]
  (->> (map-indexed * disk)
       (reduce +)))

(defn part-1
  "Day 09 Part 1"
  [input]
  (->> (load-disk input)
       compact
       (take-while #(not= % \.))
       checksum))

;; (part-1 (slurp (resource "day-09-example.txt")))

(defn- load-disk-block [input]
  (->> input
       seq
       (map #(Character/digit % 10))
       (partition 2)
       (map-indexed (fn [idx [dig emp]] (vector [idx dig] [\. emp])))
       (apply concat)
       vec))

(def ^:private disk-block (load-disk-block (slurp (resource "day-09-example.txt"))))

(defn- compact-block [disk-block]
  )

(defn- checksum-dot [disk]
  (let [*dot (fn [a b] (if (= b \.) 0 (* a b)))]
    (->> (map-indexed *dot disk)
        (reduce +))))

(defn part-2
  "Day 09 Part 2"
  [input]
  (->> (load-disk-block input)
       compact-block
       checksum-dot))
