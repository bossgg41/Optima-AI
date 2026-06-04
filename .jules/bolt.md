## 2024-05-24 - Canvas DrawScope Allocations
**Learning:** Found heavy list allocations (`flatMap { listOf(...) }`) happening inside Compose `Canvas` `DrawScope` during `ComparativeForecastChart` rendering. Since `DrawScope` operations can run frequently, allocating objects here causes unnecessary GC pressure.
**Action:** Always extract calculations that don't depend on the `DrawScope` size/context to variables outside the `Canvas`, using `remember` if they are derived from state, to avoid allocations during drawing.
## 2024-06-04 - Jetpack Compose DrawScope KFunction Allocation GC Thrashing
**Learning:** Passing a local function reference (`::getPointOffset`) to another function inside the `Canvas` `DrawScope` loop forces a `KFunction` allocation on every single frame, leading to significant GC thrashing and dropped frames, which defeats the purpose of extracting other objects out.
**Action:** Avoid passing function references within `DrawScope` inside loops. Instead, inline the calculations or pass primitive values directly.
