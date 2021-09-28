import axios from "axios";

const TODO_API_URL = "http://localhost:8080/api";

class TodoService {
  retrieveAllTasks() {
    return axios
      .get(`${TODO_API_URL}/tasks`)
      .catch((error) => console.log(error));
  }
  retrieveTask(id) {
    return axios
      .get(`${TODO_API_URL}/tasks/${id}`)
      .catch((error) => console.log(error));
  }
  createTask(task) {
    return axios
      .post(`${TODO_API_URL}/tasks/`, task)
      .catch((error) => console.log(error));
  }
  updateTask(id, task) {
    return axios
      .put(`${TODO_API_URL}/tasks/${id}`, task)
      .catch((error) => console.log(error));
  }
  deleteTask(id) {
    return axios
      .delete(`${TODO_API_URL}/tasks/${id}`)
      .catch((error) => console.log(error));
  }
}

export default new TodoService();
