import {createApp} from "vue";
import {createStore} from "vuex";
import * as VueRouter from "vue-router";

import {login, signUp, logout} from "./calls/authServicesCalls";
import {
    getAllEvents,
    getEventsByUserId,
    getEventByEventId,
    updateEvent,
    deleteEvent,
    createEvent
} from "./calls/calendarServicesCalls";
import {
    getAllUsers,
    getUserByUserId,
    getUserByUsername,
    createUser,
    deleteUser,
    updateUser
} from "./calls/userServicesCalls";
import {
    getAllTasks,
    getTaskByTaskId,
    getTaskByUserId,
    createTask,
    deleteTask,
    updateTask,
    getTaskDependencyByTaskId,
    getTaskDependencyByDependsOnId,
    createTaskDependency,
    deleteTaskDependency,
} from "./calls/taskServicesCalls";
import {
    getAllProjects,
    getProjectByProjectId,
    getProjectByUserId,
    createProject,
    deleteProject,
    updateProject
} from "./calls/projectServicesCalls";

import "./index.scss";

import App from "./App.vue";

import LoginPage from "./views/LoginPage.vue";
import MenuPage from "./views/MenuPage.vue";

import {format, parseISO} from 'date-fns';

const routes = [
    {path: "/", component: LoginPage},
    {path: "/tasktide", component: MenuPage},
];

const router = VueRouter.createRouter({
    history: VueRouter.createWebHistory(),
    routes,
});


const checkAuth = async (response) => {
    if (response?.status === 500 || !response) {
        //window.location.href = '/';
    }
}

