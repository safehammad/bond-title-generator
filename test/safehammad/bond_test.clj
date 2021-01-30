(ns safehammad.bond-test
  (:require [clojure.test :refer :all]
            [safehammad.bond :refer :all]))

(deftest pos->words-test
  (let [templates ["From/IN/ Russia/NN/ with/IN/ Love/NN/"
                   "Gold/JJ/finger/NN/"
                   "Thunder/JJ/ball/NN/"]
        ;; Output is lower cased
        output   {"IN" #{"from" "with"}
                  "NN" #{"russia" "love" "finger" "ball"}
                  "JJ" #{"gold" "thunder"}}]
    (is (= output (pos->words templates)))))

(deftest match-capital-test
  (testing "capitalise"  ; First letter captalised and rest small
    (are [src-word dest-word] (= "Foo" (match-capital src-word dest-word))
         "Big" "foo"
         "Big" "Foo"
         "Big" "foO"
         "BIg" "foo"))
  (testing "lower case"  ; All small
    (are [src-word dest-word] (= "foo" (match-capital src-word dest-word))
         "small" "Foo"
         "small" "foo"
         "small" "foO"
         "sMall" "Foo")))

(deftest remove-pos-test
  (are [result template] (= result (remove-pos template))
       "From Russia with Love" "From/IN/ Russia/NN/ with/IN/ Love/NN/"
       "Goldfinger" "Gold/JJ/finger/NN/"
       "Thunderball" "Thunder/JJ/ball/NN/"))

(deftest replacement-words-test
  (let [templates ["From/IN/ Russia/NN/ with/IN/ Love/NN/" "Gold/JJ/finger/NN/"]]
    (are [result word pos] (= result (set (replacement-words templates word pos)))
         #{"with"} "from" "IN"             ; return all IN words excluding "from"
         #{"from"} "with" "IN"             ; return all IN words excluding "with"
         #{"russia" "love"} "finger" "NN"  ; return all NN words excluding "finger""
         #{"gold"} "gold" "JJ")))          ; gold is the only JJ so it is returned

(deftest find-unknown-genres-test
  (are [genres result] (= result (find-unknown-genres genres))
       ["jamesbond" "foo"] ["foo"]
       ["jamesbond" "proverbs" "foo"] ["foo"]
       ["foo" "bar"] ["foo" "bar"]
       [] nil))
