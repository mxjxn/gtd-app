(ns inversespace.events
  (:require [re-frame.core :refer [reg-event-db after trim-v]]
            [inversespace.db :as invdb]
            [cljs.spec.alpha :as s]))

(defn check-and-throw [spec db]
  (when-not (s/valid? spec db)
    (throw (ex-info (str "spec check failed:" (s/explain-str spec db)) {}))))

(def check-spec-interceptor (after (partial check-and-throw :inversespace.db/db)))

(reg-event-db
  :change-project
  [check-spec-interceptor trim-v]
  (fn [db [project-num]]
    (assoc db :current-project project-num)))

(reg-event-db
  :new-item
  [check-spec-interceptor trim-v]
  (fn [db [title project-id]]
    (let [tasks (-> db :projects (get project-id) :todos)
          new-id (inc
                   (reduce #(if (> %1 %2) %1 %2)
                    (map #(-> % last :id) tasks)))] 
      (println "new id: " new-id)
      (assoc-in db 
                [:projects project-id :todos new-id] 
                (invdb/new-task title new-id project-id)))))
       

(reg-event-db
  :item-completed
  [check-spec-interceptor trim-v]
  (fn [db [checked project-id task-id]]
    (assoc-in db [:projects project-id :todos task-id :done] checked)))

(reg-event-db
  :save-item
  [check-spec-interceptor trim-v]
  (fn [db [title project-id task-id]]
    (println "saving..."
      (assoc-in db [:projects project-id :todos task-id :title] title))
    (assoc-in db [:projects project-id :todos task-id :title] title)))

(reg-event-db
 :initialize-db
 (fn  [_ _]
   invdb/default-db))
