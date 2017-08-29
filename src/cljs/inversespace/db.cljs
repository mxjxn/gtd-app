(ns inversespace.db
  (:require [cljs.spec.alpha :as s]
            [cljs.reader]
            [re-frame.core :refer [reg-cofx]]
           ;[cljs.spec.gen.alpha :refer generate]
            ))

(s/def ::index int?)
(s/def ::title string?)
(s/def ::done boolean?)
(s/def ::date inst?)
(s/def ::item (s/keys :req-un [::index ::title ::done]))
(s/def ::list (s/map-of ::index ::item))
(s/def ::parent (partial s/conform ::index))
(s/def ::todo (s/keys :req-un [::index ::title ::done ::parent]
                      :opt-un [::date ::list]))
(s/def ::todos (s/map-of ::index ::todo))
(s/def ::collect (s/and 
                   (s/keys :req-un [::index ::title ::list])
                   #(= (:index %) 0)))
(s/def ::project (s/keys :req-un [::title ::todos]))
(s/def ::current-project int?) 
(s/def ::projects (s/and
                    (s/map-of ::index ::project)
                    #(= "Collect" (:title (get % 0)))))
(s/def ::collect (partial s/conform ::list))
(s/def ::showing #{:all :active :done})

(s/def ::db (s/keys :req-un [::projects ::showing]))

(def default-db
  {:projects {0 {:index 0 :title "Collect" 
                 :todos {0 {:index 0 :parent 0 :title "collect items here" :done true}
                         1 {:index 1 :parent 0 :title "delete or edit these ones" :done false}}}
              1 {:index 1 :title "General" 
                 :todos {0 {:index 0 :parent 1 :title "get stuff done" :done false}}}}
   :current-project 0
   :name "max"
   ;TODO :profile {:name "max"}
   :showing :all})

; Test the data-model by generating data...
; (clojure.pprint/pprint (generate (s/gen ::projects)))
;
; TODO Elaborate on the generator 
; https://clojure.org/guindexes/spec#_custom_generators

(def ls-key "gtd-test")

(defn todos->local-store [todos]
  (.setItem js/localStorage ls-key (str todos)))

(reg-cofx
  :local-storage 
  (fn [cofx _] 
    (assoc cofx :local-storage
           (into (sorted-map)
           (some->> (.getItem js/localStorage ls-key)
                    (cljs.reader/read-string)
                    )))))
  
(defn new-task [title index parent]
  {:title title
   :index index
   :parent parent
   :done false})
