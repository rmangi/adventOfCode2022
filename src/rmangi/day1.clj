
(ns rmangi.day1
  (:require [rmangi.useful :refer [file-to-list get-day-resource int-or-nil
                                   sum-list]]))

(defn group-by-elf [list-of-data]
  (loop [elves [] next-elf [] items list-of-data]
    (if (empty? items)
      elves
      (let [maybe-int (int-or-nil (first items))]
        (if (int? maybe-int)
          (recur elves (conj next-elf maybe-int) (rest items))
          (recur (conj elves next-elf) [] (rest items)))))))

(defn sum-calories [elves]
  (map sum-list elves))

(defn part2
  "How many total Calories are the top 3 elves carrying?"
  [data]
  ;(println data)
  (->> data
       group-by-elf
       sum-calories
       sort
       reverse
       (take 3)
       sum-list
       println))

(defn part1
  "How many total Calories is that Elf carrying?"
  [data]
  ;(println data)
  (->> data
       group-by-elf
       sum-calories
       sort
       (apply max)
       println))

(defn run-day
  []
  (println "day 1...")
  (let [data (file-to-list (get-day-resource "day1"))]
    (part1 data)
    (part2 data)))