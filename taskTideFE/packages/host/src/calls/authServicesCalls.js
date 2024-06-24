import {authServicesURL} from "./utils";
import axios from "axios";

export async function login(loginRequest) {
    try {
        return await axios.post(authServicesURL + 'auth/login', loginRequest);
    } catch (error) {
        console.error(error);
    }
}

export const signUp = async (signUpRequest) => {
    try {
        return await axios.post(authServicesURL + 'auth/sign-up', signUpRequest);
    } catch (error) {
        console.error(error);
    }
}