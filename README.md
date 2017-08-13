# GTD-Todo

A [re-frame](https://github.com/Day8/re-frame) todo-application with the goal of implementing the workflow laid-out in David Allen's [The Art of Getting Things Done](http://gettingthingsdone.com/).

## Development Mode

### Run application:

```
lein clean
lein figwheel dev
```

Figwheel will automatically push cljs changes to the browser.

Wait a bit, then browse to [http://localhost:3449](http://localhost:3449).

## Production Build


To compile clojurescript to javascript:

```
lein clean
lein cljsbuild once min
```
