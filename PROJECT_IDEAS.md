# GTD App - Project Ideas by Skill Level

Practical project ideas for contributors at different experience levels with ClojureScript and re-frame.

---

## 🌱 Beginner Projects (1-3 days each)

Perfect for learning ClojureScript, re-frame patterns, and the codebase.

### Project B1: Task Counter Badge
**Skills**: Basic re-frame subscriptions, Reagent components
**Time**: 4-6 hours

Add a badge showing the number of incomplete tasks next to the project selector.

**What you'll learn**:
- Creating subscriptions
- Derived data in re-frame
- Basic Reagent component composition

**Files to modify**:
- `src/cljs/inversespace/subs.cljs` (add subscription)
- `src/cljs/inversespace/views.cljs` (add badge component)
- `resources/public/css/styles.css` (style the badge)

**Bonus**: Add different colors based on task count (green: 0-5, yellow: 6-10, red: 10+)

---

### Project B2: Task Sorting Options
**Skills**: Event handlers, state management
**Time**: 6-8 hours

Add buttons to sort tasks by title, completion status, or creation order.

**What you'll learn**:
- Event handlers with parameters
- Sorting collections in ClojureScript
- Toggle state management

**Files to modify**:
- `src/cljs/inversespace/events.cljs` (add sort event)
- `src/cljs/inversespace/db.cljs` (add sort state)
- `src/cljs/inversespace/subs.cljs` (sort in subscription)
- `src/cljs/inversespace/views.cljs` (add sort buttons)

---

### Project B3: Confirmation Dialogs
**Skills**: Component state, conditional rendering
**Time**: 4-6 hours

Add "Are you sure?" dialogs for destructive actions (delete task, clear completed).

**What you'll learn**:
- Component-local state with `r/atom`
- Conditional rendering
- Modal dialogs in React/Reagent

**Files to modify**:
- `src/cljs/inversespace/views.cljs`
- `resources/public/css/styles.css`

---

### Project B4: Empty States
**Skills**: Conditional rendering, UI/UX
**Time**: 3-4 hours

Show friendly messages when project has no tasks, with helpful actions.

**What you'll learn**:
- Empty state patterns
- UX best practices
- Conditional rendering

Example messages:
- "No tasks yet! Click 'new task' to get started."
- "All done! 🎉 Clear completed tasks or add new ones."

---

### Project B5: Dark Mode Toggle
**Skills**: CSS variables, LocalStorage, global state
**Time**: 6-8 hours

Add a dark mode toggle that persists user preference.

**What you'll learn**:
- CSS custom properties
- Theme switching
- LocalStorage for preferences
- Global app state

**Files to modify**:
- `src/cljs/inversespace/db.cljs` (add theme to state)
- `src/cljs/inversespace/events.cljs` (theme toggle event)
- `resources/public/css/styles.css` (dark theme styles)
- `resources/public/index.html` (CSS variables)

---

## 🌿 Intermediate Projects (3-7 days each)

Good for developers comfortable with re-frame basics.

### Project I1: Task Search
**Skills**: Text filtering, subscriptions, input handling
**Time**: 1-2 days

Add a search box to filter tasks by title across all projects.

**What you'll learn**:
- Parameterized subscriptions
- Filtering collections
- Input debouncing
- Performance optimization

**Files to modify**:
- `src/cljs/inversespace/subs.cljs` (search subscription)
- `src/cljs/inversespace/views.cljs` (search input)
- `src/cljs/inversespace/db.cljs` (search state)

**Bonus**: Highlight matching text, search by tags/contexts too

---

### Project I2: Drag-and-Drop Reordering
**Skills**: DOM events, HTML5 drag API, list manipulation
**Time**: 2-3 days

Allow users to reorder tasks within a project by dragging.

**What you'll learn**:
- HTML5 drag-and-drop API
- Event handling in Reagent
- List reordering logic
- Touch events for mobile

