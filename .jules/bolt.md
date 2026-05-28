## 2024-05-24 - Canvas DrawScope Allocations
**Learning:** Found heavy list allocations (`flatMap { listOf(...) }`) happening inside Compose `Canvas` `DrawScope` during `ComparativeForecastChart` rendering. Since `DrawScope` operations can run frequently, allocating objects here causes unnecessary GC pressure.
**Action:** Always extract calculations that don't depend on the `DrawScope` size/context to variables outside the `Canvas`, using `remember` if they are derived from state, to avoid allocations during drawing.
## 2024-05-28 - Avoid GC thrashing in Jetpack Compose DrawScope
**Learning:** Do not allocate complex graphical objects like `Path` and `PathEffect` directly within the Compose `Canvas` drawing block (the `DrawScope`). The `DrawScope` executes frequently during recompositions or animations. Allocating objects here rapidly churns memory, leading to heavy garbage collection (GC thrashing) and dropped frames.
**Action:** Hoist these objects out of the `Canvas` using `remember { Path() }` or `remember { PathEffect... }`. Re-use the existing `Path` instance by calling `.reset()` on it before defining new points during each draw pass.
