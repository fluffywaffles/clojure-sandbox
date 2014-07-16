(ns turtle)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(def prop :some-property)

(str prop)

;; only use macros when absolutely necessary

;; let's make a macro that generates getters....

(defmacro getter-example-1 [prop]
  `(list (symbol (str "get-" (->str prop)))))

(macroexpand (getter-example-1 prop))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; abstraction and modularization and stuffs

(defn ->str
  "keyword->string, dropping the leading :"
  [k]
  (if (= (type k) clojure.lang.Keyword)
    (->> k str rest (apply str))
    (str k)))

(->str \s)
(->str :j)

(= (->str 'j) (->str \j) (->str :j) (->str "j"))
(= (->str 'mob) (->str :mob) (->str "mob"))

(defn dashify [lst]
  "list -> string (with dashes in place of spaces)"
  (->> lst (interleave (repeat \-)) rest (apply str)))

(defn sym-key-prop [k & p]
  "takes a set of props and a prefix and
  makes a dashified symbol combining them"
  (let [x (map ->str (conj p k))]
    (-> x dashify symbol)))

(sym-key-prop :get "graph" 'info)
(sym-key-prop "get" prop)
(sym-key-prop :get 'prop)
(sym-key-prop 'get \a)

(defmacro getter-example-2 [p]
  `(defn ~(sym-key-prop :get (eval p)) [] ~p))

(macroexpand '(getter-example-2 prop))

(getter-example-2 prop)
(getter-example-2 :some-property)

(defmacro gen-fn-from-prop [prefix prop f]
  `(list 'defn (sym-key-prop ~prefix ~prop) [] `(~~f ~~prop)))

;; OK, so what the fuk?
;; let's break down what we want from the fn.
;; (:get :some-property do-this-to-it)
;;       => '( (defn get-some-property [] (do-this-to-it :some-property)) )
;;       => (#'ns/get-some-property)
;;       => (#'ns/do-this-to-it :some-property)
;;       => ? whatever the output is of the last fn
;;
;; macro-it-ize this from piece to piece...
;; first, the end result for a simple case:

(defn get-id [] :id)

(defmacro getter [property]
  `(gen-fn-from-prop :get ~property identity))

;; Bah hahahahaha! you can offset the evaluation of the defn, thereby
;; ignoring its whole "oh, I need a symbol as my first argument ah-derp"
;; situation.

(macroexpand (getter prop))
(macroexpand (gen-fn-from-prop :get :potato identity))

(def potato :potato)

(macroexpand (getter potato))
(eval (getter potato))
(get-potato)
(getter 'id)

;; this declare/binding thing is kind of gross.
;; I don't like it, really.

(declare id)
(declare ^:dynamic id)
(binding [id 0]
  (eval (getter 'id))
  (get-id))

(macroexpand (getter :a))
(eval (getter :a))

(get-a)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(def default-props '(:id :breed :world :color :heading :xcor :ycor :label :size :hidden?))

(def id 0)

(macroexpand '(getter (first default-props)))
(getter (first default-props))
(get-id)

(map #(-> % getter eval) default-props)

(get-id)
(get-breed)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(def properties {:id "a" :nonexistant "b"})

(macroexpand (gen-fn-from-prop :get :id #(#'turtle/properties %)))
(eval (gen-fn-from-prop :get :id #(#'turtle/properties %)))
(get-id)

(defmacro turtle-prop-getter [prop]
  `(gen-fn-from-prop :get ~prop #(% properties)))

(macroexpand (turtle-prop-getter :id))
(eval (turtle-prop-getter :nonexistant))

(get-nonexistant)

(get-id)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; actually, it doesn't much make sense to create a "setter"
;; the properties object will probably be reconstructed instead of
;; its values changed.

;; let's try a more practical example...

default-props

(def turtle-properties (zipmap default-props '(0 turtle nil blue 0 0 0 "turtle-0" 0 false)))

turtle-properties

(defmacro gen-turtle-prop-getter [prop]
  `(gen-fn-from-prop :get ~prop #(% turtle-properties)))

(map #(-> % gen-turtle-prop-getter eval) default-props)

(get-id)
(get-breed)
(get-world)
(get-color)
(get-heading)
(get-xcor)
(get-ycor)
(get-label)
(get-size)
(get-hidden?)

;; hooray! It works.



























