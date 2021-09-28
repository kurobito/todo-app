import "./App.css";
import { useEffect, useState } from "react";
import Todo from "./components/Todo";
import FilterButton from "./components/FilterButton";
import Form from "./components/Form";
import TodoService from "./service/TodoService";

const FILTER_MAP = {
  All: () => true,
  Active: (task) => !task.completed,
  Completed: (task) => task.completed,
};

const FILTER_NAMES = Object.keys(FILTER_MAP);

function App() {
  const [tasks, setTasks] = useState(null);
  const [filter, setFilter] = useState("All");

  function refreshTasks() {
    TodoService.retrieveAllTasks().then((res) => {
      if (res) {
        console.log("Todo items list: ", res.data);
        setTasks(res.data);
      }
    });
  }

  useEffect(() => {
    //onLoad
    if (!tasks) refreshTasks();
  }, [tasks]);

  const filterList = FILTER_NAMES.map((name) => (
    <FilterButton
      key={name}
      name={name}
      isPressed={name === filter}
      setFilter={setFilter}
    />
  ));

  // POST request to api to add new task to repo
  function addTask(name) {
    const newTask = { id: tasks.length, name: name, completed: false, createdDate: new Date() };
    TodoService.createTask(newTask).then((response) => {
      if(response.status === 201){
        refreshTasks();
      }
    });
    // setTasks([...tasks, newTask]);
  }

  // PUT request to api for toggling task completion
  function toggleTaskCompleted(id) {
    tasks.map((task) => {
      // if this task has the same ID as the edited task
      if (id === task.id) {
        // use object spread to make a new object
        // whose `completed` prop has been inverted
        TodoService.updateTask(task.id, {
          id: task.id,
          name: task.name,
          completed: !task.completed,
          createdDate: task.createdDate,
        }).then((response) => {
          if (response.status === 200 || response.status === 204) {
            refreshTasks();
            return { ...task, completed: !task.completed };
          } else return task;
        });
      }
      return task;
    });
  }

  // delete task by id from repo
  function deleteTask(id) {
    TodoService.deleteTask(id).then((response) => {
      if (response.status === 204) {
        refreshTasks();
      }
    });
  }

  // edit name of task by id from repo
  function editTask(id, newName) {
    tasks.map((task) => {
      // if this task has the same ID as the edited task
      if (id === task.id) {
        //
        TodoService.updateTask(task.id, {
          id: task.id,
          name: newName,
          completed: task.completed,
          createdDate: task.createdDate,
        }).then((response) => {
          if (response.status === 200 || response.status === 204) {
            refreshTasks();
            return { ...task, name: newName };
          } else return task;
        });
      }
      return task;
    });
    refreshTasks();
  }
  var taskList;
  var tasksNoun = "";
  var headingText = "";
  if (tasks) {
    taskList = tasks
      .filter(FILTER_MAP[filter])
      .map((task) => (
        <Todo
          id={task.id}
          name={task.name}
          completed={task.completed}
          key={task.id}
          toggleTaskCompleted={toggleTaskCompleted}
          deleteTask={deleteTask}
          editTask={editTask}
        />
      ));
    tasksNoun = taskList.length !== 1 ? "tasks" : "task";
    headingText = `${taskList.length} ${tasksNoun} remaining`;
  }

  return (
    <div className="todoapp stack-large">
      <h1>TodoMatic</h1>
      <Form addTask={addTask} />
      <div className="filters btn-group stack-exception">{filterList}</div>
      <h2 id="list-heading">{headingText}</h2>
      <ul
        className="todo-list stack-large stack-exception"
        aria-labelledby="list-heading"
      >
        {/* check if taskList is not null */}
        {taskList ? taskList : "Loading..."}
      </ul>
    </div>
  );
}

export default App;
