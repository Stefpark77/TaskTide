import {userServicesURL} from "./utils";
import axios from "axios";

export const getAllUsers = async () => {
    try {
        return await axios.get(userServicesURL + 'user');
    }
    catch (error) {
        console.error(error);
    }
}

export const getUserByUserId = async (userId, token) => {
    try {
        return await axios.get(userServicesURL + 'user/public/' + `${userId}`, {
            headers: {
                'Authorization': 'Bearer ' + token
            }
        });
    }
    catch (error) {
        console.error(error);
    }
}

export const getUserByUsername = async (username) => {
    try {
        return await axios.get(userServicesURL + 'user/public/username' + `${username}`);
    }
    catch (error) {
        console.error(error);
    }
}
export const getUserByProjectId = async (projectId, token) => {
    try {
        return await axios.get(userServicesURL + 'user/public/projectId/' + `${projectId}`, {
            headers: {
                'Authorization': 'Bearer ' + token
            }
        });
    }
    catch (error) {
        console.error(error);
    }
}
export const deleteUser = (userId) => {
    try {
        return axios.delete(userServicesURL + 'user/' + `${userId}`);
    }
    catch (error) {
        console.error(error);
    }
}

export const createUser = (user) => {
    try {
        return axios.post(userServicesURL + 'user', user);
    }
    catch (error) {
        console.error(error);
    }
}

export const updateUser = (user) => {
    try {
        return axios.put(userServicesURL + 'user', user);
    }
    catch (error) {
        console.error(error);
    }
}