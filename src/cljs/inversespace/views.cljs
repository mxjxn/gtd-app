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
         ^{:key (:index proj)} 
         [:option {:value (:index proj)} (:title proj)])]))

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
        :value @value
        :on-change #(reset! value (-> % .-target .-value))
        :on-key-down #(case (.-which %)
                            13 (save)
                            27 (stop)
                            nil)}])))
     
(defn todo-item []
  (let [editing (r/atom false)]
    (fn [{:keys [title uuid pid done cl]}]
      [:li {:class cl}
       (when (false? @editing) 
         [:div.title 
          [:input.todo-check
           {:type :checkbox
            :on-change #(do (println "check! " pid uuid)
                          (re-frame/dispatch 
                            [:item-completed 
                             (-> % .-target .-checked) pid uuid]))
            :checked done}]
          [:span
           {:on-double-click #(swap! editing not)} 
           title]])
       (when @editing 
         [todo-input 
          {:title title 
           :on-save #(re-frame/dispatch [:save-item % pid uuid])
           :on-stop #(reset! editing false)}])])))

(defn button [{:keys [on-click label]}]
  [:button 
    {:on-click #(on-click)}
    label])

(defn new-task-panel [{:keys [viewstate]}]
  (let [plist @(re-frame/subscribe [:project-list])
        cur-proj (int @(re-frame/subscribe [:current-project]))
        title (r/atom "new-todo")]
    (fn []
    [:div 
      ;project
      [project-list]
      ;title
      [:h3 "Title"]
      [:input {:id "new-item-title" :type :text
               :value @title
               :auto-focus true
               :on-change #(reset! title (-> % .-target .-value))
               :on-focus #(-> % .-target .select)}]
      ;due-date?
      ;cancel-button
      [button {:label "cancel"
              :on-click #(reset! viewstate :project)}]
      ;create-button
      [button {:label "create"
              :on-click #(do (re-frame/dispatch [:new-item @title cur-proj]) 
                             (reset! viewstate :project))}] ])))

(defn project-todo-list []
  (let [ptasks (re-frame/subscribe [:project-tasks])]
    (fn []
      [:ul.project-list
       (let [t-items (map-indexed
                       (fn [i t]
                           (merge t {:cl (if (even? i) "even" "odd") 
                                     :uuid (:uuid t) 
                                     :index i}))
                       @ptasks)]
          (for [task t-items]
            ^{:key (:index task)}
            [todo-item task]))])))

(defn project-view [{:keys [viewstate]}]
  (let [project-index (re-frame/subscribe [:current-project])]
    (fn []
      [:div.project
        [button {:label "new task"
                 :on-click #(reset! viewstate :new-task)}]
        [project-list]
        [project-todo-list]
        [button {:label "clear completed"
                 :on-click #(re-frame/dispatch [:clear-completed @project-index])}]])))

(defn main-panel []
  (let [viewstate (r/atom :project)]
    (fn []
      [:div.main-panel.col-sm-6.col-sm-offset-3
      (case @viewstate
        :project
          [project-view 
           {:viewstate viewstate}]
        :new-task 
          [new-task-panel {:viewstate viewstate}]
        )])))

