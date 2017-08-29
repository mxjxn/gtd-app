(ns inversespace.subs
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :as re-frame]
            [inversespace.db :as inv-db]))

(re-frame/reg-sub
  :projects
  (fn [db _]
    (:projects db)))

(re-frame/reg-sub
  :current-project
  (fn [db _]
    (:current-project db)))

(re-frame/reg-sub
  :project-tasks
  :<- [:projects]
  :<- [:current-project]
  (fn [[projects current] _]
    ;(cljs.pprint/pprint (get projects (int current)))
    (:todos (into {} (get projects (int current))))))

(re-frame/reg-sub
  :project-list
  :<- [:projects]
  (fn [projects _]
   (map-indexed 
     #(let [index %1
            {title :title} %2]
        (hash-map 
          :index index
          :title title))
     projects)))

(re-frame/reg-sub
  :name
  (fn [db _]
    (:name db)))
