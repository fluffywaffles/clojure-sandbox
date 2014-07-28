(ns topology.vars)

;;(declare-dyn max_pxcor
;;             min_pxcor
;;             max_pycor
;;             min_pycor
;;             wrap_in_x?
;;             wrap_in_y?)

;; declare-dyn doesn't work bc CLJS doesn't
;; allow vars to be declared;
;; they just default to nil. Which is,
;; I guess, arguably better than undefined. just
;; not as descriptive.

(def $UNBOUND "UNBOUND")

(def ^:dynamic max-pxcor  $UNBOUND)
(def ^:dynamic min-pxcor  $UNBOUND)
(def ^:dynamic max-pycor  $UNBOUND)
(def ^:dynamic min-pycor  $UNBOUND)
(def ^:dynamic wrap-in-x? $UNBOUND)
(def ^:dynamic wrap-in-y? $UNBOUND)

(def geo_struct
  {:NONE "In the beginning, there was..."
   :TORUS topology.torus})
