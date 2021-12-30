# Lambda Expressions and Functional Programming with Java

## Imperative Style vs Functional Style

When we write a software, we usually use the so-called **imperative** style.

To understand what imperative style means, we will look at an example instead of getting lost with definitions.

Let's suppose we want to develop an application that calculates the average value of the even numbers in an array.

Using the imperative style, we will write something like this:
```java
package io.tarric1.func;

public class Example1 {
    public static Double avgOfEvenNums(int[] nums) {
        int n = 0;
        double s = 0;
        Double avg = null;
        for (int num : nums) {
            if (num % 2 == 0) {
                n++;
                s += num;
            }
        }
        if (n > 0) {
            avg = s / n;
        }
        return avg;
    }

    public static void main(String[] args) {
        int[] nums = new int[] { 1, 10, 47, 14, 39, 50, 2, 6, 55, 56, 48 };
        System.out.println(avgOfEvenNums(nums));
    }
}
```

The first thing that catches the eye is that we had to use 4 temporary variables (``n``, ``s``, ``avg``, ``num``) and we had to write a lot of code for developing a simple function.

There is an alternative way to develop an application and it is known as **functional** style.

This is not new in the programming landscape, it was indeed present in the LISP programming language, older than Java, but some years ago it has been rediscovered because it allows to write more compact software, with many advantages: **less code ⇒ less mistakes, less tests**.

Our application, rewritten with the functional style, becomes:
```java
package io.tarric1.func;

import java.util.Arrays;

public class Example2 {
    public static double avgOfEvenNums(int[] nums) {
        return Arrays.stream(nums)
                .filter(num -> num % 2 == 0)
                .average()
                .getAsDouble();
    }
    
    public static void main(String[] args) {
        int[] nums = new int[] { 1, 10, 47, 14, 39, 50, 2, 6, 55, 56, 48 };
        System.out.println(avgOfEvenNums(nums));
    }
}
```

It is evident that it has become more compact, cleaner and more elegant. Let's go more in deep.

## Lambda Expressions
:fire: *A **lambda expression** is an **anonymous** method, that doesn't have a declaration, it doesn't belong to a class, it can be passed as input parameter and it can be returned as output parameter.*

The lambda expressions have been introduced with the aim of enabling the functional programming with Java.

The lambda expressions are very useful when the method has a few lines, it is used only once in the *place where it is needed*.

We have already seen an example of a lambda expression:
```java
                .filter(num -> num % 2 == 0)
```

I will explain the power of lambda expressions by means of a use case.

Let's suppose that we have a system that handles financial transactions and that the following class models the transactions:
```java
package io.tarric1.func.lambda;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.*;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Transaction {
    public enum Type {
        WITHDRAWAL, DEPOSIT, TRANSFER, PAYMENT
    }
    
    public enum Status {
        TO_BE_PROCESSED, PROCESSED
    }

    private LocalDate executionDate;
    private BigDecimal amount;
    private Type type;
    private Status status;
}
```

:information_source: *I'm using [Lombok](https://projectlombok.org/). If you don't know it, check it out, you won't regret it.*
 
Let's suppose that we want to give to our system the ability of searching all the transactions with the amount greater than a specific value. We could write something like this:
```java
package io.tarric1.func.lambda.case1;

import java.math.BigDecimal;
import java.util.*;

import io.tarric1.func.lambda.Transaction;

public class Seeker {
    public static List<Transaction> searchTrxsWithAmtGreaterThan(List<Transaction> trxs,
            BigDecimal amount) {
        List<Transaction> found = new ArrayList<>();
        for (Transaction trx : trxs) {
            if (trx.amount().compareTo(amount) > 0) {
                found.add(trx);
            }
        }
        return found;
    }
}
```

What happens if the structure of the model (```Transaction```) changes or what if we want to extend search criteria? 

We have to change the ```Seeker```, in defiance of the **open/closed** principle.

In the end this implementation is too fragile and limiting: we have to generalise it.

