(ns safehammad.bond
  (:require [clojure.string :as str]
            [safehammad.templates :as templates])
  (:gen-class))

(def template-map {:jamesbond     templates/james-bond-templates
                   :starwars      templates/star-wars-templates
                   :harrypotter   templates/harry-potter-templates
                   :proverbs      templates/proverb-templates
                   :powerrangers  templates/power-rangers-templates})

(def token-pattern #"\b([A-Za-z]*)/([A-Z|$]{2,4})/")  ; Extract word and pos from "Word/POS/"
(def tokenise-words (partial re-seq token-pattern))

(defn pos->words
  "Build map of words keyed on parts of speech from collection of templates."
  [templates]
  (->> (mapcat tokenise-words templates)
       (map (fn [[_ word pos]] {pos #{(str/lower-case word)}}))
       (reduce #(merge-with into %1 %2))))

(defn match-capital
  "Capitalise the destination word the same as the source word."
  [src-word dest-word]
  (let [capitalise (if (Character/isUpperCase (first src-word)) str/capitalize str/lower-case)]
    (capitalise dest-word)))

(defn remove-pos
  "Remove part of speech markup from string, e.g. \"Sky/NN/fall/VBP/\" -> \"Skyfall\"."
  [s]
  (str/replace s token-pattern second))

(defn replacement-words
  "Return all suitable replacement words based on part of speech."
  [templates word pos]
  (if-let [options (-> (pos->words templates) (get pos) (disj word) seq)]
    options
    [word]))

(defn generate-title [templates]
  (let [template (rand-nth templates)
        [target word pos] (rand-nth (tokenise-words template))
        random-word (rand-nth (replacement-words templates word pos))]
    (->> (match-capital word random-word)
         (str/replace template target)
         remove-pos)))

(defn find-unknown-genres [genres]
  (let [template-names (map name (keys template-map))]
    (seq (remove (set template-names) genres))))

(defn generate [& genres]
  (if-let [unknown-genres (find-unknown-genres genres)]
    (str "UNKNOWN GENRES: " (str/join ", " unknown-genres))
    (->> genres (map keyword) (map template-map) (apply concat) generate-title)))

(defn -main
  "Print randomly generated James Bond film title."
  ([] (-main "jamesbond"))
  ([& genres] (println (apply generate genres))))
