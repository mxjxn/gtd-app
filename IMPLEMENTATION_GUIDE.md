# Implementation Guide - Quick Wins & Foundation

This guide provides step-by-step instructions for implementing Tier 1 improvements.

## 🎯 Quick Start: 5 Immediate Improvements

### 1. Add Task Deletion (30 minutes)

**File**: `src/cljs/inversespace/events.cljs`

Add event handler:
```clojure
(reg-event-db
  :delete-task
  [check-spec-interceptor trim-v ->local-store]
  (fn [db [p-uuid t-uuid]]
    (let [p-idx (invdb/p-index-by-uuid db p-uuid)
          tasks (-> db :projects (get p-idx) :todos)]
      (->> tasks
        (filter #(not= (.toString (:uuid %)) (.toString t-uuid)))
        (vec)
        (assoc-in db [:projects p-idx :todos])))))
```

**File**: `src/cljs/inversespace/views.cljs`

Update `todo-item` component:
```clojure
(defn todo-item []
  (let [editing (r/atom false)]
    (fn [{:keys [title uuid pid done cl]}]
      [:li {:class cl}
       (when (false? @editing) 
         [:div.title 
          [:input.todo-check
           {:type :checkbox
            :on-change #(re-frame/dispatch 
                            [:item-completed 
                             (-> % .-target .-checked) pid uuid])
            :checked done}]
          [:span
           {:on-double-click #(swap! editing not)} 
           title]
          [:button.delete-btn
           {:on-click #(re-frame/dispatch [:delete-task pid uuid])}
           "×"]])
       (when @editing 
         [todo-input 
          {:title title 
           :on-save #(re-frame/dispatch [:save-item % pid uuid])
           :on-stop #(reset! editing false)}])])))
```

**File**: `resources/public/css/styles.css`

Add styling:
```css
.delete-btn {
  float: right;
  background: #ff4444;
  color: white;
  border: none;
  border-radius: 50%;
  width: 25px;
  height: 25px;
  cursor: pointer;
  font-size: 18px;
  line-height: 20px;
  padding: 0;
}

.delete-btn:hover {
  background: #cc0000;
}
```

---

### 2. Add Task Due Dates (1-2 hours)

**File**: `src/cljs/inversespace/db.cljs`

Update specs:
```clojure
(s/def ::due-date (s/nilable inst?))
(s/def ::todo (s/keys :req-un [::uuid ::title ::done ::pid]
                      :opt-un [::date ::list ::due-date]))

(defn new-task [title uid parent]
  {:title title
   :uuid uid
   :pid parent
   :done false
   :due-date nil})
```

**File**: `src/cljs/inversespace/events.cljs`

Add event for setting due date:
```clojure
(reg-event-db
  :set-due-date
  [check-spec-interceptor trim-v ->local-store]
  (fn [db [p-id t-id date-str]]
    (let [p-idx (invdb/p-index-by-uuid db p-id)
          t-idx (invdb/t-index-by-uuid db p-id t-id)
          due-date (when date-str (js/Date. date-str))]
      (assoc-in db [:projects p-idx :todos t-idx :due-date] due-date))))
```

**File**: `src/cljs/inversespace/views.cljs`

Update `todo-item` to show due date:
```clojure
[:span.task-title
 {:on-double-click #(swap! editing not)} 
 title]
(when due-date
  [:span.due-date 
   (str " 📅 " (.toLocaleDateString due-date))])
```

---

### 3. Add Task Contexts/Tags (1-2 hours)

**File**: `src/cljs/inversespace/db.cljs`

```clojure
(s/def ::context (s/nilable string?))
(s/def ::todo (s/keys :req-un [::uuid ::title ::done ::pid]
                      :opt-un [::date ::list ::due-date ::context]))

(defn new-task [title uid parent]
  {:title title
   :uuid uid
   :pid parent
   :done false
   :due-date nil
   :context nil})
```

Add predefined contexts:
```clojure
(def contexts
  ["@home" "@work" "@computer" "@phone" "@errands" "@anywhere"])
```

**File**: `src/cljs/inversespace/views.cljs`

