(ns id-manager)

;;
;; id-manager keeps track of how many twurtels have been
;; hatched and therefore can always tell a new twurtel
;; what its id should be. (7/17/2014)
;;

(def _count (atom 0))

(defn reset [] (reset! _count 0))
(defn next-id [] (swap! _count inc))

;; I don't think NetLogo actually needs to be able to access
;; the _count currently, outside of running next-id. (7/17/2014)

;;
;; when world resizes, id-manager needs to not be cleared.
;; however everything else gets cleared.
;; hence this. (7/17/2014)
;;

(def _prev_count (atom nil))

(add-watch _count :prev
           (fn [_key _ref old-state _new-state]
             (reset! _prev_count old-state)))

(defn restore-prev []
  (reset! _count @_prev_count))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;  sanity checks                              7/17/2014  ;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

@_count
@_prev_count

;; _count is currently 0
(= @_count 0)

;; next-id yields 1
(= (next-id) 1)
;; then 2
(= (next-id) 2)

;; reset yields 0
(= (reset) 0)

;; restore-prev yields 2
(= (restore-prev) 2)

;; hoo-ray!

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;