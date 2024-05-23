import { taskServicesURL } from "./utils";
import axios from "axios";

export const getAllTasks = async (token) => {
    try {
        const response = await axios.get(taskServicesURL + 'task/',  {
            headers: {
                'Authorization': 'Bearer '+token
            }});

        return response;
    }
    catch (error) {
        console.error(error);
    }
}

export const getTaskByTaskId = async (taskId, token) => {
    try {
        const response = await axios.get(taskServicesURL + 'task/'+`${taskId}`,  {
            headers: {
                'Authorization': 'Bearer '+token
            }});
        console.log(response);
        return response;
    }
    catch (error) {
        console.error(error);
    }
}

export const getTaskByUserId = async (userId, token) => {
    try {
        const response = await axios.get(taskServicesURL + 'task/userId/' + userId,  {
            headers: {
                "Authorization": 'Bearer '+ token
            }});

        return response;
    }
    catch (error) {
        console.error(error);
    }
}
export const deleteTask = (taskId, token) => {
    try {
        const response = axios.delete(taskServicesURL + 'task/?taskId='+taskId,  {
            headers: {
                'Authorization': 'Bearer '+token
            }});

        return response;
    }
    catch (error) {
        console.error(error);
    }
}

export const createTask = (task, token) => {
    try {
        const response = axios.post(taskServicesURL + 'task/', task,  {
            headers: {
                'Authorization': 'Bearer '+token
            }});

        return response;
    }
    catch (error) {
        console.error(error);
    }
}

export const updateTask = (task, token) => {
    try {
        const response = axios.put(taskServicesURL + 'task/', task,  {
            headers: {
                'Authorization': 'Bearer '+ token
            }});

        return response;
    }
    catch (error) {
        console.error(error);
    }
}

export const getTaskDependencyByTaskId = async (taskId, token) => {
    try {
        const response = await axios.get(taskServicesURL + 'task/' + taskId + '/depends-on/',  {
            headers: {
                "Authorization": 'Bearer '+ token
            }});

        return response;
    }
    catch (error) {
        console.error(error);
    }
}

export const getTaskDependencyByDependsOnId = async (dependsOnId, token) => {
    try {
        const response = await axios.get(taskServicesURL + 'task/' + dependsOnId + '/depended-tasks/',  {
            headers: {
                "Authorization": 'Bearer '+ token
            }});

        return response;
    }
    catch (error) {
        console.error(error);
    }
}
export const deleteTaskDependency = (taskId, dependsOnId, token) => {
    try {
        const response = axios.delete(taskServicesURL + 'task-dependency/?taskId='+taskId+'&dependsOnId='+dependsOnId,  {
            headers: {
                'Authorization': 'Bearer '+token
            }});

        return response;
    }
    catch (error) {
        console.error(error);
    }
}

export const createTaskDependency = (taskId, dependsOnId, token) => {
    try {
        const response = axios.post(taskServicesURL + 'task-dependency/?taskId='+taskId+'&dependsOnId='+dependsOnId,  {
            headers: {
                'Authorization': 'Bearer '+token
            }});

        return response;
    }
    catch (error) {
        console.error(error);
    }
}