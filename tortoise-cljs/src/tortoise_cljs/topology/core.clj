(ns topology.core
  (:use [topology.vars :only [min-pxcor min-pycor max-pxcor max-pycor wrap-in-x? wrap-in-y?]]))

;; hand topology the functions it needs from world
;; (I believe all it should need is get-patch-at)

;; funcs (will be implemented in patch-math)
;;
;; distanceXY (x1, y1, x2, y2)
;; distance (x, y, agent)
;; towards (x1, y1, x2, y2)
;; midpointx
;; midpointy
;; inRadius (x, y, agents, radius)

;; abstract methods
;;
;; shortestX ;;wrapped vs origin distance btwn pnts
;; shortestY

;; def members when inheritant-bind is called.
;;
;; members
;;
;; width
;; height

(defmacro def-width []
  '(def width (- max-pxcor min-pxcor)))

(defmacro def-height []
  '(def height (- max-pycor min-pycor)))

(defmacro add-patch-math-fn [f]
  `(def ~f ~(symbol (str "topology.patch-math/" f))))

(defmacro inheritant-bind [bind-block & body]
  `(binding ~bind-block
     (def-width)
     (def-height)
     ~@body))







