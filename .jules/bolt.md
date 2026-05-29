## 2024-05-24 - Canvas DrawScope Allocations
**Learning:** Found heavy list allocations (`flatMap { listOf(...) }`) happening inside Compose `Canvas` `DrawScope` during `ComparativeForecastChart` rendering. Since `DrawScope` operations can run frequently, allocating objects here causes unnecessary GC pressure.
**Action:** Always extract calculations that don't depend on the `DrawScope` size/context to variables outside the `Canvas`, using `remember` if they are derived from state, to avoid allocations during drawing.

## 2025-01-20 - Canvas DrawScope Object Allocations (Path & PathEffect)
**Learning:** Instantiating objects like `Path` and `PathEffect` inside the Jetpack Compose `Canvas` drawing phase (DrawScope) leads to allocations on every frame, causing GC thrashing and dropped frames.
**Action:** Hoist these objects out of the DrawScope using `remember`, and reuse them by calling `.reset()` before drawing.
