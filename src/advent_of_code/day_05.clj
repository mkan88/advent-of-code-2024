(ns advent-of-code.day-05
  (:require
   [clojure.java.io :refer [resource]]
   [clojure.string :as str]
   [advent-of-code.utils :as utils]))

(defn- append-rule-set [old-rule new-value]
  (if (nil? old-rule)
    #{new-value}
    (conj old-rule new-value)))

(defn- load-rules [rules-block]
  (->> rules-block
       str/split-lines
       (map utils/parse-out-longs)
       (reduce (fn [rules [first second]]
                 (update rules first append-rule-set second)) {})))

(defn- load-updates [updates-block]
  (->> updates-block
       str/split-lines
       (map utils/parse-out-longs)))

(defn- load-pages [input]
  (let [[rules-block updates-block] (str/split input #"\n\n")]
    {:rules (load-rules rules-block)
     :updates (load-updates updates-block)}))

;; (append-rule-set #{1 2 4} 4)
;; (append-rule-set nil 4)

(def ^:private rules-updates (load-pages (slurp (resource "day-05.txt"))))

(defn- middle-page-number [update]
  (let [n (count update)
        mid-idx (/ (dec n) 2)]
    (nth update mid-idx)))

(middle-page-number (first (:updates rules-updates)))

(defn- update-cons-correct? [rules [first second]]
  (contains? (get rules first) second))

(defn- update-correct-order? [rules update]
  (->> (partition 2 1 update)
       (every? #(update-cons-correct? rules %))))

;; (update-cons-correct? {3 #{1 2 3}} '(3 2))

(defn part-1
  "Day 05 Part 1"
  [input]
  (let [{:keys [rules updates]} (load-pages input)]
    (->> (filter #(update-correct-order? rules %) updates)
         (map middle-page-number)
         (reduce +))))

(defn- insert-at-pos [coll k v]
  (concat (take k coll)
          [v]
          (drop k coll)))

;; (insert-at-pos '(1 2 3 4 5) 2 5)

(defn- reorder-update [rules update]
  ;; Insertion sort
  (reduce (fn [acc e]
            (let [k (first (drop-while #(contains? (rules %) e) acc))
                  idx (.indexOf acc k)]
              (if (= -1 idx)
                (concat acc [e])
                (insert-at-pos acc idx e)))) [] update))

(defn part-2
  "Day 05 Part 2"
  [input]
  (let [{:keys [rules updates]} (load-pages input)]
    (->> (filter (complement #(update-correct-order? rules %)) updates)
         (map #(reorder-update rules %))
         (map middle-page-number)
         (reduce +))))
