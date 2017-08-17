(ns inversespace.db
  (:require [cljs.spec.alpha :as s]
           ;[cljs.spec.gen.alpha :refer generate]
            ))

(s/def ::id int?)
(s/def ::title string?)
(s/def ::done boolean?)
(s/def ::date inst?)
(s/def ::item (s/keys :req-un [::id ::title ::done]))
(s/def ::list (s/map-of ::id ::item))
(s/def ::parent (partial s/conform ::id))
(s/def ::todo (s/keys :req-un [::id ::title ::done ::parent]
                      :opt-un [::date ::list]))
(s/def ::todos (s/map-of ::id ::todo))
(s/def ::collect (s/and 
                   (s/keys :req-un [::id ::title ::list])
                   #(= (:id %) 0)))
(s/def ::project (s/keys :req-un [::title ::todos]))
(s/def ::current-project int?) 
(s/def ::projects (s/and
                    (s/map-of ::id ::project)
                    #(= "Collect" (:title (get % 0)))))
(s/def ::collect (partial s/conform ::list))
(s/def ::showing #{:all :active :done})

(s/def ::db (s/keys :req-un [::projects ::showing]))

(def default-db
  {:projects {0 {:id 0 :title "Collect" 
                 :todos {0 {:id 0 :parent 0 :title "collect items here" :done true}
                         1 {:id 1 :parent 0 :title "delete or edit these ones" :done false}}}
              1 {:id 1 :title "General" 
                 :todos {0 {:id 0 :parent 1 :title "get stuff done" :done false}}}}
   :current-project 0
   :name "max"
   ;TODO :profile {:name "max"}
   :showing :all})

; Test the data-model by generating data...
; (clojure.pprint/pprint (generate (s/gen ::projects)))
;
; TODO Elaborate on the generator 
; https://clojure.org/guides/spec#_custom_generators
(defn new-task [title id parent]
  {:title title
   :id id
   :parent parent
   :done false})