One idea may be to confine the search criteria to another class.

Let's start by defining of a new interface ```Criteria``` with a single method ```test``` that returns ```true``` if the criteria are met, otherwise ```false```:
```java
package io.tarric1.func.lambda.case2;

public interface Criteria {
    public boolean test(Transaction trx);
}
```

Our ```Seeker``` becomes:
```java
package io.tarric1.func.lambda.case2;

import java.util.*;

import io.tarric1.func.lambda.Transaction;

public class Seeker {
    public static List<Transaction> search(List<Transaction> trxs,
            Criteria criteria) {
        List<Transaction> found = new ArrayList<>();
        for (Transaction trx : trxs) {
            if (criteria.test(trx)) {
                found.add(trx);
            }
        }
        return found;
    }
}
```

If we want to search the transactions with the amount greater than 100:
```java
package io.tarric1.func.lambda.case2;

import java.math.BigDecimal;
import java.util.*;

import io.tarric1.func.lambda.DataStore;
import io.tarric1.func.lambda.Transaction;

public class UseCase1 {
    public static void main(String[] args) {
        List<Transaction> trxs = DataStore.trxs;

        List<Transaction> found = Seeker.search(trxs, new Criteria() {
            @Override
            public boolean test(Transaction trx) {
                return trx.amount().compareTo(BigDecimal.valueOf(100)) > 0;
            }
        });
        
        System.out.println(found);
    }
}
```

If we want to search the transactions with amount greater than 100 and less than 500:
```java
        List<Transaction> found = Seeker.search(trxs, new Criteria() {
            @Override
            public boolean test(Transaction trx) {
                return trx.amount().compareTo(BigDecimal.valueOf(100)) > 0
                        && trx.amount().compareTo(BigDecimal.valueOf(500)) < 0;
            }
        });
```

If we want to search the *withdrawal* transactions:
```java
        List<Transaction> found = Seeker.search(trxs, new Criteria() {
            @Override
            public boolean test(Transaction trx) {
                return trx.type().equals(Transaction.Type.WITHDRAWAL);
            }
        });
```

Our implementation is now less fragile and more extensible than the previous one, but we haven't used the lambda expressions yet.

Before we do that, let's introduce the concept of **functional interface**.

:fire: *A **functional interface** is an interface with a single method to implement - it can have other default and/or static methods.*

According to this definition, ```Criteria``` is a functional interface.

This means that the implementation of ```Criteria``` can be provided with a lambda expression, because the compiler without any ambiguity, can understand that it is the implementation of the unique method of the functional interface.

:fire: *A **lambda expression** can be used to implement a **functional interface**.*

So, if we want to search *withdrawal* transactions:
```java
        List<Transaction> found = Seeker.search(trxs,
                trx -> trx.type().equals(Transaction.Type.WITHDRAWAL));
```

Java has got several functional interfaces *ready-to-use*; take a look to the package ```java.util.function```.

Among predefined functional interfaces, there is **```Predicate<T>```**, which has got a method (to implement) that returns a ```boolean``` and accepts an input parameter with parametric class ```T```:
```java
package java.util.function;

import java.util.Objects;

@FunctionalInterface
public interface Predicate<T> {
    boolean test(T t);

    default Predicate<T> and(Predicate<? super T> other) {
        Objects.requireNonNull(other);
        return (t) -> test(t) && other.test(t);
    }

    default Predicate<T> negate() {
        return (t) -> !test(t);
    }

    default Predicate<T> or(Predicate<? super T> other) {
        Objects.requireNonNull(other);
        return (t) -> test(t) || other.test(t);
    }

    static <T> Predicate<T> isEqual(Object targetRef) {
        return (null == targetRef)
                ? Objects::isNull
                : object -> targetRef.equals(object);
    }
}
```

:information_source: *This is the source code of ```Predicate<T>``` with **Java 8**. **Java 11** adds the method ```static <T> Predicate<T> not(Predicate<? super T> target)```.*

