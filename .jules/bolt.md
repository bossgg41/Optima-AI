## 2024-05-24 - Canvas DrawScope Allocations
**Learning:** Found heavy list allocations (`flatMap { listOf(...) }`) happening inside Compose `Canvas` `DrawScope` during `ComparativeForecastChart` rendering. Since `DrawScope` operations can run frequently, allocating objects here causes unnecessary GC pressure.
**Action:** Always extract calculations that don't depend on the `DrawScope` size/context to variables outside the `Canvas`, using `remember` if they are derived from state, to avoid allocations during drawing.

## 2024-06-25 - Avoid GC thrashing in DrawScope via Path caching and inline primitives
**Learning:** Instantiating objects like `Path` and `PathEffect`, as well as creating iterative `Offset` instances or capturing `::functionReference` local methods inside `DrawScope` loops (like `drawPredictionLines`), triggers excessive Garbage Collection (GC thrashing) causing stutter on lower-end devices.
**Action:** Extract heavy allocations like `Path` outside `DrawScope` using `remember { Path() }` and pass them into drawing functions to `.reset()` and reuse. Calculate primitive inline values (e.g. `x`, `y` float coordinates) instead of continuously instantiating wrappers like `Offset` and functional allocations.
