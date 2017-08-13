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
  :save-item
  [check-spec-interceptor trim-v]
  (fn [db [title project-id task-id]]
    ;(println "db: ")
    ;(cljs.pprint/pprint db)
    ;(println "new db: ")
    (cljs.pprint/pprint (assoc-in db [:projects project-id :todos task-id :title] title))
    (assoc-in db [:projects project-id :todos task-id :title] title)))

(reg-event-db
 :initialize-db
 (fn  [_ _]
   invdb/default-db))
