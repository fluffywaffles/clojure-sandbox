(ns self-manager)

(def ^:dynamic ^:private _self 0)
(def ^:dynamic ^:private _myself 0)

(defn ask-agent [f]
  (fn [agent]
    (binding [_myself _self
              _self agent]
      (try (f)
        ;; NOTE:
        ;; in clojurescript, js/Object = Exception
        ;; write to log by (.log js/console e)
        ;;
        ;; also this error handling is not appropos
        ;; (7/18/2014)
        (catch Exception e (str "Caught Ex. : "
                                (.getMessage e)))
        ))))

(defn get-self [] @#'_self)

(defn get-myself [] @#'_myself)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;  sanity checks                            (7/18/2014)  ;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; self and myself should be 0 at the outermost level

(= (get-self) _self (get-myself) _myself 0)

;; ask-agent 'turtle0 --> self is turtle0, myself is 0
(defn report-selfs [] (str (get-self) " " (get-myself)))

(= ((ask-agent report-selfs)
    'turtle-0)
   "turtle-0 0")

(= ((ask-agent
     #((ask-agent report-selfs) 'turtle-1))
    'turtle-0)
   "turtle-1 turtle-0")
