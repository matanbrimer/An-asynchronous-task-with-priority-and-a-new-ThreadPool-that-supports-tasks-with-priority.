# An-asynchronous task with priority and a new ThreadPool that supports tasks with priority.

Background In Java, there is no built-in option to give priority to an asynchronous task (a task that will be executed in a separate thread). The language does allow you to set a priority for the Thread that runs the task, but not for the task itself. Therefore, we are in a problem when we want to prioritize an asynchronous task using:

1. Interface <V<Callable. It is an interface that represents a task with a generic return value. A task that is a type of <V<Callable cannot be executed within a normal    Thread, and therefore cannot be assigned to it indirectly preferred.

2. In ThreadPool, which you put as its task queue Runnable or <V<Callable, since it is not possible to determine Priority to a specific Thread in the Executor's Threads    collection.

So we create a new object that represents an asynchronous task with priority and a new ThreadPool type that supports owning tasks priority.

**we create two classes:**

1. **Task** - This class extend "FutureTask" and implements Comparable, Callable. It Represents a task that can be run asynchronously and can return a value of some type.

2. **CustomExecutor**– represents a new type of ThreadPool that supports a queue of priority tasks. CustomExecutor will execute the tasks according to their priority. but the The challenge here is that the priority queue that supports threadpool only supports variables that implement runnable but our tasks implement callable because only it returns a value as requested.


What we needed was something that would bridge the callable task and what entered the priority queue - which would be of the runnable type We used the idea of an adapter (which we learned with design patterns) but instead of creating a new class to bridge, we created an action that accepts a task of callable type and returns the same task only of runnable type so that it can enter the queue.

You will see it in our CustomExecutor class, it's called "newTaskFor" to test our project we use the tests in "test" class.

![image](https://user-images.githubusercontent.com/48315169/213812086-3f250ea2-025e-49be-854d-f9ebb75dfc38.png)
