# Курс объектно-ориентированного программирования на МКН СПбГУ
## Занятие 3: интерфейсы и обобщенные типы

На этом занятии мы будем выполнять простые задания по материалу третьей лекции.

### План занятия
1. Создание индивидуального репозитория для занятия на GitHub и его импорт в локальный проект в IntelliJ IDEA
2. Локальная работа с проектом: редактирование, запуск программ и запуск тестов, 
отправка решений каждого задания на сервер.
3. Создание пулл-реквеста (на ветку `task`) после выполнения всех заданий.
   
### Задание 1

1. Определите интерфейс `Computer` с единственной функцией `calculateAnswer` без параметров, возвращающей 
значение типа `Int`. 
2. Определите класс десктопного компьютера `Desktop`, объекты которого инициализируются значением типа `Int`. Реализуйте в этом
классе интерфейс `Computer` так, чтобы функция из него возвращала заданное значение.
3. Определите класс `SummingCloud`, инициализируемый количеством узлов, входящих в облако, 
и хранящий все узлы как список объектов класса, реализующего интерфейс `Computer` (`List<Computer>`).
Отдельные узлы облака должны создаваться как десктопные компьютеры, инициализированные числами
от 1 до `n`, где `n` — их количество. 
Реализуйте в этом классе интерфейс `Computer` так, чтобы функция из него возвращала сумму значений,
вычисленных отдельными узлами.

### Задание 2

Код в заготовке содержит не самую удачную реализацию наследования классов животных: её проблема в том, 
что переопределяемая в наследниках функция `talk` дублирует функциональность,
троекратно печатая соответствующую виду животного [ономатопею](https://ru.wikipedia.org/wiki/Ономатопея). 
Другая проблема состоит в том, что функция `talk` вполне могла бы использоваться и не животными 
(например, четырёхкратно урчащими роботами-пылесосами).

1. Определите интерфейс `Talkable`, содержащий строковое свойство для чтения `sound` и функцию `talk`.
2. Реализуйте интерфейс `Talkable` в классе `AbstractAnimal`, задав определение функции `talk` (троекратная печать
ономатопеи `sound`) и оставив без реализации свойство `sound`.
3. Реализуйте свойство `sound` в наследниках класса `AbstractAnimal` соответственно виду животного.
4. Определите класс `RobotVacuum`, реализующий интерфейс `Talkable` выводом на консоль строки `"ур-ур-ур-ур"`
   (`sound` = `"ур"`).

### Задание 3

Классы, с которыми приходится работать в программе, могут приходить из разных библиотек и не иметь
практически ничего общего. Например, в заготовке определены три не связанных между собой класса,
имеющих по одному строковому свойству. В таких случаях можно, пользуясь вспомогательными классами и интерфейсами,
создавать *адаптеры*, позволяющие работать с такими классами однообразно. 

1. Определите интерфейс `Creature` со строковым свойством `message`.
2. Для каждого из классов заготовки определите класс `*Adapter` (`HumanAdapter` и т.д.) следующим образом:
    - его конструктор должен принимать на вход экземпляр исходного класса;
    - этот класс должен реализовывать интерфейс `Creature`, беря текст сообщения из 
      экземпляра исходного класса.
3. Заполните список `creatures` в функции `main` экземплярами адаптеров и убедитесь,
что печать всех сообщений теперь выполняется однообразно (по сравнению с реализацией из предыдущего занятия).
4. Подход с адаптером можно применить и к другим классам, например, к классу `RobotVacuum`
из предыдущего задания. Можно считать, что сообщением в этом случае будет значение свойства `sound`.
Обратите внимание, что мы можем начать работу с этим классом, никак не модифицируя его определение.
Достаточно лишь определить для него класс-адаптер, причём именно в модуле этого задания. 
Сделайте это и добавьте робот-пылесос к списку `creatures` в функции `main`.

### Задание 4

Это задание посвящено обобщённым типам. Оно состоит из трёх частей. Решения для второй и третьей частей не должны 
никаким образом затрагивать код, написанный для предыдущих частей.

#### Часть 1: класс Box

Определите открытый класс `Box` так, чтобы исполнение функции `part1` приводило к следующему выводу:
```
=== Часть 1: класс Box ===
Box b: 6
ERROR: Box is not empty
ERROR: Box is empty
ERROR: Box is empty
Box b: 0
Box b2: hello
String's length is 5
String's length is 3
```

*Важно*: не определяйте в интерфейсе класса ничего лишнего.

#### Часть 2: функция convert

Определите функцию `convert` так, чтобы исполнение функции `part2` приводило к следующему выводу:
```
=== Часть 2: функция convert ===
Box stringBox: 42!
ERROR: Box is empty
Box intBox2: 3
Box intBox3: 4
```

*Важно*: изменения в написанном ранее коде класса `Box` не допускаются.

#### Часть 3: класс BoxList

Унаследуйте класс `BoxList<T>` от `ArrayList<Box<T>>` так, чтобы исполнение функции `part3` приводило к следующему выводу:
```
=== Часть 3: класс BoxList ===
[[1], [2], [3], [4], [5], [6], [7], [8]]
[[a], [b], [c], [d], [e], [f], [g], [h]]
```

*Важно*: изменения в написанном ранее коде класса `Box` и переопределение в наследнике функций класса `ArrayList` 
не допускаются.

*Подсказка*. К сожалению, мы забыли определить в классе `Box` функцию `toString`. Поскольку модифицировать его
код нельзя, а `toString`, унаследованная от `ArrayList`, вызывает `toString` для своих элементов, 
придётся как-то выкручиваться. Например, в классе `BoxList<T>` можно определить вложенный класс DecoratedBox<T>,
унаследовав его от `Box<T>` и переопределив в нём `toString` требуемым согласно заданию образом. 
Далее можно определить функцию `add`, которая сможет добавлять в список элементы типа `T`, помещая их
в `DecoratedBox<T>` и затем используя `add` предка.

### Начисление баллов за занятие

* По одному баллу за каждое задание.