Add context selector to task creation:
```clojure
(defn new-task-panel [{:keys [viewstate]}]
  (let [plist @(re-frame/subscribe [:project-list])
        p-uuid @(re-frame/subscribe [:current-project])
        title (r/atom "new-todo")
        context (r/atom nil)]
    (fn []
    [:div 
      [project-list]
      [:h3 "Title"]
      [:input {:id "new-item-title" :type :text
               :value @title
               :auto-focus true
               :on-change #(reset! title (-> % .-target .-value))
               :on-focus #(-> % .-target .select)}]
      [:h3 "Context"]
      [:select {:value (or @context "")
                :on-change #(reset! context (-> % .-target .-value))}
       [:option {:value ""} "No context"]
       (for [ctx invdb/contexts]
         ^{:key ctx} [:option {:value ctx} ctx])]
      [button {:label "cancel"
              :on-click #(reset! viewstate :project)}]
      [button {:label "create"
              :on-click #(do (re-frame/dispatch [:new-item @title p-uuid @context]) 
                             (reset! viewstate :project))}] ])))
```

---

### 4. Add Keyboard Shortcuts (30 minutes)

**File**: `src/cljs/inversespace/views.cljs`

Add global key listener:
```clojure
(defn keyboard-shortcuts []
  (let [p-uuid @(re-frame/subscribe [:current-project])]
    [:div
     {:tab-index 0
      :on-key-down (fn [e]
                     (case (.-key e)
                       "n" (when (.-ctrlKey e)
                             (.preventDefault e)
                             (re-frame/dispatch [:show-new-task]))
                       "c" (when (.-ctrlKey e)
                             (.preventDefault e)
                             (re-frame/dispatch [:clear-completed p-uuid]))
                       nil))}]))

(defn main-panel []
  (let [viewstate (r/atom :project)]
    (fn []
      [:div.main-panel.col-sm-6.col-sm-offset-3
       [keyboard-shortcuts]
       (case @viewstate
         :project
           [project-view 
            {:viewstate viewstate}]
         :new-task 
           [new-task-panel {:viewstate viewstate}]
         )])))
```

---

### 5. Add Project Creation (1 hour)

**File**: `src/cljs/inversespace/events.cljs`

```clojure
(reg-event-db
  :new-project
  [check-spec-interceptor trim-v ->local-store]
  (fn [db [title]]
    (let [new-proj {:uuid (random-uuid)
                    :title title
                    :todos []}]
      (update db :projects conj new-proj))))
```

**File**: `src/cljs/inversespace/views.cljs`

Add project creation panel:
```clojure
(defn new-project-panel [{:keys [viewstate]}]
  (let [title (r/atom "New Project")]
    (fn []
      [:div
       [:h3 "Project Title"]
       [:input {:type :text
                :value @title
                :auto-focus true
                :on-change #(reset! title (-> % .-target .-value))
                :on-focus #(-> % .-target .select)}]
       [button {:label "Cancel"
                :on-click #(reset! viewstate :project)}]
       [button {:label "Create Project"
                :on-click #(do 
                             (re-frame/dispatch [:new-project @title])
                             (reset! viewstate :project))}]])))

(defn project-view [{:keys [viewstate]}]
  (let [p-uuid (re-frame/subscribe [:current-project])]
    (fn []
      [:div.project
        [button {:label "new task"
                 :on-click #(reset! viewstate :new-task)}]
        [button {:label "new project"
                 :on-click #(reset! viewstate :new-project)}]
        [project-list]
        [project-todo-list]
        [button {:label "clear completed"
                 :on-click #(re-frame/dispatch [:clear-completed @p-uuid])}]])))

(defn main-panel []
  (let [viewstate (r/atom :project)]
    (fn []
      [:div.main-panel.col-sm-6.col-sm-offset-3
       (case @viewstate
         :project [project-view {:viewstate viewstate}]
         :new-task [new-task-panel {:viewstate viewstate}]
         :new-project [new-project-panel {:viewstate viewstate}]
         )])))
```

---

## 🧪 Testing Setup (2-3 hours)

### Step 1: Add test dependencies

**File**: `project.clj`

