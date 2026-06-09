## 2024-05-24 - Canvas DrawScope Allocations
**Learning:** Found heavy list allocations (`flatMap { listOf(...) }`) happening inside Compose `Canvas` `DrawScope` during `ComparativeForecastChart` rendering. Since `DrawScope` operations can run frequently, allocating objects here causes unnecessary GC pressure.
**Action:** Always extract calculations that don't depend on the `DrawScope` size/context to variables outside the `Canvas`, using `remember` if they are derived from state, to avoid allocations during drawing.

## 2026-06-09 - Prevent GC thrashing in Canvas
**Learning:** Instantiating `Path`, `PathEffect`, and allocating `KFunction` references inside a Jetpack Compose `DrawScope` or `Canvas` phase causes severe GC thrashing due to rapid recomposition/redrawing.
**Action:** Hoist `Path` and `PathEffect` objects using `remember` and reset paths before drawing. Avoid passing local function references (like `::getPointOffset`) and instead inline coordinate calculations.
