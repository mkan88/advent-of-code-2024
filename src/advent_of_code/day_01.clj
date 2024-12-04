(ns advent-of-code.day-01
  (:require
   [clojure.java.io :refer [resource]]
   [clojure.string :as str]))

(defn- load-locations [input]
  (let [parse-out-longs #(map parse-long (re-seq #"[-+]?\d+" %))]
    (->> input
         (str/split-lines)
         (map parse-out-longs)
         (mapv vec)
         (apply map vector)
         (map sort))))

;; (def ^:private locations (load-locations (slurp (resource "day-01.txt"))))

(defn- abs-difference [[a b]]
  (abs (- a b)))

(defn part-1
  "Day 01 Part 1"
  [input]
  (->> (load-locations input)
       (apply map vector)
       (map abs-difference)
       (reduce +)))

;; (abs-difference (first locations))
;; (reduce + (map abs-difference locations))

(defn part-2
  "Day 01 Part 2"
  [input]
  (let [locations (load-locations input)
        freq (frequencies (second locations))
        n-*-freq #(* % (if (nil? (freq %))
                         0
                         (freq %)))]
    (->> (map n-*-freq (first locations))
         (reduce +))))

;; (frequencies (second locations))
;; ((frequencies (second locations)) 38300)
