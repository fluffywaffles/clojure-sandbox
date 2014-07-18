(ns pen-manager)

;; turtle uses pen-manager
;; pen-manager uses pen-status
;; (7/18/2014)

(def ^:private Up "up")
(def ^:private Down "down")

(defprotocol ^:private IPen
  (raise-pen [_])
  (lower-pen [_])
  (get-size  [_])
  (get-mode  [_])
  (set-size  [_ s]))

(defrecord ^:private Pen [ _pen_status _pen_size ]
  IPen
  (raise-pen [p] (reset! _pen_status Up))
  (lower-pen [p] (reset! _pen_status Down))
  (get-size  [p] @_pen_size)
  (get-mode  [p] @_pen_status)
  (set-size [p s] (reset! _pen_size s)))

(defn create-pen []
  (Pen. (atom Up) (atom 1.0)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;  sanity checks                            (7/18/2014)  ;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(def p (create-pen))

(= (.get-mode p) @(:_pen_status p) Up)
(= (.lower-pen p) @(:_pen_status p) Down)
(= (.raise-pen p) @(:_pen_status p) Up)
(= (.get-mode p) @(:_pen_status p))
(= (.get-size p) @(:_pen_size p) 1.0)
(= (.set-size p 2.0) @(:_pen_size p) 2.0)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;