Update dependencies:
```clojure
:dependencies [[org.clojure/clojure "1.11.1"]
               [org.clojure/test.check "1.1.1"]
               [org.clojure/clojurescript "1.11.60"]
               [reagent "1.2.0"]
               [re-frame "1.3.0"]]

:profiles
{:dev
 {:dependencies [[binaryage/devtools "1.0.7"]
                 [day8.re-frame/test "0.1.5"]]
  :plugins [[lein-figwheel "0.5.20"]
            [lein-doo "0.1.11"]]}}
```

### Step 2: Create test directory

```bash
mkdir -p test/cljs/inversespace
```

### Step 3: Add db tests

**File**: `test/cljs/inversespace/db_test.cljs`

```clojure
(ns inversespace.db-test
  (:require [cljs.test :refer-macros [deftest is testing]]
            [inversespace.db :as db]
            [cljs.spec.alpha :as s]))

(deftest db-spec-test
  (testing "default-db conforms to spec"
    (is (s/valid? ::db/db db/default-db))))

(deftest p-index-by-uuid-test
  (testing "finds correct project index"
    (let [test-db db/default-db
          collect-uuid (-> test-db :projects first :uuid)]
      (is (= 0 (db/p-index-by-uuid test-db collect-uuid))))))

(deftest new-task-test
  (testing "creates valid task"
    (let [task (db/new-task "Test Task" (random-uuid) (random-uuid))]
      (is (s/valid? ::db/todo task))
      (is (= "Test Task" (:title task)))
      (is (false? (:done task))))))
```

### Step 4: Add event tests

**File**: `test/cljs/inversespace/events_test.cljs`

```clojure
(ns inversespace.events-test
  (:require [cljs.test :refer-macros [deftest is testing use-fixtures]]
            [re-frame.core :as re-frame]
            [day8.re-frame.test :as rf-test]
            [inversespace.events]
            [inversespace.db :as db]))

(deftest new-item-test
  (rf-test/run-test-sync
    (re-frame/dispatch [:initialize-db])
    (let [initial-db @(re-frame/subscribe [:db])
          p-uuid (:current-project initial-db)]
      (re-frame/dispatch [:new-item "Test Task" p-uuid])
      (let [updated-db @(re-frame/subscribe [:db])
            p-idx (db/p-index-by-uuid updated-db p-uuid)
            tasks (-> updated-db :projects (get p-idx) :todos)]
        (is (some #(= "Test Task" (:title %)) tasks))))))
```

### Step 5: Run tests

Add to `project.clj`:
```clojure
:doo {:build "test"
      :alias {:default [:chrome-headless]}}

:cljsbuild
{:builds
 [{:id "test"
   :source-paths ["src/cljs" "test/cljs"]
   :compiler {:output-to "target/test.js"
              :main inversespace.test-runner
              :optimizations :none}}]}
```

Run with:
```bash
lein doo chrome-headless test once
```

---

## 📝 Documentation Updates

### Update README.md

Add sections:
- Screenshots
- Feature list
- Installation instructions
- Development setup
- Testing
- Contributing guidelines
- License

### Create ARCHITECTURE.md

Document:
- State shape
- Event flow
- Subscription pattern
- re-frame concepts
- Code organization

---

## 🎨 CSS Improvements

**File**: `resources/public/css/styles.css`

