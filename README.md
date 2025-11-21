# GTD-Todo

A [re-frame](https://github.com/Day8/re-frame) todo application implementing the workflow from David Allen's [Getting Things Done](http://gettingthingsdone.com/) methodology.

## 📋 Current Status

This is an early-stage functional prototype with basic task and project management. See [APP_ANALYSIS.md](./APP_ANALYSIS.md) for a comprehensive state assessment and improvement roadmap.

**Current Features:**
- ✅ Multiple projects with switching
- ✅ Create, edit, complete, and clear tasks
- ✅ LocalStorage persistence
- ✅ Basic GTD structure (Collect and General projects)

## 🚀 Getting Started

### Prerequisites
- [Leiningen](https://leiningen.org/) (Clojure build tool)
- Java JDK 8+

### Development Mode

Run the application:

```bash
lein clean
lein figwheel dev
```

Figwheel will automatically push ClojureScript changes to the browser with hot-reloading.

Wait a bit, then browse to [http://localhost:3449](http://localhost:3449).

### Production Build

To compile ClojureScript to optimized JavaScript:

```bash
lein clean
lein cljsbuild once min
```

## 📚 Documentation

- **[APP_ANALYSIS.md](./APP_ANALYSIS.md)** - Comprehensive state assessment with tiered improvement roadmap (Basic → Advanced → Amazing)
- **[IMPLEMENTATION_GUIDE.md](./IMPLEMENTATION_GUIDE.md)** - Step-by-step guide for implementing quick wins and foundational improvements
- **[PROJECT_IDEAS.md](./PROJECT_IDEAS.md)** - Curated project ideas organized by skill level (Beginner → Intermediate → Advanced → Expert)

## 🎯 Improvement Roadmap

We've identified improvements across 4 tiers:

### Tier 1: Foundation (1-2 weeks)
- Update dependencies, add tests, polish UI, improve documentation

### Tier 2: Core GTD (2-4 weeks)  
- Add contexts, due dates, GTD workflow views, search & filter

### Tier 3: Modern Features (4-8 weeks)
- Cloud sync, mobile experience, collaboration, integrations

### Tier 4: Advanced (8+ weeks)
- AI assistant, analytics, advanced automation, gamification

See [APP_ANALYSIS.md](./APP_ANALYSIS.md) for detailed breakdown.

## 🤝 Contributing

We welcome contributions! Check out [PROJECT_IDEAS.md](./PROJECT_IDEAS.md) for project ideas suitable for different skill levels:

- **Beginners**: Task counter badge, sorting, empty states
- **Intermediate**: Search, drag-and-drop, recurring tasks
- **Advanced**: Real-time sync, AI integration, mobile app

## 🛠 Technology Stack

- **Frontend**: ClojureScript + re-frame + Reagent
- **Build**: Leiningen + Figwheel
- **State**: re-frame with cljs.spec validation
- **Storage**: LocalStorage (backend planned)
- **UI**: Bootstrap 3 (upgrade to v5 planned)

## 📖 Learn More

- [re-frame Documentation](https://day8.github.io/re-frame/)
- [Reagent Documentation](https://reagent-project.github.io/)
- [Getting Things Done Methodology](https://gettingthingsdone.com/)

## 📄 License

This project is open source. License to be determined.
