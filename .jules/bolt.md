## 2024-05-24 - Canvas DrawScope Allocations
**Learning:** Found heavy list allocations (`flatMap { listOf(...) }`) happening inside Compose `Canvas` `DrawScope` during `ComparativeForecastChart` rendering. Since `DrawScope` operations can run frequently, allocating objects here causes unnecessary GC pressure.
**Action:** Always extract calculations that don't depend on the `DrawScope` size/context to variables outside the `Canvas`, using `remember` if they are derived from state, to avoid allocations during drawing.

## 2024-05-25 - Avoid Iterative Function References inside DrawScope
**Learning:** In Jetpack Compose, passing a local function reference (e.g., `::getPointOffset`) to helper drawing functions inside `Canvas` or `DrawScope` loops triggers allocations of `KFunction` instances and the subsequent returned primitive wrappers/objects (like `Offset`) on every frame, leading to severe GC thrashing.
**Action:** Avoid passing function references into drawing loops. Instead, inline the calculations or explicitly pass primitive values to helper functions.
