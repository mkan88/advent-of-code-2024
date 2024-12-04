(ns advent-of-code.utils
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

(defn parse-out-longs
  "Parse out all numbers in `line` that are integers (longs)"
  [line]
  (map parse-long (re-seq #"[-+]?\d+" line)))
