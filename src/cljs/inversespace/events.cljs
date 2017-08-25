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

(defn deep-merge
  [& maps]
  (if (every? map? maps)
    (apply merge-with deep-merge maps)
    (last maps)))

(reg-event-fx
 :initialize-db
  [(inject-cofx :local-storage)
   check-spec-interceptor]
 (fn [{:keys [db local-storage]} _]
   (js/console.log local-storage)
   (deep-merge {:db invdb/default-db} {:db local-storage})))


(reg-event-db
  :change-project
  [check-spec-interceptor trim-v]
  (fn [db [project-num]]
    (assoc db :current-project project-num)))

(reg-event-db
  :new-item
  [check-spec-interceptor trim-v ->local-store]
  (fn [db [title project-id]]
    (let [tasks (-> db :projects (get project-id) :todos)
          new-id (inc
                   (reduce #(if (> %1 %2) %1 %2)
                    (map #(-> % last :index) tasks)))
          new-id (if (int? new-id) new-id 0)] 
      (assoc-in db 
                [:projects project-id :todos new-id] 
                (invdb/new-task title new-id project-id)))))
       

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
           
        (filter #(:done (last %)))
        (vec)
        (map #(first %))
        (apply dissoc tasks)
        (into {})
        (assoc-in db [:projects id :todos])
        ; ALTERNATE SOLUTION? ... RE-INDEXING IS FLAWED
        ;(filter #(false? (:done (last %))))
        ;(map-indexed (fn [idx data] [idx (last data)])) 
        ;(into {})
        ;(assoc-in db [:projects id :todos])
        ; OR...  Better to use update-in somehow?
           ))))
    
(reg-event-db
  :save-item
  [check-spec-interceptor trim-v ->local-store]
  (fn [db [title project-id task-id]]
    (println "saving..."
      (assoc-in db [:projects project-id :todos task-id :title] title))
    (assoc-in db [:projects project-id :todos task-id :title] title)))
