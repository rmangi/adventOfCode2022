; this one was a pain in the butt

(ns rmangi.day5
  (:require [rmangi.useful :refer [file-to-lines get-day-resource]]
            [clojure.string :as str]))


(defn parse-crate [line]
  (let [s (seq line)]
    (map #(str (nth s %)) (range 1 35 4))))

(defn parse-crate-data [data]
  (loop [piles [] count 0 remaining data]
    (let [next-line (first remaining)]
      (if (str/starts-with? next-line "move")
        [piles count remaining] ;; moves, we're done
        (if (str/starts-with? next-line "[") ;; a crate list
          (recur (conj piles (parse-crate next-line))  count (rest remaining))
          (if (str/starts-with? next-line " 1") ;; number line
            (recur piles next-line (rest remaining))
            (recur piles count (rest remaining))) ;; blank line
          )))))

(defn combine-crates [piles]
  (map  #(filterv (fn [f] (not (str/blank? (str f)))) %) (apply map list piles)))

(defn parse-move [move]
  (let [[_ num _ from _ to] (str/split move #" ")]
    (map #(Integer/parseInt %) [num from to])))

(defn move-item-9001 [piles from to num]
  (let [items (take num (nth piles (- from 1)))]
    (loop [new-piles []  i 1 remaining piles]
      (if (empty? remaining)
        new-piles
        (if (= i to)
          (recur (conj new-piles (concat items (first remaining))) (+ 1 i) (rest remaining))
          (if (= i from)
            (recur (conj new-piles (drop num (first remaining))) (+ 1 i) (rest remaining))
            (recur (conj new-piles (first remaining)) (+ 1 i) (rest remaining))))))))


(defn move-item [piles from to]
  (let [item (first (nth piles (- from 1)))]
    (loop [new-piles []  i 1 remaining piles]
      (if (empty? remaining)
        new-piles
        (if (= i to)
          (recur (conj new-piles (concat [item] (first remaining))) (+ 1 i) (rest remaining))
          (if (= i from)
            (recur (conj new-piles (rest (first remaining))) (+ 1 i) (rest remaining))
            (recur (conj new-piles (first remaining)) (+ 1 i) (rest remaining))))))))

(defn part1 [data]
  (let [[piles count moves] (parse-crate-data data)
        _ (Integer/parseInt (str (last (str/trim count))))
        stacks (combine-crates piles)]
    (loop [arranged-stacks stacks remaining-moves moves]
      (if (empty? remaining-moves)
        (map first arranged-stacks)
        (let [[num from to] (parse-move (first remaining-moves))]
          (recur (reduce (fn [stack _] (move-item stack from to)) arranged-stacks (range num)) (rest remaining-moves)))))))

(defn part2 [data]
  (let [[piles count moves] (parse-crate-data data)
        _ (Integer/parseInt (str (last (str/trim count))))
        stacks (combine-crates piles)]
    (loop [arranged-stacks stacks remaining-moves moves]
      (if (empty? remaining-moves)
        (map first arranged-stacks)
        (let [[num from to] (parse-move (first remaining-moves))]
          (recur (move-item-9001 arranged-stacks from to num) (rest remaining-moves)))))))

(defn run-day
  []
  (println "day 5. Stacking Crates.")
  (let [data (file-to-lines
              (get-day-resource "day5"))]
    (println "part1: " (part1 data))
    (println "part2: " (part2 data))))