**Files to modify**:
- `src/cljs/inversespace/events.cljs` (reorder event)
- `src/cljs/inversespace/views.cljs` (drag handlers)
- `resources/public/css/styles.css` (drag styles)

**Libraries to consider**: react-beautiful-dnd, sortablejs

---

### Project I3: Recurring Tasks
**Skills**: Date logic, specs, complex state
**Time**: 3-5 days

Add support for recurring tasks (daily, weekly, monthly).

**What you'll learn**:
- Date manipulation in ClojureScript
- Complex data modeling with specs
- Scheduled events
- State machines

**Files to modify**:
- `src/cljs/inversespace/db.cljs` (recurrence spec)
- `src/cljs/inversespace/events.cljs` (recurrence logic)
- `src/cljs/inversespace/views.cljs` (recurrence UI)

**Challenges**: Handle "complete" vs "skip", timezone handling

---

### Project I4: Task Notes with Markdown
**Skills**: Third-party libraries, rich text
**Time**: 2-3 days

Add a notes field to tasks with Markdown support.

**What you'll learn**:
- Integrating JavaScript libraries
- Markdown rendering
- Multi-line text inputs
- Component composition

**Libraries**: markdown-cljs or react-markdown

**Files to modify**:
- `src/cljs/inversespace/db.cljs` (add notes field)
- `src/cljs/inversespace/events.cljs` (save notes)
- `src/cljs/inversespace/views.cljs` (notes editor)

---

### Project I5: Import/Export
**Skills**: File handling, data serialization, JSON/EDN
**Time**: 2-4 days

Allow users to export data as JSON/EDN and import it back.

**What you'll learn**:
- File upload/download in browser
- Data serialization
- Error handling
- Data validation

**Files to modify**:
- `src/cljs/inversespace/events.cljs` (import/export events)
- `src/cljs/inversespace/views.cljs` (file inputs)

**Bonus**: Support other formats (CSV, Todoist, etc.)

---

### Project I6: Keyboard Navigation
**Skills**: Global event handling, focus management
**Time**: 2-3 days

Full keyboard navigation: arrow keys, vim keys, shortcuts.

**What you'll learn**:
- Global keyboard event handling
- Focus management
- Accessibility
- Command pattern

**Features**:
- j/k or arrow keys to navigate tasks
- Enter to edit, Escape to cancel
- Delete key to remove tasks
- Tab to switch between views

---

### Project I7: Task Templates
**Skills**: Data modeling, UI patterns
**Time**: 3-4 days

Create reusable task templates with predefined checklists.

**What you'll learn**:
- Template pattern
- Data cloning
- Form design
- User workflows

**Example templates**:
- "Plan Meeting" (agenda, invite, prep, follow-up)
- "Blog Post" (research, outline, write, edit, publish)
- "Code Review" (checkout, test, review, feedback)

---

## 🌳 Advanced Projects (1-3 weeks each)

For experienced developers ready for complex challenges.

### Project A1: Real-time Multi-Device Sync
**Skills**: WebSockets, conflict resolution, backend
**Time**: 2-3 weeks

Build backend API and real-time sync across devices.

**What you'll learn**:
- Backend development (Ring/Compojure)
- WebSocket communication
- Conflict resolution strategies
- Database design (PostgreSQL/Datomic)
- Authentication (JWT)

**Architecture**:
- Backend: Clojure + Ring + PostgreSQL
- WebSocket: Sente or Socket.io
- Sync: Operational transforms or CRDTs
- Auth: OAuth2 or JWT

**Challenges**: 
- Offline mode with sync queue
- Conflict resolution
- Performance with many users
- Security

---

### Project A2: Natural Language Input
**Skills**: Parsing, AI/ML, UX
**Time**: 2-3 weeks

Parse natural language to create tasks with dates, contexts.