This interface is suitable for replacing our ```Criteria```, thus we can change the ```Seeker```, leaving ```Criteria``` and using ```Predicate<Transaction>```, saving on source code - don't forget, **less code ⇒ less mistakes, less tests**.
```java
package io.tarric1.func.lambda.case3;

import java.util.*;
import java.util.function.Predicate;

import io.tarric1.func.lambda.Transaction;

public class Seeker {
    public static List<Transaction> search(List<Transaction> trxs,
            Predicate<Transaction> criteria) {
        List<Transaction> found = new ArrayList<>();
        for (Transaction trx : trxs) {
            if (criteria.test(trx)) {
                found.add(trx);
            }
        }
        return found;
    }
}
```

An italian proverb says "appetite comes with eating", so let's see if we can generalise further.

The method ```search``` belonging to ```Seeker```, executes a loop on the list of transactions passed with the input parameter ```trxs```, for each of them, it executes the method ```test``` of the criterion passed with the input parameter ```criteria```, if this method returns ```true```, it adds the transaction to the list ```found```.

So the performed action is to *add the transaction to the list* ```found```.

Using another predefined functional interface, we can also generalise the action that we can pass to ```Seeker``` as another input parameter, as we have already done for the search critera. We will implement this new input parameter with a lambda expression.

In this case the functional interface is **```Consumer<T>```** which has got the method ```void accept(T t)```, that must be implemented with the action:
```java
package java.util.function;

import java.util.Objects;

@FunctionalInterface
public interface Consumer<T> {
    void accept(T t);

    default Consumer<T> andThen(Consumer<? super T> after) {
        Objects.requireNonNull(after);
        return (T t) -> { accept(t); after.accept(t); };
    }
}
```

```Seeker``` becomes:
```java
package io.tarric1.func.lambda.case4;

import java.util.*;
import java.util.function.*;

import io.tarric1.func.lambda.Transaction;

public class Seeker {
    public static void search(List<Transaction> trxs,
            Predicate<Transaction> criteria,
            Consumer<Transaction> action) {
        for (Transaction trx : trxs) {
            if (criteria.test(trx)) {
                action.accept(trx);
            }
        }
    }
}
```

It can be used in this way:
```java
package io.tarric1.func.lambda.case4;

import java.util.*;

import io.tarric1.func.lambda.DataStore;
import io.tarric1.func.lambda.Transaction;

public class UseCase {
    public static void main(String[] args) {
        List<Transaction> trxs = DataStore.trxs;
        
        List<Transaction> found = new ArrayList<>();

        Seeker.search(trxs,
                trx -> trx.type().equals(Transaction.Type.WITHDRAWAL),
                trx -> found.add(trx));
        
        System.out.println(found);
    }
}
```

If we want to do something of different on the trasactions that meet the search criteria, we no longer change ```Seeker```, but the class of caller, therfore we go in the direction indicated by the **single responsibility** principle and **open/closed** principle.

Let's go further. What if we want to do a transactions processing before we consume them? For instance, let's suppose that we want to change their status from ```TO_BE_PROCESSED``` to ```PROCESSED```, before adding them to ```found```.

We can do something like this:
```java
package io.tarric1.func.lambda.case4;

import java.util.*;

import io.tarric1.func.lambda.DataStore;
import io.tarric1.func.lambda.Transaction;

public class UseCase1 {
    public static void main(String[] args) {
        List<Transaction> trxs = DataStore.trxs;

        List<Transaction> found = new ArrayList<>();

        Seeker.search(trxs,
                trx -> trx.type().equals(Transaction.Type.WITHDRAWAL),
                trx -> {
                    trx.status(Transaction.Status.PROCESSED);
                    found.add(trx);
                });

        System.out.println(found);
    }
}
```

But we would violate the **single responsibility** principle, because our lambda expression would have two responsibilities: 1) to process the transaction and 2) to add it to ```found```.

