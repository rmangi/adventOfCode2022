(ns rmangi.useful
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn file-to-list [filename]
  (with-open [rdr (io/reader filename)]
    (reduce conj [] (line-seq rdr))))

(defn file-to-lines [filename]
  (str/split-lines (slurp filename)))

(defn get-day-resource [day]
  (io/resource
   (str day ".txt")))

(defn sum-list [l]
  (reduce (fn [t n] (+ t n)) 0 l))

(defn int-or-nil [maybe-int]
  (try (Integer/parseInt maybe-int)
       (catch Exception _ nil)))

(defn find-in-map [m k v]
  (first (filter (fn [i] (= (get (second i) k) v)) (vec m))))