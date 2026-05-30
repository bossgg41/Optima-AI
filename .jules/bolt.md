## 2024-05-24 - Canvas DrawScope Allocations
**Learning:** Found heavy list allocations (`flatMap { listOf(...) }`) happening inside Compose `Canvas` `DrawScope` during `ComparativeForecastChart` rendering. Since `DrawScope` operations can run frequently, allocating objects here causes unnecessary GC pressure.
**Action:** Always extract calculations that don't depend on the `DrawScope` size/context to variables outside the `Canvas`, using `remember` if they are derived from state, to avoid allocations during drawing.
## 2024-05-30 - Prevent GC Thrashing in Compose Canvas DrawScope
**Learning:** Instantiating objects like `Path` and `PathEffect` directly inside the `Canvas` block (which executes repeatedly as part of `DrawScope`) causes significant Garbage Collection (GC) thrashing and degrades performance, especially in components like high-fidelity line charts.
**Action:** Always hoist `Path` and `PathEffect` allocations using `remember { Path() }` outside the `Canvas` and reuse them by calling `path.reset()` within the drawing phase.
