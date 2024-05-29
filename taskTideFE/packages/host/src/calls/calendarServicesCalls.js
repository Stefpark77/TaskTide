import {calendarServicesURL} from "./utils";
import axios from "axios";

export const getAllEvents = async (token) => {
    try {
        return await axios.get(calendarServicesURL + 'event/', {
            headers: {
                'Authorization': 'Bearer ' + token
            }
        });
    } catch (error) {
        console.error(error);
    }
}

export const getEventByEventId = async (eventId, token) => {
    try {
        return await axios.get(calendarServicesURL + 'event/' + `${eventId}`, {
            headers: {
                'Authorization': 'Bearer ' + token
            }
        });
    } catch (error) {
        console.error(error);
    }
}

export const getEventsByUserId = async (userId, token) => {
    try {
        return await axios.get(calendarServicesURL + 'event/userId/' + userId, {
            headers: {
                "Authorization": 'Bearer ' + token
            }
        });
    } catch (error) {
        console.error(error);
    }
}
export const deleteEvent = (eventId, token) => {
    try {
        return axios.delete(calendarServicesURL + 'event/?eventId=' + eventId, {
            headers: {
                'Authorization': 'Bearer ' + token
            }
        });
    } catch (error) {
        console.error(error);
    }
}

export const createEvent = (event, token) => {
    try {
        return axios.post(calendarServicesURL + 'event/', event, {
            headers: {
                'Authorization': 'Bearer ' + token
            }
        });
    } catch (error) {
        console.error(error);
    }
}

export const updateEvent = (event, token) => {
    try {
        return axios.put(calendarServicesURL + 'event/', event, {
            headers: {
                'Authorization': 'Bearer ' + token
            }
        });
    } catch (error) {
        console.error(error);
    }
}