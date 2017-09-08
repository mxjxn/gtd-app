(ns inversespace.subs
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :as re-frame]
            [inversespace.db :as invdb]))

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
  (fn [db _]
    (let [{projects :projects
           current :current-project} db
          idx (invdb/p-index-by-uuid db current)]
      (:todos (into {} (get projects idx))))))

(re-frame/reg-sub
  :project-list
  :<- [:projects]
  (fn [projects _]
   (map-indexed 
     #(let [index %1
            {:keys [title uuid]} %2]
        (hash-map 
          :index index
          :uuid uuid
          :title title))
     projects)))

(re-frame/reg-sub
  :name
  (fn [db _]
    (:name db)))