**What you'll learn**:
- Text parsing (instaparse)
- Date parsing (complex!)
- LLM integration (OpenAI API)
- Smart defaults and suggestions

**Examples**:
- "Call John tomorrow at 2pm" → Task with due date
- "Buy groceries @errands" → Task with context
- "Review PR #high" → Task with high priority

**Approaches**:
1. Rule-based parser (instaparse)
2. LLM API (GPT-4)
3. Hybrid approach

---

### Project A3: Mobile App (React Native)
**Skills**: React Native, mobile development
**Time**: 3-4 weeks

Build native iOS/Android app with shared CLJS logic.

**What you'll learn**:
- React Native with ClojureScript
- Krell or Shadow-CLJS for mobile
- Platform-specific code
- Mobile UX patterns
- Push notifications

**Features**:
- Quick capture widget
- Offline-first
- Push reminders
- Home screen widgets
- Share extension

---

### Project A4: Analytics Dashboard
**Skills**: Data visualization, aggregation, charting
**Time**: 2-3 weeks

Build insights dashboard with productivity metrics.

**What you'll learn**:
- Data aggregation
- Charting libraries (Victory, Recharts)
- Time series analysis
- Statistical calculations

**Metrics**:
- Tasks completed per day/week/month
- Average completion time
- Most productive contexts
- Project progress
- Burndown charts
- Productivity streaks

**Libraries**: oz (Vega-Lite), victory, d3

---

### Project A5: AI Task Assistant
**Skills**: AI/ML, NLP, UX design
**Time**: 3-4 weeks

Intelligent assistant for task management and GTD coaching.

**What you'll learn**:
- OpenAI/Anthropic API integration
- Prompt engineering
- Context management
- Streaming responses
- Cost optimization

**Features**:
- Weekly review assistant
- Task breakdown suggestions
- Smart scheduling
- Context recommendations
- Natural language queries
- Email → tasks conversion

**Challenges**:
- Prompt design for GTD methodology
- Managing API costs
- Privacy concerns
- Response quality

---

### Project A6: Calendar Integration
**Skills**: OAuth, external APIs, date/time
**Time**: 2-3 weeks

Sync with Google Calendar, Outlook, Apple Calendar.

**What you'll learn**:
- OAuth2 flows
- External API integration
- iCalendar format
- Timezone handling
- Webhook handling

**Features**:
- Two-way sync
- Conflict resolution
- Calendar view in app
- Time blocking
- Meeting → tasks conversion

---

### Project A7: Collaboration & Sharing
**Skills**: Multi-user systems, permissions, real-time
**Time**: 3-4 weeks

Enable project sharing and task assignment.

**What you'll learn**:
- Multi-user data modeling
- Permission systems
- Real-time updates
- Notification systems
- Activity feeds

**Features**:
- Share projects with users
- Assign tasks
- Comments and discussions
- @mentions
- Activity feed
- Email notifications
- Role-based permissions (owner, editor, viewer)

---

## 🚀 Expert Projects (1-3 months)

Large-scale features that could become standalone products.

### Project E1: GTD Methodology AI Coach
**Skills**: Advanced AI, psychology, UX research
**Time**: 2-3 months

AI that teaches and guides users through GTD methodology.

**Components**:
- Onboarding wizard with GTD training
- Weekly review assistant
- Habit formation
- Personalized advice
- Progress tracking
- Methodology adaptation

**Research needed**: GTD literature, behavioral psychology, coaching

---

### Project E2: Enterprise Edition
**Skills**: Enterprise architecture, security, scalability
**Time**: 3+ months

Self-hostable, multi-tenant, enterprise-ready version.

**Features**:
- Multi-tenant architecture
- SSO (SAML, LDAP)
- Team workspaces
- Admin dashboard
- Audit logs
- Compliance features (GDPR, SOC2)
- Custom branding
- API for integrations
- High availability
- Performance at scale

---

