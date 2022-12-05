(ns rmangi.advent
  (:gen-class)
  (:require [clojure.tools.cli :refer [parse-opts]]
            [rmangi.day1 :as day1]
            [rmangi.day2 :as day2]
            [rmangi.day3 :as day3]
            [rmangi.day4 :as day4]))

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
        1 (day1/run-day)
        2 (day2/run-day)
        3 (day3/run-day)
        4 (day4/run-day)
        5 (day5/run-day)
        (println "not yet!"))
      (println "Got errors parsing: " (:errors opts)))))
