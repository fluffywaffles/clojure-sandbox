(ns util.macros)

(defmacro declare-dyn [& sym]
  `(declare ~@(map #(vary-meta % assoc :dynamic true) sym)))

(defn _to- [_] (->> _
                    str
                    rest
                    (replace {\_ \-})
                    clojure.string/join))

(defmacro memoizer
  ([name-gen fun nsp]
     `(def (~name-gen ~fun)
        `(memoize (symbol (str ~~nsp ~~fun)))))
  ([fun nsp]
    (let [ nfn #(symbol (_to- %))]
      `(def ~(nfn fun) (memoize ~(symbol (str nsp "/" fun))))))
  ([fun]
    (let [ nfn #(symbol (_to- %)) ]
      `(def ~(nfn fun) (memoize ~fun)))))

(macroexpand '(memoizer _get_patch_north topology.patch-math))

(defmacro add-patch-math-fn
  ([f]
   `(def ~f ~(symbol (str "topology.patch-math/" f))))
  ([f nm]
    `(def ~nm ~(symbol (str "topology.patch-math/" f)))))
