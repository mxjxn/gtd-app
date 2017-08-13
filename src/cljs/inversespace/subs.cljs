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
    (:todos (into {} (get projects (int current))))))

(re-frame/reg-sub
  :project-list
  :<- [:projects]
  (fn [projects _]
   (map 
     #(hash-map 
        :id (first %) 
        :title (get (last %) :title)) 
     projects)))

(re-frame/reg-sub
  :name
  (fn [db _]
    (:name db)))

(re-frame/reg-sub
  :last-id
  :<- [:project-tasks]
  (fn [tasks]
    (reduce #(if (> %1 %2) %1 %2)
      (map #(-> % last :id) tasks))))
