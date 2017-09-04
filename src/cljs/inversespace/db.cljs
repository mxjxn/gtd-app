(ns inversespace.db
  (:require [cljs.spec.alpha :as s]
            [cljs.reader]
            [re-frame.core :refer [reg-cofx]]
           ;[cljs.spec.gen.alpha :refer generate]
            
            ))

(s/def ::uuid uuid?)
(s/def ::pid (partial s/conform ::uuid))
(s/def ::index int?)
(s/def ::title string?)
(s/def ::done boolean?)
(s/def ::date inst?)
(s/def ::item (s/keys :req-un [::uuid ::title ::done]))
(s/def ::list (s/coll-of ::item :kind vector?))
(s/def ::todo (s/keys :req-un [::uuid ::title ::done ::pid]
                      :opt-un [::date ::list]))
(s/def ::todos (s/coll-of ::todo :kind vector?))
(s/def ::collect (s/and 
                   (s/keys :req-un [::index ::title ::list])
                   #(= (:index %) 0)))
(s/def ::project (s/keys :req-un [::uuid ::title ::todos]))
(s/def ::current-project int?) 
(s/def ::projects (s/and
                    (s/coll-of ::project :kind vector?)
                    #(= "Collect" (:title (get % 0)))))
(s/def ::collect (partial s/conform ::list))
(s/def ::showing #{:all :active :done})

(s/def ::db (s/keys :req-un [::projects ::showing]))

(def default-db
  (let [uu1 (random-uuid)
        uu2 (random-uuid)]
  {:projects [{:uuid uu1 :title "Collect" 
                 :todos [{:uuid (random-uuid) :title "collect items here" :done true :pid uu1}
                         {:uuid (random-uuid) :title "delete or edit these ones" :done false :pid uu1}]}
              {:uuid uu2 :title "General" 
                 :todos [{:uuid (random-uuid) :title "get stuff done" :done false :pid uu2}]}]
   :current-project 0
   :name "max"
   ;TODO :profile {:name "max"}
   :showing :all}))

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
      (into {} 
        (some->> 
          (.getItem js/localStorage ls-key)
          (cljs.reader/read-string))))))
  
(defn- get-index-by-uuid [uu-vec uid]
  (loop [uvec uu-vec i 0]
      (if (empty? uvec) 
        -1
        (if (= (.toString uid) (.toString (:uuid (first uvec))))
          i
          (recur (rest uvec) (inc i))))))
  

(defn p-index-by-uuid [a-db uid]
  (get-index-by-uuid (:projects a-db) uid))

(defn t-index-by-uuid [a-db pid uid]
  (let [p-idx (p-index-by-uuid a-db pid)
        tasks (-> a-db :projects (get p-idx) :todos)]
    (get-index-by-uuid tasks uid)))

(defn new-task [title uid parent]
  {:title title
   :uuid uid
   :pid parent
   :done false})

