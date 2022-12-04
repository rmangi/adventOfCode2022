(ns rmangi.day2
  (:require
   [clojure.string :as str]
   [rmangi.useful :refer [file-to-lines find-in-map get-day-resource]]))

(def rules
  {:Rock {:points 1
          :input :A
          :output :X
          :beats :Scissors}
   :Paper {:points 2
           :input :B
           :output :Y
           :beats :Rock}
   :Scissors {:points 3
              :input :C
              :output :Z
              :beats :Paper}})

(def addendum {:X :lose :Y :tie :Z :win})

(defn part2
  [data]
  (println  "Would you like to play another game?")
  (loop [score 0 moves data]
    (if (= 0 (count moves))
      score
      (let [m (str/split (first moves) #" ")
            theyplay (find-in-map rules :input (keyword (first m)))
            outcome (get addendum (keyword (second m)))
            _ (println "they played a " theyplay " we want to " outcome)
            weplay (if (= outcome :tie)
                     (val theyplay)
                     (if (= outcome :win)
                       (val (find-in-map rules :beats (key theyplay)))
                       (get rules (:beats (val theyplay)))))
            _ (println "They play " (key theyplay) " we want to " outcome " so we counter with " weplay)
            playpoints (if (= (:beats weplay) (key theyplay)) 6
                           (if (= weplay (val theyplay)) 3 0))]
        (recur (+ score playpoints (:points weplay)) (rest moves))))))

(defn part1
  [data]
  (println  "Would you like to play a game?")
  (loop [score 0 moves data]
    (if (= 0 (count moves))
      score
      (let [m (str/split (first moves) #" ")
            theyplay (find-in-map rules :input (keyword (first m)))
            weplay (find-in-map rules :output (keyword (second m)))
            _ (println "They play " (key theyplay) " we counter with " (key weplay))
            playpoints (if (= (:beats (val weplay)) (key theyplay)) 6
                           (if (= (key weplay) (key theyplay)) 3 0))]
        (recur (+ score playpoints (:points (val weplay))) (rest moves))))))

(defn run-day
  []
  (println "day 2...")
  (let [data (file-to-lines
              (get-day-resource "day2"))]
    (print "first game score: " (part1 data))

    (print "second game score: " (part2 data))))