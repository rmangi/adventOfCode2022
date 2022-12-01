(ns rmangi.advent
  (:gen-class)
  (:require [clojure.java.io :as io]
            [clojure.tools.cli :refer [parse-opts]]))

(defn file-to-list [filename]
  (with-open [rdr (clojure.java.io/reader filename)]
    (reduce conj [] (line-seq rdr))))

(defn int-or-nil [maybe-int]
  (try (Integer/parseInt maybe-int)
       (catch Exception _ nil)))

(defn read-data [day]
  (io/resource
   (str day ".txt")))

(defn sum-list [l]
  (reduce (fn [t n] (+ t n)) 0 l))

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

(defn day1-2
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

(defn day1-1
  "How many total Calories is that Elf carrying?"
  [data]
  ;(println data)
  (->> data
       group-by-elf
       sum-calories
       sort
       (apply max)
       println))

(defn run-day-1
  []
  (println "day 1...")
  (let [data (file-to-list (read-data "day1"))]
    (day1-1 data)
    (day1-2 data)))

(def cli-options
  [["-d" "--day DAY" "which day"
    :parse-fn #(Integer/parseInt %)
    :validate [#(< 0 % 31) "Must be a number between 1 and 31"]]
   ["-h" "--help"]])

(defn -main
  [& args]
  (let [opts (parse-opts args cli-options)
        day (get-in opts [:options :day])]
    (if (nil? (:errors opts))
      (condp = day
        1 (run-day-1)
        (println "not yet!"))
      (println "Got errors parsing: " (:errors opts)))))
