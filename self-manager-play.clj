;; declare self, myself
;; bind when askAgent

;; binding as opposed to 'let':
;;    binding is thread-local mutable
;;    let is locally scoped

(def ^:dynamic self 0)
(def ^:dynamic myself 0)
(def ^:dynamic _prev_selfs (atom nil))

(reset! _prev_selfs {:self self :myself myself})
_prev_selfs
(:self @_prev_selfs)

(binding [self "self"]
  (var-get #'self)) ;; "self"

self ;; still 0

(defn ask-agent []
  (binding [self "self"
            myself "myself"]
    (str self " " myself)))

(ask-agent)
(str self " " myself)

;;
;;   need to keep track of self and myself
;;   some 0/nullable default
;;   self -> self
;;   myself -> myself
;;   askAgent -> [lots of side effects]
;;               try to do f
;;   (7/18/2014)
;;

(ns self-manager)

(def ^:dynamic _self 0)
(def ^:dynamic _myself 0)

(defn ask-agent [f]
  (fn [agent]
    (binding [_myself _self
              _self agent]
      (try (f)
        ;; NOTE:
        ;; in clojurescript, replace Exception with js/Object
        ;; write to log then by (.log js/console e)
        ;; NOTE: also this error handling is not appropos
        ;; (7/18/2014)
        (catch Exception e (str "Caught Ex. : " (.getMessage e)))
        ))))

(defn get-self [] @#'_self)
(get-self)

(defn get-myself [] @#'_myself)
(get-myself)


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;  sanity checks                            (7/18/2014)  ;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; self and myself should be 0 at the outermost level
(= _self _myself 0)

;; ask-agent 'turtle0 --> self is turtle0, myself is 0
(defn report-selfs [] (str (get-self) " " (get-myself)))

(= ((ask-agent report-selfs)
    'turtle-0)
   "turtle-0 0")

(= ((ask-agent
     #((ask-agent report-selfs) 'turtle-1))
    'turtle-0)
   "turtle-1 turtle-0")


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
