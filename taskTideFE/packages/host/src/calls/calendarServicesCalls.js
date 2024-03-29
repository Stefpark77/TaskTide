import { calendarServicesURL } from "./utils";
import axios from "axios";

export const getAllEvents = async (token) => {
    try {
        const response = await axios.get(calendarServicesURL + 'event/',  {
            headers: {
                'Authorization': 'Bearer '+token
            }});

        return response;
    }
    catch (error) {
        console.error(error);
    }
}

export const getEventByEventId = async (eventId, token) => {
    try {
        const response = await axios.get(calendarServicesURL + 'event/'+`${eventId}`,  {
            headers: {
                'Authorization': 'Bearer '+token
            }});

        return response;
    }
    catch (error) {
        console.error(error);
    }
}

export const getEventByUserId = async (userId, token) => {
    try {
        const response = await axios.get(calendarServicesURL + 'event/userId/' + userId,  {
            headers: {
                "Authorization": 'Bearer '+ token
            }});

        return response;
    }
    catch (error) {
        console.error(error);
    }
}
export const deleteEvent = (eventId, token) => {
    try {
        const response = axios.delete(calendarServicesURL + 'event/?eventId='+eventId,  {
            headers: {
                'Authorization': 'Bearer '+token
            }});

        return response;
    }
    catch (error) {
        console.error(error);
    }
}

export const createEvent = (event, token) => {
    try {
        const response = axios.post(calendarServicesURL + 'event/', event,  {
            headers: {
                'Authorization': 'Bearer '+token
            }});

        return response;
    }
    catch (error) {
        console.error(error);
    }
}

export const updateEvent = (event, token) => {
    try {
        const response = axios.put(calendarServicesURL + 'event/', event,  {
            headers: {
                'Authorization': 'Bearer '+ token
            }});

        return response;
    }
    catch (error) {
        console.error(error);
    }
}