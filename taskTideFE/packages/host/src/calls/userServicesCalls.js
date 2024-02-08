import { userServicesURL } from "./utils";
import axios from "axios";

export const getAllUsers = async () => {
    try {
        const response = await axios.get(userServicesURL + 'user');

        return response;
    }
    catch (error) {
        console.error(error);
    }
}

export const getUserByUserId = async (userId) => {
    try {
        const response = await axios.get(userServicesURL + 'user/'+`${userId}`);

        return response;
    }
    catch (error) {
        console.error(error);
    }
}

export const getUserByUsername = async (username) => {
    try {
        const response = await axios.get(userServicesURL + 'user/username/'+`${username}`);

        return response;
    }
    catch (error) {
        console.error(error);
    }
}
export const deleteUser = (userId) => {
    try {
        const response = axios.delete(userServicesURL + 'user/'+`${userId}`);

        return response;
    }
    catch (error) {
        console.error(error);
    }
}

export const createUser = (user) => {
    try {
        const response = axios.post(userServicesURL + 'user', user);

        return response;
    }
    catch (error) {
        console.error(error);
    }
}

export const updateUser = (user) => {
    try {
        const response = axios.put(userServicesURL + 'user', user);

        return response;
    }
    catch (error) {
        console.error(error);
    }
}