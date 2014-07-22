(ns topology.torus
  (:use topology.vars)
  (:require [topology.core :refer [inheritant-bind]]))

(inheritant-bind
 [ min-pxcor -5
   max-pxcor 5
   min-pycor -5
   max-pycor 5
   wrap-in-x? true
   wrap-in-y? true ]

   width)

;; instantiate a record within inheritant-bind - does it retain access to those values?