```css
/* Variables for consistency */
:root {
  --primary-color: #4CAF50;
  --danger-color: #ff4444;
  --text-color: #333;
  --border-color: #ddd;
  --bg-light: #f9f9f9;
  --bg-dark: #e9e9e9;
}

body {
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, sans-serif;
  color: var(--text-color);
  line-height: 1.6;
}

.main-panel {
  padding: 20px;
  max-width: 800px;
  margin: 0 auto;
}

/* Project view */
.project {
  background: white;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

/* Buttons */
button {
  background: var(--primary-color);
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 4px;
  cursor: pointer;
  font-size: 14px;
  margin: 5px;
  transition: background 0.2s;
}

button:hover {
  background: #45a049;
}

/* Task list */
ul.project-list {
  position: relative;
  padding-left: 0;
  list-style-type: none;
  margin-top: 20px;
}

ul.project-list li {
  padding: 15px;
  margin: 5px 0;
  border: 1px solid var(--border-color);
  border-radius: 4px;
  background: white;
  transition: all 0.2s;
}

ul.project-list li:hover {
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

ul.project-list li.even { background: var(--bg-light); }
ul.project-list li.odd { background: white; }

/* Task checkbox */
ul.project-list li input[type=checkbox] {
  width: 20px;
  height: 20px;
  margin-right: 10px;
  cursor: pointer;
}

/* Task title */
ul.project-list li span.task-title {
  font-size: 16px;
  cursor: pointer;
}

ul.project-list li span.due-date {
  font-size: 12px;
  color: #666;
  margin-left: 10px;
}

/* Input fields */
input[type=text], select {
  width: 100%;
  padding: 10px;
  margin: 10px 0;
  border: 1px solid var(--border-color);
  border-radius: 4px;
  font-size: 14px;
}

input[type=text]:focus, select:focus {
  outline: none;
  border-color: var(--primary-color);
  box-shadow: 0 0 0 2px rgba(76, 175, 80, 0.2);
}

/* Project dropdown */
select {
  appearance: none;
  background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='12' height='12' viewBox='0 0 12 12'%3E%3Cpath fill='%23333' d='M6 9L1 4h10z'/%3E%3C/svg%3E");
  background-repeat: no-repeat;
  background-position: right 10px center;
  padding-right: 30px;
}

/* Responsive */
@media (max-width: 768px) {
  .main-panel {
    padding: 10px;
  }
  
  .col-sm-6 {
    width: 100%;
  }
  
  .col-sm-offset-3 {
    margin-left: 0;
  }
}
```

---

## 🚀 Deployment Improvements

### Add GitHub Actions CI/CD

**File**: `.github/workflows/ci.yml`

```yaml
name: CI

on:
  push:
    branches: [ main ]
  pull_request:
    branches: [ main ]

jobs:
  test:
    runs-on: ubuntu-latest
    
    steps:
    - uses: actions/checkout@v3
    
    - name: Install Java
      uses: actions/setup-java@v3
      with:
        distribution: 'temurin'
        java-version: '11'
    
    - name: Install Leiningen
      run: |
        wget https://raw.githubusercontent.com/technomancy/leiningen/stable/bin/lein
        chmod +x lein
        sudo mv lein /usr/local/bin/
    
    - name: Run tests
      run: lein test
    
    - name: Build
      run: lein cljsbuild once min
```

---

## 📦 Package.json for Modern Tooling

Consider migrating to shadow-cljs for better developer experience:

**File**: `package.json`

```json
{
  "name": "gtd-app",
  "version": "0.1.0",
  "description": "GTD Todo Application",
  "scripts": {
    "dev": "shadow-cljs watch app",
    "build": "shadow-cljs release app",
    "test": "shadow-cljs compile test && node target/test.js"
  },
  "dependencies": {
    "react": "^18.2.0",
    "react-dom": "^18.2.0"
  },
  "devDependencies": {
    "shadow-cljs": "^2.20.0"
  }
}
```

---

## 🎯 Priority Order

1. ✅ Task deletion (immediate user need)
2. ✅ Better CSS (improves perception)
3. ✅ Task contexts (core GTD)
4. ✅ Testing setup (enables future work)
5. ✅ Documentation (onboarding)
6. ✅ Due dates (common request)
7. ✅ Project creation (flexibility)
8. ✅ Keyboard shortcuts (power users)

---

## 💡 Pro Tips

1. **Incremental Development**: Implement one feature at a time, test, commit
2. **REPL-Driven**: Use Figwheel's REPL for interactive development
3. **Spec Everything**: Use cljs.spec for all data structures
4. **Test First**: Write tests before implementation when possible
5. **Mobile First**: Test on mobile devices early and often
6. **Performance**: Use React DevTools to identify render bottlenecks
7. **Accessibility**: Use WAVE or axe DevTools to check accessibility

---

## 📚 Resources

- [re-frame Documentation](https://day8.github.io/re-frame/)
- [ClojureScript Guide](https://clojurescript.org/guides/quick-start)
- [GTD Methodology](https://gettingthingsdone.com/)
- [Reagent Documentation](https://reagent-project.github.io/)
- [cljs.spec Guide](https://clojure.org/guides/spec)

---

This guide should get you from the current state to a solid, testable foundation in 1-2 weeks of focused work!
