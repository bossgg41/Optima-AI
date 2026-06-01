## 2024-05-24 - Canvas DrawScope Allocations
**Learning:** Found heavy list allocations (`flatMap { listOf(...) }`) happening inside Compose `Canvas` `DrawScope` during `ComparativeForecastChart` rendering. Since `DrawScope` operations can run frequently, allocating objects here causes unnecessary GC pressure.
**Action:** Always extract calculations that don't depend on the `DrawScope` size/context to variables outside the `Canvas`, using `remember` if they are derived from state, to avoid allocations during drawing.

## 2024-06-01 - Avoid Object Allocation in Compose DrawScope
**Learning:** Instantiating objects like `Path` and `PathEffect` directly within the `Canvas` drawing block causes heavy object allocations on every draw frame, leading to GC thrashing and stuttering UI animations.
**Action:** Always hoist `Path`, `PathEffect`, and similar classes outside the `Canvas` using `remember`. Use `Path.reset()` before rebuilding the path within the draw loop.
