(ns rmangi.day4
  (:require [clojure.set :refer [intersection subset?]]
            [clojure.string :as str]
            [rmangi.useful :refer [file-to-lines get-day-resource]]))

(defn parse-instructions [instruction]
  (let [[order1 order2]  (str/split instruction #",")
        group1 (map #(Integer/parseInt %) (str/split order1 #"-"))
        group2 (map #(Integer/parseInt %) (str/split order2 #"-"))]
    [(set (range (first group1) (+ 1 (second group1))))
     (set (range (first group2) (+ 1 (second group2))))]))


(defn part2 [data]
  (loop [count 0 pairs data]
    (if (empty? pairs)
      count
      (let [[group1 group2] (parse-instructions (first pairs))]
        (if (seq (intersection group1 group2))
          (recur (+ count 1) (rest pairs))
          (recur count (rest pairs)))))))

(defn part1 [data]
  (loop [count 0 pairs data]
    (if (empty? pairs)
      count
      (let [[group1 group2] (parse-instructions (first pairs))]
        (if (or (subset? group1 group2) (subset? group2 group1))
          (recur (+ count 1) (rest pairs))
          (recur count (rest pairs)))))))


(defn run-day
  []
  (println "day 4... overlaps.")
  (let [data (file-to-lines
              (get-day-resource "day4"))]
    (println "part1: " (part1 data))
    (println "part2: " (part2 data))))