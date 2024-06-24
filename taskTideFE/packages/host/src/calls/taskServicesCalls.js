import {taskServicesURL} from "./utils";
import axios from "axios";

export const getAllTasks = async (token) => {
    try {
        return await axios.get(taskServicesURL + 'task/', {
            headers: {
                'Authorization': 'Bearer ' + token
            }
        });
    } catch (error) {
        console.error(error);
    }
}

export const getTaskByTaskId = async (taskId, token) => {
    try {
        return await axios.get(taskServicesURL + 'task/' + `${taskId}`, {
            headers: {
                'Authorization': 'Bearer ' + token
            }
        });
    } catch (error) {
        console.error(error);
    }
}

export const getTaskByUserId = async (userId, projectIds, token) => {
    try {
        return await axios.post(taskServicesURL + 'task/userId/' + userId, projectIds, {
            headers: {
                "Authorization": 'Bearer ' + token
            }
        });
    } catch (error) {
        console.error(error);
    }
}
export const getTaskByProjectId = async (projectId, token) => {
    try {
        return await axios.get(taskServicesURL + 'task/projectId/' + projectId, {
            headers: {
                "Authorization": 'Bearer ' + token
            }
        });
    } catch (error) {
        console.error(error);
    }
}
export const deleteTask = (taskId, token) => {
    try {
        return axios.delete(taskServicesURL + 'task/?taskId=' + taskId, {
            headers: {
                'Authorization': 'Bearer ' + token
            }
        });
    } catch (error) {
        console.error(error);
    }
}

export const createTask = (task, token) => {
    try {
        return axios.post(taskServicesURL + 'task/', task, {
            headers: {
                'Authorization': 'Bearer ' + token
            }
        });
    } catch (error) {
        console.error(error);
    }
}

export const updateTask = (task, token) => {
    try {
        return axios.put(taskServicesURL + 'task/', task, {
            headers: {
                'Authorization': 'Bearer ' + token
            }
        });
    } catch (error) {
        console.error(error);
    }
}

export const getTaskDependencyByTaskId = async (taskId, token) => {
    try {
        return await axios.get(taskServicesURL + 'task/' + taskId + '/depends-on/', {
            headers: {
                "Authorization": 'Bearer ' + token
            }
        });
    } catch (error) {
        console.error(error);
    }
}

export const getTaskDependencyByDependsOnId = async (dependsOnId, token) => {
    try {
        return await axios.get(taskServicesURL + 'task/' + dependsOnId + '/depended-tasks/', {
            headers: {
                "Authorization": 'Bearer ' + token
            }
        });
    } catch (error) {
        console.error(error);
    }
}
export const deleteTaskDependency = (taskId, dependsOnId, token) => {
    try {
        return axios.delete(taskServicesURL + 'task-dependency/?taskId=' + taskId + '&dependsOnId=' + dependsOnId, {
            headers: {
                'Authorization': 'Bearer ' + token
            }
        });
    } catch (error) {
        console.error(error);
    }
}

export const createTaskDependency = (dependency, token) => {
    try {
        return axios.post(taskServicesURL + 'task-dependency/', dependency, {
            headers: {
                'Authorization': 'Bearer ' + token
            }
        });
    } catch (error) {
        console.error(error);
    }
}