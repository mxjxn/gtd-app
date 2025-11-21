# GTD App - Quick Reference Card

## 🎯 At a Glance

**What it is**: ClojureScript-based Getting Things Done (GTD) todo app
**Status**: Early prototype, functional but needs enhancement
**Tech**: re-frame + Reagent + ClojureScript + Bootstrap

---

## 📊 Current State Summary

| Category | Status | Next Steps |
|----------|--------|------------|
| **Core Functionality** | ✅ Basic tasks & projects | Add GTD features (contexts, due dates) |
| **Dependencies** | ⚠️ 2017 versions | Update to latest (ClojureScript 1.11+) |
| **Testing** | ❌ None | Add cljs.test suite |
| **Mobile** | ❌ Not responsive | Make mobile-friendly |
| **Cloud Sync** | ❌ LocalStorage only | Build backend API |
| **Documentation** | ✅ Now comprehensive | Keep updated |

---

## 🚀 Top 5 Immediate Improvements

### 1. Task Deletion (30 min)
Add delete button to each task
**Impact**: High | **Effort**: Low

### 2. Update Dependencies (1-2 hours)
Modernize ClojureScript, re-frame, Reagent
**Impact**: High (security, features) | **Effort**: Low-Medium

### 3. Add Testing (2-3 hours)
Set up cljs.test with basic coverage
**Impact**: High (confidence) | **Effort**: Medium

### 4. Task Contexts/Tags (1-2 hours)
Add @home, @work, @errands contexts
**Impact**: High (core GTD) | **Effort**: Low-Medium

### 5. Better CSS (1-2 hours)
Modern, responsive styling
**Impact**: Medium (perception) | **Effort**: Low

---

## 📋 File Structure

```
gtd-app/
├── src/
│   └── cljs/inversespace/
│       ├── core.cljs       # App initialization
│       ├── db.cljs          # State schema & specs
│       ├── events.cljs      # State mutations
│       ├── subs.cljs        # Data queries
│       ├── views.cljs       # UI components
│       ├── config.cljs      # Configuration
│       └── effects.cljs     # Side effects (empty)
├── resources/public/
│   ├── index.html           # Entry point
│   └── css/
│       └── styles.css       # Custom styles
├── project.clj              # Leiningen config
└── README.md

NEW DOCS:
├── APP_ANALYSIS.md          # Comprehensive analysis
├── IMPLEMENTATION_GUIDE.md  # How-to guide
└── PROJECT_IDEAS.md         # Skill-based projects
```

---

## 🔑 Key Files to Understand

### 1. `db.cljs` - State Shape
Defines data structure with cljs.spec:
- `::projects` - Vector of project maps
- `::todo` - Individual task spec
- `default-db` - Initial state

### 2. `events.cljs` - State Changes
All mutations happen here:
- `:new-item` - Create task
- `:item-completed` - Toggle done
- `:clear-completed` - Remove done tasks
- `:save-item` - Edit task title

### 3. `subs.cljs` - Data Queries
Read from state:
- `:project-list` - All projects
- `:project-tasks` - Current project tasks
- `:current-project` - Active project UUID

### 4. `views.cljs` - UI Components
React components:
- `main-panel` - Root component
- `project-view` - Main view
- `todo-item` - Individual task
- `new-task-panel` - Create form

---

## 🎓 re-frame Pattern

```
User Action
    ↓
[View] dispatches event
    ↓
[Event Handler] updates db
    ↓
[Subscription] derives data
    ↓
[View] re-renders automatically
```

**Key Concept**: Unidirectional data flow
**State Location**: Single `app-db` atom
**Time Travel**: re-frame DevTools available

---

## 💻 Development Commands

```bash
# Start dev server with hot-reload
lein figwheel dev

# Build production
lein cljsbuild once min

# Clean build artifacts
lein clean

# Run tests (after setup)
lein test

# REPL
lein figwheel dev
# Then in browser console: cljs.user
```

---

## 🐛 Known Issues

1. **LocalStorage key hardcoded**: "gtd-test" should be configurable
2. **No error handling**: Needs error boundaries
3. **Index-based UUIDs**: Recently migrated but may have edge cases
4. **No validation feedback**: Silent failures
5. **Mobile unfriendly**: Not responsive
6. **No task migration**: Can't move tasks between projects

---

## 📈 Roadmap Tiers

### Tier 1: Foundation (1-2 weeks)
→ Update deps, add tests, polish UI

### Tier 2: Core GTD (2-4 weeks)
→ Contexts, due dates, GTD views, search

### Tier 3: Modern (4-8 weeks)
→ Cloud sync, mobile, collaboration

### Tier 4: Advanced (8+ weeks)
→ AI, analytics, automation

---

## 🎯 Best Starting Projects

**If you want to learn re-frame:**
→ B1: Task Counter Badge (easy, touches all layers)

**If you want immediate impact:**
→ I1: Task Search (useful, demonstrates subscriptions)

**If you're a designer:**
→ B5: Dark Mode (fun, CSS-focused)

**If you're full-stack:**
→ A1: Real-time Sync (challenging, complete system)

See PROJECT_IDEAS.md for 20+ more options!

---

## 🔧 Tech Stack Details

| Component | Current | Latest | Notes |
|-----------|---------|--------|-------|
| ClojureScript | 1.9.542 | 1.11.60+ | Major update needed |
| re-frame | 0.9.4 | 1.3.0 | Breaking changes |
| Reagent | 0.6.0 | 1.2.0 | Update needed |
| Figwheel | 0.5.9 | 0.5.20 | Or migrate to shadow-cljs |
| Bootstrap | 3.3.7 | 5.3.0 | Consider Tailwind |

---

## 💡 Pro Tips

1. **Use Figwheel REPL**: Live coding is ClojureScript's superpower
2. **Learn re-frame docs**: 6 dominoes pattern is essential
3. **Spec everything**: catches bugs before runtime
4. **Small commits**: Easier to review and revert
5. **Mobile-first**: Test on phones early
6. **Profile early**: React DevTools shows bottlenecks

---

## 📚 Essential Reading

1. **re-frame docs**: https://day8.github.io/re-frame/
2. **Getting Things Done**: Book by David Allen
3. **ClojureScript**: https://clojurescript.org/guides/quick-start
4. **Reagent**: https://reagent-project.github.io/

---

## 🤝 Contributing

1. Read APP_ANALYSIS.md for context
2. Pick a project from PROJECT_IDEAS.md
3. Follow patterns in IMPLEMENTATION_GUIDE.md
4. Open an issue before big changes
5. Include tests with PRs
6. Have fun! 🎉

---

## 🎁 What Makes This Special

✅ **Open Source GTD**: Most GTD apps are proprietary
✅ **ClojureScript**: Functional, immutable, powerful
✅ **re-frame**: Best-in-class state management
✅ **Learning Friendly**: Well-documented, clear patterns
✅ **Self-Hostable**: Own your data
✅ **Active Development**: Growing feature set

---

**Quick Start**: `lein figwheel dev` → http://localhost:3449 → Start coding!

For detailed analysis, see [APP_ANALYSIS.md](./APP_ANALYSIS.md)
