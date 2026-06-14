## 2024-05-24 - Canvas DrawScope Allocations
**Learning:** Found heavy list allocations (`flatMap { listOf(...) }`) happening inside Compose `Canvas` `DrawScope` during `ComparativeForecastChart` rendering. Since `DrawScope` operations can run frequently, allocating objects here causes unnecessary GC pressure.
**Action:** Always extract calculations that don't depend on the `DrawScope` size/context to variables outside the `Canvas`, using `remember` if they are derived from state, to avoid allocations during drawing.

## 2025-02-12 - KFunction and Path allocations in Canvas DrawScope
**Learning:** Instantiating `Path`, `PathEffect`, or creating local function references (`KFunction` objects like `::functionName`) inside Jetpack Compose `Canvas` or `DrawScope` causes continuous GC allocations on every frame.
**Action:** Always hoist object allocations like `Path` and `PathEffect` outside of `Canvas` using `remember` and call `.reset()` on them if they need to be re-used. Pass primitive parameters instead of local function references for dynamic coordinate calculations.
