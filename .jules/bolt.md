## 2024-05-24 - Canvas DrawScope Allocations
**Learning:** Found heavy list allocations (`flatMap { listOf(...) }`) happening inside Compose `Canvas` `DrawScope` during `ComparativeForecastChart` rendering. Since `DrawScope` operations can run frequently, allocating objects here causes unnecessary GC pressure.
**Action:** Always extract calculations that don't depend on the `DrawScope` size/context to variables outside the `Canvas`, using `remember` if they are derived from state, to avoid allocations during drawing.

## 2024-05-25 - DrawScope KFunction & Object Allocations
**Learning:** Found KFunction references (`::getPointOffset`) and objects (`Path`, `PathEffect`) being iteratively allocated inside Compose `Canvas` drawing functions in `Charts.kt`. These per-frame allocations cause unnecessary GC thrashing.
**Action:** Hoist `Path` and `PathEffect` instantiations outside `Canvas` using `remember` and call `.reset()` before reuse. Replace function references and intermediate `Offset` objects by calculating primitive coordinates directly inline.
