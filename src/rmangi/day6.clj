(ns rmangi.day6
  (:require [rmangi.useful :refer [get-day-resource]]))


(defn part2 [data]
  (let [d (seq data)]
    (loop [items (take 14 d) i 14 remaining (drop 14 d)]
      (if (= 14 (count (set items)))
        i
        (if (empty? remaining)
          nil
          (recur (flatten (cons (rest items) (list (first remaining)))) (+ 1 i) (rest remaining)))))))

(defn part1 [data]
  (let [d (seq data)]
    (loop [items (take 4 d) i 4 remaining (drop 4 d)]
      (if (= 4 (count (set items)))
        i
        (if (empty? remaining)
          nil
          (recur (flatten (cons (rest items) (list (first remaining)))) (+ 1 i) (rest remaining)))))))

(defn run-day []
  (println "day 6. Protocols.")
  (let [data (slurp (get-day-resource "day6"))]
    (println "part1: " (part1 data))
    (println "part2: " (part2 data))))