const store = createStore({
    state() {
        return {
            /*auth services*/
            username: '',
            password: '',
            loginError: '',
            showSignUp: false,
            addFirstName: '',
            addLastName: '',
            addUsername: '',
            addPassword: '',
            addEmail: '',
            token: '',
            /*calendar services*/
            events: [],
            showEvents: false,
            showAddEvent: false,
            showEditEvent: false,
                /*Add Event*/
            addName: '',
            addDescription: '',
            addDate: '',
            addDateTime: '',
            addEndDate: '',
            addEndDateTime: '',
                /*Edit Event*/
            editEventId: '',
            updateName: '',
            updateDescription: '',
            updateDate: '',
            updateDateTime: '',
            updateEndDate: '',
            updateEndDateTime: '',
            /*task services*/
            tasks: [],
            showTasks: false,
            showAddTask: false,
            showEditTask: false,
            showTaskPage: false,
                /*Add/Edit Task*/
            editTaskId: '',
            addDifficulty: '',
            addPriority: '',
            updateDifficulty: '',
            updatePriority: '',
            updateStage: '',
            updateProgress: '',
            /*Task Page*/
            task: '',
            taskDependencies: [],
            taskDependenciesOn: [],
            taskDependenciesIds: [],
            taskDependenciesOnIds: [],
            /*project services*/
            projects: [],
            showProjects: false,
            showAddProject: false,
            showEditProject: false,
            showProjectPage: false,
                /*Add/Edit Project*/
            editProjectId: '',
            addDeadline: '',
            updateDeadline: '',
            /*Project Page*/
        };
    },
    mutations: {
        /*Auth Services*/
        async login(state) {
            try {
                const response = await login({username: state.username, password: state.password});

                window.location.href = '/tasktide';
                if (response.status !== 200 || response.data === '') throw new Error('Failed to login');
                state.loginError = false;
                localStorage.setItem("username", response.data.username)
                localStorage.setItem("email", response.data.email)
                localStorage.setItem("userId", response.data.userId)
                localStorage.setItem("token", response.data.token)
                window.location.href = '/tasktide';
            } catch (error) {
                console.log(error);
                state.loginError = true;
            }
        },
        async signUp(state, user) {
            try {
                const response = await signUp(user);

                if (response.status !== 200) throw new Error('Failed to sign up user');

            } catch (error) {
                console.error(error);
            }

            state.addFirstName = '';
            state.addLastName = '';
            state.addUsername = '';
            state.addPassword = '';
            state.addEmail = '';
            state.showSignUp = false;
        },
        async logout() {
            try {
                const response = await logout();

                localStorage.setItem("userId", '')
                localStorage.setItem("token", '')
                window.location.href = '/';
            } catch (error) {
                console.log(error);
            }
        },
        async prepareMenu(state) {
            state.username = await localStorage.getItem("username");
            state.email = await localStorage.getItem("email");
        },

        /*Calendar Services*/
        async fetchEvents(state) {
            try {
                const response = await getEventsByUserId(localStorage.getItem("userId"), localStorage.getItem("token"));
                state.token = localStorage.getItem("token");
                checkAuth(response);

                if (response.status !== 200) throw new Error('Failed to get events');

                state.events = response.data;
            } catch (error) {
                console.error(error);
            }
        },
        async removeEvent(state, eventId) {
            try {
                const response = await deleteEvent(eventId, localStorage.getItem("token"));

                checkAuth(response);

                if (response.status !== 200) throw new Error('Failed to delete event');

                state.events = state.events.filter(event => event.id !== eventId);
            } catch (error) {
                console.error(error);
            }
        },
        async createEvent(state, event) {
            console.log(event);
            try {
                const dateObject = parseISO(event.date);
                const timeParts = event.startTime.split(':');
                dateObject.setHours(parseInt(timeParts[0], 10));
                dateObject.setMinutes(parseInt(timeParts[1], 10));
                event.date = format(dateObject, "yyyy-MM-dd'T'HH:mm:ss.SSSxxx");
                delete event.startTime;

                const endDateObject = parseISO(event.endDate);
                const endTimeParts = event.endTime.split(':');
                endDateObject.setHours(parseInt(endTimeParts[0], 10));
                endDateObject.setMinutes(parseInt(endTimeParts[1], 10));
                event.endDate = format(endDateObject, "yyyy-MM-dd'T'HH:mm:ss.SSSxxx");
                delete event.endTime;

                event.userId = localStorage.getItem("userId")
                console.log(event);

                const response = await createEvent(event, localStorage.getItem("token"));

                checkAuth(response);

                if (response.status !== 200) throw new Error('Failed to create event');

                state.events.push(response.data);
            } catch (error) {
                console.error(error);
            }

            state.addName = '';
            state.addDescription = '';
            state.addDate = '';
            state.addEndDate = '';
            state.addDateTime = '';
            state.addEndDateTime = '';
        },
        setUpdateEvent(state, event) {
            state.showEditEvent = true;
            state.editEventId = event.id;
            state.updateName = event.name;
            state.updateDescription = event.description;
            state.updateDate = format(parseISO(event.date),"yyyy-MM-dd");
            state.updateDateTime = format(parseISO(event.date),"HH:mm");
            state.updateEndDate =format( parseISO(event.endDate),"yyyy-MM-dd");
            state.updateEndDateTime = format( parseISO(event.endDate),"HH:mm");

        },
        async updateEvent(state, event) {
            try {
                event.id = state.editEventId;
                const dateObject = parseISO(event.date);
                const timeParts = event.startTime.split(':');
                dateObject.setHours(parseInt(timeParts[0], 10));
                dateObject.setMinutes(parseInt(timeParts[1], 10));
                event.date = format(dateObject, "yyyy-MM-dd'T'HH:mm:ss.SSSxxx");
                delete event.startTime;

                const endDateObject = parseISO(event.endDate);
                const endTimeParts = event.endTime.split(':');
                endDateObject.setHours(parseInt(endTimeParts[0], 10));
                endDateObject.setMinutes(parseInt(endTimeParts[1], 10));
                event.endDate = format(endDateObject, "yyyy-MM-dd'T'HH:mm:ss.SSSxxx");
                delete event.endTime;
                event.userId = localStorage.getItem("userId")

                console.log(event);

                const response = await updateEvent(event, localStorage.getItem("token"));

                checkAuth(response);

                if (response.status !== 200) throw new Error('Failed to update event');

                state.events = state.events.map(e => e.id === event.id ? response.data : e);

                state.showEditEvent = false;
            } catch (error) {
                console.error(error);
            }
        },

        /*Task Services*/
        async fetchTasks(state) {
            try {
                const response = await getTaskByUserId(localStorage.getItem("userId"), localStorage.getItem("token"));

                checkAuth(response);

                if (response.status !== 200) throw new Error('Failed to get tasks');

                state.tasks = response.data;
            } catch (error) {
                console.error(error);
            }
        },
        async fetchTaskByTaskId(taskId, state) {
            try {
                const response = await getTaskByTaskId(localStorage.getItem("taskId"), localStorage.getItem("token"));

                checkAuth(response);

                if (response.status !== 200) throw new Error('Failed to get task by taskId');

                return response.data;
            } catch (error) {
                console.error(error);
            }
        },
        async removeTask(state, taskId) {
            try {
                const response = await deleteTask(taskId, localStorage.getItem("token"));

                checkAuth(response);

                if (response.status !== 200) throw new Error('Failed to delete task');

                state.tasks = state.tasks.filter(task => task.id !== taskId);
            } catch (error) {
                console.error(error);
            }
        },
        async createTask(state, task) {
            console.log(task);
            try {
                task.userId = localStorage.getItem("userId")
                console.log(task);

                const response = await createTask(task, localStorage.getItem("token"));

                checkAuth(response);

                if (response.status !== 200) throw new Error('Failed to create task');

                state.tasks.push(response.data);
            } catch (error) {
                console.error(error);
            }

            state.addName = '';
            state.addDescription = '';
            state.addDate = '';
            state.addEndDate = '';
            state.addDateTime = '';
            state.addEndDateTime = '';
        },
        setUpdateTask(state, task) {
            state.showEditTask = true;
            state.editTaskId = task.id;
            state.updateName = task.name;
            state.updateDescription = task.description;
            state.updateDifficulty = task.difficulty;
            state.updatePriority = task.priority;
            state.updateStage = task.stage;
            state.updateProgress = task.progress;

        },
        async updateTask(state, task) {
            try {
                console.log(task);
                task.id = state.editTaskId;

                const response = await updateTask(task, localStorage.getItem("token"));

                checkAuth(response);

                if (response.status !== 200) throw new Error('Failed to update task');

                state.tasks = state.tasks.map(e => e.id === task.id ? response.data : e);

                state.showEditTask = false;
            } catch (error) {
                console.error(error);
            }
        },
        setTaskPage(state, task) {
            state.showTaskPage = true;
            state.task = task;
        },
        /*TaskDependency Services*/
        async fetchTaskDependenciesByTaskId(state, taskId) {
            try {
                const response = await getTaskDependencyByTaskId(taskId, localStorage.getItem("token"));

                checkAuth(response);

                if (response.status !== 200) throw new Error('Failed to get task dependencies');

                state.taskDependencies = response.data;
                console.log(state.taskDependencies);
            } catch (error) {
                console.error(error);
            }
        },
        async fetchTaskDependenciesByDependsOnId(state, dependsOnId) {
            try {
                const response = await getTaskDependencyByDependsOnId(dependsOnId, localStorage.getItem("token"));

                checkAuth(response);

                if (response.status !== 200) throw new Error('Failed to get task dependencies');

                console.log(dependsOnId);
                console.log(response.data);
                state.taskDependenciesOn = response.data;
                console.log(state.taskDependenciesOn);
            } catch (error) {
                console.error(error);
            }
        },
        async removeTaskDependency(state, taskId, dependsOnId) {
            try {
                const response = await deleteTaskDependency(taskId, dependsOnId, localStorage.getItem("token"));

                checkAuth(response);

                if (response.status !== 200) throw new Error('Failed to delete task dependency');

                state.tasks = state.tasks.filter(task => task.id !== taskId);
            } catch (error) {
                console.error(error);
            }
        },
        async createTaskDependency(state, taskId, dependsOnId) {
            try {
                const response = await createTaskDependency(taskId, dependsOnId, localStorage.getItem("token"));

                checkAuth(response);

                if (response.status !== 200) throw new Error('Failed to create task dependency');

                state.tasks.push(response.data);
            } catch (error) {
                console.error(error);
            }
        },

        /*Project Services*/
        async fetchProjects(state) {
            try {
                const response = await getAllProjects(localStorage.getItem("token"));

                checkAuth(response);

                if (response.status !== 200) throw new Error('Failed to get projects');
                state.projects = response.data;
            } catch (error) {
                console.error(error);
            }
        },
        async removeProject(state, projectId) {
            try {
                const response = await deleteProject(projectId, localStorage.getItem("token"));

                checkAuth(response);

                if (response.status !== 200) throw new Error('Failed to delete project');

                state.projects = state.projects.filter(project => project.id !== projectId);
            } catch (error) {
                console.error(error);
            }
        },
        async createProject(state, project) {
            console.log(project);
            try {
                const dateObject = parseISO(project.deadline);
                project.deadline = format(dateObject, "yyyy-MM-dd'T'HH:mm:ss.SSSxxx");
                /*TODO create user project connection
                project.userId = localStorage.getItem("userId")*/

                const response = await createProject(project, localStorage.getItem("token"));

                checkAuth(response);

                if (response.status !== 200) throw new Error('Failed to create project');

                state.projects.push(response.data);
            } catch (error) {
                console.error(error);
            }

            state.addName = '';
            state.addDescription = '';
            state.addDeadline = '';
        },
        setUpdateProject(state, project) {
            state.showEditProject = true;
            state.editProjectId = project.id;
            state.updateName = project.name;
            state.updateDescription = project.description;
            state.updateDeadline = format(parseISO(project.deadline),"yyyy-MM-dd");

        },
        async updateProject(state, project) {
            try {
                project.id = state.editProjectId;
                const dateObject = parseISO(project.deadline);
                project.deadline = format(dateObject, "yyyy-MM-dd'T'HH:mm:ss.SSSxxx");

                console.log(project);

                const response = await updateProject(project, localStorage.getItem("token"));

                checkAuth(response);

                if (response.status !== 200) throw new Error('Failed to update project');

                state.projects = state.projects.map(e => e.id === project.id ? response.data : e);

                state.showEditProject = false;
            } catch (error) {
                console.error(error);
            }
        },


        /*setters*/
        setUsername(state, username) {
            state.username = username;
        },
        setPassword(state, password) {
            state.password = password;
        },
        setShowEvents(state, showEvents) {
            state.showEvents = showEvents;
        },
        setShowTasks(state, showTasks) {
            state.showTasks = showTasks;
        },
        setShowProjects(state, showProjects) {
            state.showProjects = showProjects;
        },
        setShowAddEvent(state, showAddEvent) {
            state.showAddEvent = showAddEvent;
        },
        setShowAddTask(state, showAddTask) {
            state.showAddTask = showAddTask;
        },
        setShowAddProject(state, showAddProject) {
            state.showAddProject = showAddProject;
        },
        setShowSignUp(state, showSignUp) {
            state.showSignUp = showSignUp;
        },
        setShowEditEvent(state, showEditEvent) {
            state.showEditEvent = showEditEvent;
        },

        setShowEditTask(state, showEditTask) {
            state.showEditTask = showEditTask;
        },
        setShowEditProject(state, showEditProject) {
            state.showEditProject = showEditProject;
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
        setAddDateTime(state, addDateTime) {
            state.addDateTime = addDateTime;
        },
        setAddEndDate(state, addEndDate) {
            state.addEndDate = addEndDate;
        },
        setAddEndDateTime(state, addEndDateTime) {
            state.addEndDateTime = addEndDateTime;
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
        setUpdateDateTime(state, updateDateTime) {
            state.updateDateTime = updateDateTime;
        },
        setUpdateEndDate(state, updateEndDate) {
            state.updateEndDate = updateEndDate;
        },
        setUpdateEndDateTime(state, updateEndDateTime) {
            state.updateEndDateTime = updateEndDateTime;
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
        setAddDeadline(state, addDeadline) {
            state.addDeadline = addDeadline;
        },
        setUpdateDeadline(state, updateDeadline) {
            state.updateDeadline = updateDeadline;
        },
        setAddDifficulty(state, addDifficulty) {
            state.addDifficulty = addDifficulty;
        },
        setAddPriority(state, addPriority) {
            state.addPriority = addPriority;
        },
        setUpdateDifficulty(state, updateDifficulty) {
            state.updateDifficulty = updateDifficulty;
        },
        setUpdatePriority(state, updatePriority) {
            state.updatePriority = updatePriority;
        },

        setUpdateProgress(state, updateProgress) {
            state.updateProgress = updateProgress;
        },
        setUpdateStage(state, updateStage) {
            state.updateStage = updateStage;
        },
        setShowProjectPage(state, showProjectPage) {
            state.showProjectPage = showProjectPage;
        },
        setShowTaskPage(state, showTaskPage) {
            state.showTaskPage = showTaskPage;
        },
    },
});

createApp(App).use(router).use(store).mount("#app");