For these cases another functional interface comes to our aid: **```Function<T, R>```**
```java
package java.util.function;

import java.util.Objects;

@FunctionalInterface
public interface Function<T, R> {
    R apply(T t);

    default <V> Function<V, R> compose(Function<? super V, ? extends T> before) {
        Objects.requireNonNull(before);
        return (V v) -> apply(before.apply(v));
    }

    default <V> Function<T, V> andThen(Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (T t) -> after.apply(apply(t));
    }

    static <T> Function<T, T> identity() {
        return t -> t;
    }
}
```

This functional interface has got the method ```R apply(T t)``` that must be implemented for executing the processing that we need.

```Seeker``` becomes:
```java
package io.tarric1.func.lambda.case5;

import java.util.*;
import java.util.function.*;

import io.tarric1.func.lambda.Transaction;

public class Seeker {
    public static void search(List<Transaction> trxs,
            Predicate<Transaction> criteria,
            Consumer<Transaction> action,
            Function<Transaction, Transaction> processor) {
        for (Transaction trx : trxs) {
            if (criteria.test(trx)) {
                action.accept(processor.apply(trx));
            }
        }
    }
}
```

Now it can be used by means of a lambda expression:
```java
package io.tarric1.func.lambda.case5;

import java.util.*;

import io.tarric1.func.lambda.DataStore;
import io.tarric1.func.lambda.Transaction;

public class UseCase {
    public static void main(String[] args) {
        List<Transaction> trxs = DataStore.trxs;
        
        List<Transaction> found = new ArrayList<>();

        Seeker.search(trxs,
                trx -> trx.type().equals(Transaction.Type.WITHDRAWAL),
                trx -> found.add(trx),
                trx -> trx.status(Transaction.Status.PROCESSED));

        System.out.println(found);
    }
}
```

The last functional interface that we are going to analyse is **```Supplier<T>```** that with its method ```T get()``` can be used, for instance, for retrieving the list of transactions to filter and process:
```java
package java.util.function;

@FunctionalInterface
public interface Supplier<T> {
    T get();
}
```

```Seeker``` becomes:
```java
package io.tarric1.func.lambda.case6;

import java.util.*;
import java.util.function.*;

import io.tarric1.func.lambda.Transaction;

public class Seeker {
    public static void search(Supplier<List<Transaction>> supplier,
            Predicate<Transaction> criteria,
            Consumer<Transaction> action,
            Function<Transaction, Transaction> processor) {
        for (Transaction trx : supplier.get()) {
            if (criteria.test(trx)) {
                action.accept(processor.apply(trx));
            }
        }
    }
}
```

It can be used in this way:
```java
package io.tarric1.func.lambda.case6;

import java.util.*;

import io.tarric1.func.lambda.DataStore;
import io.tarric1.func.lambda.Transaction;

public class UseCase {
    public static void main(String[] args) {
        List<Transaction> trxs = DataStore.trxs;

        List<Transaction> found = new ArrayList<>();

        Seeker.search(() -> trxs,
                trx -> trx.type().equals(Transaction.Type.WITHDRAWAL),
                trx -> found.add(trx),
                trx -> trx.status(Transaction.Status.PROCESSED));

        System.out.println(found);
    }
}
```

We can go even further on the road of generalisation and review everything with **generics**, but that's not the goal of this article.

### Recap
So far we have analysed the predefined functional interfaces more used. There are many other, take a look at the documentation of the package ```java.util.function```.

To sum up:
| Functional Interface | Method | Usage |
|----------------------|--------|----------|
| ```Predicate<T>``` | ```boolean test(T t)``` | To filter. |
| ```Consumer<T>``` | ```void accept(T t)``` | To process. |
| ```Function<T, R>``` | ```R apply(T t)``` | To transform. |
| ```Supplier<T>``` | ```T get()``` | To supply. |

## Wrap up
We have seen how the use of functional interfaces and lambda expressions helps us to write more compact and more easily extendable programs, in other words, more maintainable.

In a future article we will look at the **Stream API**, to which all that has been explained has been applied and we will see how their use gives a significant boost in the direction of functional programming.