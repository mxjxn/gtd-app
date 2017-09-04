(ns inversespace.events
  (:require [re-frame.core :refer [reg-event-db reg-event-fx after trim-v inject-cofx reg-fx]]
            [inversespace.db :as invdb]
            [cljs.spec.alpha :as s]))

(defn check-and-throw [spec db]
  (when-not (s/valid? spec db)
    (throw (ex-info (str "spec check failed:" (s/explain-str spec db)) {}))))

(def check-spec-interceptor (after (partial check-and-throw :inversespace.db/db)))

(def ls-key "gtd-test") 

(def ->local-store (after invdb/todos->local-store))

(defn deep-map-merge [& maps] 
  (if (every? map? maps) 
    (apply merge-with deep-map-merge maps) 
    (last maps)))

(reg-event-fx
 :initialize-db
  [(inject-cofx :local-storage)
   check-spec-interceptor]
 (fn [{:keys [db local-storage]} _]
   {:db invdb/default-db}))


(reg-event-db
  :change-project
  [check-spec-interceptor trim-v]
  (fn [db [project-num]]
    (assoc db :current-project project-num)))

(reg-event-db
  :new-item
  [check-spec-interceptor trim-v ->local-store]
  (fn [db [title project-id]]
    (let [tasks (-> db 
                    :projects 
                    (get project-id) 
                    :todos)
          new-task (invdb/new-task title (random-uuid) project-id) ]
      (assoc-in 
        db 
        [:projects project-id :todos] 
        (conj tasks new-task))))) 

(reg-event-db
  :item-completed
  [check-spec-interceptor trim-v ->local-store]
  (fn [db [checked project-id task-id]]
    (assoc-in db [:projects project-id :todos task-id :done] checked)))

(reg-event-db
  :clear-completed
  [check-spec-interceptor trim-v ->local-store]
  (fn [db [project-id]]
    (let [id (int project-id)
          tasks (-> db :projects (get id) :todos)]
      (->> tasks
        (filter #(false? (:done %)))
        (vec)
        (assoc-in db [:projects id :todos])))))

(reg-event-db
  :save-item
  [check-spec-interceptor trim-v ->local-store]
  (fn [db [title p-id t-id]]
    (let [p-idx (invdb/p-index-by-uuid db p-id)
          t-idx (invdb/t-index-by-uuid db p-id t-id)]
    (assoc-in db [:projects p-idx :todos t-idx :title] title))))
