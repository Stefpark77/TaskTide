import {projectServicesURL} from "./utils";
import axios from "axios";

export const getAllProjects = async (token) => {
    try {
        return await axios.get(projectServicesURL + 'project/', {
            headers: {
                'Authorization': 'Bearer ' + token
            }
        });
    } catch (error) {
        console.error(error);
    }
}

export const getProjectByProjectId = async (projectId, token) => {
    try {
        return await axios.get(projectServicesURL + 'project/' + `${projectId}`, {
            headers: {
                'Authorization': 'Bearer ' + token
            }
        });
    } catch (error) {
        console.error(error);
    }
}

export const getProjectByUserId = async (userId, token) => {
    try {
        return await axios.get(projectServicesURL + 'project-user/' + userId, {
            headers: {
                "Authorization": 'Bearer ' + token
            }
        });
    } catch (error) {
        console.error(error);
    }
}
export const deleteProject = (projectId, token) => {
    try {
        return axios.delete(projectServicesURL + 'project/?projectId=' + projectId, {
            headers: {
                'Authorization': 'Bearer ' + token
            }
        });
    } catch (error) {
        console.error(error);
    }
}

export const createProject = (project, token) => {
    try {
        return axios.post(projectServicesURL + 'project/', project, {
            headers: {
                'Authorization': 'Bearer ' + token
            }
        });
    } catch (error) {
        console.error(error);
    }
}

export const updateProject = (project, token) => {
    try {
        return axios.put(projectServicesURL + 'project/', project, {
            headers: {
                'Authorization': 'Bearer ' + token
            }
        });
    } catch (error) {
        console.error(error);
    }
}

export const deleteProjectUser = (projectId, userId, token) => {
    try {
        return axios.delete(projectServicesURL + 'project-user/?projectId=' + projectId + '&userId=' + userId, {
            headers: {
                'Authorization': 'Bearer ' + token
            }
        });
    } catch (error) {
        console.error(error);
    }
}

export const createProjectUser = (projectUser, token) => {
    try {
        return axios.post(projectServicesURL + 'project-user/', projectUser, {
            headers: {
                'Authorization': 'Bearer ' + token
            }
        });
    } catch (error) {
        console.error(error);
    }
}