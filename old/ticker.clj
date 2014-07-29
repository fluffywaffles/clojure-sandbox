(ns ticker)

;; NOTE: does no error checking or exception
;;       throwing. (7/18/2014)

(def ^:private _count (atom 0))

;; needs tick, reset, clear

(defn tick [] (swap! _count inc))

(defn reset [] (reset! _count 0))

(defn clear [] (reset! _count -1))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;  sanity checks                            (7/18/2014)  ;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; tick -> 1
(= (tick) 1)

;; 5x tick -> 6
(= (->> #(tick) (repeatedly 5) last)
   6)

;; reset -> 0
(= (reset) 0)

;; clear -> -1
(= (clear) -1)

;; hoo-ray! it works

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
