(ns advent-of-code.day-06
  (:require
   [clojure.java.io :refer [resource]]
   [clojure.string :as str]
   [advent-of-code.utils :as utils]))

(defn- load-map [input]
  (->> input
       str/split-lines
       (mapv seq)
       (apply mapv vector)))

;; (def ^:private guard-map (load-map (slurp (resource "day-06.txt"))))

(defn- initial-pos [m]
  (let [row-matches (map #(.indexOf % \^) m)
        y (first (filter #(not= -1 %) row-matches))
        x (.indexOf row-matches y)]
    [x y]))

;; (initial-pos guard-map)

(defn- map-shape [m]
  [(count m) (count (first m))])

(defn move [[x y :as pos] d]
  (let [move-map {:n [0 -1] :e [1 0] :s [0 1] :w [-1 0]}]
    (mapv + pos (move-map d))))

;; (move [2 4] :n)

(def ^:private turn {:n :e, :e :s, :s :w, :w :n})

(defn part-1
  "Day 06 Part 1"
  [input]
  (let [guard-map (load-map input)]
    (loop [[x y :as pos] (initial-pos guard-map)
           d :n
           seen #{pos}]
      (let [new-pos (move pos d)
            cell (get-in guard-map new-pos nil)]
        (if (nil? cell)
          (count seen)
          (if (= cell \#)
            (recur pos (turn d) seen)
            (recur new-pos d (conj seen new-pos))))))))

(defn- has-cycle? [m starting-pos]
  (loop [pos starting-pos
         d :n
         seen #{[pos d]}]
    (let [new-pos (move pos d)
          cell (get-in m new-pos nil)]
      (if (nil? cell)
        false
        (if (= cell \#)
          (recur pos (turn d) seen)
          (if (contains? seen [new-pos d])
            true
            (recur new-pos d (conj seen [new-pos d]))))))))

(defn part-2
  "Day 06 Part 2"
  [input]
  (let [guard-map (load-map input)
        [M N] (map-shape guard-map)
        free (for [x (range M) y (range N) :when (= \. (get-in guard-map [x y]))]
               [x y])
        starting-pos (initial-pos guard-map)]
    (->> (filter (fn [obstacle]
                   (has-cycle? (assoc-in guard-map obstacle \#) starting-pos))
                 free)
         count)))
