(ns rmangi.advent
  (:gen-class)
  (:require [clojure.tools.cli :refer [parse-opts]]
            [rmangi.day1 :refer [run-day-1]]))

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
