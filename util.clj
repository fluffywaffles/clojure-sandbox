(ns util.macros)

(defmacro declare-dyn [& sym]
  `(declare ~@(map #(vary-meta % assoc :dynamic true) sym)))

(ns util.math)

(defn clamp [v mn mx]
  (cond (> v mx) mx
        (< v mn) mn
        :default v))

(ns util.in?)

(defn in? [col elm]
  (some #(= elm %) col))
