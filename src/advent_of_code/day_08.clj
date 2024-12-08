(ns advent-of-code.day-08
  (:require
   [clojure.java.io :refer [resource]]
   [clojure.string :as str]
   [advent-of-code.utils :as utils]
   [clojure.math.combinatorics :as comb]))

(defn- load-city [input]
  (->> input
       str/split-lines
       (apply mapv vector)))

(def ^:private city (load-city (slurp (resource "day-08.txt"))))

(first city)

(defn- antennas [city]
  (let [[M N] (utils/arr-shape city)]
    (reduce (fn [m [c coord]]
              (update m c (fn [old new] (conj old new)) coord))
            {}
            (for [i (range M)
                  j (range N)
                  :let [c (get-in city [i j])]
                  :when (not= c \.)]
              [c [i j]]))))

(antennas city)

(defn- potential-antinodes [[[a b] [c d]]]
  (let [xs (abs (- a c))
        ys (abs (- b d))]
    (cond (and (< a c) (< b d)) [[(- a xs) (- b ys)] [(+ c xs) (+ d ys)]]
          (and (< a c) (> b d)) [[(- a xs) (+ b ys)] [(+ c xs) (- d ys)]]
          (and (> a c) (< b d)) [[(+ a xs) (- b ys)] [(- c xs) (+ d ys)]]
          (and (> a c) (> b d)) [[(+ a xs) (+ b ys)] [(- c xs) (- d ys)]])))

(defn part-1
  "Day 08 Part 1"
  [input]
  (let [city (load-city input)]
    (->> (antennas city)
         vals
         (map #(comb/combinations % 2))
         (mapcat (fn [pairs]
                (mapcat potential-antinodes pairs)))
         (filter #(get-in city %))
         set
         count)))

(defn part-2
  "Day 08 Part 2"
  [input]
  input)

