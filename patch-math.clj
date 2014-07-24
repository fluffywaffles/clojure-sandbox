(ns topology.patch-math
  (:use topology.vars)
  (:require [util.math :refer [squash]])) ;; should refer get-patch-at from world(?)

(defn get-patch-at [x y]
  "stub (and in the wrong place)"
  (if (and (<= min-pxcor x max-pxcor)
           (<= min-pycor y max-pycor))
    {:id -1 :name (str "Patch " x " " y)}))

(defn squash-8 [v mn]
  (squash v mn 1.0E-8))

;; wrap is tentatively corrected from the version found in
;;  topology.coffee (translated into clojure below).
;;  justification also below.
(defn wrap [p mn mx]
  (let [pos (squash-8 p mn)]
  (cond
    ;; use >= to consistently return -5.5 for the "seam" of the
    ;; wrapped shape -- i.e., -5.5 = 5.5, so consistently
    ;; report -5.5 in order to have equality checks work
    ;; correctly.
    (>= pos mx) (-> pos (- mx) (mod (- mx mn)) (+ mn))
    (< pos mn)  (- mx (-> (- mn pos)
                          (mod (- mx mn)))) ;; ((min - pos) % (max - min))
    :default pos)))

(defn wrap-y [y]
  (if wrap-in-y?
    (wrap y min-pycor max-pycor)
    y))

;; topology wraps, but so does world.getPatchAt ??

(defn wrap-x [x]
  (if wrap-in-x?
    (wrap x min-pxcor max-pxcor)
    x))

;; direct neighbors (eg getNeighbors4 patches)

(defn _get_patch_north [x y] (get-patch-at  x (wrap-y (inc y))))

(defn _get_patch_east  [x y] (get-patch-at (wrap-x (inc x)) y))

(defn _get_patch_south [x y] (get-patch-at  x (wrap-y (dec y))))

(defn _get_patch_west  [x y] (get-patch-at (wrap-x (dec x)) y))

;; corners

(defn _get_patch_northeast [x y]
  (get-patch-at (wrap-x (inc x)) (wrap-y (inc y))))

(defn _get_patch_southeast [x y]
  (get-patch-at (wrap-x (inc x)) (wrap-y (dec y))))

(defn _get_patch_southwest [x y]
  (get-patch-at (wrap-x (dec x)) (wrap-y (dec y))))

(defn _get_patch_northwest [x y]
  (get-patch-at (wrap-x (dec x)) (wrap-y (inc y))))

;; can memoize all the things. :D
;; perhaps will not want/need to memoize EVERYTHING,
;; but for now just everything.

(defmacro ^:private memoizer [name-gen fun]
  `(list 'def
         (~name-gen ~fun)
         `(memoize ~~fun)))

;; I have to dbl quote (memoize fun) b/c I want it to run at
;;  the time the macro runs, not when the macro compiles.

(defn ^:private dequalify [f]
  (-> f
      str
      (clojure.string/split #"/")
      last))

(defn ^:private _to- [_] (->> (dequalify _)
                              rest
                              (replace {\_ \-})
                              clojure.string/join))

;; this is probably an unnecessary abstraction.
;; but hopefully it'll be useful later?

(defn memorize-by [fn]
  (map #(->> % (memoizer fn) eval)
       '(_get_patch_north
         _get_patch_east
         _get_patch_south
         _get_patch_west
         _get_patch_northeast
         _get_patch_southeast
         _get_patch_southwest
         _get_patch_northwest)))

(memorize-by (fn [n] (symbol (_to- n))))

;; get neighbors

(defn _get_neighbors_4 [x y]
   (filter #(not= % nil)
          [(_get_patch_north x y)
           (_get_patch_east x y)
           (_get_patch_south x y)
           (_get_patch_west x y)]))

(defn _get_neighbors [x y]
  (concat
    (_get_neighbors_4 x y)
    (filter #(not= % nil)
          [(_get_patch_northeast x y)
           (_get_patch_northwest x y)
           (_get_patch_southwest x y)
           (_get_patch_southeast x y)])))


;; if the fns inside of get-neighbors are memoized,
;; I don't think there's a point to memoizing get-neighbors(?)

