;; Anything you type in here will be executed
;; immediately with the results shown on the
;; right.

;; "Hello" maker.

(#(str "Hello, " % "!") "Dave")

;; "nth"

(defn mynth [lst n]
  (->> lst (drop n) (first)) ;; potentially easier to read as (first (drop n lst))
  )

(mynth '(1 2 3) 1)

;; "count"

(#(loop [n 0, l %]
    (if (nil? (first l))
      n
      (recur (inc n) (rest l)))
    ) '(1 2 3))

;; palindrome

(seq "racecar")
(defn palindrome? [v]
  (= (seq v) (reverse v)))

;; 4clojure fibonacci seq challenge

(defn n [x]
  (loop [i 1, fib '(1 1)]
    (if (= i (dec x)) (reverse fib)
      (recur (inc i) (cons (+ (first fib) (second fib)) fib))
      )
    )
  )

(= (n 3) '(1 1 2))
(= (n 6) '(1 1 2 3 5 8))
(= (n 8) '(1 1 2 3 5 8 13 21))

(n 6)

;;4clojure max value

(defn o [& nums]
  (last (sort nums))
  )

(= (o 1 8 3 4) 8)
(= (o 30 20) 30)
(= (o 45 67 11) 67)

;;4clojure get only CAPS

(defn caps [string]
  (clojure.string/join (re-seq #"[A-Z]" string)) ;;?
  )

(= (caps "HeLlO, WoRlD") "HLOWRD")
(empty? (caps "nothing"))
(= (caps "$#A(*987Zf") "AZ")

;;4clojure duplicate a sequence

(defn duplicate [s]
  (reverse (reduce into (map #(repeat 2 %) s)))
 )

(= (duplicate [1 2 3]) '(1 1 2 2 3 3))
(= (duplicate [:a :a :b :b]) '(:a :a :a :a :b :b :b :b))
(= (duplicate [[1 2] [3 4]]) '([1 2] [1 2] [3 4] [3 4]))

(duplicate '[1 2 3])

;; Intro to some

(some #{2 7 6} [5 6 7 8])

;; Implement range

(defn newRange [lower, upper]
  (loop [l lower, u upper, acc '()]
    (if (= l u)
      (reverse acc)
      (recur (inc l) upper (cons l acc))
     )
   )
  )

(= (newRange 1 4) '(1 2 3))
(= (newRange -2 2) '(-2 -1 0 1))
(= (newRange 5 8) '(5 6 7))

(newRange 1 4)

;; Partition?

(partition 2 (range 20))

;; Map defaults

(zipmap [:a :b :c] (repeat 0))
;; works because (repeat 0) is a lazy-seq of 0s

;; 4clojure flatten

((fn [outermost-lst] (reverse ((fn flattr2 [lst]
   (loop [l lst, n (first l), acc '()]
     (if (nil? n) acc
       (if (sequential? n)
         (recur (rest l)
                (second l)
                (concat (flattr2 n) acc ))
         (recur (rest l)
                (second l)
                (conj acc n)))
       )
     )
 ) outermost-lst)))
 '(1 2 (4 5 (55)) 7 (15 (8 (5 (3 (0)))))))

;; 4clojure factorial

(defn fac [n]
  (if (= n 0)
    1
    (* n (fac (dec n)))
    )
  )

(= (fac 1) 1)
(= (fac 3) 6)
(= (fac 5) 120)
(= (fac 8) 40320)

;; 4clojure Interleave two seqs

(defn interleaf [ls1 ls2]
   (apply concat (for [li (range (count ls1))
        :while (< li (count ls2))
        :let [li1 (nth ls1 li),
              li2 (nth ls2 li)]]
    (list li1 li2)))
)

(interleaf '(2 4 6 8) '(1 3))

;; 4clojure compress a seq

(defn cpress [lst]
  (reverse
    (loop [l lst,
           prev nil,
           i (first lst),
           acc '()]
      (if (= i nil)
        acc
        (if (= prev i)
          (recur (rest l) i (second l) acc)
          (recur (rest l) i (second l) (conj acc i))
        )
        )
      )
   ))

(defn better-cpress [lst]
  (reverse (reduce into '() (map set (partition-by identity lst)))))

(cpress "Leeeeeroyyyy")
(better-cpress "Leeeeeroyyyy")

;; iterate - "infinite lazy seq"

(take 5 (iterate #(+ 3 %) 1))

;; so is (repeat [n]) just (
(defn my-repeat [n]
  (iterate identity n))

(zipmap '(1 2 3) (my-repeat 0))

;; replicate

(defn myreplicate [s n]
  (reverse (reduce into (map #(repeat n %) s)))
 )

(myreplicate '(1 2 3) 2)

;; interpose seq

(interpose 0 [1 2 3])

(defn myinterpose [el lst]
  (reduce  #(concat %1 (list el %2))
           (list (first lst))
           (rest lst))
  )

(myinterpose 5 '(1 2 3 4))

;; pack a seq

(defn pack [s]
  (partition-by identity s))

(pack '(1 1 2 1 1 1 3))

(= (pack '(1 1 2 1 1 1 3 3)) '((1 1) (2) (1 1 1) (3 3)))

;; drop every nth item

(defn drop-every-nth [s n]
  (for [i (range (count s))
        :when (not= (mod (inc i) n) 0)
        :let [e (nth s i)]]
    e))

(drop-every-nth '(1 2 1 2 1 2) 4)

;; what?

(let [[a b c d e f] (range)] [c e])
;; apparently (range) is a lazy-seq from 0 to ...

;; split a seq (at a given index)

(defn split-seq [at s]
  (let [isolated
        (partition-by
         #(= (.indexOf s %) at)
         s)
        [f s r] isolated]
    (list f (concat s r))
    ))

(split-seq 2 [1 2 3 4 5 6])

;; half-truth 4clojure

(defn some-args [& bools]
  (or (and
       (some identity bools)
       (not (every? identity bools)))
      false)
  ;; because (or nil false) => false
  )

(some identity '(false false true))

(some-args true true true)
(some-args false false)

;; map constructor 4clojure

(defn make-map [keys vals]
          (apply hash-map (interleave keys vals)))

(apply hash-map (interleave [:a :b :c] '(1 2 3)))

(make-map [:a :b :c] [1 2 3])

;; 4clojure gcd

(defn gcd [a b]
  (apply max (filter #(and
          (= (mod a %) 0)
          (= (mod b %) 0))
        (range 1 (inc (min a b))))))

(gcd 2 4)
(gcd 10 5)
(gcd 5 7)
(gcd 1023 858)

;; 4clojure set intersection

(defn intersect [a b]
  (set (for [i a
        :when (contains? b i)]
    i)))

(intersect #{0 1 2 3} #{2 3 4 5})

;; re-implement iterate

(defn reiterate
  ([f]
   (let [i 1]
     (cons i (lazy-seq (reiterate f (f i))))))
  ([f base]
    (cons base (lazy-seq (reiterate f (f base))))
   ))

(or 4 "a")

(take 5 (reiterate #(* 2 %)))

;; lazy-seq?
(defn doubler [n]
  (cons  n (lazy-seq (doubler (* 2 n)))))

(take 30 (doubler 1))
;;yesss... yes to lazy-seqs

;; stupidly named 4clojure power partial generator

(((letfn (
        [power [n b]
         (if (= n 0)
           1
           (nth (iterate #(* b %) b) (dec n)))]
        )
  #(partial power %)) 8) 2) ;; should report 2^8, or 256

;; product sequencer

(defn seq-product [a b]
  (map #(Integer/parseInt (str %)) (str (* a b))))

(seq-product 1 1)

;; comparison result... er... namer. 4clojure

(defn comparison-result-key [c a b]
  (let [c? (c a b)
        eq? (= (c a b) (c b a))]
    (or
      (or
        (and c? :lt)
        (and eq? :eq))
       :gt)))

(comparison-result-key < 5 9)
(comparison-result-key (fn [x y] (< (count x) (count y))) "pear" "plum")

;; Cartesian Product

(defn cartesian [s1 s2]
   (set (for [m s1
              n s2]
     [m n])))

(= (cartesian #{"A" "B" "C"} #{:a :b :c})
   #{["A" :a] ["A" :b] ["A" :c]
     ["B" :a] ["B" :b] ["B" :c]
     ["C" :a] ["C" :b] ["C" :c]})
(= (cartesian #{"ace" "king" "queen"} #{"♠" "♥" "♦" "♣"})
   #{["ace"   "♠"] ["ace"   "♥"] ["ace"   "♦"] ["ace"   "♣"]
     ["king"  "♠"] ["king"  "♥"] ["king"  "♦"] ["king"  "♣"]
     ["queen" "♠"] ["queen" "♥"] ["queen" "♦"] ["queen" "♣"]})




















