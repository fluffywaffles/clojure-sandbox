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

(defmacro add-patch-math-fn [f]
  `(def ~f ~(symbol (str "topology.patch-math/" f))))