(defn get-neighbors-4 [x y]
  (filter #(not= % nil)
          [(get-patch-north x y)
           (get-patch-east x y)
           (get-patch-south x y)
           (get-patch-west x y)]))

(defn get-neighbors [x y]
  (concat
    (get-neighbors-4 x y)
    (filter #(not= % nil)
           [(get-patch-northeast x y)
            (get-patch-northwest x y)
            (get-patch-southwest x y)
            (get-patch-southeast x y)])))

;; shortest-x
;; It's weird af, but this mimics
;; Tortoise EXCEPT in the case that
;; the difference is greater than
;; max-pxcor.
;; In that case, shortest-x wraps
;; the difference. _shortestX
;; does not.
(defn shortest-x [x1 x2]
  (wrap-x (- x2 x1)))

;; midpoints


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;  sanity checks                            (7/22/2014)  ;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; IMPORTANT: tests should use unmemoized versions bc I
;; want to change the underlying values and get a computed,
;; not STORED, result.

;; Memoization should still be used by all topology types tho.

(ns topology.patch-math.sanity-checks
  (:use topology.vars)
  (:refer util.in?)
  (:refer topology.patch-math
          :rename { _get_patch_north     gpn
                    _get_patch_northeast gpne
                    _get_patch_east      gpe
                    _get_patch_southeast gpse
                    _get_patch_south     gps
                    _get_patch_southwest gpsw
                    _get_patch_west      gpw
                    _get_patch_northwest gpnw
                    get-patch-at         gp
                    _get_neighbors_4      gn4
                    _get_neighbors        gn}))

(def d-min-pxcor (atom -5))
(def d-max-pxcor  (atom 5))
(def d-min-pycor (atom -5))
(def d-max-pycor  (atom 5))
(def d-wrap-in-x? (atom true))
(def d-wrap-in-y? (atom true))

