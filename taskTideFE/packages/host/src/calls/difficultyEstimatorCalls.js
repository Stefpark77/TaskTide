import {estimateDifficultyURL} from "./utils";
import axios from "axios";


export const getEstimatedDifficulty = (text) => {
    try {
        return axios.post(estimateDifficultyURL, text);
    }
    catch (error) {
        console.error(error);
    }
}