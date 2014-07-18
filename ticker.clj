(ns ticker)

;;
;; needs to keep a count and update it each time the
;; world changes
;;
;; advance the count -> inc
;; reset the count   -> 0
;; clear the count   -> -1
;; (7/17/2014)
;;

;; NOTE: does no error checking or exception
;;       throwing. (7/17/2014)

;; FIXME: ticker can be static,
;; no need for protocol/record structure.

(defprotocol ITicker
  (tick  [t] "increments the tick count")
  (reset [t] "resets the tick count")
  (clear [t] "clears the ticker"))

(deftype ticker [_count_atom]
  ITicker
  (tick  [_] (swap!  _count_atom inc))
  (reset [_] (reset! _count_atom 0))
  (clear [_] (reset! _count_atom -1)))

(defn create-ticker []
  (let [t (ticker. (atom 0))] t))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;  sanity checks                            (7/17/2014)  ;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; create ticker t
(def t (create-ticker))

;; tick -> 1
(= (.tick t) 1)

;; 5x tick -> 6
(= (->> #(.tick t) (repeatedly 5) last)
   6)

;; reset -> 0
(= (.reset t) 0)

;; clear -> -1
(= (.clear t) -1)

;; hoo-ray! it works

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
