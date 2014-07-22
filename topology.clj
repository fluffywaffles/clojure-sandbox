(ns topology.core
  (:use topology.vars))

;; hand topology the functions it needs from world
;; (I believe all it should need is get-patch-at)

;; memoize all the things? or just some?

;; getNeighbors, getNeighbors4
;; called often, rarely different inputs
;; should be memo'd

;; members
;;
;; height
;; width

;; funcs
;;
;; getNeighbors (px, py)
;; getNeighbors4 (px, py) // ignores corners
;; distanceXY (x1, y1, x2, y2)
;; distance (x, y, agent )
;; towards (x1, y1, x2, y2)
;; midpointx
;; midpointy
;; inRadius (x, y, agents, radius)
;; wrap (pos, min, max)

;; abstract members
;;
;; wrapX
;; wrapY

;; abstract methods
;;
;; shortestX ;;wrapped vs origin distance btwn pnts
;; shortestY
;; getPatchNorth
;; getPatchEast
;; getPatchSouth
;; getPatchWest
;; getPatchNorthEast
;; getPatchSouthEast
;; getPatchSouthWest
;; getPatchNorthWest
;; all the getPatches can be memoized


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






