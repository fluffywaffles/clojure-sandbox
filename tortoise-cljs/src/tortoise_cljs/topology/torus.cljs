(ns topology.torus
  (:use [topology.vars :only [min-pycor min-pxcor max-pxcor max-pycor wrap-in-x? wrap-in-y?]])
  (:require topology.patch-math)
  (:require-macros [topology.core :refer [inheritant-bind]]))

;; get-patch-north = memoize _get_patch_north
;; and so on...

(defn create [mnx mxx mny mxy]
  (inheritant-bind
   [ min-pxcor mnx
     max-pxcor mxx
     min-pycor mny
     max-pycor mxy
     wrap-in-x? true
     wrap-in-y? true ]))

;; instantiate a record within inheritant-bind - does it retain access to those values?
