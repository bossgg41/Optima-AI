## 2024-05-24 - Canvas DrawScope Allocations
**Learning:** Found heavy list allocations (`flatMap { listOf(...) }`) happening inside Compose `Canvas` `DrawScope` during `ComparativeForecastChart` rendering. Since `DrawScope` operations can run frequently, allocating objects here causes unnecessary GC pressure.
**Action:** Always extract calculations that don't depend on the `DrawScope` size/context to variables outside the `Canvas`, using `remember` if they are derived from state, to avoid allocations during drawing.
## 2024-06-11 - Jetpack Compose Canvas GC Thrashing
**Learning:** Instantiating `Path` and `PathEffect` objects directly inside the `Canvas` `onDraw` scope in Jetpack Compose leads to significant garbage collection (GC) thrashing, as `onDraw` is called very frequently (especially during animations). This can cause frame drops and UI jank.
**Action:** Always hoist `Path`, `PathEffect`, and similar allocation-heavy objects outside of the `Canvas` `onDraw` scope using `remember`. Re-use these objects inside `onDraw` by calling `.reset()` before use.
