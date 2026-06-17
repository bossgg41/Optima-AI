## 2024-05-24 - Canvas DrawScope Allocations
**Learning:** Found heavy list allocations (`flatMap { listOf(...) }`) happening inside Compose `Canvas` `DrawScope` during `ComparativeForecastChart` rendering. Since `DrawScope` operations can run frequently, allocating objects here causes unnecessary GC pressure.
**Action:** Always extract calculations that don't depend on the `DrawScope` size/context to variables outside the `Canvas`, using `remember` if they are derived from state, to avoid allocations during drawing.

## 2024-05-25 - Prevent Path and PathEffect Allocations inside Canvas
**Learning:** Instantiating `Path()` and `PathEffect.dashPathEffect()` directly inside the Compose `Canvas` drawing loop creates GC thrashing, as these operations run frequently. Also, passing local functions (like `::getPointOffset`) to separate helper methods allocates `KFunction` on each render.
**Action:** Hoist `Path` and `PathEffect` using `remember` and reuse paths by calling `.reset()`. Inline simple helper functions to avoid closure and `KFunction` allocations inside DrawScope.
