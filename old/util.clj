(ns util.macros)

(defmacro declare-dyn [& sym]
  `(declare ~@(map #(vary-meta % assoc :dynamic true) sym)))

(defn _to- [_] (->> _
                    str
                    rest
                    (replace {\_ \-})
                    clojure.string/join))

(defmacro memoizer [name-gen fun]
   `(list 'def
         (~name-gen ~fun)
         `(memoize ~~fun)))

(ns util.math)

(defn clamp [v mn mx]
  (cond (> v mx) mx
        (< v mn) mn
        :default v))

(defn squash [v to precision]
  "squash 'v' to be 'to' if v - to < precision"
  (if (< (Math/abs (- v to))
         precision)
    to
    v))

(ns util.in?)

(defn in? [col elm]
  (some #(= elm %) col))
