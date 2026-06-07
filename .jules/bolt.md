## 2024-05-24 - Canvas DrawScope Allocations
**Learning:** Found heavy list allocations (`flatMap { listOf(...) }`) happening inside Compose `Canvas` `DrawScope` during `ComparativeForecastChart` rendering. Since `DrawScope` operations can run frequently, allocating objects here causes unnecessary GC pressure.
**Action:** Always extract calculations that don't depend on the `DrawScope` size/context to variables outside the `Canvas`, using `remember` if they are derived from state, to avoid allocations during drawing.

## 2025-02-14 - Compose DrawScope Reusable Paths
**Learning:** When using `remember { Path() }` and hoisting out of `Canvas`, passing local extension functions referencing them eliminates GC allocations on the Compose DrawScope thread. Ensure you call `.reset()` on the `Path` before starting new coordinate calculations inside the drawing phase.
**Action:** Do not allocate `Path`, `PathEffect`, or `Offset` via local functions mapping coordinates inside `DrawScope`; hoist instances and calculate values iteratively.
