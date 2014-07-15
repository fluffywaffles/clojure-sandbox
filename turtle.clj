(ns turtle)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(def prop :some-property)

(str prop)

;; only use macros when absolutely necessary

(defn keyword->str
  "keyword->string, dropping the leading :"
  [k] (->> k str rest (apply str)))

(defn dashify [lst]
  "list -> string (with dashes in place of spaces)"
  (->> lst (interleave (repeat \-)) rest (apply str)))

(defn sym-key-prop [k & p]
  (let [x (map keyword->str (conj p k))]
    (-> x dashify symbol)))

(sym-key-prop :get :graph :info)
(sym-key-prop :get prop)

(defmacro getter-example-1 [prop]
  `(list (symbol (str "get-" (keyword->str prop)))))

(macroexpand (getter-example-1 prop))

(defmacro getter-example-2 [p]
  `(defn ~(sym-key-prop :get `(~p)) [] ~p))

(macroexpand '(getter-example-2 prop))

(getter-example-2 prop)
(getter-example-2 :some-property)

(defmacro kw-getter [prop]
  `(list 'defn (sym-key-prop :get ~prop) [] ~prop))
;; Bah hahahahaha! you can offset the evaluation of the defn, thereby
;; ignoring its whole "oh, I need a symbol as my first argument ah-derp"
;; situation.

;; now for a setter...

((eval (kw-getter prop)))

(kw-getter :potato)
(kw-getter :id)

(get-id)

(macroexpand '(kw-getter :a))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(def default-props '(:id :breed :world :color :heading :xcor :ycor :label :size :hidden))

(def sym-props (map #(sym-key-prop :get %) default-props))

(macroexpand '(kw-getter (first default-props)))
(kw-getter (first default-props))
(get-id)

(map eval (map #(kw-getter %) default-props))

(get-breed)