### Project E3: Open Plugin Architecture
**Skills**: Plugin systems, API design, documentation
**Time**: 2-3 months

Enable community to build extensions.

**Architecture**:
- Plugin manifest format
- API for plugins
- Plugin marketplace
- Sandboxing/security
- Documentation
- Example plugins

**Example Plugins**:
- Pomodoro timer
- Eisenhower matrix view
- Time tracking
- Invoice generation
- Custom integrations

---

## 🎓 Learning Path Recommendations

### Path 1: Frontend Mastery
1. B1 (Counter Badge) → B2 (Sorting) → I1 (Search)
2. I2 (Drag-Drop) → I6 (Keyboard Nav)
3. I4 (Markdown Notes) → A4 (Analytics)

**Outcome**: Master re-frame, Reagent, complex UI

---

### Path 2: Full-Stack Developer
1. B1 → I5 (Import/Export) → I7 (Templates)
2. A1 (Backend & Sync) → A6 (Calendar Integration)
3. A3 (Mobile App) or A7 (Collaboration)

**Outcome**: End-to-end application development

---

### Path 3: AI/ML Specialist
1. B1 → I1 (Search) → I4 (Markdown)
2. A2 (Natural Language Input)
3. A5 (AI Assistant) → E1 (AI Coach)

**Outcome**: AI-enhanced productivity apps

---

### Path 4: Product Designer
1. B3 (Confirmations) → B4 (Empty States) → B5 (Dark Mode)
2. I2 (Drag-Drop) → I6 (Keyboard Nav)
3. A3 (Mobile App) → E1 (AI Coach)

**Outcome**: Exceptional UX/UI skills

---

## 💡 Tips for Success

### Getting Started
1. **Start Small**: Pick a beginner project even if you're experienced
2. **Read the Code**: Understand existing patterns before adding features
3. **Test Locally**: Use Figwheel for instant feedback
4. **Ask Questions**: Create issues for clarification
5. **Document**: Update docs as you learn

### Best Practices
1. **Follow re-frame patterns**: Events, effects, coeffects, subscriptions
2. **Use specs**: Define specs for all new data structures
3. **Write tests**: Aim for 70%+ coverage
4. **Mobile-first**: Test on mobile devices
5. **Accessibility**: Use semantic HTML, ARIA labels
6. **Performance**: Profile with React DevTools

### Contributing
1. **Open an issue first**: Discuss before implementing
2. **Small PRs**: One feature per PR
3. **Tests required**: Include tests with features
4. **Document**: Update relevant docs
5. **Code review**: Be open to feedback

---

## 🎯 Quick Start

1. **Clone repo**: `git clone https://github.com/mxjxn/gtd-app.git`
2. **Install Leiningen**: [leiningen.org](https://leiningen.org/)
3. **Run app**: `lein figwheel dev`
4. **Open browser**: http://localhost:3449
5. **Make changes**: Files hot-reload automatically
6. **Pick a project**: Choose from beginner list
7. **Have fun!**: Learn by doing

---

## 📚 Resources by Topic

### ClojureScript
- [ClojureScript.org](https://clojurescript.org/)
- "ClojureScript Up and Running" (book)
- [ClojureScript Koans](http://clojurescriptkoans.com/)

### re-frame
- [Official docs](https://day8.github.io/re-frame/)
- [re-frame FAQ](https://github.com/day8/re-frame/wiki/FAQs)
- [re-frame examples](https://github.com/day8/re-frame/tree/master/examples)

### Reagent
- [Reagent GitHub](https://github.com/reagent-project/reagent)
- [Reagent cookbook](https://github.com/reagent-project/reagent-cookbook)

### GTD Methodology
- "Getting Things Done" by David Allen (book)
- [GTD official site](https://gettingthingsdone.com/)
- GTD subreddit: r/gtd

---

**Remember**: The best project is the one you're excited to build! Pick something that interests you and aligns with your learning goals. 🚀
