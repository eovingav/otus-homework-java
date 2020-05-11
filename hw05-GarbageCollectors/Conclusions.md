# Домашняя работа Сборщики мусора

##### Задание

Сравнение разных сборщиков мусора
Написать приложение, которое следит за сборками мусора и пишет в лог количество сборок каждого типа
(young, old) и время которое ушло на сборки в минуту.

Добиться OutOfMemory в этом приложении через медленное подтекание по памяти
(например добавлять элементы в List и удалять только половину).

Настроить приложение (можно добавлять Thread.sleep(...)) так чтобы оно падало
с OOM примерно через 5 минут после начала работы.

Собрать статистику (количество сборок, время на сборки) по разным GC.
Какой gc лучше и почему?

##### Статистика по логам

| Garbage Collector     | Количество запусков   | Время на сборки  | Максимальное время Stop the World | Общее время работы  | Количество циклов по данным |
|-------------|---------|----------------|----------|----------|----------|
| G1          | 337 | 2 min 16 sec | 	916 ms | 3 min 6 sec | 7353311 |
| Serial      | 54 | 	21 sec 614 ms | 1 sec 81 ms | 1 min 22 sec | 6230317 |
| Parallel    | 64 | 	28 sec 704 ms | 1 sec 857 ms | 1 min 25 sec | 6230317 |
| CMS         | 89 | 36 sec 227 ms | 	1 sec 511 ms | 1 min 33 sec | 6230317 |
| ZGC         | | | | 1 min 7 sec | 4153545 |

Примечание: анализ логов GC выполнялся при помощи https://gceasy.io/
 
##### Выводы:
Судя по статистике для имеющейся задачи, выполняющей некий длительный процесс, требующий сборки мусора, максимально эффективно использовать GC G1, т.к. именно он обеспечивает макисмальное количество циклов обработки данных на выделенном объеме памяти до падения. Применение других сборщиков нецелесообразно для процесса, т.к. на них процесс на выделенном объеме памяти падает быстрее и успевает обработать меньшее количество данных.