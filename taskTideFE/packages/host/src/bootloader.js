import { createApp } from "vue";
import { createStore } from "vuex";
import * as VueRouter from "vue-router";

import { login, signUp, logout} from "./calls/authServicesCalls";
import { getAllEvents, getEventByUserId, getEventByEventId, updateEvent, deleteEvent, createEvent } from "./calls/calendarServicesCalls";
import { getAllUsers, getUserByUserId, getUserByUsername, createUser, deleteUser, updateUser } from "./calls/userServicesCalls";

import "./index.scss";

import App from "./App.vue";

import LoginPage from "./views/LoginPage.vue";
import EventPage from "./views/EventPage.vue";

import { format, parseISO } from 'date-fns';

const routes = [
    { path: "/", component: LoginPage },
    { path: "/event", component: EventPage },
];

const router = VueRouter.createRouter({
    history: VueRouter.createWebHistory(),
    routes,
});


const checkAuth = async (response) => {
    if (response?.status === 500 || !response) {
        window.location.href = '/';
    }
}

const store = createStore({
    state() {
        return {
            username: '',
            password: '',
            loginError: '',
            events: [],
            showAddEvent: false,
            showSignUp: false,
            showEditEvent: false,
            editEventId: '',
            addName: '',
            addDescription: '',
            addDate: '',
            updateName: '',
            updateDescription: '',
            updateDate: '',
            addFirstName: '',
            addLastName: '',
            addUsername: '',
            addPassword: '',
            addEmail: '',
        };
    },
    mutations: {
        setUsername(state, username) {
            state.username = username;
        },
        setPassword(state, password) {
            state.password = password;
        },
        setShowAddEvent(state, showAddEvent) {
            state.showAddEvent = showAddEvent;
        },
        setShowSignUp(state, showSignUp) {
            state.showSignUp = showSignUp;
        },
        setShowEditEvent(state, showEditEvent) {
            state.showEditEvent = showEditEvent;
        },
        setAddName(state, addName) {
            state.addName = addName;
        },
        setAddDescription(state, addDescription) {
            state.addDescription = addDescription;
        },
        setAddDate(state, addDate) {
            state.addDate = addDate;
        },
        setUpdateName(state, updateName) {
            state.updateName = updateName;
        },
        setUpdateDescription(state, updateDescription) {
            state.updateDescription = updateDescription;
        },
        setUpdateDate(state, updateDate) {
            state.updateDate = updateDate;
        },
        setAddFirstName(state, addFirstName) {
            state.addFirstName = addFirstName;
        },
        setAddLastName(state, addLastName) {
            state.addLastName = addLastName;
        },
        setAddUsername(state, addUsername) {
            state.addUsername = addUsername;
        },
        setAddPassword(state, addPassword) {
            state.addPassword = addPassword;
        },
        setAddEmail(state, addEmail) {
            state.addEmail = addEmail;
        },
        async login(state) {
            try {
                const response = await login({username:state.username, password:state.password});

                if (response.status !== 200 || response.data === '') throw new Error('Failed to login');
                state.loginError = false;
                localStorage.setItem("userId", response.data.userId)
                localStorage.setItem("token", response.data.token)
                window.location.href = '/event';
            }
            catch (error) {
                console.log(error);
                state.loginError = true;
            }
        },
        async logout() {
            try {
                const response = await logout();

                localStorage.setItem("userId", '')
                localStorage.setItem("token", '')
                window.location.href = '/';
            }
            catch (error) {
                console.log(error);
            }
        },
        async fetchEvents(state) {
            try {
                const response = await getEventByUserId(localStorage.getItem("userId"), localStorage.getItem("token"));

                checkAuth(response);

                if (response.status !== 200) throw new Error('Failed to get events');

                state.events = response.data;
            }
            catch (error) {
                console.error(error);
            }
        },
        async removeEvent(state, eventId) {
            try {
                const response = await deleteEvent(eventId, localStorage.getItem("token"));

                checkAuth(response);

                if (response.status !== 200) throw new Error('Failed to delete event');

                state.events = state.events.filter(event => event.id !== eventId);
            }
            catch (error) {
                console.error(error);
            }
        },
        async createEvent(state, event) {
            console.log(event);
            try {
                const dateObject = parseISO(event.date);
                event.date = format(dateObject, "yyyy-MM-dd'T'HH:mm:ss.SSSxxx");
                event.userId = localStorage.getItem("userId")
                console.log(event);

                const response = await createEvent(event, localStorage.getItem("token"));

                checkAuth(response);

                if (response.status !== 200) throw new Error('Failed to create event');

                state.events.push(response.data);
            }
            catch (error) {
                console.error(error);
            }

            state.addName = '';
            state.addDescription = '';
            state.addDate = '';
        },


        async signUp(state, user) {
            try {
                const response = await signUp(user);

                if (response.status !== 200) throw new Error('Failed to sign up user');

            }
            catch (error) {
                console.error(error);
            }

            state.addFirstName = '';
            state.addLastName = '';
            state.addUsername = '';
            state.addPassword = '';
            state.addEmail = '';
            state.showSignUp = false;
        },

        setUpdateEvent(state, id) {
            state.showEditEvent = true;
            state.editEventId = id;
        },

        async updateEvent(state, event) {
            try {
                event.id = state.editEventId;
                const dateObject = parseISO(event.date);
                event.date = format(dateObject, "yyyy-MM-dd'T'HH:mm:ss.SSSxxx");
                event.userId = localStorage.getItem("userId")
                console.log(event);

                const response = await updateEvent(event, localStorage.getItem("token"));

                checkAuth(response);

                if (response.status !== 200) throw new Error('Failed to update event');

                state.events = state.events.map(e => e.id === event.id ? response.data : e);

                state.showEditEvent = false;
            }
            catch (error) {
                console.error(error);
            }
        },
    },
});

createApp(App).use(router).use(store).mount("#app");
