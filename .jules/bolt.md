## 2024-05-24 - Canvas DrawScope Allocations
**Learning:** Found heavy list allocations (`flatMap { listOf(...) }`) happening inside Compose `Canvas` `DrawScope` during `ComparativeForecastChart` rendering. Since `DrawScope` operations can run frequently, allocating objects here causes unnecessary GC pressure.
**Action:** Always extract calculations that don't depend on the `DrawScope` size/context to variables outside the `Canvas`, using `remember` if they are derived from state, to avoid allocations during drawing.

## 2024-05-24 - Avoiding Allocations in Compose DrawScope
**Learning:** Instantiating `Path` and `PathEffect` objects, or using method references (`::functionName`) inside Compose's `Canvas` `DrawScope` triggers `KFunction` and object allocations on every frame, leading to GC thrashing and frame drops.
**Action:** Always hoist `Path` and `PathEffect` out of the `Canvas` using `remember` and reuse them inside `DrawScope` by calling `.reset()`. Additionally, inline coordinate calculations or pass primitive parameters instead of passing local function references to drawing helper functions.
