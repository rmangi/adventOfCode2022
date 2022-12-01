(ns rmangi.advent
  (:gen-class)
  (:require [clojure.java.io :as io]))

(defn file-to-list [filename]
  (with-open [rdr (clojure.java.io/reader filename)]
    (reduce conj [] (line-seq rdr))))

(defn int-or-nil [maybe-int]
  (try (Integer/parseInt maybe-int)
       (catch Exception _ nil)))

(defn group-by-elf [list-of-data]
  (loop [elves [] next-elf [] items list-of-data]
    (if (empty? items)
      elves
      (let [maybe-int (int-or-nil (first items))]
        (if (int? maybe-int)
          (recur elves (conj next-elf maybe-int) (rest items))
          (recur (conj elves next-elf) [] (rest items)))))))

(defn sum-list [l]
  (reduce (fn [t n] (+ t n)) 0 l))

(defn sum-calories [elves]
  (map sum-list elves))


(defn day1-2 
  "How many total Calories is the top 3 elves carrying?"
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

(defn day1
  "How many total Calories is that Elf carrying?"
  [data]
  ;(println data)
  (->> data
       group-by-elf
       sum-calories
       sort
       (apply max)
       println))

(defn -main
  [& args]
  (println (first args))
  (let [data (file-to-list (first args))]
    (day1 data)
    (day1-2 data)))
