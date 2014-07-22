(ns topology.patch-math
  (:require [util.math :refer [clamp]])
  (:use topology.vars)) ;; should refer get-patch-at from world

(defn get-patch-at [x y]
  "stub (and in the wrong place)"
  {:id -1 :name (str "Patch " x " " y)})

(defn wrap [pos mn mx]
  (cond
   (> pos mx) (-> pos (- mx) (mod (- mx mn)) (+ mn))
   (< pos mn) (let [diff (-> (- mn pos)
                             (mod (- mx mn)))] ;; ((min - pos) % (max - min))
                (if (= diff 0) mn (- mx diff)))
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

(-> 6 (- 5) (mod (- 5 -5)) (+ -5))

(+ -5 (mod (- 6 5) (- 5 -5)))

;; I do not like this one bit.

(- 5 (-> (- -5 -6) (mod (- 5 -5))))

;; ok that's correct

(when (= (-> (- -5 -5) (mod (- 5 -5))) 0) -5)

;; and that math works out fine...

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
