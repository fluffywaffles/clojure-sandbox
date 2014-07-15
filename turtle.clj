(ns turtle)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(def prop :some-property)

(str prop)

;; only use macros when absolutely necessary

(defn ->str
  "keyword->string, dropping the leading :"
  [k]
  (if (= (type k) clojure.lang.Keyword)
    (->> k str rest (apply str))
    (str k)))

(type :a)

(->str \s)
(->str :j)

(defn dashify [lst]
  "list -> string (with dashes in place of spaces)"
  (->> lst (interleave (repeat \-)) rest (apply str)))

(defn sym-key-prop [k & p]
  (let [x (map ->str (conj p k))]
    (-> x dashify symbol)))

(sym-key-prop :get :graph :info)
(sym-key-prop :get prop)

(defmacro getter-example-1 [prop]
  `(list (symbol (str "get-" (->str prop)))))

(macroexpand (getter-example-1 prop))

(defmacro getter-example-2 [p]
  `(defn ~(sym-key-prop :get `(~p)) [] ~p))

(macroexpand '(getter-example-2 prop))

(getter-example-2 prop)
(getter-example-2 :some-property)

(defmacro fn-er [prop prefix]
  `(list 'defn (sym-key-prop ~prefix ~prop) [] ~prop))

(defmacro getter [prop]
  (fn-er prop :get))
;; Bah hahahahaha! you can offset the evaluation of the defn, thereby
;; ignoring its whole "oh, I need a symbol as my first argument ah-derp"
;; situation.

;; now for a setter...

((eval (getter prop)))

(getter :potato)
(getter 'id)

(get-id)

(macroexpand '(getter :a))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(def default-props '(:id :breed :world :color :heading :xcor :ycor :label :size :hidden?))

(macroexpand '(kw-getter (first default-props)))
(getter (first default-props))
(get-id)

(map eval (map #(getter %) default-props))

(get-breed)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


