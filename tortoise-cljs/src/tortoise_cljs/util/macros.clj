(ns util.macros)

(defmacro declare-dyn [& sym]
  `(declare ~@(map #(vary-meta % assoc :dynamic true) sym)))

(defn _to- [_] (->> _
                    str
                    rest
                    (replace {\_ \-})
                    clojure.string/join))

(defmacro memoizer
  ([name-gen nsp fun]
     `(def (~name-gen ~fun)
        `(memoize (symbol (str ~~nsp ~~fun)))))
  ([nsp fun]
    (let [ nfn #(symbol (_to- %))]
      `(def ~(nfn fun) (memoize ~(symbol (str nsp "/" fun))))))
  ([fun]
    (let [ nfn #(symbol (_to- %)) ]
      `(def ~(nfn fun) (memoize ~fun)))))

(defmacro add-patch-math-fn
  ([f]
   `(def ~f ~(symbol (str "world.topology.patch-math/" f))))
  ([f nm]
    `(def ~nm ~(symbol (str "world.topology.patch-math/" f)))))