;;(defmacro bind-and-do [stuff]
;;  `(list 'binding
;;            '[min-pxcor  @d-min-pxcor
;;              max-pxcor  @d-max-pxcor
;;              min-pycor  @d-min-pycor
;;              max-pycor  @d-max-pycor
;;              wrap-in-x? @d-wrap-in-x?
;;              wrap-in-y? @d-wrap-in-y?]
;;    ~stuff))

;; and after all that, it turns out (with-bindings) already exists in clojure.core. whatever.

(defmacro bind-and-do [stuff]
  `(with-bindings {#'min-pxcor  @d-min-pxcor
                   #'max-pxcor  @d-max-pxcor
                   #'min-pycor  @d-min-pycor
                   #'max-pycor  @d-max-pycor
                   #'wrap-in-x? @d-wrap-in-x?
                   #'wrap-in-y? @d-wrap-in-y?} ~stuff))

;; wrap equivalency checks
(bind-and-do (= (wrap-x 5) (wrap-y 5) (wrap 5 -5 5) 5))
(bind-and-do (= (wrap-x -5) (wrap-y -5) (wrap -5 -5 5) -5))

(bind-and-do (= (wrap-x 6) (wrap-y 6) (wrap 6 -5 5) -5))

;; wrap-x and wrap-y respect the bindings. The other fns? Not.
;; WHYY? ohhhhh wait. It's because they are memoized. Ha! Oh shit. That was stupid.

(bind-and-do (= (wrap-x -6) (wrap-y -6) (wrap -6 -5 5) 5))

(bind-and-do (= (wrap-x 10) (wrap-y 10) (wrap 10 -5 5) -1))
(bind-and-do (= (wrap-x -10) (wrap-y -10) (wrap -10 -5 5) 1))

(bind-and-do (= (wrap-x 101) (wrap-y 101) (wrap 101 -5 5) 0))
(bind-and-do (= (wrap-x -101) (wrap-y -101) (wrap -101 -5 5) 0))

;; get-patch wrap checks
(bind-and-do (= (gpn 0 5) (gp 0 -5)))
(bind-and-do (= (gps 0 -5) (gp 0 5)))
(bind-and-do (= (gpe 5 0) (gp -5 0)))
(bind-and-do (= (gpw -5 0) (gp 5 0)))

(bind-and-do (= (gpne 5 5) (gp -5 -5)))
(bind-and-do (= (gpsw -5 -5) (gp 5 5)))
(bind-and-do (= (gpnw -5 5) (gp 5 -5)))
(bind-and-do (= (gpse 5 -5) (gp -5 5)))

(bind-and-do (= (take 8 (repeat true))
   (let [neighbors (gn 0 0)]
     (map #(in? neighbors %) [(gp 0 1) (gp 1  0) (gp 0  -1) (gp -1 0)
                              (gp 1 1) (gp 1 -1) (gp -1 -1) (gp -1 1)]))))

(bind-and-do (= (take 4 (repeat true))
   (let [neighbors (gn4 0 0)]
     (map #(in? neighbors %) [(gp 0 1) (gp 1 0) (gp 0 -1) (gp -1 0)]))))

(bind-and-do (= (gpn 0 0) (gp 0  1)))
(bind-and-do (= (gpe 0 0) (gp 1  0)))
(bind-and-do (= (gps 0 0) (gp 0 -1)))
(bind-and-do (= (gpw 0 0) (gp -1 0)))

(bind-and-do (= (gpne 0 0) (gp 1   1)))
(bind-and-do (= (gpse 0 0) (gp 1  -1)))
(bind-and-do (= (gpsw 0 0) (gp -1 -1)))
(bind-and-do (= (gpnw 0 0) (gp -1  1)))

(reset! d-wrap-in-x? false)
(reset! d-wrap-in-y? false)

(bind-and-do
  (= nil
    (gpn 5 5)
    (gpe 5 5)
    (gpne 5 5)))

(bind-and-do
  (= nil
    (gps -5 -5)
    (gpw -5 -5)
    (gpsw -5 -5)))

;; success!

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;                                                        ;;
;; directly translated wrap seems to be doing something   ;;
;; really strange?                                        ;;
;;                                                        ;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;;;;;;;;;;;;;;;;;;;;;;; WRAP CHECKS ;;;;;;;;;;;;;;;;;;;;;;;

(defn directly-translated-wrap [pos mn mx]
  (cond
   (>= pos mx) (-> pos (- mx) (mod (- mx mn)) (+ mn))
   (< pos mn) (let [diff (-> (- mn pos)
                             (mod (- mx mn)))] ;; ((min - pos) % (max - min))
                (if (= diff 0) mn (- mx diff)))
   :default pos))

(-> 6 (- 5) (mod (- 5 -5)) (+ -5)) ;; -4
;; equivalent to:
(directly-translated-wrap 6 -5 5) ;; -4

;; uh... what happened to xcor/ycor -5?

;; I do not like this.

;; equivalent to
(directly-translated-wrap -6 -5 5) ;; 4

;; or that.

(when (= (-> (- -5 -5) (mod (- 5 -5))) 0) -5)

;; but that math works out fine (duh)...

;; Dafuk. a -5,5 grid (10x10) is actually 11x11 in Nlogo
;;  because of course it is.
;; And if you tell netlogo to make a -4,4 grid, that
;;  grid is 9x9. So... in Netlogo, you can only have
;;  odd sidelength grids.
;; If you are at patch 0,5, for that matter, the wrapping
;;  algorithm as written wraps you to 0,-5. What dafuk
;;  happened to 0,5?

;; see?

(directly-translated-wrap 5 -5 5)

;; Aha. While _wrap(6, -5, 5) yields the wrong answer (-4),
;;  actually testing wrapX or wrapY in the engine yields
;;  the correct answer (-5). How...?
;; In the current version of Tortoise, this is solved
;;  by adding/subtracting 0.5 from min/max Px/ycor in
;;  the definitions for wrapX and wrapY. Which is
;;  confusing if you think you're going to use _wrap
;;  to like actually wrap values.
;; I think the behavior of _wrap is incorrect, b/c
;;  _wrap(6, -5, 5) is clearly wrong if it spits out -4.

(-> 5 (- 5) (mod 10) (+ -5))

(wrap -5 -5 5)  ;; -5
(wrap -6 -5 5)  ;; 5
(wrap -10 -5 5) ;; 1
(wrap 5 -5 5)   ;; 5
(wrap 6 -5 5)   ;; -5
(wrap 10 -5 5)  ;; -1
(wrap 5.5 -5.5 5.5)
(directly-translated-wrap 5.5 -5.5 5.5)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
