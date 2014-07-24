(ns topology.core
  (:use topology.vars))

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

(defmacro inheritant-bind [bind-block & body]
  `(binding ~bind-block
     (def-width)
     (def-height)
     ~@body))

(macroexpand '(inheritant-bind [max-pxcor 5 min-pxcor -10 max-pycor 10 min-pycor -5]))

(defprotocol TopologyCore
  (shortest-x [_ x1 x2] "finds shortest wrapped x distance")
  (shortest-y [_ y1 y2] "finds shortest wrapped y distance")
  ())






