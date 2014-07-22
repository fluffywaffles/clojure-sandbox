(ns topology.patch-math
  (:require [util.math :refer [clamp]])
  (:use topology.vars)) ;; should refer get-patch-at from world

(defn get-patch-at [x y]
  "stub (and in the wrong place)"
  {:id -1 :name (str "Patch " x " " y)})

;; wrap is tentatively corrected from the version found in
;;  topology.coffee (translated into clojure below)
;;  justification also below.
(defn wrap [pos mn mx]
  (cond
   (> pos mx) (-> pos (- mx) (mod (- mx mn)) (+ (dec mn)))
   (< pos mn) (- (inc mx) (-> (- mn pos)
                              (mod (- mx mn)))) ;; ((min - pos) % (max - min))
   :default pos))

(defn wrap-y [y]
  (if wrap-in-y?
    (wrap y min-pycor max-pycor)
    (clamp y min-pycor max-pycor)))

;; topology wraps, but so does world.getPatchAt ??

(defn wrap-x [x]
  (if wrap-in-x?
    (wrap x min-pxcor max-pxcor)
    (clamp x min-pxcor max-pxcor)))

;; direct neighbors (eg getNeighbors4 patches)

(defn get-patch-north [x y] (get-patch-at  x (wrap-y (inc y))))

(defn get-patch-east  [x y] (get-patch-at (wrap-x (inc x)) y))

(defn get-patch-south [x y] (get-patch-at  x (wrap-y (dec y))))

(defn get-patch-west  [x y] (get-patch-at (wrap-x (dec x)) y))

;; corners

(defn get-patch-northeast [x y]
  (get-patch-at (wrap-x (inc x)) (wrap-y (inc y))))

(defn get-patch-southeast [x y]
  (get-patch-at (wrap-x (inc x)) (wrap-y (dec y))))

(defn get-patch-southwest [x y]
  (get-patch-at (wrap-x (dec x)) (wrap-y (dec y))))

(defn get-patch-northwest [x y]
  (get-patch-at (wrap-x (dec x)) (wrap-y (inc y))))

;; can memoize all the get-patch-es.


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;  sanity checks                            (7/21/2014)  ;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(ns topology.patch-math.sanity-checks
  (:use topology.patch-math))

(binding [min-pxcor -5
          max-pxcor 5
          min-pycor -5
          max-pycor 5
          wrap-in-x? true
          wrap-in-y? true]
  (get-patch-at 5 5)
  (get-patch-north 5 5))

(binding [min-pxcor -5
          max-pxcor 5
          min-pycor -5
          max-pycor 5
          wrap-in-x? false
          wrap-in-y? false]
  (and
    (= (get-patch-at 5 5)
       (get-patch-north 5 5)
       (get-patch-northeast 5 5)
       (get-patch-east 5 5))

    (= (get-patch-north 0 0) (get-patch-at 0 1))
    (= (get-patch-east  0 0) (get-patch-at 1 0))
    (= (get-patch-south 0 0) (get-patch-at 0 -1))
    (= (get-patch-west 0 0) (get-patch-at -1 0))

    (= (get-patch-northeast 0 0) (get-patch-at 1 1))
    (= (get-patch-southeast 0 0) (get-patch-at 1 -1))
    (= (get-patch-southwest 0 0) (get-patch-at -1 -1))
    (= (get-patch-northwest 0 0) (get-patch-at -1 1))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;                                                      ;;
;; directly translated wrap seems to be doing something ;;
;; really strange?                                      ;;
;;                                                      ;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;;;;;;;;;;;;;;;;;;;;; WRAP CHECKS ;;;;;;;;;;;;;;;;;;;;;;;

(defn directly-translated-wrap [pos mn mx]
  (cond
   (>= pos mx) (-> pos (- mx) (mod (- mx mn)) (+ mn))
   (< pos mn) (let [diff (-> (- mn pos)
                             (mod (- mx mn)))] ;; ((min - pos) % (max - min))
                (if (= diff 0) mn (- mx diff)))
   :default pos))

(-> 6 (- 5) (mod (- 5 -5)) (+ -5)) ;; -4
;; equivalent to:
(directly-translated-wrap 6 -5 5) ;; -4

;; uh... what happened to xcor/ycor -5?

;; I do not like this.

(- 5 (-> (- -5 -6) (mod (- 5 -5)))) ;; 4
;; equivalent to
(directly-translated-wrap -6 -5 5) ;; 4

;; or that.

(when (= (-> (- -5 -5) (mod (- 5 -5))) 0) -5)

;; but that math works out fine (duh)...

;; Dafuk. a -5,5 grid (10x10) is actually 11x11 in Nlogo
;;  because of course it is.
;; And if you tell netlogo to make a -4,4 grid, that
;;  grid is 9x9. So... in Netlogo, you can only have
;;  odd sidelength grids.
;; If you are at patch 0,5, for that matter, the wrapping
;;  algorithm as written wraps you to 0,-5. What dafuk
;;  happened to 0,5?

;; see?

(directly-translated-wrap 5 -5 5)

;; Aha. While _wrap(6, -5, 5) yields the wrong answer (-4),
;;  actually testing wrapX or wrapY in the engine yields
;;  the correct answer (-5). How...?
;; In the current version of Tortoise, this is solved
;;  by adding/subtracting 0.5 from min/max Px/ycor in
;;  the definitions for wrapX and wrapY. Which is
;;  confusing if you think you're going to use _wrap
;;  to like actually wrap values.
;; I think the behavior of _wrap is incorrect, b/c
;;  _wrap(6, -5, 5) is clearly wrong if it spits out -4.

(-> 5 (- 5) (mod 10) (+ -5))

(wrap -5 -5 5)  ;; -5
(wrap -6 -5 5)  ;; 5
(wrap -10 -5 5) ;; 1
(wrap 5 -5 5)   ;; 5
(wrap 6 -5 5)   ;; -5
(wrap 10 -5 5)  ;; -1

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

