(ns inversespace.views
  (:require [reagent.core :as r]
            [re-frame.core :as re-frame]))

; TODO collect-new-info
;      project-list
;      general (default project)
;      add-project
;      toggle-visible
;      project-view
;        add-new-task 
;        clear-complete
;        sorting
;        add-new-task
;      
;      task-item (double-click on text to edit title)
;        complete
;        edit (detailed edit incl. add list)
;        migrate
;      task-edit
;
; TODO LATER...
;      project-reference
;        reference-item


(defn project-list []
  (let [plist @(re-frame/subscribe [:project-list])
        selected @(re-frame/subscribe [:current-project])]
      [:select 
       {:default-value selected
        :on-change (fn [evt]
                     (let [value (-> evt .-target .-value)]
                       (re-frame/dispatch [:change-project value])))}
       (for [proj plist]
         ^{:key (:id proj)} 

         [:option 
          {:value (:id proj)} 
          (:title proj)])]))

(defn todo-input [{:keys [title on-save on-stop]}]
  (let [value (r/atom title)
        stop #(on-stop)
        save #(let [v (-> @value str clojure.string/trim)] 
                (when (seq v) 
                  (on-save v))
                (stop))]
    (fn []
      [:input.todo
       {:type :text
        :auto-focus true
        :on-focus #(-> % .-target .select)
        :on-blur #(stop)
        :on-load #(println " utt")
        :value @value
        :on-change #(reset! value (-> % .-target .-value))
        :on-key-down #(case (.-which %)
                            13 (save)
                            27 (stop)
                            nil)}])))
     
(defn todo-item []
  (let [editing (r/atom false)]
    (fn [{:keys [title id parent done]}]
      [:li 
       (when (false? @editing) 
         [:span.title 
            {:on-double-click #(swap! editing not)} 
            title]
         [:div 
          [:small "done?"]
          [:input (into {:type :checkbox}
                    (if (true? done) {:checked :checked}))]])
       (when @editing 
         [todo-input 
          { :title title 
            :on-save #(do (println "title: " % " parent: " parent " id: " id)
                        (re-frame/dispatch [:save-item % parent id]))
            :on-stop #(reset! editing false)}])])))

(defn new-task-button []
  (let [lastid @(re-frame/subscribe [:last-id])]
    (println "last id: " lastid)
    [:button 
     {:on-click #(js/console.log (-> % .-target)) }
     "New Task"]))

(defn project-view []
  (let [ptasks @(re-frame/subscribe [:project-tasks])]
    [:ul
     (for [task ptasks]
       ^{:key (first task)}
       [todo-item (last task)])]))

(defn main-panel []
  (let [viewstate (r/atom :project)]
    (fn []
      [:div.main-panel
      (case @viewstate
        :project
        [:div.project
          [project-list]
          [project-view]]
          ;[new-task-button]
        )])))
