
(ns rmangi.day3
  (:require [rmangi.useful :refer [file-to-lines get-day-resource]]
            [clojure.set :refer [intersection]]))

(def priorities
  (zipmap (map str (seq "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"))
          (range 1 53)))

(defn parse-sack [sack]
  (let [[left  right] (split-at (/ (count sack) 2) (seq sack))]
    (intersection (set left) (set right))))

(defn day3-1 [data]
  (let [results
        (reduce (fn [totals sack]
                  (conj totals (get priorities (str (first (parse-sack sack))))))
                [] data)]
    (reduce #(+ %1 %2) 0 results)))

(defn day3-2 [data]
  (loop [total 0 sacks data]
    (if (empty? sacks)
      total
      (recur (+ total (get priorities (str (first (apply intersection (map set (take 3 sacks))))))) (drop 3 sacks)))))

(defn run-day
  []
  (println "day 3... rucksacks. stupid elves.")
  (let [data (file-to-lines
              (get-day-resource "day3"))]
    (println "part1: " (day3-1 data))
    (println "part2: " (day3-2 data))))